package com.example.mhn.trailattendance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rahma on 12/9/2016.
 */


public class BluetoohSignUpAdapter extends ArrayAdapter {
    Context context;
    ArrayList<String> deviceList;
    TextView deviceAddressTv;
    TextView deviceNameTv;
    public BluetoohSignUpAdapter(Context context, ArrayList<String> deviceList) {
        super(context,R.layout.bluetooth_signup_adapter_layout,deviceList);
        this.context=context;
        this.deviceList=deviceList;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.bluetooth_signup_adapter_layout,parent,false);

        deviceAddressTv=(TextView)convertView.findViewById(R.id.tv_adapter_mac);
        deviceNameTv=(TextView)convertView.findViewById(R.id.tv_adapter_devicename);

        deviceNameTv.setText(deviceList.get(position));
        return convertView;
    }
}
