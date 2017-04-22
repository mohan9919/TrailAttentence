package com.example.mhn.trailattendance;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static android.R.attr.button;

public class AdminActivity extends AppCompatActivity {
    ListView listView;
    ListView startClassLv;
    Button adminShowInfoBtn;
    ArrayList<Contact> contactList;
    Contact contact;
    AdminShowInfoAdapter adapter;
    ContactManeger contactManeger;
    StartClassAdapter startClassAdapter;
    int presenceFlag=0;


    long startTime;

   // Set set=new HashSet()

    BluetoothDevice bluetoothDevice;
    BluetoohSignUpAdapter bluetoohSignUpAdapter;
    private ArrayList<String> bluetoothInfoList;
    private Set<String> bluetoothInfoSet;
    private ArrayList<String> registeredDevice;
    String macFromLv;

    ListView deviceListLv;

    BluetoothAdapter bluetoothAdapter;
    private static final IntentFilter FIND_BLUETOOTH_ACTION=new IntentFilter(BluetoothDevice.ACTION_FOUND);
    private static final String FIND_DISCOVERABLE_DEVICE=BluetoothDevice.EXTRA_DEVICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        listView=(ListView)findViewById(R.id.admin_trainee_lv);
        startClassLv=(ListView)findViewById(R.id.start_class_lv);
        contactList=new ArrayList<Contact>();
        adminShowInfoBtn=(Button)findViewById(R.id.show_trainee_info_btn);
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        bluetoothInfoList=new ArrayList<String>();
        deviceListLv=(ListView)findViewById(R.id.sign_up_lv);
        bluetoothInfoSet=new HashSet<>();
        registeredDevice=new ArrayList<String>();

