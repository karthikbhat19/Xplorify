package com.example.xplorify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

public class OTP extends AppCompatActivity implements View.OnClickListener{

    AlertDialog.Builder builder;
    EditText no;
    Button sub;

    int otp;

    int tries = 3;

    HashMap ticket;
    String ngrokAddr;
    String email;

    private NotificationManagerCompat notificationManagerCompat;

    private static final String CHANNEL_ID = "OTP";

    @Override
    public void onBackPressed() {
        builder=new AlertDialog.Builder(OTP.this);
        builder.setTitle("Cancel?");
        builder.setMessage("You have Pressed Back! Are you sure you want to cancel booking?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent log = new Intent(OTP.this,Search.class);
                Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();

                log.putExtra("token", ngrokAddr);
                log.putExtra("mail", email);

                startActivity(log,Anims);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog ad=builder.create();
        ad.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        email = getIntent().getExtras().getString("mail");
        ngrokAddr = getIntent().getExtras().getString("token");
        ticket = (HashMap<String,String>)getIntent().getSerializableExtra("Hashmap");

        Random random = new Random();
        otp = random.nextInt(8999)+1000;
        createNotificationChannel();

        no=findViewById(R.id.no);
        sub=findViewById(R.id.sub);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        sendOTP();

        sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int temp;
        if(no.getText().toString().equals(""))
            temp = 0;
        else
            temp = Integer.parseInt(no.getText().toString());
        if (otp == temp) {

            Intent a = new Intent(OTP.this, Booked.class);
            Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.come_into_left, R.anim.go_out_left).toBundle();

            a.putExtra("mail",email);
            a.putExtra("token",ngrokAddr);
            a.putExtra("Hashmap",ticket);

            startActivity(a, Anims);
            finish();

        }else{

            if(--tries == 0){ failedtrans(); }
            else { badtrans(); }

        }

    }

    public void sendOTP(){
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("OTP")
                .setContentText(Integer.toString(otp))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"OTP", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setDescription("This is otp");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public void failedtrans(){
        builder=new AlertDialog.Builder(OTP.this);
        builder.setTitle("Failed");
        builder.setMessage("Failed to enter correct otp thrice");
        builder.setNeutralButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent log = new Intent(OTP.this,Search.class);
                Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();

                log.putExtra("token", ngrokAddr);
                log.putExtra("mail", email);

                startActivity(log,Anims);
                finish();
            }
        });
        AlertDialog ad=builder.create();
        ad.show();
    }

    public void badtrans(){
        builder=new AlertDialog.Builder(OTP.this);
        builder.setTitle("Check OTP");
        builder.setMessage(tries + " tries left");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog ad=builder.create();
        ad.show();
    }
}
