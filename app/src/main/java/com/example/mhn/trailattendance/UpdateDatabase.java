package com.example.mhn.trailattendance;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by MHN on 12/9/2016.
 */

public class UpdateDatabase extends AppCompatActivity {
    ListView listViewLV;
    Button deleteBtn;
    Button updateBtn;
    Button showAllContatcBtn;
   // Contact contact;
    ContactManeger maneger;
    ListDatabaseAdapter adapter;
    ArrayList<Contact> contactList;
    String id;
    String name;
    int roll;
    String email;
    String phoneNo;
    String batchNo;


    EditText updateNameEt;
    EditText updateMailEt;
    EditText updatePhoneEt;
    Button updateInfoBtn;

    String userNameFromIntent="";




    public static final String USER_NAME="user_name";
//    String userName;
//    String password;
//    String macAddress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_database_layout);
        //Contact contact=new Contact();
        updateNameEt=(EditText)findViewById(R.id.update_name_et);
        updateMailEt=(EditText)findViewById(R.id.update_email_et);
        updatePhoneEt=(EditText)findViewById(R.id.update_phone_et);
        updateInfoBtn=(Button)findViewById(R.id.update_btn);
        listViewLV=(ListView)findViewById(R.id.update_lv);
        deleteBtn=(Button)findViewById(R.id.update_delete_btn);
        updateBtn=(Button)findViewById(R.id.update_update_btn);

        showAllContatcBtn=(Button)findViewById(R.id.update_show_all_contact_btn);
        contactList=new ArrayList<Contact>();
        maneger=new ContactManeger(this);
        Log.w("size",contactList.size()+"");
        //adapter=new ListDatabaseAdapter(this,contactList);
    }

    public void clickShowAllContactBtn(View view) {
        //contact=new Contact(name,roll,email,phoneNo,batchNo,macAddress);
        //contact=new Contact(name,roll,email,phoneNo,batchNo);
        contactList=maneger.getAllContact();
        adapter=new ListDatabaseAdapter(this,contactList);
        listViewLV.setAdapter(adapter);
    }

    public void clickDeleteBtn(View view) {
        boolean deleted=maneger.deleteContact(roll);
        Toast.makeText(this,String.valueOf(deleted),Toast.LENGTH_SHORT).show();
    }

    public void clickUpdateBtn(View view) {
        updateNameEt.setVisibility(View.VISIBLE);
        updateMailEt.setVisibility(View.VISIBLE);
        updatePhoneEt.setVisibility(View.VISIBLE);
        updateInfoBtn.setVisibility(View.VISIBLE);

        Bundle bundle=getIntent().getExtras();
        if(!bundle.getString(USER_NAME).equals(null))
        {
            userNameFromIntent=bundle.getString(USER_NAME);
            //Toast.makeText(UpdateDatabase.this, userName, Toast.LENGTH_SHORT).show();
        }
        Contact contact= maneger.getInfosToUpdate(userNameFromIntent);
        String name=contact.getName();
        String email=contact.getEmail();
        String phone=contact.getPhoneNo();
        updateNameEt.setText(name);
        updatePhoneEt.setText(phone);
        updateMailEt.setText(email);



        //boolean updated=maneger.updateContact(roll,contact);
        Toast.makeText(this,"up",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent=new Intent(UpdateDatabase.this,MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void updateInfo(View view) {
        String name=updateNameEt.getText().toString();
        String email=updateMailEt.getText().toString();
        String phone=updatePhoneEt.getText().toString();
        updateNameEt.setVisibility(View.GONE);
        updateMailEt.setVisibility(View.GONE);
        updatePhoneEt.setVisibility(View.GONE);
        updateInfoBtn.setVisibility(View.GONE);
        boolean editFlag=maneger.editInfos(userNameFromIntent,name,email,phone);
        if(editFlag)
        {
            Toast.makeText(UpdateDatabase.this, "Updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(UpdateDatabase.this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
