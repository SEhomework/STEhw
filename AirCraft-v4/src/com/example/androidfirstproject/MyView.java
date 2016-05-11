package com.example.androidfirstproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

//�Զ�����ͼ���󣬻���UI��ͼ
public class MyView extends View {

	// ����
	Paint paint;

	// Constructor ��ʼ��
	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		// ����ϵͳ��������ɫ����
		// paint.setColor(Color.BLUE);
		// ����ARGB��ɫseekBar
		// alpha͸���ȣ�0������ȫ͸����255������ȫ��͸��
		paint.setColor(Color.argb(220, 200, 100, 100));
		// ���û���Բ��
		paint.setAntiAlias(true);
	}

	// ��������AndroidUI��ͼ
	// Canvas����
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawCircle(100, 100, 100, paint);
		Paint paint1 = new Paint();
		paint1.setColor(Color.RED);
		// �������ִ�С
		paint1.setTextSize(25);
		paint1.setStyle(Style.STROKE);
		canvas.drawText("�й����ʴ�ѧ��ţ��", 100, 350, paint1);

		Paint paint2 = new Paint();
		paint2.setStrokeWidth(9.5f);
		paint2.setColor(Color.BLACK);
		canvas.drawLine(100, 400, 400, 500, paint2);

		canvas.drawRect(100, 450, 300, 600, paint2);

		// Bitmap λͼ������������ͼ����Դ
		// BitmapFactory λͼ���칤������������λͼ����
		// R�ļ���Ӧ�ó��������ļ�
		// Resources �������res��ԴĿ¼����
		Resources res = getResources();
		Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.mainmenu);
		// ��������
		// Rect����������λͼ��Դ��ȡ��ĳ�����ε���Դ
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		// RectF������������Ŀ���豸�о��ε�����
		RectF rectF = new RectF(0, 0, this.getWidth(), this.getHeight());
		canvas.drawBitmap(bitmap, rect, rectF, paint2);
		// canvas.drawBitmap(bitmap, 0, 0, paint2);
		super.onDraw(canvas);
	}

}
