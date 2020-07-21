package com.example.xplorify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xplorify.classes.HttpParse;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public class Payment extends AppCompatActivity implements View.OnClickListener{

    AlertDialog.Builder builder;
    EditText mob,cvv,doe,cno,name;
    Button otp;

    String ngrokAddr, email;
    String httpurl, finalresult;

    HttpParse httpParse = new HttpParse();

    HashMap<String,String> ticket;
    HashMap<String,String> card = new HashMap<>();

    ProgressDialog progressDialog;

    final Calendar myCal=java.util.Calendar.getInstance();

    private void updateLabel () {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf= new SimpleDateFormat(myFormat, Locale.US);

        doe.setText(sdf.format(myCal.getTime()));
    }

    @Override
    public void onBackPressed() {
        builder=new AlertDialog.Builder(Payment.this);
        builder.setTitle("Cancel?");
        builder.setMessage("You have Pressed Back! Are you sure you want to cancel booking?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent log = new Intent(Payment.this,Search.class);
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
        setContentView(R.layout.activity_payment);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        Bundle bundle = getIntent().getExtras();

        ngrokAddr = bundle.getString("token");
        email = bundle.getString("mail");
        httpurl = "https://"+ngrokAddr+".ngrok.io/trav/confirmMob.php";

        ticket = (HashMap<String,String>) getIntent().getSerializableExtra("Hashmap");


        doe=findViewById(R.id.doe);
        otp=findViewById(R.id.otp);
        mob=findViewById(R.id.mob);
        cvv=findViewById(R.id.cvv);
        cno=findViewById(R.id.cno);
        name=findViewById(R.id.name);

        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCal.set(Calendar.YEAR,year);
                myCal.set(Calendar.MONTH,month);
                myCal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };

        doe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Payment.this,date,myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        otp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        boolean Cname = name.getText().toString().equals("");
        boolean Ccvv = cvv.getText().toString().equals("");
        boolean Cdoe = doe.getText().toString().equals("");
        boolean Ccno = cno.getText().toString().equals("");
        boolean Cmob = mob.getText().toString().equals("");


        if(!Cname && !Ccvv && !Cdoe && !Ccno && !Cmob) {

            if(checkmob()) {

                card.put("mob",mob.getText().toString());
                card.put("mail",email);

                PayMethod();

            }

        }
    }

    boolean checkmob(){

        if(Pattern.matches("\\d{10}",mob.getText().toString()))
            return true;
        else
            raise_mobile_alert();
        return false;

    }

    void raise_mobile_alert(){

        AlertDialog.Builder obj = new AlertDialog.Builder(Payment.this);
        obj.setTitle("OI VEY!");
        obj.setMessage("Please Enter a 10 digit phone number");
        obj.setNeutralButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = obj.create();
        alertDialog.show();

    }

    public void PayMethod(){

        class PayClass extends AsyncTask<Void, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Payment.this,"Checking Details",null,true,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressDialog.dismiss();

                if(s.equals("Success")){

                    Intent b=new Intent(Payment.this,OTP.class);
                    Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_left,R.anim.go_out_left).toBundle();

                    b.putExtra("mail",email);
                    b.putExtra("token",ngrokAddr);
                    b.putExtra("Hashmap",ticket);

                    startActivity(b, Anims);
                    finish();

                }else{

                    nomob();

                }

            }

            @Override
            protected String doInBackground(Void... voids) {

                finalresult = httpParse.postRequest(card,httpurl);

                return finalresult;

            }
        }

        PayClass payClass = new PayClass();
        payClass.execute();

    }

    void nomob(){

        AlertDialog.Builder obj = new AlertDialog.Builder(Payment.this);
        obj.setTitle("OI VEY!");
        obj.setMessage("Your mobile number is wrong");
        obj.setNeutralButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = obj.create();
        alertDialog.show();

    }

}
