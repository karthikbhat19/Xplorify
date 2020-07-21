package com.example.xplorify;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xplorify.classes.HttpParse;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private boolean backpressed;
    private TextView snup;
    private Button log;
    private EditText uname,pwd;
    private String ngrokAddr ="";
    private ProgressDialog progressDialog;
    private HashMap<String, String> loginDetails = new HashMap<>();
    private String finalResult, HttpURL;
    HttpParse httpParse = new HttpParse();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ip_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ip){

            ngrok_address_prompt();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        uname=findViewById(R.id.uname);
        pwd=findViewById(R.id.pwd);
        snup=findViewById(R.id.signup);
        log=findViewById(R.id.log);
        String text="Create a new Account --> Sign-Up";
        SpannableString ss=new SpannableString(text);

        if(getIntent().getExtras()!=null){
            ngrokAddr = getIntent().getExtras().getString("IP");
        }


        ClickableSpan signup=new ClickableSpan() {
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }

            @Override
            public void onClick(@NonNull View widget) {

                Intent a=new Intent(Login.this,SignUp.class);
                Bundle aAnim = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.downtoup,R.anim.go_out_up).toBundle();

                a.putExtra("ngrAddr", ngrokAddr);
                startActivity(a,aAnim);
                finish();
            }
        };
        ss.setSpan(signup,25,32,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        snup.setText(ss);
        snup.setMovementMethod(LinkMovementMethod.getInstance());

        log.setOnClickListener(this);
    }

    public void nextact(){
        Intent l=new Intent(Login.this,Search.class);

        //animate <screen moves from right to left> forward to next activity
        Bundle lAnim = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_left,R.anim.go_out_left).toBundle();

        l.putExtra("mail",uname.getText().toString());
        l.putExtra("token", ngrokAddr);
        startActivity(l,lAnim);

        finish();
    }

    public void raise_alert(){
        AlertDialog.Builder obj = new AlertDialog.Builder(Login.this);
        obj.setTitle("OI VEY!");
        obj.setMessage("Please Enter email and password");
        obj.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = obj.create();
        alertDialog.show();
    }


    @Override
    public void onClick(View v) {

        String mail = uname.getText().toString();
        String pass = pwd.getText().toString();
        if (mail.isEmpty() || pass.isEmpty()){

            raise_alert();

        }else{

                HttpURL = "https://" + ngrokAddr + ".ngrok.io/trav/login.php";
                LoginFunction(mail, pass);


        }
    }

    public void LoginFunction(final String email, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Login.this,"Attempting Log-In",null,true,false);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    nextact();

                }
                else{

                    Toast.makeText(Login.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                loginDetails.put("email",params[0]);

                loginDetails.put("password",params[1]);

                finalResult = httpParse.postRequest(loginDetails, HttpURL);

                return finalResult;

            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(email,password);
    }


    @Override
    public void onBackPressed() {
        if(backpressed){
            super.onBackPressed();
            return;
        }

        backpressed = true;

        Toast.makeText(Login.this,"Press back again to exit",Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backpressed=false;
            }
        },2000);

    }


    public void ngrok_address_prompt(){

        final AlertDialog.Builder obj = new AlertDialog.Builder(Login.this);
        View prompt = getLayoutInflater().inflate(R.layout.ip_prompt,null);
        obj.setView(prompt);

        final EditText Et = prompt.findViewById(R.id.ngrokAddr);


        Et.setText(ngrokAddr);

        obj.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ngrokAddr = Et.getText().toString();
            }
        });


        obj.setCancelable(false);
        AlertDialog aobj = obj.create();
        aobj.show();
    }

}
