package com.brownrice.matchtouch;

import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.brownrice.utils.DM;
//import com.google.ads.AdRequest;
//import com.google.ads.AdView;

public class StageSelectTransport extends Activity
{
//    public static AdView                            adView;
//    public static AdRequest                         adRequest;
    
    Context                                         mContext;
    ImageView                                       imageView[];
    TextView                                        textViewStage[];
    TextView                                        textViewScore[];
    TextView                                        textViewSec[];
    
    private int                                     cleared_stage = 0;
    private int                                     cleared_stage_after_calc;
    private int                                     requested_stage = 0;
    
    private static final String                     USESTAGE = "USESTAGE";
    private static final String                     STAGEREQUESTED  = "STAGEREQUESTED";
    
    // db search
    ScoreDBHelper                                   mScoreDB; 
    SQLiteDatabase                                  db;
    Cursor                                          cur;
    private String[]                                PROJECTION = new String[]{"stage", "score", "time", "combo"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        DM.i(this, "in onCreate()");
        
        mContext = this;
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        loadStage();
        cleared_stage_after_calc = cleared_stage - StaticInfo.STAGE_SIZE_EACH * 2;
        
        if(cleared_stage_after_calc > StaticInfo.STAGE_SIZE_EACH)
            cleared_stage_after_calc = StaticInfo.STAGE_SIZE_EACH;
        
        imageView = new ImageView[StaticInfo.STAGE_SIZE_EACH];
        textViewStage = new TextView[StaticInfo.STAGE_SIZE_EACH];
        textViewScore = new TextView[StaticInfo.STAGE_SIZE_EACH];
        textViewSec = new TextView[StaticInfo.STAGE_SIZE_EACH];
        
        setContentView(R.layout.stageselecttransport);
        
        /******************************************************************************************
         * Admob 
         *****************************************************************************************/
//        adView = (AdView)findViewById(R.id.adView);
//        adRequest = new AdRequest();
//        adRequest.setTesting(false);
//        adView.loadAd(adRequest);
        
        updateClearedStages();
    }    
    
    @Override
    protected void onResume()
    {
        super.onResume();
        updateClearedStages();
    }


    // loadStage()
    private void loadStage()
    {
        DM.i(this, "in loadStage()  ");
        try
        {
            FileInputStream fileInput = mContext.openFileInput("MatchTouch");
            
            byte[] data = new byte[fileInput.available()];
            
            if(fileInput.read(data) != -1)
            {
                fileInput.close();
                String[] buffer = (new String(data)).split("\\|");
                cleared_stage = Integer.parseInt(buffer[0]);
                DM.i(this, "cleared_stage:" + cleared_stage);
            }
        }
        catch(IOException e)
        {
            DM.e("Fail to open");
        }
    }
    
    // updateClearedStages()
    private void updateClearedStages()
    {
        mScoreDB = new ScoreDBHelper(mContext);
        db = mScoreDB.getReadableDatabase();
        
        int min10, min1, sec10, sec1, time;
        String stage_score = null, stage_time = null, stage_time_to_dp = null;
        
        for(int i = 0; i < cleared_stage_after_calc; i++)
        {
            stage_score = null;
            stage_time = "0";
            stage_time_to_dp = null;
            
            imageView[i] = (ImageView)findViewById(R.id.stage_81 + i * 5);
            imageView[i].setImageResource(R.drawable.btn_stage_select_transport_each);
            
            textViewStage[i] = (TextView)findViewById(R.id.stage_text_81 + i * 5);
            textViewStage[i].setVisibility(View.VISIBLE);
            
            // display each stages score and time.
            textViewScore[i] = (TextView)findViewById(R.id.stage_score_81 + i * 5);
            textViewSec[i] = (TextView)findViewById(R.id.stage_sec_81 + i * 5);
            
            cur  = db.query("score", PROJECTION, "stage = " + "" + (i + 1 + (StaticInfo.STAGE_SIZE_EACH * 2)), null, null, null, null);
            DM.i(this, "cur.getCount:" + cur.getCount());
            
            while(cur.moveToNext())
            {
                DM.i(this, "in while  - stage:" + cur.getString(0) + "  /  score:" + cur.getString(1) + "  /  time:" + cur.getString(2));
                stage_score = cur.getString(1);
                stage_time = cur.getString(2);
            }
            
            // score display
            textViewScore[i].setText("SCORE: " + stage_score);
            textViewScore[i].setVisibility(View.VISIBLE);
            
            // time display
            time = Integer.parseInt(stage_time);
            sec1 = time%10;
            sec10 = (time%60)/10;
            min1 = (time/60)%10;
            min10 = (time/60)/10; 
            stage_time_to_dp = String.format("%d%d:%d%d", min10, min1, sec10, sec1);
            textViewSec[i].setText("TIME: " + stage_time_to_dp); 
            textViewSec[i].setVisibility(View.VISIBLE);
            DM.i(this, "2 score_time:" + stage_time);
            
            imageView[i].setOnClickListener(new View.OnClickListener()
            {
                
                @Override
                public void onClick(View v)
                {
                    for(int nStage = 0; nStage < cleared_stage; nStage++)
                    {
                        if((R.id.stage_81 + nStage * 5) == v.getId())
                        {
                            requested_stage = nStage + StaticInfo.STAGE_SIZE_NUMERIC + StaticInfo.STAGE_SIZE_ANIMAL;
                            Intent intent = new Intent(mContext, MatchTouchActivity.class);
                            intent.putExtra(USESTAGE, true);
                            intent.putExtra(STAGEREQUESTED, requested_stage);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
        
        
        if( cur != null)
            cur.close();
        mScoreDB.close();
    }
}
