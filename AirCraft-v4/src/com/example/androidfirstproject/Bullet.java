package com.example.androidfirstproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {

	Bitmap bitmap;
	float x, y;
	int speed;
	public static final int BULLET_PLAYER = 1;
	public static final int BULLET_ENEMY = 2;
	public static final int BULLET_BOSS = 3;
	int type;
	boolean isDead;

	public Bullet(Bitmap bitmap, int type, float x, float y) {
		this.bitmap = bitmap;
		this.type = type;
		this.x = x;
		this.y = y;

		switch (type) {
		case BULLET_PLAYER:
			speed = 10;
			break;
		case BULLET_ENEMY:
			speed = 7;
			break;
		}
	}

	public void Draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bitmap, x, y, paint);
	}

	public void Logic() {
		switch (type) {
		case BULLET_PLAYER:
			y = y - speed;
			if (y < -50) {
				isDead = true;
			}
			break;

		}
	}
}
