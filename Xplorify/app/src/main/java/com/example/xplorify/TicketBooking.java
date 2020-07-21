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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xplorify.classes.HttpParseJSON;
import com.example.xplorify.listview.TicketAdapter;
import com.example.xplorify.listview.TicketModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TicketBooking extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView tickets;
    AlertDialog.Builder builder;

    String ngrokAddr;
    String email;
    String mode;
    String from;
    String to;
    String date;
    String HttpUrl;
    String finaljsonResult;

    ProgressDialog progressDialog;
    HttpParseJSON httpParseJSON = new HttpParseJSON();

    HashMap<String,String> bookingdetails = new HashMap<>();

    ArrayList<TicketModel> arrayList;

    @Override
    public void onBackPressed() {
        Intent log = new Intent(TicketBooking.this,Search.class);
        Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();

        log.putExtra("token", ngrokAddr);
        log.putExtra("mail", email);

        startActivity(log,Anims);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);

        Bundle bundle = getIntent().getExtras();
        ngrokAddr = bundle.getString("token");
        email = bundle.getString("mail");
        from = bundle.getString("source");
        to = bundle.getString("dest");
        mode = bundle.getString("mode");
        date = bundle.getString("date");

        HttpUrl = "https://"+ngrokAddr+".ngrok.io/trav/showtrans.php";

        bookingdetails.put("mode",mode);
        bookingdetails.put("from",from);
        bookingdetails.put("to",to);

        arrayList = new ArrayList<>();


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        tickets=findViewById(R.id.TBlv);

        tickets.setVisibility(ListView.VISIBLE);
        getTicketsMethod();

        tickets.setOnItemClickListener(this);

    }

    private void getTicketsMethod(){
        class GetTickets extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(TicketBooking.this,"Retrieving Data",null, true, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressDialog.dismiss();

                if (s.equalsIgnoreCase("err")){
                    Toast.makeText(TicketBooking.this,"Error retrieving data.",Toast.LENGTH_LONG).show();
                }else if(!s.equalsIgnoreCase("error")){
                    try{
                        setList(s);
                    }catch (JSONException je){
                        je.printStackTrace();
                        Toast.makeText(TicketBooking.this,"No routes between the given locations for this type of transport",Toast.LENGTH_LONG).show();
                        notickets();
                    }
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                finaljsonResult = httpParseJSON.postRequest(bookingdetails,HttpUrl,TicketBooking.this);
                return finaljsonResult;
            }

        }

        GetTickets getTickets = new GetTickets();
        getTickets.execute();
    }

    private void setList(String json) throws JSONException{
        JSONArray jsonArray = new JSONArray(json);
        //Toast.makeText(Available_transports.this,Integer.toString(jsonArray.length()),Toast.LENGTH_LONG).show();
        for( int i=0; i<jsonArray.length(); ++i){

            HashMap<String,String> tempdata = new HashMap<>();

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            tempdata.put("id",jsonObject.getString("ID"));
            tempdata.put("source",jsonObject.getString("Source"));
            tempdata.put("dest",jsonObject.getString("Destination"));
            tempdata.put("dept",jsonObject.getString("Departure"));
            tempdata.put("trv",jsonObject.getString("Travel")+" hours");
            tempdata.put("price","₹ "+jsonObject.getString("Price"));

            TicketModel ticketModel = new TicketModel();
            ticketModel.setData(tempdata);
            arrayList.add(ticketModel);

        }

        //Toast.makeText(TicketBooking.this,Integer.toString(arrayList.size()),Toast.LENGTH_LONG).show();
        TicketAdapter ticketAdapter = new TicketAdapter(this, arrayList);
        tickets.setAdapter(ticketAdapter);

    }


    public void notickets(){
        builder=new AlertDialog.Builder(TicketBooking.this);
        builder.setTitle("Sorry for the inconvenience");
        builder.setMessage("There are no tickets available for this route.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                Intent log = new Intent(TicketBooking.this,Search.class);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> temp = arrayList.get(position).getData();
        HashMap<String, String> ticket = new HashMap<>();

        ticket.put("id",temp.get("id"));
        ticket.put("mode",mode);
        ticket.put("date",date);
        ticket.put("mail",email);

        Intent pay = new Intent(TicketBooking.this,Payment.class);

        Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.come_into_left, R.anim.go_out_left).toBundle();

        pay.putExtra("mail",email);
        pay.putExtra("token",ngrokAddr);
        pay.putExtra("Hashmap",ticket);
        Toast.makeText(TicketBooking.this,"ID: " + temp.get("id"), Toast.LENGTH_LONG ).show();

        confirmcontinue(pay,Anims);
    }

    public void confirmcontinue(final Intent intent, final Bundle anims){
        builder=new AlertDialog.Builder(TicketBooking.this);
        builder.setTitle("Booking");
        builder.setMessage("Continue to booking?");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(intent, anims);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog ad=builder.create();
        ad.show();
    }
}
