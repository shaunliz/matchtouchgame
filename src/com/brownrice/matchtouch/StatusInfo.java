package com.brownrice.matchtouch;


// singleton
public class StatusInfo
{
    private static StatusInfo                           instance;
    
    private StatusInfo()
    {
        ;
    }
    
    private synchronized static void doSync()
    {
        if(instance == null)
        {
            instance = new StatusInfo();
        }
    }
    
    public static StatusInfo getInstance()
    {
        if(instance == null)
        {
            doSync();
        }
        
        return instance;
    }
    
    //********************************************************************************************/
    
    // member variables & method
    private int                                         snd, vib, gamestate;
    
    // sound
    public int getSoundState()
    {
        return snd;
    }
    public void setSoundState(int _var)
    {
        snd = _var;
    }
    
    // vibration
    public int getVibrationState()
    {
        return vib;
    }
    public void setVibration(int _var)
    {
        vib = _var;
    }
    
    // game state
    public int getGameState()
    {
        return gamestate;
    }
    public void setGameState(int _var)
    {
        gamestate = _var;
    }
}

