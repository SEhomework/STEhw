package com.example.androidfirstproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy {

	Bitmap Enemy;
	float x, y;
	boolean isDead;
	int type;
	int speed;
	public static final int EnemyOne = 1;
	public static final int EnemyTwo = 2;
	public static final int EnmeyThree = 3;

	float frameW, frameH;
	int frameIndex;

	public Enemy(Bitmap Enemy, int type, float x, float y) {
		this.Enemy = Enemy;
		this.type = type;
		this.x = x;
		this.y = y;
		switch (type) {
		case EnemyOne:
			speed = 10;
			break;
		}
		frameW = Enemy.getWidth() / 10;
		frameH = Enemy.getHeight();
	}

	public void Draw(Canvas canvas, Paint paint) {
		canvas.save();//
		// ²¶×½Ö¡¶¯»­¾ØÐÎ
		canvas.clipRect(x, y, x + frameW, y + frameH);
		canvas.drawBitmap(Enemy, x - frameIndex * frameW, y, paint);
		canvas.restore();//ÖØÔØ
	}

	public void Logic() {
		frameIndex++;
		if (frameIndex >= 10) {
			frameIndex = 0;
		}
		switch (type) {
		case EnemyOne:
			y = y + speed;
			x = x - speed;
			break;
		}
	}
}
