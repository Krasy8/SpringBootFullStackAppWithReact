package com.krasy8.fullStackApp.spTracking;

import com.snowplowanalytics.snowplow.tracker.emitter.RequestCallback;
import com.snowplowanalytics.snowplow.tracker.events.Event;

import java.util.List;

public class ReqCallBack implements RequestCallback {

    @Override
    public void onSuccess(int successCount) {
        System.out.println("Success, successCount: " + successCount);
    }

    @Override
    public void onFailure(int successCount, List<Event> failedEvents) {
        System.out.println("Failure, successCount: " + successCount + "\nfailedEvent:\n" + failedEvents.toString());
    }
}
