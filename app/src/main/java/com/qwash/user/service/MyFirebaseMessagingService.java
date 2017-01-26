package com.qwash.user.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by binderbyte on 11/01/17.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        if (remoteMessage.getData().size() > 0) {
            EventBus.getDefault().post(new MessageFireBase(remoteMessage.getData()));
        }


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
        }
    }


}
