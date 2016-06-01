package com.brownrice.matchtouch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

//import com.google.ads.AdRequest;
//import com.google.ads.AdView;

public class StageSelectPage extends Activity
{
//    public static AdView                            adView;
//    public static AdRequest                         adRequest;
    
    Context                                         mContext;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        mContext = this;
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        setContentView(R.layout.stageselectpage);
        
        /******************************************************************************************
         * Admob 
         *****************************************************************************************/
//        adView = (AdView)findViewById(R.id.adView);
//        adRequest = new AdRequest();
//        adRequest.setTesting(false);
//        adView.loadAd(adRequest);
        
        findViewById(R.id.stage_numeric).setOnClickListener(StageButtonClick);
        findViewById(R.id.stage_animal).setOnClickListener(StageButtonClick);
        findViewById(R.id.stage_transport).setOnClickListener(StageButtonClick);
        findViewById(R.id.stage_face).setOnClickListener(StageButtonClick);
        
    }
    
    Button.OnClickListener StageButtonClick = new OnClickListener()
    {
        Intent intent;

        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.stage_numeric:
                {
                    intent = new Intent(mContext, StageSelectNumeric.class);
                    startActivity(intent);
                    break;
                }
                
                case R.id.stage_animal:
                {
                    intent = new Intent(mContext, StageSelectAnimal.class);
                    startActivity(intent);
                    break;
                }
                
                case R.id.stage_transport:
                {
                    intent = new Intent(mContext, StageSelectTransport.class);
                    startActivity(intent);
                    break;
                }
                
                case R.id.stage_face:
                {
                    intent = new Intent(mContext, StageSelectFace.class);
                    startActivity(intent);
                    break;
                }
            }
        }
        
    };
}
