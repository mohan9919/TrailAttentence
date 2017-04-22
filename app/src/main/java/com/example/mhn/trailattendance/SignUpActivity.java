package com.example.mhn.trailattendance;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MHN on 12/5/2016.
 */

public class SignUpActivity extends AppCompatActivity {
    EditText nameET;
    EditText rollET;
    EditText emailET;
    EditText phoneNoET;
    EditText batchNoET;
    EditText userNameET;
    EditText passwordET;
    EditText retypePassEt;

    Button submitBtn;
    //EditText macAddressET;
    BluetoothDevice bluetoothDevice;
    BluetoohSignUpAdapter bluetoohSignUpAdapter;
    private ArrayList<String> bluetoothInfoList;
    private Set<String> bluetoothInfoSet;
    String macFromLv;

    ListView deviceListLv;


    BluetoothAdapter bluetoothAdapter;
    private static final IntentFilter FIND_BLUETOOTH_ACTION=new IntentFilter(BluetoothDevice.ACTION_FOUND);
    private static final String FIND_DISCOVERABLE_DEVICE=BluetoothDevice.EXTRA_DEVICE;

    ListView listView;

    int id;
    String name;
    String rollNo;
    String emailId;
    String phoneNo;
    String batchNo;
    String userName;
    String password;
    String macAddress="";
    String retypePass;


    Contact contact;
    ContactManeger maneger;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        submitBtn=(Button)findViewById(R.id.submit_btn) ;
        nameET=(EditText)findViewById(R.id.sign_up_name_et);
        rollET=(EditText)findViewById(R.id.sign_up_roll_no_et);
        emailET=(EditText)findViewById(R.id.sign_up_email_et);
        phoneNoET=(EditText)findViewById(R.id.sign_up_phone_no_et);
        batchNoET=(EditText)findViewById(R.id.sign_up_batch_no_et);
        userNameET=(EditText)findViewById(R.id.sign_up_user_name_et);
        passwordET=(EditText)findViewById(R.id.sign_up_password_et);
        retypePassEt=(EditText)findViewById(R.id.sign_up_retype_password_et);

        submitBtn.setVisibility(View.INVISIBLE);
        //macAddressET=(EditText)findViewById(R.id.sign_up_mac_address_et);
        bluetoothInfoList=new ArrayList<String>();
        deviceListLv=(ListView)findViewById(R.id.sign_up_lv);
        bluetoothInfoSet=new HashSet<>();

        maneger=new ContactManeger(this);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled())
        {
            Intent intent=new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,101);
        }
        else
        {
            new AsyncBluetoothTask().execute();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==101 && resultCode==RESULT_OK)
        {
            new AsyncBluetoothTask().execute();
        }
    }

    public void clikeSubmitBtn(View view) {
       // new AsyncBluetoothTask().execute();

        //??????


        //String null="";
        String nullEt="";
        name = nameET.getText().toString();
        rollNo = rollET.getText().toString();
        emailId = emailET.getText().toString();
        phoneNo = phoneNoET.getText().toString();
        batchNo = batchNoET.getText().toString();
        userName=userNameET.getText().toString();
        password=passwordET.getText().toString();
        retypePass=retypePassEt.getText().toString();
        macAddress=macFromLv;
        if(name.equals(nullEt)||emailId.equals(nullEt)||phoneNo.equals(nullEt)||
                batchNo.equals(nullEt)||userName.equals(nullEt)||password.equals(nullEt)||
                rollNo.equals(nullEt)||retypePass.equals(nullEt)||macFromLv.equals(nullEt))
        {
            Toast.makeText(this, "Each fields are required to be registered.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(password.equals(retypePass))
            {

                contact = new Contact(name, Integer.valueOf(rollNo), emailId, phoneNo, batchNo, userName, password, macAddress);
                boolean inserted = maneger.addContact(contact);
                if (inserted) {
                    nameET.getText().clear();
                    rollET.getText().clear();
                    emailET.getText().clear();
                    phoneNoET.getText().clear();
                    batchNoET.getText().clear();
                    userNameET.getText().clear();
                    passwordET.getText().clear();
                    retypePassEt.getText().clear();
                    //macAddressET.getText().clear();
                    Toast.makeText(this, "Contact Save", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to save Contact", Toast.LENGTH_SHORT).show();
                }

                Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "Password Didn't Match", Toast.LENGTH_SHORT).show();
            }


        }



    }
    private class AsyncBluetoothTask extends AsyncTask<Void,Integer,Void>
    {
        @Override
        protected void onPreExecute() {
            //Toast.makeText(MainActivity.this, "Started PreExecute", Toast.LENGTH_SHORT).show();
            bluetoothInfoList.clear();
            bluetoothInfoSet.clear();
            if(bluetoothAdapter.isDiscovering())
            {
                bluetoothAdapter.cancelDiscovery();
            }
            bluetoothAdapter.startDiscovery();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            for(int i =0;i<4;i++)
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
            registerReceiver(new DiscoverBluetooth(),FIND_BLUETOOTH_ACTION);
           /* for(String set:bluetoothInfoSet)
            {
                Toast.makeText(MainActivity.this, set, Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(MainActivity.this, "Ended onProgressUpdate", Toast.LENGTH_SHORT).show();*/
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(bluetoothAdapter.isDiscovering())
            {
                bluetoothAdapter.cancelDiscovery();
                //bluetoothAdapter.disable();
            }
            //String value;
            bluetoohSignUpAdapter=new BluetoohSignUpAdapter(SignUpActivity.this,bluetoothInfoList);
            deviceListLv.setAdapter(bluetoohSignUpAdapter);
            deviceListLv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    macFromLv=bluetoothInfoList.get(position);
                    submitBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(SignUpActivity.this, macFromLv, Toast.LENGTH_SHORT).show();
                }
            });

            for(String set:bluetoothInfoSet)
            {
                bluetoothInfoList.add(set);
                Toast.makeText(SignUpActivity.this, "Count:"+bluetoothInfoSet.size()+"\nInfos:"+set, Toast.LENGTH_SHORT).show();
                bluetoohSignUpAdapter.notifyDataSetChanged();
            }
        }
    }

    private class DiscoverBluetooth extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(SignUpActivity.this, "Entererd onReceive", Toast.LENGTH_SHORT).show();
            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND))
            {
                //Toast.makeText(MainActivity.this, "Started onReceive", Toast.LENGTH_SHORT).show();
                bluetoothDevice=(BluetoothDevice) intent.getParcelableExtra(FIND_DISCOVERABLE_DEVICE);
                String deviceMac=bluetoothDevice.getAddress();
                String deviceName=bluetoothDevice.getName();
                bluetoothInfoSet.add(deviceMac);
                Toast.makeText(SignUpActivity.this, "MAC:"+deviceMac+"\nNAME:"+deviceName, Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "Ended onReceive", Toast.LENGTH_SHORT).show();



            }
            else
            {
                Toast.makeText(SignUpActivity.this, "Didn't Match", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
        finish();
        startActivity(intent);
    }
}
