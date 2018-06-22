package com.example.bluetooth;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
public class Bluetooth extends Thread {
  private final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
  private BluetoothAdapter mBluetoothAdapter;
  private Context mContext;

  private BroadcastReceiver mReceiver;
  private BluetoothSocket mSocket;
  private OutputStream mOutStream;
  private Set<BluetoothDevice> mPairedDevice;



  public Bluetooth(Context context) {
    this.mContext = context;

    mPairedDevice = new HashSet<BluetoothDevice>();

  }

  public boolean isOpen() {
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (mBluetoothAdapter == null) {
      return false;
    }
    if (!mBluetoothAdapter.isEnabled()) {
      return false;
    } else {
      return true;
    }
  }

  public void open() {
    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    mContext.startActivity(enableBtIntent);
  }

  public ArrayList<String> scanMatchedDevices() {
    Set<BluetoothDevice> pairedDevice = mBluetoothAdapter.getBondedDevices();
    // If there are paired devices
    if (pairedDevice.size() > 0) {
      // Loop through paired devices
      for (BluetoothDevice device : pairedDevice) {

        ArrayList<String> deviceArry = new ArrayList<String>();
        deviceArry.add(device.getName() + "\n" + device.getAddress());
        mPairedDevice.add(device);
        return deviceArry;
      }
    };
    return null;
  }

  public void addPairedDevice(BluetoothDevice device) {
    mPairedDevice.add(device);
  }



  public void scanNewDevices() {
    mBluetoothAdapter.startDiscovery();
  }


  public void clean() {
    mContext.unregisterReceiver(mReceiver);
    close();
  }

  public void connectThread(String addr) {
    for (BluetoothDevice bluetoothDevice : mPairedDevice) {
      if (bluetoothDevice.getAddress().equals(addr)) {
        connectThread(bluetoothDevice);
      }
    }
  }

  public void connectThread(BluetoothDevice device) {
    // Use a temporary object that is later assigned to mSocket,
    // because mmSocket is final
    BluetoothSocket tmp = null;
    // Get a BluetoothSocket to connect with the given BluetoothDevice
    try {
      tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(SPP_UUID));
    } catch (IOException e) {
    }
    mSocket = tmp;
    mBluetoothAdapter.cancelDiscovery();
    Thread thd = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          mSocket.connect();
          manageConnectedSocket(mSocket);
        } catch (IOException connectException) {
          try {
            mSocket.close();
          } catch (IOException closeException) {
          }
          return;
        }
      }
    });
    thd.start();

  }

  public void manageConnectedSocket(BluetoothSocket socket) {
    OutputStream tempOut = null;
    try {
      tempOut = socket.getOutputStream();
    } catch (IOException e) {
    }
    mOutStream = tempOut;
  }

  public void writeColor(String fstFlag, int red, String sedFlag, int green, String trdFlag,
      int blue) {
    byte[] data = new byte[6];
    data[0] = (byte) (fstFlag.getBytes()[0]);
    data[1] = (byte) (0xFF & red);
    data[2] = (byte) (sedFlag.getBytes()[0]);
    data[3] = (byte) (0xFF & green);
    data[4] = (byte) (trdFlag.getBytes()[0]);
    data[3] = (byte) (0xFF & blue);
    write(data);
  }


  public void writeColor(int red, int green, int blue) {
    byte[] data = new byte[3];
    data[1] = (byte) (0xFF & red);
    data[3] = (byte) (0xFF & green);
    data[5] = (byte) (0xff & blue);
    write(data);
  }

  public void writeColor(int color) {
    byte[] data = new byte[6];
    data[0] = (byte) ("r".getBytes()[0]);
    data[1] = (byte) (0xFF & Color.red(color));
    data[2] = (byte) ("g".getBytes()[0]);
    data[3] = (byte) (0xFF & Color.green(color));
    data[4] = (byte) ("b".getBytes()[0]);
    data[5] = (byte) (0xff & Color.blue(color));
    write(data);
  }

  public void writeRed(int color) {
    byte[] data = new byte[1];
    data[1] = (byte) (0xFF & Color.red(color));
    write(data);
  }

  public void writeGreen(int color) {
    byte[] data = new byte[1];
    data[1] = (byte) (0xFF & Color.red(color));
    write(data);
  }

  public void writeBlue(int color) {
    byte[] data = new byte[1];
    data[1] = (byte) (0xFF & Color.red(color));
    write(data);
  }

  public void writeRed(String flag, int color) {
    byte[] data = new byte[2];
    data[0] = (byte) (flag.getBytes()[0]);
    data[1] = (byte) (0xFF & Color.red(color));
    write(data);
  }

  public void writeRed(String flag, int color, int ack) {
    byte[] data = new byte[3];
    data[0] = (byte) (flag.getBytes()[0]);
    data[1] = (byte) (0xff & Color.green(color));
    data[3] = (byte) (0xff & ack);
    write(data);
  }

  public void writeGreen(String flag, int color, int ack) {
    byte[] data = new byte[3];
    data[0] = (byte) (flag.getBytes()[0]);
    data[1] = (byte) (0xff & Color.green(color));
    data[3] = (byte) (0xff & ack);
    write(data);
  }

  public void writeBlue(String flag, int color, int ack) {
    byte[] data = new byte[3];
    data[0] = (byte) (flag.getBytes()[0]);
    data[1] = (byte) (0xff & Color.green(color));
    data[3] = (byte) (0xff & ack);
    write(data);
  }

  public void writeGreen(String flag, int color) {
    byte[] data = new byte[2];
    data[0] = (byte) (flag.getBytes()[0]);
    data[1] = (byte) (0xFF & Color.green(color));
    write(data);
  }

  public void writeBlue(String flag, int color) {
    byte[] data = new byte[2];
    data[0] = (byte) (flag.getBytes()[0]);
    data[1] = (byte) (0xff & Color.blue(color));
    write(data);
  }

  public void writeWithChar(String flag, int color, int ack) {
    byte[] str = itoa(color);
    byte[] data = new byte[str.length + 2];
    for (int i = 0; i < str.length; i++) {
      data[i + 1] = str[i];
    }
    data[0] = (byte) (flag.getBytes()[0]);
    data[str.length + 1] = (byte) (0Xff & ack);
    write(data);
  }
  public byte[] itoa(int color) {
    int i = 0;
    int temp = color ;
    while (true) {
      temp = temp / 10;
      if (0 == temp) {
        break;
      } else {
        i++;
      }
    }
    byte[] str = new byte[i + 1];
    for (int j = 0; j < str.length; j++) {
      str[i--] = (byte) (color % 10 + 48);
      color /= 10 ;
    }
    return str;
  }

  public Boolean write(final byte[] bytes) {
    if (true == isConnected()) {
      Thread thd = new Thread(new Runnable() {
        public void run() {
          try {
            mOutStream.write(bytes);
          } catch (IOException e) {
          }

        }
      });
      thd.start();
      return true;
    } else {
      return false;
    }

  }

  public boolean isConnected() {
    if (null == mSocket) {
      return false;
    }
    if (true == mSocket.isConnected()) {
      return true;
    } else {
      return false;
    }
  }

  /** Will cancel an in-progress connection, and close the socket */
  public void close() {
    try {
      if (null == mSocket) {
        return;
      } else {
        mSocket.close();
      }
    } catch (IOException e) {
    }
  }
}
