package com.krasy8.fullStackApp.student;

import com.krasy8.fullStackApp.EmailValidator;
import com.krasy8.fullStackApp.exception.ApiRequestException;
import com.krasy8.fullStackApp.spTracking.ReqCallBack;
import com.snowplowanalytics.snowplow.tracker.DevicePlatform;
import com.snowplowanalytics.snowplow.tracker.Tracker;
import com.snowplowanalytics.snowplow.tracker.emitter.BatchEmitter;
import com.snowplowanalytics.snowplow.tracker.events.Unstructured;
import com.snowplowanalytics.snowplow.tracker.payload.SelfDescribingJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class StudentService {

    private final StudentDataAccessService studentDataAccessService;
    private final EmailValidator emailValidator;

    @Autowired
    public StudentService(StudentDataAccessService studentDataAccessService, EmailValidator emailValidator) {
        this.studentDataAccessService = studentDataAccessService;
        this.emailValidator = emailValidator;
    }

    public List<Student> getAllStudents() {
        return studentDataAccessService.selectAllStudents();
    }

    public Student findStudent(UUID studentId) {
        return studentDataAccessService.findStudentById(studentId);
    }

    void addNewStudent(Student student) {
        addNewStudent(null, student);
    }

    public void addNewStudent(UUID studentId, Student student) {
        UUID newStudentId = Optional.ofNullable(studentId).orElse(UUID.randomUUID());

        if (!emailValidator.test(student.getEmail())) {
            throw new ApiRequestException(student.getEmail() + " is not a valid email address");
        }
        // TODO: Verify that email is not taken

        if (studentDataAccessService.isEmailTaken(student.getEmail())) {
            throw new ApiRequestException(student.getEmail() + " is taken");
        }

        studentDataAccessService.insertStudent(newStudentId, student);
    }

    public List<StudentCourse> getAllStudentCourses(UUID studentId) {
        return studentDataAccessService.SelectAllCoursesByStudentId(studentId);
    }


    public void updateStudent(UUID studentId, Student student) {
        Optional.ofNullable(student.getEmail())
                .ifPresent(email -> {
                    boolean taken = studentDataAccessService.selectExistsEmail(studentId, email);
                    if (!taken) {
                        studentDataAccessService.updateEmail(studentId, email);
                    } else {
                        throw new IllegalStateException("Email already in use: " + student.getEmail());
                    }
                });

        Optional.ofNullable(student.getFirstName())
                .filter(firstName -> !StringUtils.isEmpty(firstName))
                .map(StringUtils::capitalize)
                .ifPresent(firstName -> studentDataAccessService.updateFirstName(studentId, firstName));

        Optional.ofNullable(student.getLastName())
                .filter(lastName -> !StringUtils.isEmpty(lastName))
                .map(StringUtils::capitalize)
                .ifPresent(lastName -> studentDataAccessService.updateLastName(studentId, lastName));
    }

    void deleteStudent(UUID studentId) {

        // prepare the event to track
        Unstructured deletedStudentEvent = buildDeletedStudentEvent(studentId);

        // delete the student
        studentDataAccessService.deleteStudentById(studentId);

        //send the event
        sendDeletedStudentEvent(deletedStudentEvent);

    }

    private Unstructured buildDeletedStudentEvent(UUID studentId) {

        Student studentToDelete = findStudent(studentId);

        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("userFirstName", studentToDelete.getFirstName());
        eventMap.put("userLastName", studentToDelete.getLastName());
        eventMap.put("userEmail", studentToDelete.getEmail());
        eventMap.put("leavingDate", LocalDate.now().toString());
        eventMap.put("leavingTime", LocalTime.now().toString());

        return Unstructured.builder()
                .eventData(new SelfDescribingJson(
                        "iglu:com.snowplowanalytics.iglu/anything-a/jsonschema/1-0-0",
                        eventMap
                ))
                .build();
    }

    private void sendDeletedStudentEvent(Unstructured deletedStudentEvent) {

        String collectorEndpoint = "http://0.0.0.0:9090";
        String appId = "sp-java-tracker-backend";
        String namespace = "demo";

        BatchEmitter emitter = BatchEmitter.builder()
                .url(collectorEndpoint)
                .requestCallback(new ReqCallBack())
                .bufferSize(5)
                .build();

        final Tracker tracker = new Tracker.TrackerBuilder(emitter, namespace, appId)
                .base64(true)
                .platform(DevicePlatform.ServerSideApp)
                .build();

        tracker.track(deletedStudentEvent);

        emitter.close();
    }
}
