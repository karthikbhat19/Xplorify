package com.example.xplorify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Search extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView src;
    AutoCompleteTextView dest;
    EditText date1;
    AlertDialog.Builder builder;
    RadioGroup travelmode;
    Button sub;
    ScrollView sv;
    String ngrokAddr;
    String email;

    private static final String[] COUNTRIES = new String[]{
            "Agra", "Ahmedabad", "Bangalore", "Bombay", "Chattisgarh", "Chandigarh", "Cochin", "Darjeeling", "Delhi", "Ernakulam", "Faizabad",
            "Gadag", "Harinagar", "Indore", "Jabalpur", "Kolkata", "Ladakh", "Lucknow", "Mangalore", "Manipur", "Nadora", "Ooty", "Panaji",
            "Raichur", "Rajkot", "Ranipuram", "Srinagar", "Thiruvananthapuram", "Travancore", "Udaipur", "Visakhapatnam"
    };

    final Calendar myCal=Calendar.getInstance();

    private void updateLabel () {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf= new SimpleDateFormat(myFormat, Locale.US);

        date1.setText(sdf.format(myCal.getTime()));
    }

    @Override
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
                //Toast.makeText(getApplicationContext(),"Account Details",Toast.LENGTH_LONG).show();
                //Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ\"&t=0m43s"));
                //startActivity(i);
                Intent details = new Intent(Search.this,User_details.class);
                Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_left,R.anim.go_out_left).toBundle();
                details.putExtra("token",ngrokAddr);
                details.putExtra("mail",email);
                startActivity(details,Anims);
                finish();
                return true;

            case R.id.history:
                Intent hist = new Intent(Search.this,Booking_History.class);
                Bundle Animate = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_left,R.anim.go_out_left).toBundle();
                hist.putExtra("token",ngrokAddr);
                hist.putExtra("mail",email);
                startActivity(hist,Animate);
                finish();
                return true;

            case R.id.logout:
                builder=new AlertDialog.Builder(Search.this);
                builder.setTitle("LOGOUT?");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Toast.makeText(Search.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                        Intent a=new Intent(Search.this,Login.class);

                        Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();
                        startActivity(a,Anims);
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(Search.this, "Enjoy!...", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog ad=builder.create();
                ad.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        builder=new AlertDialog.Builder(Search.this);
        builder.setTitle("LOGOUT?");
        builder.setMessage("You have Pressed Back! Are you sure you want to Logout?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(Search.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                Intent a=new Intent(Search.this,Login.class);

                Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.come_into_right,R.anim.go_out_right).toBundle();
                startActivity(a,Anims);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(Search.this, "Enjoy!...", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog ad=builder.create();
        ad.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ngrokAddr = getIntent().getExtras().getString("token");
        email = getIntent().getExtras().getString("mail");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        String[] countries = getResources().getStringArray(R.array.countries);

        src = findViewById(R.id.src);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, countries);
        src.setAdapter(adapter);
        //editText.setThreshold(1);

        //get the input like for a normal EditText
        //String input = editText.getText().toString();
       dest = findViewById(R.id.dest);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, countries);
        dest.setAdapter(adapter2);

        date1=findViewById(R.id.date);
        sub=findViewById(R.id.sub);
        sv=findViewById(R.id.sv);
        travelmode=findViewById(R.id.mode);
        sv.setHorizontalScrollBarEnabled(false);
        sv.setVerticalScrollBarEnabled(false);
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCal.set(Calendar.YEAR,year);
                myCal.set(Calendar.MONTH,month);
                myCal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Search.this,date,myCal.get(Calendar.YEAR),myCal.get(Calendar.MONTH),myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        sub.setOnClickListener(this);
    }

    public void raiseradioalert(){
        builder=new AlertDialog.Builder(Search.this);
        builder.setTitle("Transport");
        builder.setMessage("Please select one of the 3 modes of transport");
        builder.setCancelable(false);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog ad=builder.create();
        ad.show();
    }

    @Override
    public void onClick(View v) {
        Intent b;

        String source = src.getText().toString().trim();
        String destination = dest.getText().toString().trim();
        String date = date1.getText().toString();
        int radioID = travelmode.getCheckedRadioButtonId();
        String mode = "";

        if (radioID == R.id.bus) {
            mode = "bus";
        } else if (radioID == R.id.train) {
            mode = "train";
        } else if (radioID == R.id.flight) {
            mode = "airplane";
        } else {
            raiseradioalert();
        }

        if (!mode.equals("")) {

            final boolean b1 = !(source.equals("") || destination.equals("") || date.equals(""));
            if (b1)
                b = new Intent(Search.this, TicketBooking.class);
            else
                b = new Intent(Search.this, Available_transports.class);

            if (!source.equals(""))
                b.putExtra("source", source);
            if (!destination.equals(""))
                b.putExtra("dest", destination);
            if (!date.equals("")) {
                b.putExtra("date", date);
            }

            b.putExtra("mode", mode);
            b.putExtra("token", ngrokAddr);
            b.putExtra("mail", email);

            Bundle Anims = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.come_into_left, R.anim.go_out_left).toBundle();

            startActivity(b, Anims);
            finish();

        }
    }
}
