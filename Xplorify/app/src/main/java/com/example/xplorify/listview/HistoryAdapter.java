package com.example.xplorify.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xplorify.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<TicketModel> arrayList;

    public HistoryAdapter(Context mcontext, ArrayList<TicketModel> mArrayList){
        this.context = mcontext;
        this.arrayList = mArrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.booking_list_item,parent,false);

        TextView id, source, dest, dept, trv, type, date, bno;

        id = convertView.findViewById(R.id.Hid);
        source = convertView.findViewById(R.id.Hsource);
        dest = convertView.findViewById(R.id.Hdestination);
        dept = convertView.findViewById(R.id.Hdeparture);
        trv = convertView.findViewById(R.id.Htravtime);
        type = convertView.findViewById(R.id.Htype);
        date = convertView.findViewById(R.id.Hdate);
        bno = convertView.findViewById(R.id.Hbno);

        HashMap<String, String> temp = arrayList.get(position).getData();
        id.setText(temp.get("id"));
        source.setText(temp.get("source"));
        dest.setText(temp.get("dest"));
        dept.setText(temp.get("dept"));
        trv.setText(temp.get("trv"));
        type.setText(temp.get("type"));
        date.setText(temp.get("date"));
        bno.setText(temp.get("bno"));

        return convertView;
    }
}
