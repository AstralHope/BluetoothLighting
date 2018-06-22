package com.example.bluetooth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {
  private Paint paint = new Paint();
  private int mColor;

  public CustomView(Context context,AttributeSet attrs) {
    super(context,attrs);
    paint.setAntiAlias(true); // 抗锯齿
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (Color.rgb(0, 0, 0) == mColor) {
      paint.setColor(getResources().getColor(R.color.backgroundColor));
    }else {
      paint.setColor(mColor); // 路径的颜色
    }
    
    int cx = canvas.getHeight() / 2;
    int cy = canvas.getWidth() / 2;
    canvas.drawCircle(cx, cy, cx, paint);
  }

  public void setColor(int color) {
    mColor = color;
  }
}
