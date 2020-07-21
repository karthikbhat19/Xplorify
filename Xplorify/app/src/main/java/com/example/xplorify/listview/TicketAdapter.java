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

public class TicketAdapter extends BaseAdapter {

    Context context;
    ArrayList<TicketModel> arrayList;

    public TicketAdapter(Context mcontext, ArrayList<TicketModel> mArrayList){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.ticket_list_item,parent,false);

        TextView id, source, dest, dept, trv, price;

        id = convertView.findViewById(R.id.id);
        source = convertView.findViewById(R.id.source);
        dest = convertView.findViewById(R.id.destination);
        dept = convertView.findViewById(R.id.departure);
        trv = convertView.findViewById(R.id.travtime);
        price = convertView.findViewById(R.id.price);

        HashMap<String, String> temp = arrayList.get(position).getData();
        id.setText(temp.get("id"));
        source.setText(temp.get("source"));
        dest.setText(temp.get("dest"));
        dept.setText(temp.get("dept"));
        trv.setText(temp.get("trv"));
        price.setText(temp.get("price"));

        return convertView;
    }
}
