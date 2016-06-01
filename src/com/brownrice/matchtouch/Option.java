package com.brownrice.matchtouch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.brownrice.utils.DM;
//import com.google.ads.AdRequest;
//import com.google.ads.AdView;

public class Option extends Activity
{
    Context                     mContext;
    RadioGroup                  GroupSound, GroupVib;
    StatusInfo                  mStatusInfo;
    
    int                         Sound, Vib;
    int reserved1, reserved2, reserved3, reserved4, reserved5;
    int reserved6, reserved7, reserved8, reserved9, reserved10;
    
    
    
    // admin option - make all clear~~
    boolean admin = true;
   
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;
        mStatusInfo = StatusInfo.getInstance();
        
        // make full screen 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        setContentView(R.layout.option);
        
        
        // admin option - make all clear~~
        if(admin == true)
        {
            Button admin_btn = (Button)findViewById(R.id.admin);
            
            admin_btn.setOnClickListener(new View.OnClickListener()
            {
                
                public void onClick(View v)
                {
                    StringBuffer buffer = new StringBuffer();
                    
                    buffer.append(160).append("|")
                    /*.append(score).append("|")
                    .append(totalScore).append("|")
                    .append(mGameInfo.getClearedStageNumber()).append("|")*/
                    .append("0").append("|")
                    .append("0").append("|")
                    .append("0").append("|")
                    .append(reserved1).append("|")
                    .append(reserved2).append("|")
                    .append(reserved3).append("|")
                    .append(reserved4).append("|")
                    .append(reserved5).append("|")
                    .append(reserved6).append("|")
                    .append(reserved7).append("|")
                    .append(reserved8).append("|")
                    .append(reserved9).append("|")
                    .append(reserved10).append("|");
                    try
                    {
                        FileOutputStream fileOutput = mContext.openFileOutput("MatchTouch", Context.MODE_PRIVATE);
                        fileOutput.write(buffer.toString().getBytes());
                        fileOutput.close();
                    }
                    catch(IOException e)
                    {
                        DM.e("Fail to save : " + e);
                    }
                }
            });
        }   // only dev code
        
        //-----------------------------------------------------------
        // admob ����
        //-----------------------------------------------------------
//        MainMenu.adView = (AdView)findViewById(R.id.adView);
//        MainMenu.adRequest = new AdRequest();
//        MainMenu.adRequest.setTesting(false);
//        MainMenu.adView.loadAd(MainMenu.adRequest);
        
        // save button listener
        Button.OnClickListener SaveBtnClick = new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DM.i(this, "Save Settings btn was clicked !");
                saveSettings();
                finish();
            }
        };
        
        findViewById(R.id.SaveSettings).setOnClickListener(SaveBtnClick);
        
        loadSettings();
        
        GroupSound = (RadioGroup)findViewById(R.id.SoundGroup);
        GroupVib = (RadioGroup)findViewById(R.id.VibGroup);
        
        if(Sound == 0 || Sound == 1)
            GroupSound.check(R.id.soundon);
        else if(Sound == 2)
            GroupSound.check(R.id.soundoff);
        
        if(Vib == 0 || Vib == 1)
            GroupVib.check(R.id.vibon);
        else if(Vib == 2)
            GroupVib.check(R.id.viboff);
    }
    
    protected void saveSettings()
    {
        StringBuffer strBuffer = new StringBuffer();
        RadioButton tmpRadio;
        int id;
        
        // sound
        id = GroupSound.getCheckedRadioButtonId();
        tmpRadio = (RadioButton)findViewById(id);
        Sound = Integer.parseInt(tmpRadio.getTag().toString());
        
        // vibration
        id = GroupVib.getCheckedRadioButtonId();
        tmpRadio = (RadioButton)findViewById(id);
        Vib = Integer.parseInt(tmpRadio.getTag().toString());
        
        DM.i(this, "in saveSettings()   ][   Sound:" + Sound + "   ][   Vib:" + Vib);
        
        mStatusInfo.setSoundState(Sound);
        mStatusInfo.setVibration(Vib);
        
        // save to file
        strBuffer.append(Sound).append("|")
                .append(Vib).append("|")
                .append(reserved1).append("|")
                .append(reserved2).append("|")
                .append(reserved3).append("|")
                .append(reserved4).append("|")
                .append(reserved5).append("|")
                .append(reserved6).append("|")
                .append(reserved7).append("|")
                .append(reserved8).append("|")
                .append(reserved9).append("|")
                .append(reserved10).append("|");
        
        try
        {
            FileOutputStream fileOutput = mContext.openFileOutput("MatchTouch_Settings", Context.MODE_PRIVATE);
            fileOutput.write(strBuffer.toString().getBytes());
            fileOutput.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    protected boolean loadSettings()
    {
        boolean result = false;
        
        try
        {
            FileInputStream fileInput = mContext.openFileInput("MatchTouch_Settings");
            
            byte[] data = new byte[fileInput.available()];
            
            if(fileInput.read(data) != -1)
            {
                fileInput.close();
                
                String[] strBuffer = (new String(data)).split("\\|");
                
                Sound = Integer.parseInt(strBuffer[0]);
                Vib = Integer.parseInt(strBuffer[1]);
                mStatusInfo.setSoundState(Sound);
                mStatusInfo.setVibration(Vib);
                
                DM.i(this, "in loadSettings()   ][   Sound:" + Sound + "   ][   Vib:" + Vib);
                
                result = true;
            }
        }
        catch(IOException e)
        {
            result = false;
        }
        
        return result;
    }
}
