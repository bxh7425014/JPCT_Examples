package com.collsion4;

import java.io.IOException;
import java.io.InputStream;
import com.threed.jpct.Interact2D;
import com.threed.jpct.SimpleVector;
import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Activity类
 * 
 * @author itde1
 * 
 */
public class Jpct_collsion4Activity extends Activity {
	private GLSurfaceView glView;
	private MyRenderer mr = new MyRenderer();
	// 这里设置为public static，是因为在MyRenderer里面用到
	public static boolean up = false; // 方向上下左右
	public static boolean down = false;
	public static boolean left = false;
	public static boolean right = false;
	private INotify mNotify;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// 传入Resources方法
		LoadFile.loadb(getResources());
		mNotify = new INotify() {
			@Override
			public void notify(Object obj) {
				if (obj instanceof String) {
					Toast.makeText(Jpct_collsion4Activity.this, (String)obj, Toast.LENGTH_SHORT).show();
				}
			}
		};

		glView = new GLSurfaceView(this);
		glView.setRenderer(mr);
		setContentView(glView);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		glView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		glView.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { // 按键处理，当上下左右中的一个按下时，则将相应的变量置true
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_UP:
			up = true;
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			down = true;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			left = true;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			right = true;
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) { // 松开按键
		up = false;
		down = false;
		left = false;
		right = false;
		return super.onKeyUp(keyCode, event);
	}
	
	private float xpos = -1;
	private float ypos = -1;
	public boolean onTouchEvent(MotionEvent me) {
		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			xpos = me.getX();
			ypos = me.getY();
			mr.touchEvent((int)xpos, (int)ypos, mNotify);
			return true;
		}

		if (me.getAction() == MotionEvent.ACTION_UP) {
			xpos = -1;
			ypos = -1;
			return true;
		}

		try {
			Thread.sleep(15);
		} catch (Exception e) {
			// No need for this...
		}
		return super.onTouchEvent(me);
	}
}

// 载入文件
class LoadFile {
	public static Bitmap bitmap1;
	public static Bitmap bitmap2;

	// 载入纹理图片
	public static void loadb(Resources res) {
		bitmap1 = BitmapFactory.decodeResource(res, R.drawable.back);
		bitmap2 = BitmapFactory.decodeResource(res, R.drawable.face);
	}
}
