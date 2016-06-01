package com.brownrice.sound;

import com.brownrice.matchtouch.R;
import com.brownrice.matchtouch.StaticInfo;
import com.brownrice.utils.DM;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundEffect
{
	// right, wrong, tictoc, clear
	private SoundPool				sndPool;
	private int						sfxRight, sfxWrong, sfxTictoc, sfxStageClear, sfxChangeQuestion;
	private int						streamTictoc;
	
	// use singleton. 
	private static SoundEffect		instance;
	private static Context			mContext;
	
	
	
	private SoundEffect()
	{
		sndPool = new SoundPool(StaticInfo.SOUND_ITEM_COUNT, AudioManager.STREAM_MUSIC, 0);
		
		sfxRight = sndPool.load(mContext, R.raw.effect_right, 1);
		sfxWrong = sndPool.load(mContext, R.raw.effect_wrong, 1);
		sfxTictoc = sndPool.load(mContext, R.raw.snd_tictoc, 1);
		sfxStageClear = sndPool.load(mContext, R.raw.snd_stageclear, 1);
		sfxChangeQuestion = sndPool.load(mContext, R.raw.effect_changequestion, 1);
	}
	
	private synchronized static void doSync(Context context)
	{
		if(instance == null)
		{
			mContext = context;
			instance = new SoundEffect();
		}
	}
	
	public static SoundEffect getInstance(Context context)
	{
		if(instance == null)
		{
			DM.e("<SoundEffect> / create SoundEffect instance in getInstance()");
			doSync(context);
		}
		
		return instance;
	}
	
	public void unload()
	{
	    sndPool.unload(sfxRight);
	    sndPool.unload(sfxWrong);
	    sndPool.unload(sfxTictoc);
	    sndPool.unload(sfxStageClear);
	    sndPool.unload(sfxChangeQuestion);
	    
	    DM.i(this, " in unload()   /   sfxRight:" + sfxRight + "   /   sfxWrong:" + sfxWrong);
	}
	
	
	// public methods. -----------------------------------------------------------------------------
	public void playSfxRight()
	{
		sndPool.play(sfxRight, 0.7f, 0.7f, 0, 0, 0);
	}
	
	public void playSfxWrong()
	{
		sndPool.play(sfxWrong, 0.8f, 0.8f, 0, 0, 0);
	}
	
	public void playSfxTictocLoop()
	{
		streamTictoc = sndPool.play(sfxTictoc, 1.0f, 1.0f, 0, -1, 0);
	}
	public void playSfxTictocStop()
	{
		sndPool.stop(streamTictoc);
	}
	
	public void playSfxStageClear()
	{
		sndPool.play(sfxStageClear, 1.0f, 1.0f, 0, 0, 0);
	}
	
	public void playSfxChangeQuestion()
	{
	    DM.i(this, "--->>>   playSfxChangeQuestion()");
		sndPool.play(sfxChangeQuestion, 1.0f, 1.0f, 0, 0, 0);
	}
}
