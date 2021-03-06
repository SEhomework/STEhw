package com.example.androidfirstproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GameMenu {

	Bitmap menu;
	Bitmap logo;
	Bitmap startButton;
	Bitmap startButtonPress;
	Bitmap text;

	float buttonX, buttonY;
	boolean isPress;

	public GameMenu(Bitmap menu, Bitmap logo, Bitmap startButton,
			Bitmap startButtonPress, Bitmap text) {
		this.menu = menu;
		this.logo = logo;
		this.startButton = startButton;
		this.startButtonPress = startButtonPress;
		this.text = text;

		buttonX = MySurfaceView.ScreenW / 2.0f - startButton.getWidth() / 2.0f;
		buttonY = MySurfaceView.ScreenH * 2.0f / 3;
	}

	public void Draw(Canvas canvas, Paint paint) {

		canvas.drawBitmap(menu, 0, 0, paint);
		if (isPress) {
			canvas.drawBitmap(startButtonPress, buttonX, buttonY, paint);
		} else {
			canvas.drawBitmap(startButton, buttonX, buttonY, paint);
		}

	}

	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// MotionEvent.ACTION_DOWN 按下
		// MotionEvent.ACTION_MOVE 拖动
		// MotionEvent.ACTION_UP 抬起
		float x = event.getX();
		float y = event.getY();
		// getAction()判断当前触屏事件
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			// 判断按下的触点位置是否在按钮范围内

			if (x > buttonX && x < buttonX + startButton.getWidth()) {
				if (y > buttonY && y < buttonY + startButton.getHeight()) {
					isPress = true;
				} else {
					isPress = false;
				}
			} else {
				isPress = false;
			}

		} else if (event.getAction() == MotionEvent.ACTION_UP) {

			if (x > buttonX && x < buttonX + startButton.getWidth()) {
				if (y > buttonY && y < buttonY + startButton.getHeight()) {
					isPress = false;
					MySurfaceView.gameState = MySurfaceView.GAMING;
				}
			}
		}

	}
}
