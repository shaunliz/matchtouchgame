package com.brownrice.matchtouch;

import java.util.Random;

import android.content.Context;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.brownrice.sound.SoundEffect;
import com.brownrice.utils.DM;

public class BoardSurfaceView extends SurfaceView implements Callback
{
    PlayThread                          mPlayThread;
    SurfaceHolder                       mHolder;
    Context                             mContext;
    StatusInfo                          mStatusInfo;
    Random                              rnd = new Random();

    
    private SoundEffect                 mSoundEffect;
    private Vibrator                    mVibrator;
    private int                         imgIdxToChange = 0;
    private boolean                     bPlaySnd = true;
    private boolean                     bPlayVib = true;
    
    public boolean                      bUseStage = false;
    public int                          nStageRequested = 0;
    
    
    public BoardSurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        
        SurfaceHolder   holder = getHolder();
        holder.addCallback(this);
        
        mHolder = holder;
        mContext = context;
        mStatusInfo = StatusInfo.getInstance();
        mSoundEffect = SoundEffect.getInstance(mContext);
        mVibrator = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);
        
        DM.e(this, "MatchTouchActivity.bUseStage:" + MatchTouchActivity.bUseStage);
        bUseStage = MatchTouchActivity.bUseStage;
        
        // set sound and vibration 
        if(mStatusInfo.getSoundState() == 0 || mStatusInfo.getSoundState() == 1)
            bPlaySnd = true;
        else
            bPlaySnd = false;
        
        if(mStatusInfo.getVibrationState() == 0 || mStatusInfo.getVibrationState() == 1)
            bPlayVib = true;
        else
            bPlayVib = false;
        
        // DM.i(this, "in BoardSurfaceView()   ][   bUseStage:" + bUseStage + "    ][    nStageRequested:" + nStageRequested);
        
        if(bUseStage == true)
        {
            nStageRequested = MatchTouchActivity.nStageRequested;
            PlayThread.bUseStage = true;
            PlayThread.nStageRequested = nStageRequested;
        }
        
        mPlayThread = new PlayThread(holder, context);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height)
    {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        DM.i(this, "in surfaceCreated()");
        mStatusInfo.setGameState(StaticInfo.GAME_STATE_PLAYING);
        try
        {
            mPlayThread.start();
        }
        catch(Exception e)
        {
            restartMatchTouchGame();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        DM.i(this, "in surfaceDestroyed()");
        // mSoundEffect.unload();
        
        // mPlayThread.mQuestionTileChangeHandler.removeMessages(StaticInfo.HANDLER_ID_DEFAULT);
        mPlayThread.mQuestionTileChangeHandler.removeCallbacksAndMessages(null);
        mPlayThread.mCountDownHandler.removeCallbacksAndMessages(null);
        
        mPlayThread.mTickHandler.removeMessages(StaticInfo.HANDLER_ID_FOR_QUESTION_IMAGE_PROGRESS_TICK);
        mPlayThread.stopPlayThread();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            // DM.i(this, "in 'onTouchEvent()' | ACTION_DOWN ");
            
            /*if(bPlayVib)
                mVibrator.vibrate(StaticInfo.VIB_TILE_TOUCH);*/
            
            mPlayThread.touch_x = (int)event.getX();
            mPlayThread.touch_y = (int)event.getY();
            
            if( mStatusInfo.getGameState() == StaticInfo.GAME_STATE_END || mStatusInfo.getGameState() == StaticInfo.GAME_STATE_ALL_CLEAR )
                MatchTouchActivity.mExitHandler.sendEmptyMessage(0);
        }
        return true;
    } 

    
    /******************************
     * game status control functions 
     ******************************/
    // stop game
    public void stopMatchTouchGame()
    {
        mPlayThread.stopPlayThread();
        //mPlayThread.mQuestionTileChangeHandler.removeMessages(StaticInfo.HANDLER_ID_DEFAULT);
        mPlayThread.mQuestionTileChangeHandler.removeCallbacksAndMessages(null);
        mPlayThread.mCountDownHandler.removeCallbacksAndMessages(null);
    }
    
    // pause game
    public void pauseMatchTouchGame()
    {
        mPlayThread.pausePlayThread();
        // mPlayThread.mQuestionTileChangeHandler.removeMessages(StaticInfo.HANDLER_ID_DEFAULT);
        mPlayThread.mQuestionTileChangeHandler.removeCallbacksAndMessages(null);
        mPlayThread.mCountDownHandler.removeCallbacksAndMessages(null);
    }
    
    // resume game
    public void resumeMatchTouchGame()
    {
        DM.i(this, "in resumeMatchTouchGame() / call resumePlayThread()");
        mPlayThread.resumePlayThread();
    }
    
    // restart game
    public void restartMatchTouchGame()
    {
        DM.i(this, "in restartMatchTouchGame()  /  call stop and play thread");
        
        mPlayThread.stopPlayThread();
        
        mPlayThread = null;
        mPlayThread = new PlayThread(mHolder, mContext);
        mPlayThread.start();
    }
    
} // BoardSurfaceView
