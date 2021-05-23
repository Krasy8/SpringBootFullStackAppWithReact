package com.krasy8.fullStackApp.student;

import com.krasy8.fullStackApp.spTracking.ReqCallBack;
import com.snowplowanalytics.snowplow.tracker.DevicePlatform;
import com.snowplowanalytics.snowplow.tracker.Tracker;
import com.snowplowanalytics.snowplow.tracker.emitter.BatchEmitter;
import com.snowplowanalytics.snowplow.tracker.emitter.Emitter;
import com.snowplowanalytics.snowplow.tracker.events.PageView;
import com.snowplowanalytics.snowplow.tracker.events.Timing;
import com.snowplowanalytics.snowplow.tracker.events.Unstructured;
import com.snowplowanalytics.snowplow.tracker.payload.SelfDescribingJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(path = "{studentId}/courses")
    public List<StudentCourse> getAllCoursesForStudent(@PathVariable("studentId") UUID studentId) {
        return studentService.getAllStudentCourses(studentId);
    }

    @PostMapping
    public void addNewStudent(@RequestBody @Valid Student student) {
        studentService.addNewStudent(student);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") UUID studentId,
                              @RequestBody Student student) {
        studentService.updateStudent(studentId, student);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") UUID studentId) {

        trackDeletedAccount(studentId);

        studentService.deleteStudent(studentId);
    }

    private void trackDeletedAccount(UUID studentId) {

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

        Student studentToDelete = studentService.findStudent(studentId);

        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("userFirstName", studentToDelete.getFirstName());
        eventMap.put("userLastName", studentToDelete.getLastName());
        eventMap.put("userEmail", studentToDelete.getEmail());
        eventMap.put("leavingDate", LocalDate.now().toString());

        Unstructured unstructured = Unstructured.builder()
                .eventData(new SelfDescribingJson(
                        "iglu:com.snowplowanalytics.iglu/anything-a/jsonschema/1-0-0",
                        eventMap
                ))
                .build();

        tracker.track(unstructured);

        emitter.close();
    }
}
