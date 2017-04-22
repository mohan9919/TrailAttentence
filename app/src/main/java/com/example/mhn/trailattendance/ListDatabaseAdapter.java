package com.example.mhn.trailattendance;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MHN on 12/8/2016.
 */

public class ListDatabaseAdapter extends ArrayAdapter {
    private Context context;
    private Contact contact;
    private ArrayList<Contact> contactList;
    TextView nameTV;
    TextView rollTV;
    TextView emailTV;
    TextView phoneNoTV;
    TextView batchNoTV;
    //TextView userNameTV;
    //TextView passwordTV;
    TextView macAddressTV;

    public ListDatabaseAdapter(Context context, ArrayList<Contact> contactList) {
        super(context, R.layout.list_database_adapter_layout,contactList);
        this.context=context;
        this.contactList=contactList;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.list_database_adapter_layout,parent,false);
        nameTV=(TextView)view.findViewById(R.id.list_database_name_tv);
        rollTV=(TextView)view.findViewById(R.id.list_database_roll_tv);
        emailTV=(TextView)view.findViewById(R.id.list_database_email_tv);
        phoneNoTV=(TextView)view.findViewById(R.id.list_database_phone_no_tv);
        batchNoTV=(TextView)view.findViewById(R.id.list_database_batch_no_tv);
        //userNameTV=(TextView)view.findViewById(R.id.list_database_user_name_tv);
        //passwordTV=(TextView)view.findViewById(R.id.list_database_password_tv);
        macAddressTV=(TextView)view.findViewById(R.id.list_database_mac_address_tv);

        contact=contactList.get(position);

        nameTV.setText(contact.getName());
        rollTV.setText(String.valueOf(contact.getRoll()));
        emailTV.setText(contact.getEmail());
        phoneNoTV.setText(contact.getPhoneNo());
        batchNoTV.setText(contact.getBatchNo());
        //userNameTV.setText(contact.getUserName());
        //passwordTV.setText(contact.getPassword());
        macAddressTV.setText(contact.getMacAddress());

        return view;
    }

}
