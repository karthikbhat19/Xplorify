package com.example.xplorify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xplorify.classes.HttpParseJSON;
import com.example.xplorify.listview.TicketAdapter;
import com.example.xplorify.listview.TicketModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Available_transports extends AppCompatActivity {

   TextView textView;
    ListView listView;
    String token;
    String src;
    String dest;
    String mode;
    String HttpUrl;
    String finaljsonResult;
    HashMap<String,String> transdata = new HashMap<>();
    ProgressDialog progressDialog;
    HttpParseJSON httpParseJSON = new HttpParseJSON();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_transports);
        listView = findViewById(R.id.ATlist);
        textView = findViewById(R.id.ATtype);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        token = bundle.getString("token");
        src = bundle.getString("source","");
        dest = bundle.getString("dest","");
        mode = bundle.getString("mode","all");

        HttpUrl = "https://"+token+".ngrok.io/trav/showtrans.php";


        if(!src.equals(""))
            transdata.put("from",src);
        if(!dest.equals(""))
            transdata.put("to",dest);

        transdata.put("mode",mode);

        switch (mode) {
            case "bus":

                textView.setText("BUSES");
                break;

            case "train":

                textView.setText("TRAINS");
                break;

            case "airplane":

                textView.setText("FLIGHTS");
                break;
        }

        listView.setVisibility(ListView.VISIBLE);
        getTicketsMethod();

    }

    private void getTicketsMethod(){
        class GetTickets extends AsyncTask<Void,Void,String>{

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(Available_transports.this,"Retrieving Data",null, true, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressDialog.dismiss();

                if (s.equalsIgnoreCase("err")){
                    Toast.makeText(Available_transports.this,"Error retrieving data.",Toast.LENGTH_LONG).show();
                }else if(!s.equalsIgnoreCase("error")){
                    try{
                        setList(s);
                    }catch (JSONException je){
                        je.printStackTrace();
                        Toast.makeText(Available_transports.this,"No routes between the given locations for this type of transport",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                finaljsonResult = httpParseJSON.postRequest(transdata,HttpUrl,Available_transports.this);
                return finaljsonResult;
            }

        }

        GetTickets getTickets = new GetTickets();
        getTickets.execute();
    }

    private void setList(String json) throws JSONException{
        ArrayList<TicketModel> arrayList = new ArrayList<>();
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

        //Toast.makeText(Available_transports.this,Integer.toString(arrayList.size()),Toast.LENGTH_LONG).show();
        TicketAdapter ticketAdapter = new TicketAdapter(this, arrayList);
        listView.setAdapter(ticketAdapter);

    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(Available_transports.this,Search.class);
        back.putExtra("token",token);
        back.putExtra("mail",getIntent().getExtras().getString("mail"));

        Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();
        startActivity(back,Anims);

        finish();
    }
}
