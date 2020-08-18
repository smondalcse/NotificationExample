package com.example.sanat.notificationexample;

import android.app.NotificationManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RemoteReceiverActivity extends AppCompatActivity {

    TextView txt_reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_receiver);

        txt_reply = (TextView) findViewById(R.id.txt_reply);

        Bundle reply_text = RemoteInput.getResultsFromIntent(getIntent());

        if(reply_text != null)
        {
            String text = reply_text.getCharSequence(MainActivity.TXT_REPLY).toString();
            txt_reply.setText(text);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(MainActivity.NOTIFICATION_ID);

    }
}
