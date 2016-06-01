package com.brownrice.matchtouch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.brownrice.utils.DM;
//import com.google.ads.AdRequest;
//import com.google.ads.AdView;

public class MatchTouchActivity extends Activity
{
//    public static AdView                            adView;
//    public static AdRequest                         adRequest;
    
    Context                                         mContext;
    BoardSurfaceView                                mBoardSurfaceView;
    
    public static Handler                           mExitHandler;
    
    public static boolean                           bUseStage;
    public static int                               nStageRequested;
    
    private static final String                     USESTAGE = "USESTAGE";
    private static final String                     STAGEREQUESTED  = "STAGEREQUESTED";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        DM.i(this, "in onCreate()");
        
        super.onCreate(savedInstanceState);
        
        mContext = this;
        
        // full screen setting
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        Intent intent = getIntent();
        bUseStage = intent.getBooleanExtra(USESTAGE, false);
        
        DM.i(this, "in onCreate()    ][    bUseStage:" + bUseStage + "    ][    ");
        
        if(bUseStage == true)
            nStageRequested = intent.getIntExtra(STAGEREQUESTED, 0);
        
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.gameboard);
        mBoardSurfaceView = (BoardSurfaceView)findViewById(R.id.mBoardSurfaceView);
        
        
        
        /******************************************************************************************
         * Admob 
         *****************************************************************************************/
//        adView = (AdView)findViewById(R.id.adView);
//        adRequest = new AdRequest();
//        adRequest.setTesting(false);
//        adView.loadAd(MainMenu.adRequest);
        
        // exit handler ---------------------------------------------------------------------------
        mExitHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                finish();
            }
        };
        
        // startActivity(new Intent(mContext, LoadingPage.class)); // stage loading image display 
    } // onCreate()
    
    
    // load stage data ----------------------------------------------------------------------------
    public void LoadStage()
    {
        DM.i(this, "in LoadStage()");
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        DM.i(this, "onDeatroy()");
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        DM.i(this, "onPause()");
        mBoardSurfaceView.pauseMatchTouchGame();
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        DM.i(this, "onResume()");
        mBoardSurfaceView.resumeMatchTouchGame();
    }
    
    
}