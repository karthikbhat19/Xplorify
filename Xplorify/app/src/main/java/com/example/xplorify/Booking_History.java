package com.example.xplorify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xplorify.classes.HttpParseJSON;
import com.example.xplorify.listview.HistoryAdapter;
import com.example.xplorify.listview.TicketAdapter;
import com.example.xplorify.listview.TicketModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Booking_History extends AppCompatActivity {

    ListView listView;
    ProgressDialog progressDialog;
    String ngrokAddr;
    String email;
    HashMap<String,String> tickets = new HashMap<>();
    String finalJSONresult;
    HttpParseJSON httpParseJSON = new HttpParseJSON();
    String httpURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking__history);

        listView = findViewById(R.id.history_list);

        ngrokAddr = getIntent().getExtras().getString("token");
        email = getIntent().getExtras().getString("mail");

        httpURL = "https://"+ngrokAddr+".ngrok.io/trav/history.php";

        tickets.put("mail",email);

        getHistory();
    }

    public void getHistory(){
        class HistoryClass extends AsyncTask<Void, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(Booking_History.this,"Retrieving Data",null, true, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressDialog.dismiss();

                if (s.equalsIgnoreCase("err")){
                    Toast.makeText(Booking_History.this,"Error retrieving data.",Toast.LENGTH_LONG).show();
                }else if(!s.equalsIgnoreCase("error")){
                    try{
                        setList(s);
                    }catch (JSONException je){
                        je.printStackTrace();
                        Toast.makeText(Booking_History.this,"No history found",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                finalJSONresult = httpParseJSON.postRequest(tickets,httpURL,Booking_History.this);
                return finalJSONresult;

            }

        }

        HistoryClass historyClass = new HistoryClass();
        historyClass.execute();
    }

    public void setList(String json) throws JSONException{
        ArrayList<TicketModel> arrayList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        //Toast.makeText(Available_transports.this,Integer.toString(jsonArray.length()),Toast.LENGTH_LONG).show();
        for( int i=0; i<jsonArray.length(); ++i){

            HashMap<String,String> tempdata = new HashMap<>();

            JSONObject jsonObject = jsonArray.getJSONObject(i);



            tempdata.put("id",jsonObject.getString("Ticket_ID"));
            tempdata.put("source",jsonObject.getString("Source"));
            tempdata.put("dest",jsonObject.getString("Destination"));
            tempdata.put("dept",jsonObject.getString("Departure"));
            tempdata.put("trv",jsonObject.getString("Travel")+" hours");
            tempdata.put("type",jsonObject.getString("Type"));
            tempdata.put("date",jsonObject.getString("Date"));
            tempdata.put("bno",jsonObject.getString("Booking_ID"));

            TicketModel ticketModel = new TicketModel();
            ticketModel.setData(tempdata);
            arrayList.add(ticketModel);

        }

        //Toast.makeText(Available_transports.this,Integer.toString(arrayList.size()),Toast.LENGTH_LONG).show();
        HistoryAdapter historyAdapter = new HistoryAdapter(this,arrayList);
        listView.setAdapter(historyAdapter);
    }

    @Override
    public void onBackPressed() {
        Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();

        Intent a = new Intent(Booking_History.this,Search.class);
        a.putExtra("token",ngrokAddr);
        a.putExtra("mail",email);

        startActivity(a,Anims);
        finish();
    }
}
