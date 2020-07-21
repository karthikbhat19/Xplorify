package com.example.xplorify;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xplorify.classes.HttpParseJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class User_details extends AppCompatActivity {

    String ngrokAddr;
    String httpurl;
    String finaljsonResult;
    private ProgressDialog progressDialog;
    HttpParseJSON httpParseJSON = new HttpParseJSON();
    HashMap<String,String> detail = new HashMap<>();
    TextView nametv, mailtv, mobtv, dobtv;
    private int source;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        ScrollView sv = findViewById(R.id.userdeetsv);
        sv.setVerticalScrollBarEnabled(false);
        sv.setHorizontalScrollBarEnabled(false);

        nametv = findViewById(R.id.nameTV);
        mailtv = findViewById(R.id.emailTV);
        mobtv = findViewById(R.id.mobTV);
        dobtv = findViewById(R.id.dobTV);

        ngrokAddr = getIntent().getExtras().getString("token");
        httpurl = "https://"+ngrokAddr+".ngrok.io/trav/getuserdata.php";

        email = getIntent().getExtras().getString("mail");

        getDetails(email);

    }


    private void getDetails(final String mail){
        class GetDetails extends AsyncTask<String, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(User_details.this,"Retrieving data",null,true,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressDialog.dismiss();

                if (s.equalsIgnoreCase("err")){
                    Toast.makeText(User_details.this,"Error retrieving data.",Toast.LENGTH_LONG).show();
                }else if(!s.equalsIgnoreCase("error")){
                    try{
                        setdetails(s);
                    }catch (JSONException je){
                        je.printStackTrace();
                        Toast.makeText(User_details.this,je.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            protected String doInBackground(String... params) {
                detail.put("email",params[0]);
                finaljsonResult = httpParseJSON.postRequest(detail,httpurl,User_details.this);
                return finaljsonResult;
            }
        }

        GetDetails getDetails = new GetDetails();
        getDetails.execute(mail);
    }

    private void setdetails(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        nametv.setText(jsonObject.getString("Name"));
        mailtv.setText(jsonObject.getString("Email"));
        dobtv.setText(jsonObject.getString("DOB"));
        mobtv.setText(jsonObject.getString("Mobile"));
    }

    @Override
    public void onBackPressed() {
        Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();

        Intent a = new Intent(User_details.this,Search.class);
        a.putExtra("token",ngrokAddr);
        a.putExtra("mail",email);

        startActivity(a,Anims);
        finish();
    }
}
