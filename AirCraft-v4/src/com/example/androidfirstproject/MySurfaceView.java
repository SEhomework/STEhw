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

//可以在SurfaceView的线程中直接更新UI
//通常用于游戏绘图和视频播放
//调用SurfaceView对象后可以得到一块用于绘图的区域，称之为Surface
//Callback回调函数，实现该函数为SurfaceView创建生命周期
public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	// SurfaceView构建框架，用于添加SurfaceView绘图所需的组件到SurfaceView对象
	SurfaceHolder sfh;
	// 判断循环条件
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
	int count;// 计时器
	int createPlayerBullet = 20;
	int createEnemy = 40;
	// AI数组
	int[][] enemyArray = { { 1, 2 }, { 2, 3 }, { 1, 2, 3 }, { 1, 2, 3 },
			{ 1, 2 } };
	// 数组索引
	int enemyArrayIndex;

	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		// 通过当前SurfaceView对象获取构建框架
		sfh = this.getHolder();
		// 添加回调函数生命周期到SurfaceView对象
		sfh.addCallback(this);
		flag = true;

		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.YELLOW);

	}

	// Surface初始化创建时调用
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		ScreenW = this.getWidth();
		ScreenH = this.getHeight();
		initialize();
		// 启动绘图线程
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

	// Surface状态改变时调用
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	// Surface销毁时调用，一般加入释放资源的操作
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

		flag = false;
	}

	public void Draw() {
		// 绘图操作
		// lockCanvas()锁定画布，用于从SurfaceView中拿
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
				// 遍历集合
				// 绘制主角子弹
				for (int i = 0; i < playerbulletlist.size(); i++) {
					playerbulletlist.get(i).Draw(canvas, paint);
				}
				if (!isBoss) {
					// 绘制敌机
					for (int i = 0; i < enemyList.size(); i++) {
						enemyList.get(i).Draw(canvas, paint);
					}
				} else {

				}

				break;
			default:
			}
		}
		// 解锁画布，释放画布对象
		if (canvas != null) {
			sfh.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			// 绘图函数
			Draw();
			// 逻辑函数
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
			// 计时器定时生成子弹
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
				// 定时生成敌机
				if (count % createEnemy == 0) {
					// 生成的敌机类型需要设计
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
				// 绘制BOSS
			}
			count++;
			// player.Logic();

			break;
		}
	}

	// MotionEvent代表触屏事件,每次触屏事件发生返回一个Event对象
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (gameState) {
		case GAME_MENU:
			// 处理按钮事件方法
			gameMenu.onTouchEvent(event);
			break;
		case GAMING:
			player.onTouchEvent(event);
			break;
		}

		// 返回true代表触屏事件发生
		return true;
	}

}
