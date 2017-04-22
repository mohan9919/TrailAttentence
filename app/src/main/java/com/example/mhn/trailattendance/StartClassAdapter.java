package com.example.mhn.trailattendance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rahma on 12/10/2016.
 */

public class StartClassAdapter extends ArrayAdapter
{
    ArrayList<Contact> contactList;
    Context context;
    TextView nameTV;
    TextView rollTV;
    TextView presentTv;
    AdminActivity adminActivity=new AdminActivity();

    //TextView emailTV;
    //TextView phoneNoTV;
    //String presence="pesence here";
    Contact contact;
    public StartClassAdapter(Context context, ArrayList<Contact> contactList) {
        super(context,R.layout.start_class_layout,contactList);
        this.context=context;
        this.contactList=contactList;

    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.start_class_layout,parent,false);


        nameTV=(TextView)view.findViewById(R.id.start_class_name_tv);
        rollTV=(TextView)view.findViewById(R.id.start_class_roll_tv);
        presentTv=(TextView)view.findViewById(R.id.start_class_present_tv);
        //emailTV=(TextView)view.findViewById(R.id.admin_trainee_email_tv);
        //phoneNoTV=(TextView)view.findViewById(R.id.admin_trainee_phone_tv);


        contact=contactList.get(position);


        nameTV.setText(contact.getName());
        rollTV.setText(String.valueOf(contact.getRoll()));
        presentTv.setText(contact.getPresence());

        //emailTV.setText(contact.getEmail());
        //phoneNoTV.setText(contact.getPhoneNo());

        return view;
    }






}
