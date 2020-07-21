package com.example.xplorify;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xplorify.classes.HttpParse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText name,email,no,dob,pwd;
    private Button sub;
    private String ngrokAddr = "";
    private HashMap<String,String> registerDetails = new HashMap<>();
    final Calendar myCal=Calendar.getInstance();
    private HttpParse httpParse = new HttpParse();
    private ProgressDialog progressDialog;
    private String finalResult;
    private String signupPage;

    private void updateLabel () {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf= new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCal.getTime()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        ngrokAddr = getIntent().getExtras().getString("ngrAddr");

        if(ngrokAddr.equals("")){
            raise_IP_alert();
        }

        dob=findViewById(R.id.dob);
        sub=findViewById(R.id.sub);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        no=findViewById(R.id.ph);
        pwd=findViewById(R.id.pswd);
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCal.set(Calendar.YEAR,year);
                myCal.set(Calendar.MONTH,month);
                myCal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUp.this,date,myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        sub.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        nextact();
    }

    public void nextact(){
        Intent l=new Intent(SignUp.this,Login.class);

        //animate <screen moves from right to left> forward to next activity
        Bundle lAnim = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.uptodown,R.anim.go_out_down).toBundle();

        l.putExtra("IP", ngrokAddr);
        startActivity(l,lAnim);

        finish();
    }

    public void raise_alert(){
        AlertDialog.Builder obj = new AlertDialog.Builder(SignUp.this);
        obj.setTitle("OI VEY!");
        obj.setMessage("Please Fill all the fields");
        obj.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = obj.create();
        alertDialog.show();
    }

    public boolean check_mob_valid(String mobile){
        return Pattern.matches("\\d{10}",mobile);
    }

    public void raise_invphone(){
        AlertDialog.Builder obj = new AlertDialog.Builder(SignUp.this);
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

    public boolean check_mail_valid(String email){
       return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void raise_mail(){
        AlertDialog.Builder obj = new AlertDialog.Builder(SignUp.this);
        obj.setTitle("OI VEY");
        obj.setMessage("Please enter a valid email");
        obj.setNeutralButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = obj.create();
        alertDialog.show();
    }

    public void raise_IP_alert(){
        AlertDialog.Builder obj = new AlertDialog.Builder(SignUp.this);
        obj.setTitle("Subdomain not provided");
        obj.setMessage("Please go to the previous page and provide the ngrok subdomain");
        obj.setNeutralButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                nextact();
            }
        });
        AlertDialog alertDialog = obj.create();
        alertDialog.show();
    }


    @Override
    public void onClick(View v) {

        String nme = name.getText().toString();
        String mail = email.getText().toString();
        String num = no.getText().toString();
        String birth = dob.getText().toString();
        String pass = pwd.getText().toString();

        if (nme.isEmpty() || mail.isEmpty() || num.isEmpty() || birth.isEmpty() || pass.isEmpty()) {
            raise_alert();
        } else {
            if (check_mail_valid(mail)) {
                if (check_mob_valid(num)) {

                    signupPage = "https://"+ ngrokAddr +".ngrok.io/trav/signup.php";
                    userRegister(mail, pass, nme, birth, num);

                } else {
                    raise_invphone();
                }

            } else {
                raise_mail();
            }
        }
    }

    public void userRegister(final String mail, final String pass, final String name, final String dob, final String mob){

        class userRegisterClass extends AsyncTask<String, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(SignUp.this,"Submitting",null,true,true);

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Success")){
                    nextact();
                } else {
                    Toast.makeText(SignUp.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... strings) {

                registerDetails.put("email",strings[0]);
                registerDetails.put("pass",strings[1]);
                registerDetails.put("name",strings[2]);
                registerDetails.put("dob",strings[3]);
                registerDetails.put("mob",strings[4]);

                finalResult = httpParse.postRequest(registerDetails,signupPage);

                return finalResult;
            }
        }

        userRegisterClass userRegisterClass = new userRegisterClass();

        userRegisterClass.execute(mail,pass,name,dob,mob);

    }

}
