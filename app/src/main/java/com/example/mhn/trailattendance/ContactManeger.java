package com.example.mhn.trailattendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by MHN on 12/8/2016.
 */

public class ContactManeger {
    private Context context;
    private Contact contact;
    private SQLiteDatabase database;
    private ContactHelper helper;

    public ContactManeger(Context context) {
        helper = new ContactHelper(context);
    }

    private void openDatabase() {
        database = helper.getReadableDatabase();
    }

    private void closeDatabase() {
        helper.close();
    }

    public boolean addContact(Contact contact) {
        this.openDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ContactHelper.COL_NAME, contact.getName());
        cv.put(ContactHelper.COL_ROLL, contact.getRoll());
        cv.put(ContactHelper.COL_EMAIL, contact.getEmail());
        cv.put(ContactHelper.COL_PHONENO, contact.getPhoneNo());
        cv.put(ContactHelper.COL_BATCHNO, contact.getBatchNo());
        cv.put(ContactHelper.COL_USER_NAME,contact.getUserName());
        cv.put(ContactHelper.COL_PASSWORD,contact.getPassword());
        cv.put(ContactHelper.COL_MAC_ADDRESS, contact.getMacAddress());

        long inserted = database.insert(ContactHelper.CONTACT_TABLE, null, cv);
        this.closeDatabase();

        return (inserted > 0);
    }
    public boolean updatePresence(int roll,String presenceStatus)
    {
        String rollSt=String.valueOf(roll);
        openDatabase();
        String selection=ContactHelper.COL_ROLL+" LIKE ?";
        String []arguments={rollSt};
        ContentValues contentValues=new ContentValues();
        contentValues.put(ContactHelper.COL_PRESENCE,presenceStatus);

        int updateFlag=database.update(ContactHelper.CONTACT_TABLE,contentValues,selection,arguments);
        closeDatabase();
        return (updateFlag>0);
    }
    public boolean setPesenceToDefault()
    {
        String presenceStatus="-";
        openDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ContactHelper.COL_PRESENCE,presenceStatus);
        int setDefaultflag=database.update(ContactHelper.CONTACT_TABLE,contentValues,null,null);
        closeDatabase();
        return (setDefaultflag>0);
    }

    public Contact getContact(int id) {
        this.openDatabase();
        Cursor cursor = database.query(ContactHelper.CONTACT_TABLE, new String[]{ContactHelper.COL_ID,
                ContactHelper.COL_NAME, ContactHelper.COL_EMAIL, ContactHelper.COL_PHONENO,
                ContactHelper.COL_BATCHNO,ContactHelper.COL_USER_NAME,ContactHelper.COL_PASSWORD, ContactHelper.COL_MAC_ADDRESS}, null, null, null, null, null);
        cursor.moveToFirst();


        int contactId = cursor.getInt(cursor.getColumnIndex(ContactHelper.COL_ID));
        String name = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_NAME));
        int roll = cursor.getInt(cursor.getColumnIndex(ContactHelper.COL_ROLL));
        String email = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_EMAIL));
        String phoneNo = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_PHONENO));
        String batchNo = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_BATCHNO));
        String macAddress = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_MAC_ADDRESS));
        String userName=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_USER_NAME));
        String passWord=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_PASSWORD));

        //contact = new Contact(contactId, name, roll, email, phoneNo, batchNo,userName,passWord, macAddress);
        contact =new Contact(contactId,name,roll,email,phoneNo,batchNo,userName,passWord,macAddress);

        return contact;
    }
    public Contact getInfosToUpdate(String userName)
    {
        this.openDatabase();
        String []columns={ContactHelper.COL_NAME,ContactHelper.COL_ROLL,ContactHelper.COL_EMAIL,ContactHelper.COL_PHONENO};
        String selection=ContactHelper.COL_USER_NAME+" LIKE ? ";
        String []argument={userName};
        Cursor cursor=database.query(ContactHelper.CONTACT_TABLE,columns,selection,argument,null,null,null,null);
        cursor.moveToFirst();
        String name="",roll="",email="",phone="";
        int count=0;
        while(count<cursor.getCount())
        {
            name=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_NAME));
            roll=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_ROLL));
            email=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_EMAIL));
            phone=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_PHONENO));
            count ++;
        }

        Contact contact=new Contact(name,Integer.valueOf(roll),email,phone);
        return contact;
    }
    public boolean editInfos(String userName,String name,String email,String phone)
    {
        this.openDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ContactHelper.COL_NAME,name);
        contentValues.put(ContactHelper.COL_EMAIL,email);
        contentValues.put(ContactHelper.COL_PHONENO,phone);
        String selection=ContactHelper.COL_USER_NAME+" LIKE ? ";
        String []argument={userName};

        int updateFlag=database.update(ContactHelper.CONTACT_TABLE,contentValues,selection,argument);
        return (updateFlag>0);
    }

    public ArrayList<Contact> getAllContact() {
        ArrayList<Contact> contactList = new ArrayList<>();
        this.openDatabase();
        Cursor cursor = database.query(ContactHelper.CONTACT_TABLE, null, null, null, null, null,
                null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int contactId = cursor.getInt(cursor.getColumnIndex(ContactHelper.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_NAME));
                int roll = cursor.getInt(cursor.getColumnIndex(ContactHelper.COL_ROLL));
                String email = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_EMAIL));
                String phoneNo = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_PHONENO));
                String batchNo = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_BATCHNO));
                String macAddress = cursor.getString(cursor.getColumnIndex(ContactHelper.COL_MAC_ADDRESS));
                String userName=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_USER_NAME));
                String passWord=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_PASSWORD));
                String presence=cursor.getString(cursor.getColumnIndex(ContactHelper.COL_PRESENCE));

                contact = new Contact(contactId, name, roll, email, phoneNo, batchNo,userName,passWord, macAddress,presence);
                contactList.add(contact);
                cursor.moveToNext();
            }
            this.closeDatabase();
        }
        return contactList;
    }
    public boolean updateContact(int roll, Contact contact){
        this.openDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ContactHelper.COL_NAME, contact.getName());
        cv.put(ContactHelper.COL_ROLL, contact.getRoll());
        cv.put(ContactHelper.COL_EMAIL, contact.getEmail());
        cv.put(ContactHelper.COL_PHONENO, contact.getPhoneNo());
        cv.put(ContactHelper.COL_BATCHNO, contact.getBatchNo());
        cv.put(ContactHelper.COL_MAC_ADDRESS, contact.getMacAddress());

        int update=database.update(ContactHelper.CONTACT_TABLE,cv,ContactHelper.COL_ROLL+
         "="+ roll,null);
        this.closeDatabase();

        return (update>0);
    }
    public boolean deleteContact(int roll){
        this.openDatabase();

        int delete=database.delete(ContactHelper.CONTACT_TABLE,ContactHelper.COL_ROLL+"="+ roll,null);
        this.closeDatabase();

        return (delete>0);
    }
    public boolean deleteAllContact(){
        this.openDatabase();
        int deleted=database.delete(ContactHelper.CONTACT_TABLE,null,null);
        this.closeDatabase();

        return (deleted>0);
    }
    public Cursor getInfo()
    {
        this.openDatabase();
        String[] columns={ContactHelper.COL_USER_NAME,ContactHelper.COL_PASSWORD};
        //String selection=
        Cursor cursor=database.query(ContactHelper.CONTACT_TABLE,columns,null,null,null,null,null,null);
        return cursor;
    }
}
