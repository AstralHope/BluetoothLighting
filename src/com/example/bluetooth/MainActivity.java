package com.example.bluetooth;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
/**
 * @author Huang Xiao Long
 * @version 1.0
 * */
public class MainActivity extends Activity implements OnSeekBarChangeListener, OnClickListener {

  private CustomView mView;
  private int mRed;
  private int mGreen;
  private int mBlue;
  private SeekBar mSeekBarRed;
  private SeekBar mSeekBarGreen;
  private SeekBar mSeekBarBlue;
  private Button mBtnConnectManager;
  private Bluetooth mBlueTooth;
  private CustomDialog mCustomDialog;
  private Button mBtn1;
  private Button mBtn2;
  private Button mBtn3;
  private Button mBtn4;
  private Button mBtn5;
  private Button mBtn6;
  private Button mBtn7;
  private Button mBtn8;
  private Button mBtnTurnOff;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mBlueTooth = new Bluetooth(this);
    mCustomDialog = new CustomDialog(this, mBlueTooth);
    mView = (CustomView) findViewById(R.id.view1);
    mBtnConnectManager = (Button) findViewById(R.id.btnConnectManager);
    mSeekBarRed = (SeekBar) findViewById(R.id.seekBarRed);
    mSeekBarGreen = (SeekBar) findViewById(R.id.seekBarGreen);
    mSeekBarBlue = (SeekBar) findViewById(R.id.seekBarBlue);
    mSeekBarRed.setOnSeekBarChangeListener(this);
    mSeekBarGreen.setOnSeekBarChangeListener(this);
    mSeekBarBlue.setOnSeekBarChangeListener(this);
    mBtnConnectManager.setOnClickListener(this);
    mBtn1 = (Button) findViewById(R.id.Button01);
    mBtn2 = (Button) findViewById(R.id.Button02);
    mBtn3 = (Button) findViewById(R.id.Button03);
    mBtn4 = (Button) findViewById(R.id.Button04);
    mBtn5 = (Button) findViewById(R.id.Button05);
    mBtn6 = (Button) findViewById(R.id.Button06);
    mBtn7 = (Button) findViewById(R.id.Button07);
    mBtn8 = (Button) findViewById(R.id.Button08);
    mBtnTurnOff = (Button) findViewById(R.id.btnTurnoff);
    mBtnTurnOff.setOnClickListener(this);
    mBtn1.setOnClickListener(this);
    mBtn2.setOnClickListener(this);
    mBtn3.setOnClickListener(this);
    mBtn4.setOnClickListener(this);
    mBtn5.setOnClickListener(this);
    mBtn6.setOnClickListener(this);
    mBtn7.setOnClickListener(this);
    mBtn8.setOnClickListener(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    mBlueTooth.close();
  }

  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    switch (seekBar.getId()) {
      case R.id.seekBarRed:
        mRed = seekBar.getProgress();
        break;
      case R.id.seekBarGreen:
        mGreen = seekBar.getProgress();
        break;
      case R.id.seekBarBlue:
        mBlue = seekBar.getProgress();
        break;
      default:
        break;
    }
    mView.setColor(Color.rgb(mRed, mGreen, mBlue));
    mView.invalidate();
    if (mBlueTooth.isConnected()) {
      mBlueTooth.writeWithChar("r",mRed,19);
      mBlueTooth.writeWithChar("g",mGreen,19);
      mBlueTooth.writeWithChar("b",mBlue,19);
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {

  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.Button01:
        mBlueTooth.writeWithChar("r",Color.red(getResources().getColor(R.color.btnOneColor)),19);
        mBlueTooth.writeWithChar("g",Color.green(getResources().getColor(R.color.btnOneColor)),19);
        mBlueTooth.writeWithChar("b",Color.blue(getResources().getColor(R.color.btnOneColor)),19);
        mView.setColor(getResources().getColor(R.color.btnOneColor));
        mView.invalidate();
        break;
      case R.id.Button02:
        mBlueTooth.writeWithChar("r",Color.red(getResources().getColor(R.color.btnTwoColor)),19);
        mBlueTooth.writeWithChar("g",Color.green(getResources().getColor(R.color.btnTwoColor)),19);
        mBlueTooth.writeWithChar("b",Color.blue(getResources().getColor(R.color.btnTwoColor)),19);
        mView.setColor(getResources().getColor(R.color.btnTwoColor));
        mView.invalidate();
        break;
      case R.id.Button03:
        mBlueTooth.writeWithChar("r",Color.red(getResources().getColor(R.color.btnThreeColor)),19);
        mBlueTooth.writeWithChar("g",Color.green(getResources().getColor(R.color.btnThreeColor)),19);
        mBlueTooth.writeWithChar("b",Color.blue(getResources().getColor(R.color.btnThreeColor)),19);
        mView.setColor(getResources().getColor(R.color.btnThreeColor));
        mView.invalidate();
        break;
      case R.id.Button04:
        mBlueTooth.writeWithChar("r",Color.red(getResources().getColor(R.color.btnFourColor)),19);
        mBlueTooth.writeWithChar("g",Color.green(getResources().getColor(R.color.btnFourColor)),19);
        mBlueTooth.writeWithChar("b",Color.blue(getResources().getColor(R.color.btnFourColor)),19);
        mView.setColor(getResources().getColor(R.color.btnFourColor));
        mView.invalidate();
        break;
      case R.id.Button05:
        mBlueTooth.writeWithChar("r",Color.red(getResources().getColor(R.color.btnFiveColor)),19);
        mBlueTooth.writeWithChar("g",Color.green(getResources().getColor(R.color.btnFiveColor)),19);
        mBlueTooth.writeWithChar("b",Color.blue(getResources().getColor(R.color.btnFiveColor)),19);
        mView.setColor(getResources().getColor(R.color.btnFiveColor));
        mView.invalidate();
        break;
      case R.id.Button06:
        mBlueTooth.writeWithChar("r",Color.red(getResources().getColor(R.color.btnSixColor)),19);
        mBlueTooth.writeWithChar("g",Color.green(getResources().getColor(R.color.btnSixColor)),19);
        mBlueTooth.writeWithChar("b",Color.blue(getResources().getColor(R.color.btnSixColor)),19);
        mView.setColor(getResources().getColor(R.color.btnSixColor));
        mView.invalidate();
        break;
      case R.id.Button07:
        mBlueTooth.writeWithChar("r",Color.red(getResources().getColor(R.color.btnSevenColor)),19);
        mBlueTooth.writeWithChar("g",Color.green(getResources().getColor(R.color.btnSevenColor)),19);
        mBlueTooth.writeWithChar("b",Color.blue(getResources().getColor(R.color.btnSevenColor)),19);
        mView.setColor(getResources().getColor(R.color.btnSevenColor));
        mView.invalidate();
        break;
      case R.id.Button08:
        mBlueTooth.writeWithChar("r",Color.red(getResources().getColor(R.color.btnEightColor)),19);
        mBlueTooth.writeWithChar("g",Color.green(getResources().getColor(R.color.btnEightColor)),19);
        mBlueTooth.writeWithChar("b",Color.blue(getResources().getColor(R.color.btnEightColor)),19);
        mView.setColor(getResources().getColor(R.color.btnEightColor));
        mView.invalidate();
        break;
      case R.id.btnConnectManager:
        if (!mBlueTooth.isOpen()) {
          mBlueTooth.open();
        } else {
          if (!mBlueTooth.isConnected()) {
            mCustomDialog.show();
          } else {
            mBlueTooth.close();
            mBtnConnectManager.setText("Connect");
          }
        }
      case R.id.btnTurnoff:
        if (mBlueTooth.isConnected()) {
          mBlueTooth.writeWithChar("r",0,19);
          mBlueTooth.writeWithChar("g",0,19);
          mBlueTooth.writeWithChar("b",0,19);
        }
        break;
      default:
        break;
    }
  }
}
