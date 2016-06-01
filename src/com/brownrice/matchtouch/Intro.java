package com.brownrice.matchtouch;

import com.brownrice.utils.DM;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

// 시작시 인트로 화면을 보여주기 위한 부분 입니다.
public class Intro extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        DM.i("<Intro.java> - onCreate()");
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        setContentView(R.layout.intro);
        showIntro();
    }

    private void showIntro()
    {
        Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                DM.i("<Intro.java> - in showIntro() : call - finish()");
                finish();
            }
        };
        
        handler.sendEmptyMessageDelayed(0, 2500);
    }
}
