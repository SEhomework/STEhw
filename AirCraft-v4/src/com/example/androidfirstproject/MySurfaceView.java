package com.example.androidfirstproject;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

//������SurfaceView���߳���ֱ�Ӹ���UI
//ͨ��������Ϸ��ͼ����Ƶ����
//����SurfaceView�������Եõ�һ�����ڻ�ͼ�����򣬳�֮ΪSurface
//Callback�ص�������ʵ�ָú���ΪSurfaceView������������
public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	// SurfaceView������ܣ��������SurfaceView��ͼ����������SurfaceView����
	SurfaceHolder sfh;
	// �ж�ѭ������
	boolean flag;
	Canvas canvas;
	Paint paint;

	Bitmap mainmenu;
	Bitmap bk1;
	Bitmap logo;
	Bitmap startButton;
	Bitmap startButtonPress;
	Bitmap text;
	Bitmap Bplayer;
	Bitmap hp;
	Bitmap playerBullet;
	Bitmap enemyOne;

	static int ScreenW, ScreenH;

	GameMenu gameMenu;
	GameBg gameBg;
	Player player;
	Random random;
	boolean isBoss;

	public static final int GAME_MENU = 1;
	public static final int GAMING = 2;
	public static final int GAME_OVER = 3;
	public static final int GAME_WIN = 4;
	public static int gameState = GAME_MENU;

	ArrayList<Bullet> playerbulletlist;
	ArrayList<Enemy> enemyList;
	int count;// ��ʱ��
	int createPlayerBullet = 20;
	int createEnemy = 40;
	// AI����
	int[][] enemyArray = { { 1, 2 }, { 2, 3 }, { 1, 2, 3 }, { 1, 2, 3 },
			{ 1, 2 } };
	// ��������
	int enemyArrayIndex;

	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		// ͨ����ǰSurfaceView�����ȡ�������
		sfh = this.getHolder();
		// ��ӻص������������ڵ�SurfaceView����
		sfh.addCallback(this);
		flag = true;

		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.YELLOW);

	}

	// Surface��ʼ������ʱ����
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		ScreenW = this.getWidth();
		ScreenH = this.getHeight();
		initialize();
		// ������ͼ�߳�
		new Thread(this).start();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		mainmenu = BitmapFactory.decodeResource(getResources(),
				R.drawable.mainmenu);
		logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		startButton = BitmapFactory.decodeResource(getResources(),
				R.drawable.menustart);
		startButtonPress = BitmapFactory.decodeResource(getResources(),
				R.drawable.menustartpress);
		text = BitmapFactory.decodeResource(getResources(),
				R.drawable.starttext);
		bk1 = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
		Bplayer = BitmapFactory.decodeResource(getResources(),
				R.drawable.myplane);
		hp = BitmapFactory.decodeResource(getResources(), R.drawable.myhp);
		playerBullet = BitmapFactory.decodeResource(getResources(),
				R.drawable.mybullet);
		enemyOne = BitmapFactory.decodeResource(getResources(),
				R.drawable.orangeenemy);

		gameMenu = new GameMenu(mainmenu, logo, startButton, startButtonPress,
				text);
		gameBg = new GameBg(bk1);
		player = new Player(Bplayer, hp);
		playerbulletlist = new ArrayList<Bullet>();
		enemyList = new ArrayList<Enemy>();
		random = new Random();
	}

	// Surface״̬�ı�ʱ����
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	// Surface����ʱ���ã�һ������ͷ���Դ�Ĳ���
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

		flag = false;
	}

	public void Draw() {
		// ��ͼ����
		// lockCanvas()�������������ڴ�SurfaceView����
		canvas = sfh.lockCanvas();
		if (canvas != null) {
			canvas.drawColor(Color.BLACK);
			switch (gameState) {
			case GAME_MENU:
				gameMenu.Draw(canvas, paint);
				break;
			case GAMING:
				gameBg.Draw(canvas, paint);
				player.Draw(canvas, paint);
				// ��������
				// ���������ӵ�
				for (int i = 0; i < playerbulletlist.size(); i++) {
					playerbulletlist.get(i).Draw(canvas, paint);
				}
				if (!isBoss) {
					// ���Ƶл�
					for (int i = 0; i < enemyList.size(); i++) {
						enemyList.get(i).Draw(canvas, paint);
					}
				} else {

				}

				break;
			default:
			}
		}
		// �����������ͷŻ�������
		if (canvas != null) {
			sfh.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			// ��ͼ����
			Draw();
			// �߼�����
			Logic();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void Logic() {
		// TODO Auto-generated method stub
		switch (gameState) {
		case GAME_MENU:
			break;
		case GAMING:
			gameBg.Logic();
			// ��ʱ����ʱ�����ӵ�
			if (count % createPlayerBullet == 0) {

				Bullet b = new Bullet(playerBullet, 1, player.midX, player.y);
				playerbulletlist.add(b);
			}
			for (int i = 0; i < playerbulletlist.size(); i++) {
				if (playerbulletlist.get(i).isDead) {
					playerbulletlist.remove(i);
				} else {
					playerbulletlist.get(i).Logic();
				}

			}
			if (!isBoss) {
				// ��ʱ���ɵл�
				if (count % createEnemy == 0) {
					// ���ɵĵл�������Ҫ���
					for (int i = 0; i < enemyArray[enemyArrayIndex].length; i++) {
						if (enemyArray[enemyArrayIndex][i] == 1) {
							float y = random.nextInt(120) + 50;
							Enemy enemy = new Enemy(enemyOne, 1,
									MySurfaceView.ScreenW + 20, y);
							enemyList.add(enemy);
						} else if (enemyArray[enemyArrayIndex][i] == 2) {

						} else if (enemyArray[enemyArrayIndex][i] == 3) {

						}
					}
					if (enemyArrayIndex < enemyArray.length - 1) {
						enemyArrayIndex++;
					} else {
						isBoss = true;
					}

				}
				for (int i = 0; i < enemyList.size(); i++) {
					if (enemyList.get(i).isDead) {
						enemyList.remove(i);
					} else {
						enemyList.get(i).Logic();
					}
				}
			} else {
				// ����BOSS
			}
			count++;
			// player.Logic();

			break;
		}
	}

	// MotionEvent�������¼�,ÿ�δ����¼���������һ��Event����
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (gameState) {
		case GAME_MENU:
			// ����ť�¼�����
			gameMenu.onTouchEvent(event);
			break;
		case GAMING:
			player.onTouchEvent(event);
			break;
		}

		// ����true�������¼�����
		return true;
	}

}
