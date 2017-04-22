package com.example.mhn.trailattendance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rahma on 12/10/2016.
 */

public class AdminShowInfoAdapter extends ArrayAdapter
{
    ArrayList<Contact> contactList;
    Context context;
    TextView nameTV;
    TextView rollTV;
    TextView emailTV;
    TextView phoneNoTV;
    TextView presenceTv;
    Contact contact;
    public AdminShowInfoAdapter(Context context, ArrayList<Contact> contactList) {
        super(context,R.layout.show_trainee_layout,contactList);
        this.context=context;
        this.contactList=contactList;

    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.show_trainee_layout,parent,false);


        nameTV=(TextView)view.findViewById(R.id.admin_trainee_name_tv);
        rollTV=(TextView)view.findViewById(R.id.admin_trainee_roll_tv);
        emailTV=(TextView)view.findViewById(R.id.admin_trainee_email_tv);
        phoneNoTV=(TextView)view.findViewById(R.id.admin_trainee_phone_tv);
        presenceTv=(TextView)view.findViewById(R.id.admin_trainee_presence_tv);


        contact=contactList.get(position);


        nameTV.setText(contact.getName());
        rollTV.setText(String.valueOf(contact.getRoll()));
        emailTV.setText(contact.getEmail());
        phoneNoTV.setText(contact.getPhoneNo());
        presenceTv.setText(contact.getPresence());

        return view;
    }
}
