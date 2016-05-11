package com.example.androidfirstproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Player {

	Bitmap player;
	float x, y;
	Bitmap bitmapHp;
	int HP = 3;
	float hpX, hpY;
	boolean isTouch;
	public float midX, midY;
	float touchX, touchY;
	// ÒÆ¶¯ËÙ¶È
	int speed = 15;

	public Player(Bitmap player, Bitmap bitmapHp) {
		this.player = player;
		this.bitmapHp = bitmapHp;

		x = MySurfaceView.ScreenW / 2.0f - player.getWidth() / 2.0f;
		y = MySurfaceView.ScreenH - player.getHeight();

		midX = x + player.getWidth() / 2.0f;
		midY = y + player.getHeight() / 2.0f;

		hpY = MySurfaceView.ScreenH - bitmapHp.getHeight();
		hpX = 0;
	}

	public void Draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(player, x, y, paint);
		for (int i = 0; i < HP; i++) {
			canvas.drawBitmap(bitmapHp, hpX + i * bitmapHp.getWidth(), hpY,
					paint);
		}
	}

	// ´¥ÆÁ¼àÌý
	public void onTouchEvent(MotionEvent event) {

		touchX = event.getX();
		touchY = event.getY();

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// ÅÐ¶ÏÊÇ·ñ´¥¿Øµ½Ö÷½Ç·É»ú
			if (touchX > x && touchX < x + player.getWidth()) {
				if (touchY > y && touchY < y + player.getHeight()) {
					isTouch = true;
				}
			}

		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (isTouch) {
				// ÅÐ¶ÏXÖáÎ»ÒÆÔö¼õ
				if (touchX > midX) {
					if (midX + speed <= MySurfaceView.ScreenW) {
						midX = midX + speed;
						x = midX - player.getWidth() / 2.0f;
					}
				} else if (touchX < midX) {
					if (midX - speed >= 0) {
						midX = midX - speed;
						x = midX - player.getWidth() / 2.0f;
					}
				}
				// ÅÐ¶ÏYÖáÎ»ÒÆÔö¼õ
				if (touchY > midY) {
					if (midY + speed <= MySurfaceView.ScreenH) {
						midY = midY + speed;
						y = midY - player.getHeight() / 2.0f;
					}

				} else if (touchY < midY) {
					if (midY - speed >= 0) {
						midY = midY - speed;
						y = midY - player.getHeight() / 2.0f;
					}

				}
			}

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			isTouch = false;
		}
	}

	// public void Logic() {
	// // TODO Auto-generated method stub
	// if (isTouch) {
	// // ÅÐ¶ÏXÖáÎ»ÒÆÔö¼õ
	// if (touchX > midX) {
	// if (midX + speed <= MySurfaceView.ScreenW) {
	// midX = midX + speed;
	// x = midX - player.getWidth() / 2.0f;
	// }
	// } else if (touchX < midX) {
	// if (midX - speed >= 0) {
	// midX = midX - speed;
	// x = midX - player.getWidth() / 2.0f;
	// }
	//
	// }
	// // ÅÐ¶ÏYÖáÎ»ÒÆÔö¼õ
	// if (touchY > midY) {
	// if (midY + speed <= MySurfaceView.ScreenH) {
	// midY = midY + speed;
	// y = midY - player.getHeight() / 2.0f;
	// }
	//
	// } else if (touchY < midY) {
	// if (midY - speed >= 0) {
	// midY = midY - speed;
	// y = midY - player.getHeight() / 2.0f;
	// }
	//
	// }
	// }
	//
	// }
}
