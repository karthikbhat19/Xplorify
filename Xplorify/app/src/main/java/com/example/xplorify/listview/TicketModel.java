package com.example.xplorify.listview;

import java.util.HashMap;

public class TicketModel {

    private HashMap<String, String> TicketData;

    public void setData(HashMap<String, String> data) {
        this.TicketData = data;
    }

    public HashMap<String, String> getData() {
        return TicketData;
    }
}
