package com.example.xplorify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xplorify.classes.HttpParse;
import com.example.xplorify.classes.HttpParseJSON;
import com.example.xplorify.listview.TicketModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Booked extends AppCompatActivity {

    Button bck;
    AlertDialog.Builder builder;

    String ngrokAddr;
    String email;
    HashMap<String, String > ticket;

    String finalhttpResult;

    HttpParse httpParse = new HttpParse();

    HttpParseJSON httpParseJSON = new HttpParseJSON();

    ProgressDialog progressDialog;

    String HttpUrl;


    LinearLayout LL;
    TextView Bno, Bto, Bfrom, Btype, Bdate, Bid, Bdept, Btrv;

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.acct:
                Toast.makeText(getApplicationContext(),"Account Details",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ\"&t=0m43s"));
                startActivity(i);
                return true;
            case R.id.logout:
                builder=new AlertDialog.Builder(Booked.this);
                builder.setTitle("LOGOUT?");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Booked.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                        Intent a=new Intent(Booked.this,Login.class);

                        Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();
                        startActivity(a,Anims);
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(Booked.this, "Enjoy!...", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog ad=builder.create();
                ad.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public void onBackPressed() {
        Intent a=new Intent(Booked.this,Search.class);
        Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();

        a.putExtra("token", ngrokAddr);
        a.putExtra("mail", email);

        startActivity(a,Anims);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        email = getIntent().getExtras().getString("mail");
        ngrokAddr = getIntent().getExtras().getString("token");
        ticket = (HashMap<String, String>) getIntent().getSerializableExtra("Hashmap");

        HttpUrl = "https://"+ngrokAddr+".ngrok.io/trav/maketicket.php";

        LL = findViewById(R.id.bookedLL);

        Bno = findViewById(R.id.Bbno);
        Bid = findViewById(R.id.Bid);
        Bto = findViewById(R.id.Bdestination);
        Bdept = findViewById(R.id.Bdeparture);
        Bdate = findViewById(R.id.Bdate);
        Bfrom = findViewById(R.id.Bsource);
        Btrv = findViewById(R.id.Btravtime);
        Btype = findViewById(R.id.Btype);


        bookMeth();

        bck=findViewById(R.id.bck);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(Booked.this,Search.class);
                Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();

                a.putExtra("token", ngrokAddr);
                a.putExtra("mail", email);

                startActivity(a,Anims);
                finish();
            }
        });
    }

    /*public void bookMeth(){

        class Booking extends AsyncTask<Void, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Booked.this,"Booking Ticket",null,true,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressDialog.dismiss();

                if(s.equals("Success")){

                    Toast.makeText(Booked.this,"Booking was succesfull",Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(Booked.this,"Booking failed",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                finalhttpResult = httpParse.postRequest(ticket,HttpUrl);

                return finalhttpResult;
            }

        }

        Booking booking = new Booking();
        booking.execute();

    }*/

    public void bookMeth(){
        class Booking extends AsyncTask<Void, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Booked.this,"Booking Ticket",null,true,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressDialog.dismiss();

                /*if(s.equals("Success")){

                    Toast.makeText(Booked.this,"Booking was succesfull",Toast.LENGTH_LONG).show();

                }else{

                    Toast.makeText(Booked.this,"Booking failed",Toast.LENGTH_SHORT).show();

                }*/

                try{

                    setTicket(s);
                    LL.setVisibility(LinearLayout.VISIBLE);


                }catch (JSONException je){

                    LL.setVisibility(LinearLayout.GONE);
                    je.printStackTrace();
                    Toast.makeText(Booked.this,"Issue in booking ticket",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                finalhttpResult = httpParseJSON.postRequest(ticket,HttpUrl,Booked.this);

                return finalhttpResult;
            }

        }

        Booking booking = new Booking();
        booking.execute();
    }

    public void setTicket(String json) throws JSONException{

         JSONArray jsonArray = new JSONArray(json);
        //Toast.makeText(Available_transports.this,Integer.toString(jsonArray.length()),Toast.LENGTH_LONG).show();

       // JSONObject jsonObject = jsonArray.getJSONObject(0);

        JSONObject jsonObject = jsonArray.getJSONObject(0);

        String t = jsonObject.getString("Travel")+" hours";
        Bid.setText(jsonObject.getString("Ticket_ID"));
        Bfrom.setText(jsonObject.getString("Source"));
        Bto.setText(jsonObject.getString("Destination"));
        Bdept.setText(jsonObject.getString("Departure"));
        Btrv.setText(t);
        Btype.setText(jsonObject.getString("Type"));
        Bdate.setText(jsonObject.getString("Date"));
        Bno.setText(jsonObject.getString("Booking_ID"));

    }
}
