package com.example.sanat.notificationexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID = 101;
    public static final String TXT_REPLY = "Text Reply";

    Button btn_N_ReplyAction, btn_N_ProgressBar, btn_N_ExpandableImg, btn_N_ExpandableTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_N_ReplyAction = (Button) findViewById(R.id.btn_N_ReplyAction);

        btn_N_ReplyAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNotificationWithReplyAction();
            }
        });


        btn_N_ProgressBar = (Button) findViewById(R.id.btn_N_ProgressBar);
        btn_N_ProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNotificationWithProgressBar();
            }
        });

        btn_N_ExpandableImg = (Button) findViewById(R.id.btn_N_ExpandableImg);
        btn_N_ExpandableImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNotificationWithExpandableImage();
            }
        });

        btn_N_ExpandableTxt = (Button) findViewById(R.id.btn_N_ExpandableTxt);
        btn_N_ExpandableTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendNotificationWithExpandableText();
            }
        });
    }

    public void SendNotification(View view) {

        // this code will work bellow android version 8.0
        // For android version 8.0 and above have to create NotificationChannel

        Intent landingIntent = new Intent(MainActivity.this, LandingActivity.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent landingPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent YesIntent = new Intent(MainActivity.this, YesActivity.class);
        YesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent YesPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, YesIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent NoIntent = new Intent(MainActivity.this, NoActivity.class);
        NoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent NoPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, NoIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentIntent(landingPendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_sms_nofitication);
        mBuilder.setContentTitle("My notification");
        mBuilder.setContentText("Hello World!");
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        mBuilder.setAutoCancel(true);

        mBuilder.addAction(R.drawable.ic_yes, "Yes", YesPendingIntent);
        mBuilder.addAction(R.drawable.ic_close, "No", NoPendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());;

    }

    public void SendNotificationWithReplyAction(){


        Intent landingIntent = new Intent(MainActivity.this, LandingActivity.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent landingPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentIntent(landingPendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_sms_nofitication);
        mBuilder.setContentTitle("My notification");
        mBuilder.setContentText("Hello World!");
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(landingPendingIntent);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            RemoteInput remoteInput = new RemoteInput.Builder(TXT_REPLY).setLabel("Test Reply").build();
            Intent reply_intent = new Intent(this, RemoteReceiverActivity.class);
            reply_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent replyPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, reply_intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Action action = new android.support.v4.app.NotificationCompat.Action.Builder(R.drawable.ic_sms_nofitication, "Reply", replyPendingIntent).addRemoteInput(remoteInput).build();
            mBuilder.addAction(action);
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());


    }



    private void SendNotificationWithProgressBar() {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_file_download);
        mBuilder.setContentTitle("Image Download");
        mBuilder.setContentText("Downloading");
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        mBuilder.setAutoCancel(true);

        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);

        final int MaxProgress = 100;
        int CurrentProgress = 0;
        mBuilder.setProgress(MaxProgress, CurrentProgress, false);

        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());

        Thread thread  = new Thread()
        {
            @Override
            public void run() {
                int count = 0;

                try {
                    while (count <= 100){
                        count = count + 10;
                        sleep(1000);

                        mBuilder.setProgress(MaxProgress, count, false);
                        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());
                    }

                    mBuilder.setContentText("Download Completed");
                    mBuilder.setProgress(0, 0, false);// remove the progress bar
                    notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());

                }catch (InterruptedException e){

                }

            }
        };

        thread.start();

    }


    private void SendNotificationWithExpandableImage() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_sms_nofitication);
        mBuilder.setContentTitle("Image Expandable Notification");
        mBuilder.setContentText("Expandable Notification");
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sanat);
        mBuilder.setLargeIcon(bitmap);
        mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());

    }

    public void SendNotificationWithExpandableText()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_sms_nofitication);
        mBuilder.setContentTitle("Image Expandable Notification");
        mBuilder.setContentText("Expandable Notification Text");
        mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.notification_text)));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        notificationManagerCompat.notify(NOTIFICATION_ID, mBuilder.build());
    }


}