        //listView.setAdapter(adapter);
        contactManeger=new ContactManeger(this);





    }
    public final String TURN_ON_BLUETOOTH=bluetoothAdapter.ACTION_REQUEST_ENABLE;



    public void clickedShowTraineeinfo(View view) {
        contactList=contactManeger.getAllContact();
        adapter=new AdminShowInfoAdapter(this,contactList);
        listView.setAdapter(adapter);
    }

    public void clickedStartClass(View view) {
        /*contactList=contactManeger.getAllContact();
        startClassAdapter=new StartClassAdapter(this,contactList);*/
        if(!bluetoothAdapter.isEnabled())
        {
            Intent intent=new Intent(TURN_ON_BLUETOOTH);
            startActivityForResult(intent,101);
        }
        else
        {
            new AdminActivity.AsyncBluetoothTask().execute();
        }





    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==101)
        {
            new AdminActivity.AsyncBluetoothTask().execute();
        }
    }

    private class AsyncBluetoothTask extends AsyncTask<Void,Integer,Void>
    {
        @Override
        protected void onPreExecute() {
            startTime=System.currentTimeMillis();
            //Toast.makeText(MainActivity.this, "Started PreExecute", Toast.LENGTH_SHORT).show();
            bluetoothInfoList.clear();
            bluetoothInfoSet.clear();
            contactManeger.setPesenceToDefault();
            contactList=contactManeger.getAllContact();
            startClassAdapter=new StartClassAdapter(AdminActivity.this,contactList);
            startClassLv.setAdapter(startClassAdapter);
            startClassAdapter.notifyDataSetChanged();

            if(bluetoothAdapter.isDiscovering())
            {
                bluetoothAdapter.cancelDiscovery();
            }
            bluetoothAdapter.startDiscovery();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            for(int i =0;i<10;i++)
            {
                try
                {
                    //registerReceiver(new DiscoverBluetooth(),FIND_BLUETOOTH_ACTION);
                    publishProgress(i);
                    Thread.sleep(5000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //Toast.makeText(MainActivity.this, String.valueOf(values[0]), Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, "Started onProgressUpdate : "+values[0], Toast.LENGTH_SHORT).show();
            registerReceiver(new AdminActivity.DiscoverBluetooth(),FIND_BLUETOOTH_ACTION);
            long currentTime=System.currentTimeMillis();

            for(String set:bluetoothInfoSet)
            {
                bluetoothInfoList.add(set);
                Toast.makeText(AdminActivity.this, "Count:"+bluetoothInfoSet.size()+"\nInfos:"+set, Toast.LENGTH_SHORT).show();
//                bluetoohSignUpAdapter.notifyDataSetChanged();
            }
            String presence="";
            int roll=0;
            String name="";
            boolean present=false;
            Bundle bundle=new Bundle();

            for(String macPresent:bluetoothInfoList)
            {
                for(Contact contact:contactList)
                {
                    if(!macPresent.equals(contact.getMacAddress()))
                    {
                        present=false;
                    }
                    else
                    {
                        /*for(String alreadyPresent:registeredDevice)
                        {
                            if(macPresent .equals(alreadyPresent))
                            {
                                presenceFlag=1;
                            }
                            else
                            {
                                presenceFlag=0;

                           }
                        }*/

                        //if(presenceFlag==0)
                        //{
                           // registeredDevice.add(macPresent);
                            //if((currentTime-startTime)<15000)
                           // {
                                present=true;
                                presence="Present";
                                roll=contact.getRoll();
                                name=contact.getName();
                            //}
                            /*else if((currentTime-startTime)>15000)
                            {
                                present=true;
                                presence="Late";
                                roll=contact.getRoll();
                                name=contact.getName();
                            }*/
                        //}




                    }
                    if(present)


                    {
                        Toast.makeText(AdminActivity.this, "Present "+name, Toast.LENGTH_SHORT).show();
                        contactManeger.updatePresence(roll,presence);
                        //hashMap.put(name,roll);
                        //bundle.putInt(name,roll);
                        //startClassAdapter.setPresence(presence,roll);
                    }
                }
            }

            contactList=contactManeger.getAllContact();
            startClassAdapter=new StartClassAdapter(AdminActivity.this,contactList);
            startClassLv.setAdapter(startClassAdapter);
            startClassAdapter.notifyDataSetChanged();

           /* for(String set:bluetoothInfoSet)
            {
                Toast.makeText(MainActivity.this, set, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(MainActivity.this, "Ended onProgressUpdate", Toast.LENGTH_SHORT).show();*/
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            if(bluetoothAdapter.isDiscovering())
            {
                bluetoothAdapter.cancelDiscovery();
                //bluetoothAdapter.disable();
            }
            //String value;
            //bluetoohSignUpAdapter=new BluetoohSignUpAdapter(AdminActivity.this,bluetoothInfoList);
            //deviceListLv.setAdapter(bluetoohSignUpAdapter);
/*
            deviceListLv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    macFromLv=bluetoothInfoList.get(position);
                    //submitBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(AdminActivity.this, macFromLv, Toast.LENGTH_SHORT).show();
                }
            });
*/

            /*for(String set:bluetoothInfoSet)
            {
                bluetoothInfoList.add(set);
                Toast.makeText(AdminActivity.this, "Count:"+bluetoothInfoSet.size()+"\nInfos:"+set, Toast.LENGTH_SHORT).show();
//                bluetoohSignUpAdapter.notifyDataSetChanged();
            }*/
            //HashMap hashMap=new HashMap();
            /*String presence="";
            int roll=0;
            String name="";
            boolean present=false;
            Bundle bundle=new Bundle();
            for(String macPresent:bluetoothInfoList)
            {
                for(Contact contact:contactList)
                {
                    if(!macPresent.equals(contact.getMacAddress()))
                    {
                        present=false;
                    }
                    else
                    {
                        present=true;
                        presence="Present";
                        roll=contact.getRoll();
                        name=contact.getName();


                    }
                    if(present)


                    {
                        Toast.makeText(AdminActivity.this, "Present "+name, Toast.LENGTH_SHORT).show();
                        contactManeger.updatePresence(roll,presence);
                        //hashMap.put(name,roll);
                        //bundle.putInt(name,roll);
                        //startClassAdapter.setPresence(presence,roll);
                    }
                }
            }*/




            contactList=contactManeger.getAllContact();
            startClassAdapter=new StartClassAdapter(AdminActivity.this,contactList);
            startClassLv.setAdapter(startClassAdapter);
            startClassAdapter.notifyDataSetChanged();
        }
    }

    private class DiscoverBluetooth extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(AdminActivity.this, "Entererd onReceive", Toast.LENGTH_SHORT).show();
            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND))
            {
                //Toast.makeText(MainActivity.this, "Started onReceive", Toast.LENGTH_SHORT).show();
                bluetoothDevice=(BluetoothDevice) intent.getParcelableExtra(FIND_DISCOVERABLE_DEVICE);
                String deviceMac=bluetoothDevice.getAddress();
                String deviceName=bluetoothDevice.getName();
                //long time=System.currentTimeMillis();
                bluetoothInfoSet.add(deviceMac);
                //receiveTimeSet.add(String.valueOf(time));

                //Toast.makeText(AdminActivity.this, "MAC:"+deviceMac+"\nNAME:"+deviceName, Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "Ended onReceive", Toast.LENGTH_SHORT).show();



            }
            else
            {
                Toast.makeText(AdminActivity.this, "Didn't Match", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(AdminActivity.this,MainActivity.class);
        startActivity(intent);
        //Toast.makeText(MainActivity.this, "Admin", Toast.LENGTH_SHORT).show();
        finish();
    }
}
