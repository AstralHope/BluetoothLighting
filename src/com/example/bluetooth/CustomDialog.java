package com.example.bluetooth;

import java.util.ArrayList;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomDialog extends Dialog implements OnItemClickListener,
    android.view.View.OnClickListener {
  private ListView mListViewMatchedDevices;
  private ListView mListViewNewDevices;
  private Button mBtnScan;
  private Bluetooth mBluetooth;
  private TextView mTextViewNewDevices;
  private ArrayAdapter<String> mArrayAdapterMatchedDevices;
  private ArrayAdapter<String> mArrayAdapterNewDevices;
  private BroadcastReceiver mReceiver;
  private IntentFilter mFilter;
  private Context mContext;
  private ProgressBar mProgressbar;

  public CustomDialog(Context context, Bluetooth bluetooth) {
    super(context, R.style.customeDialog);
    mContext = context;
    mBluetooth = bluetooth;
    mArrayAdapterMatchedDevices = new ArrayAdapter<String>(context,R.layout.textview);
    mArrayAdapterNewDevices = new ArrayAdapter<String>(context,R.layout.textview);

  }

  @Override
  protected void onStart() {
    super.onStart();
    mReceiver = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
          case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
            mBtnScan.setText("Scan New Devices");
            mProgressbar.setVisibility(View.GONE);
            break;
          case BluetoothDevice.ACTION_FOUND:
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            mArrayAdapterNewDevices.add(device.getName() + "\n" + device.getAddress());
            mArrayAdapterNewDevices.notifyDataSetChanged();
            mBluetooth.addPairedDevice(device);
            break;
          case BluetoothDevice.ACTION_ACL_CONNECTED:     
            mProgressbar.setVisibility(View.GONE);
            dismiss();
            break;
          case BluetoothDevice.ACTION_PAIRING_REQUEST:
            mProgressbar.setVisibility(View.VISIBLE);
            break;
          default:
        }
      }
    };
    mFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    mContext.registerReceiver(mReceiver, mFilter);
    mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    mContext.registerReceiver(mReceiver, mFilter);
    mFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
    mContext.registerReceiver(mReceiver, mFilter);
    mFilter = new IntentFilter(BluetoothDevice.ACTION_PAIRING_REQUEST);
    mContext.registerReceiver(mReceiver, mFilter);

    mArrayAdapterMatchedDevices.clear();
    mArrayAdapterNewDevices.clear();
    ArrayList<String> deviceArry =  mBluetooth.scanMatchedDevices();
    for (int i = 0; i < deviceArry.size(); i++) {
       mArrayAdapterMatchedDevices.add(deviceArry.get(i));
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    mContext.unregisterReceiver(mReceiver);
    mBtnScan.setText("Scan New Devices");
    mProgressbar.setVisibility(View.GONE);
    mTextViewNewDevices.setVisibility(View.GONE);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dialog_main);
    mProgressbar = (ProgressBar) findViewById(R.id.progress);
    mBtnScan = (Button) findViewById(R.id.btnScan);
    mBtnScan.setOnClickListener(this);
    mTextViewNewDevices = (TextView) findViewById(R.id.textViewNewDevices);
    mListViewMatchedDevices = (ListView) findViewById(R.id.listViewMatchedDevice);
    mListViewMatchedDevices.setAdapter(mArrayAdapterMatchedDevices);
    mListViewMatchedDevices.setOnItemClickListener(this);

    mListViewNewDevices = (ListView) findViewById(R.id.listViewNewDevices);
    mListViewNewDevices.setAdapter(mArrayAdapterNewDevices);
    mListViewNewDevices.setOnItemClickListener(this);

  }

  @Override
  public void onClick(View v) {
    mArrayAdapterNewDevices.clear();
    mArrayAdapterNewDevices.notifyDataSetChanged();
    mBtnScan.setText("Scanning");
    mProgressbar.setVisibility(View.VISIBLE);
    mTextViewNewDevices.setVisibility(View.VISIBLE);
    mBluetooth.scanNewDevices();
  }

  public void setBtnText(String text) {
    mBtnScan.setText(text);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    TextView textview = (TextView) view;
    String addr = textview.getText().toString();
    int pos = addr.indexOf("\n");
    addr = addr.substring(pos + 1);
    mProgressbar.setVisibility(View.VISIBLE);
    mBluetooth.connectThread(addr);
  }
}
