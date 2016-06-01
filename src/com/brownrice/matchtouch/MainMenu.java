package com.brownrice.matchtouch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.brownrice.utils.DM;
//import com.google.ads.AdRequest;
//import com.google.ads.AdView;

public class MainMenu extends Activity
{
//    public static AdView                            adView;
//    public static AdRequest                         adRequest;
    
    Context                                         context;
    AlertDialog.Builder                             dialog;
    
    private static final String                     USESTAGE = "USESTAGE";
    private static final String                     STAGEREQUESTED  = "STAGEREQUESTED";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        context = this;
        dialog = new AlertDialog.Builder(this);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // remove annunciator
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // remove window title
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // fix screen orientation
        
        setContentView(R.layout.menu);
        startActivity(new Intent(context, Intro.class));    // for intro - brownrice software
        
        /******************************************************************************************
         * Admob 
         *****************************************************************************************/
//        adView = (AdView)findViewById(R.id.adView);
//        adRequest = new AdRequest();
//        adRequest.setTesting(false);
//        adView.loadAd(adRequest);
        
        /******************************************************************************************
         * menu image button listener
         ******************************************************************************************/
        findViewById(R.id.start).setOnClickListener(MenuBtnOnClick);
        findViewById(R.id.stage).setOnClickListener(MenuBtnOnClick);
        findViewById(R.id.option).setOnClickListener(MenuBtnOnClick);
        findViewById(R.id.about).setOnClickListener(MenuBtnOnClick);
        findViewById(R.id.exit).setOnClickListener(MenuBtnOnClick);
    }

    @Override
    protected void onDestroy()
    { 
        super.onDestroy();
        DM.i(this, "in onDestroy()");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    
    /**********************************************************************************************
     * menu button listener
     *********************************************************************************************/
    View.OnClickListener MenuBtnOnClick = new View.OnClickListener()
    {
        
        @Override
        public void onClick(View v)
        {
            Intent intent;
            
            switch(v.getId())
            {
                case R.id.start:
                {
                    intent = new Intent(context, MatchTouchActivity.class);
                    startActivity(intent);
                    break;
                }
                
                case R.id.stage:
                {
                    intent = new Intent(context, StageSelectPage.class);
                    startActivity(intent);
                    break;
                }
                
                case R.id.option:
                {
                    intent = new Intent(context, Option.class);
                    startActivity(intent);
                    break;
                }
                
                case R.id.about:
                {
                    intent = new Intent(context, About.class);
                    startActivity(intent);
                    break;
                }
                
                case R.id.exit:
                {
                    finish();
                    break;
                }
            }
        }
    };
}
