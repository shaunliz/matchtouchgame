package com.brownrice.matchtouch;

public class StaticInfo
{
    
    public static final int                 TILE_COUNT = 6; // game tile 5 and margin 1
    
    // life count
    public static final int					LIFE_COUNT_EASY = 5;
    public static final int					LIFE_COUNT_NORMAL = 4;
    public static final int					LIFE_COUNT_HARD = 3;
    
    
    public static final int                 GAME_TILE_SIZE = 30; // total tile count
    // public static final int                 GAME_TILE_SIZE = 3; // for test tile count
    
    
    // timers value
    public static final int                 TIMER_TICK = 100;   // duration of tick timer
    public static final int                 TIMER_COUNTDOWN = 1000; // duratio of countdown
    
    
    // handlers value
    public static final int                 HANDLER_ID_DEFAULT = 0;
    public static final int                 HANDLER_ID_FOR_QUESTION_IMAGE_PROGRESS_TICK = 1; 
    
    
    // progress bar height
    public static final int                 PROGRESS_H = 18;
    
    
    // vibratioin
    public static final int                 VIB_ANSWER_TILE_TOUCH = 30;
    public static final int                 VIB_ANSWER_TILE_TOUCH_WRONG = 120;
    public static final int                 VIB_CHANGE_QUESTION_TILE = 20;
    
    
    // life count
    public static final int                 LIFE_EASY = 7;
    public static final int                 LIFE_NORMAL = 5;
    public static final int                 LIFE_HARD = 3;
    public static final int                 LIFE_EXTREAM = 1;       // using in bonu stage
    
    
    // each stage size
    public static final int                 STAGE_SIZE_EACH = 40; 
    public static final int                 STAGE_SIZE_NUMERIC = 40;
    public static final int                 STAGE_SIZE_ANIMAL = 40;
    public static final int                 STAGE_SIZE_TRANSPORT = 40;
    
    
    // each stage start score
    public static final int                 STAGE_INIT_SCORE = 5000;
    
    
    // game state
    public static final int                 GAME_STATE_NONE = 0;
    public static final int                 GAME_STATE_READY = 1;
    public static final int                 GAME_STATE_PLAYING = 2;
    public static final int                 GAME_STATE_PAUSE = 3;
    public static final int                 GAME_STATE_END = 4;
    public static final int                 GAME_STATE_ALL_CLEAR = 5;
    
    
    // sount item count
    public static final int					SOUND_ITEM_COUNT = 6;
}