package com.brownrice.matchtouch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.brownrice.sound.SoundEffect;
import com.brownrice.utils.DM;

public class PlayThread extends Thread
{
    private Context                             mContext;
    private SurfaceHolder                       mHolder;
    // private Bitmap                              /*imgBackground, */imgCountDown, imgCountDownBackground, /*imgQuestionChangeProgressBar,*/ /*imgAnswerRight, imgAnswerWrong,*/ imgPopupEndGame, imgPopupClearAllStages;
    private Random                              rnd = new Random();
    private Thread                              thisThread;
    private Handler                             /*mCountDownHandler,*/ mDialogHandler; // handlers for count down and dialog
    private AlertDialog.Builder                 dialog;
    private Paint                               mPaint = new Paint();
    private Paint                               mAnswerCheckPaint = new Paint();
    private StatusInfo                          mStatusInfo;
    private Vibrator                            mViberator;
    private SoundEffect							mSoundEffect;		// this singleton object
        
    
    
    private boolean                             threadRun = true;
    private boolean                             threadWait = false;
    private boolean                             startGameFlag = false;
    private boolean                             bPlaySnd  = true;
    private boolean                             bPlayVib = true;
    private int                                 startGameCount = -1;
    private int                                 imgIdxToChange = 0;
    private int                                 imgIdxPrevChanged = -1;
    private int                                 idxRandomAnswerPos = 0;
    private int                                 question_progress_w = 0;
    private int                                 question_progress_change_w = 0;
    private int                                 frag_count, frag_angle;
    private int                                 sec1, sec10, min1, min10;
    private int                                 reserved1, reserved2, reserved3, reserved4, reserved5;
    private int                                 reserved6, reserved7, reserved8, reserved9, reserved10;
    private int                                 indexData[] = new int[10];
    
    
    
    
    public static Bitmap                        imgBackground, imgCountDown, imgCountDownBackground, /*imgQuestionChangeProgressBar,*/ /*imgAnswerRight, imgAnswerWrong,*/ imgPopupEndGame, imgPopupClearAllStages;
    public static Bitmap                        imgLife;
    
    
    
    public static int                           stage_number, life_count, stage_number_loaded;
    public static int                           tile_w, tile_h, tile_half_w, tile_half_h;
    public static int                           screen_margin_left, screen_margin_right, question_tile_margin_top;
    public static int                           answer_tile_margin_top, answer_tile_margin_between;
    public static int                           screen_width, screen_height, screen_half_width, screen_half_height;
    public static int                           nStageRequested;
    public static int                           idxBackgroundImage;
    public static int                           ufoMovingPattern = 0;
    public static boolean                       bUseStage;
    public static boolean                       bDrawItemUfo = false;
    
        
    public QuestionTile                         mQuestionTile;
    public ArrayList<AnswerTile>                mAnswerTile;
    public ArrayList<Frag>                      mFrag;
    public Handler                              mQuestionTileChangeHandler, mTickHandler, mCountDownHandler; // handlers for question image change and tick timer
    public ArrayList<AnswerCheckImageDraw>      mAnswerCheckImageDraw;
    public ItemUfo                              mItemUfo;
    
    
    
    public int                                  touch_x, touch_y;
    
    // [score formula =>] total_score = StaticInfo.STAGE_INIT_SCORE - (score_progress_tick * 100) - (score_time * 100) + (score_combo * 100);
    public int                                  total_score, score_time, score_time_add_check, score_combo, score_combo_check, score_progress_tick;
    public String                               score_time_dp = null;
    
    
    // PlayThread constructors - begin
    /*public PlayThread(Context context)
    {
        mContext = context;
        
        // get display information
        Display disp = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        screen_width = disp.getWidth();
        screen_height = disp.getHeight();
        screen_half_width = screen_width / 2;
        screen_half_height = screen_height / 2;
        
        Initialize(mContext);
    }*/
    
    public PlayThread(SurfaceHolder holder, Context context)
    {
        DM.i(this, "constructor - PlayThread() with holder and context");

        mContext = context;
        mHolder = holder;
        dialog = new AlertDialog.Builder(mContext);
        mStatusInfo = StatusInfo.getInstance();
        mViberator = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);
        mSoundEffect = SoundEffect.getInstance(mContext);
        
        if(loadSettings() == false)
        {
            SetDefaultOption();
        }
        
        if(bUseStage == true)
        {
            DM.i(this, "    ][    use specific stage ~~~~~~~~~~~~");
            stage_number = nStageRequested;
            bUseStage = false;
        }
        else
        {
            DM.i(this, "    ][    call loadStage()");
            stage_number = loadStage();
            if( stage_number >= StageData.Stages.length )
            {
                stage_number = StageData.Stages.length - 1;
                DM.i( this, "in PlayyThread() make stage_number : " + stage_number );
            }
        }
        
        DM.i(this, "# STAGE:" + stage_number);
        
        Initialize(mContext);
        MakeGame(mContext);
        
        DM.i(this, "# CONSTRUCTOR of ''PlayThread()'' with surfaceholder, context, handler  |  stage_number:" + stage_number);
    }
    
    private boolean loadSettings()
    {
        boolean result = false;
        int Sound, Vibration;
        try
        {
            FileInputStream fileInput = mContext.openFileInput("MatchTouch_Settings");
            
            byte[] data = new byte[fileInput.available()];
            
            if(fileInput.read(data) != -1)
            {
                fileInput.close();
                
                String[] strBuffer = (new String(data)).split("\\|");
                
                Sound = Integer.parseInt(strBuffer[0]);
                Vibration = Integer.parseInt(strBuffer[1]);
                mStatusInfo.setSoundState(Sound);
                mStatusInfo.setVibration(Vibration);
                
                DM.i(this, "in loadSettings()   ][   Sound:" + Sound + "   ][   Vib:" + Vibration);
                
                result = true;
            }
        }
        catch(IOException e)
        {
            result = false;
        }
        
        return result;
    }
    
    private void SetDefaultOption()
    {
        DM.i("<GameVIew> / SetDefaultOption()");
        /*cntLife = nLife = 6;                // life count is '6'
        bSound = 1;                         // on
        bVib = 1;                           // on
        nSpeed = 2; // TODO : �����ؾ� ��        // '2' is medium*/
    } // SetDefaultOption()
    
    // Load Stage ----------------------------------------------------------------------------------
    public int loadStage()
    {
        DM.i(this, "in loadStage()  ][  ");
        //mGameView.pauseGame();
        
        try
        {
            FileInputStream fileInput = mContext.openFileInput("MatchTouch");
            
            byte[] data = new byte[fileInput.available()];
            
            if(fileInput.read(data) != -1)
            {
                fileInput.close();
                String[] buffer = (new String(data)).split("\\|");
                stage_number_loaded = Integer.parseInt(buffer[0]);
                DM.i(this, " in loadStage()    ][    stage is :" + stage_number_loaded);
            }
        }
        catch(IOException e)
        {
            DM.e("Fail to open");
        }
        //mGameView.resumeGame();
        return stage_number_loaded;
    }
    
    // save stage function ---------------------------------------------------------------------
    private void saveStage()
    {
        DM.i(this, "    ][    in saveStage()");
        // pauseGame();
        
        if(stage_number <= loadStage())
        {
            DM.e(this, "return saveStage()");
            return;
        }
        
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(stage_number).append("|")
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
        
        // resumeGame();
    } // saveStage()
    
    /*public PlayThread(SurfaceHolder holder, Context context, Handler question_tile_change_handler)
    {
        mContext = context;
        mHolder = holder;
        mQuestionTileChangeHandler = question_tile_change_handler;
        
        Initialize(mContext);
        MakeGame(mContext);
        mQuestionTileChangeHandler.sendEmptyMessage(0);
        
        // start countdown
        startGameCount = 4;
        mCountDownHandler.sendEmptyMessage(0);
        
        DM.i(this, "constructor of PlayThread() with surfaceholder, context, handler  |  stage_number:" + stage_number);
    }*/
    
    /*public PlayThread(SurfaceHolder holder, Context context, Handler handler, Activity activity)
    {
        mContext = context;
        mHolder = holder;
        
        // get display information
        screen_width = BSRINFO.getWindowWidth(activity);
        screen_height = BSRINFO.getWindowHeight(activity);
        screen_half_width = screen_width / 2;
        screen_half_height = screen_height / 2;
        
        DM.i(this, "screen w:" + screen_width + " | screen h:" + screen_height);
        
        Initialize(mContext);
        MakeGame(mContext);
    }*/ 
    // PlayThread constructors - end
    
    
    @Override
    public void run()
    {
        Canvas canvas = null;
        while(threadRun)
        {
            canvas = mHolder.lockCanvas();
            try
            {
                synchronized(mHolder)
                {
                    DrawAll(canvas);
                }
            }
            finally
            {
                if(canvas != null)
                    mHolder.unlockCanvasAndPost(canvas);
            }
            
            synchronized(this)
            {
                if(threadWait)
                {
                    try
                    {
                        wait();
                    }
                    catch(Exception e)
                    {
                        DM.e(this, "thread; sync. exception in wait()   ][   e:" + e);
                    }
                }
            }
        } 
    }
    
    
    
    /**************************************************************************
     * PlayThread's methods ===================================================
     **************************************************************************/
    
    // Initialize =================================================================================
    private void Initialize(Context context)
    {
        InitHandler(context);
        InitScreenInfo(context);
        InitTileSize(context);
        InitEnv();
        InitPaint();
        InitImage(context);
    }
    
    // methods of Initialize() : InitHandler(Context context) -------------------------------------
    private void InitHandler(final Context context)
    {
        question_progress_w = tile_w * 5 + answer_tile_margin_between * 4;
        
        
        // question tile change handler
        mQuestionTileChangeHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
            	/*if(bPlaySnd == true)
            		mSoundEffect.playSfxChangeQuestion();*/
            	
                switch(msg.what)
                {
                    case StaticInfo.HANDLER_ID_DEFAULT:
                    {
                        // DM.e(this, "Start Handler for Quetion  Tile  Change");
                        
                        question_progress_w = tile_w * 5 + answer_tile_margin_between * 4;
                        imgIdxToChange = rnd.nextInt(StageData.Stages[PlayThread.stage_number][0]) + StageData.Stages[PlayThread.stage_number][1];
                        
                        if(mAnswerTile.size() > 0)
                        {
                            // 20120325 - temp block, unlock after release, ���� ��� - question tile ����� vibe
                            /*if(bPlayVib == true)
                            {
                                for(int i = 0; i < 2; i++)
                                {
                                    mViberator.vibrate(StaticInfo.VIB_CHANGE_QUESTION_TILE);
                                    try
                                    {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }*/
                            
                            /*try
                            {
                                Thread.sleep(50);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }*/
                            
                            idxRandomAnswerPos = rnd.nextInt(mAnswerTile.size());
                            imgIdxToChange = mAnswerTile.get(idxRandomAnswerPos).answer;
                            
                            // question image change
                            mQuestionTile.ChangeQuestionTileImage(mContext, imgIdxToChange, StageData.Stages[PlayThread.stage_number][4]);
                            sendEmptyMessageDelayed(0, StageData.Stages[PlayThread.stage_number][3]);
                            score_progress_tick += 1;
                            score_combo_check = 0;
                        }
                        else
                        {
                            // removeMessages(StaticInfo.HANDLER_ID_DEFAULT);
                            removeCallbacksAndMessages(null);
                        }
                        
                        break;
                    }
                }
            }
        }; 
        
        
        // handler for start countdown and countdown image draw
        mCountDownHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                DM.i(this, " in CountDownHandler / startGameCount:" + startGameCount);
                
                
                switch(msg.what)
                {
                    case StaticInfo.HANDLER_ID_DEFAULT:
                    {
                        imgCountDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.countdown00 + startGameCount);
                        imgCountDown = Bitmap.createBitmap(imgCountDown);
                        
                        sendEmptyMessageDelayed(0, StaticInfo.TIMER_COUNTDOWN);
                        if(startGameCount < 0)
                        {
                        	DM.e(this, " ++++++++        GO        ++++++++");
                            startGameFlag = true;
                            mCountDownHandler.removeMessages(StaticInfo.HANDLER_ID_DEFAULT);
                            
                            mQuestionTileChangeHandler.sendEmptyMessage(StaticInfo.HANDLER_ID_DEFAULT);
                            mTickHandler.sendEmptyMessage(StaticInfo.HANDLER_ID_FOR_QUESTION_IMAGE_PROGRESS_TICK);
                            score_time_add_check = 0;
                        }
                        startGameCount--;
                        
                        break;
                    }
                }
            }
        };
        
        
        // tick timer handler
        mTickHandler = new Handler()
        {
            
            @Override
            public void handleMessage(Message msg)
            {
                switch(msg.what)
                {
                    case StaticInfo.HANDLER_ID_FOR_QUESTION_IMAGE_PROGRESS_TICK:
                    {
                        sendEmptyMessageDelayed(StaticInfo.HANDLER_ID_FOR_QUESTION_IMAGE_PROGRESS_TICK, StaticInfo.TIMER_TICK);
                        // DM.e(this, " - ------------ question_progress_w:" + question_progress_w);
                        mQuestionTile.ChangeQuestionProgressImage(mContext, question_progress_w, StaticInfo.PROGRESS_H);
                        question_progress_w = question_progress_w - question_progress_change_w;
                        if(question_progress_w <= 0)
                            question_progress_w = 2;
                        
                        // time score
                        if(score_time_add_check % 10 == 0)
                        {
                            score_time += 1;
                            /*DM.i(this, "in Tick Handler   ][   score_time:" + score_time + "   ][   score_time_add_check:" + score_time_add_check
                                    + "   ][   score_progress_tick" + score_progress_tick);*/
                            score_time_add_check = 1;
                        }
                        else
                            score_time_add_check += 1;
                        
                        break;
                    }
                }
            }
            
        };
        
        mDialogHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
               dialog.show();
            }
        };
    }
    
    // methods of Initialize() : InitScreenInfo(Context context) ----------------------------------
    private void InitScreenInfo(Context context)
    {
        // get display information
        Display disp = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        screen_width = disp.getWidth();
        screen_height = disp.getHeight();
        screen_half_width = screen_width / 2;
        screen_half_height = screen_height / 2;
        
        DM.i(this, "in InitScreenInfo() [screen w:" + screen_width + " | screen h:" + screen_height + "]");
    }
    
    // methods of Initialize() : InitTileSize(Context context) ------------------------------------
    private void InitTileSize(Context context)
    {
        // calculate tile size and margin value
        tile_w = screen_width / StaticInfo.TILE_COUNT;
        tile_h = tile_w;
        tile_half_h = tile_half_w = tile_w / 2;
        screen_margin_left = screen_margin_right = tile_half_w;
        question_tile_margin_top = screen_height / 20;
        answer_tile_margin_top = question_tile_margin_top + tile_half_h * 3;
        answer_tile_margin_between = tile_w / 20;
        
        DM.i(this, "in InitTileSize() [tile w:" + tile_w + " | h:" + tile_h + " | half_w:" + tile_half_w + " | half_h:" + tile_half_h 
                + " | margin left:" + screen_margin_left + " | margin right:" + screen_margin_right 
                + " | question_margin_top:" + question_tile_margin_top + " | answer_margin_top:" + answer_tile_margin_top 
                + "  |  answer_tile_margin_between:" + answer_tile_margin_between + "]");
    }
    
    // methods of Initialize() : InitEnv() --------------------------------------------------------
    private void InitEnv()
    {
        mStatusInfo.setGameState(StaticInfo.GAME_STATE_NONE);
        mAnswerTile = new ArrayList<AnswerTile>();
        mAnswerCheckImageDraw = new ArrayList<AnswerCheckImageDraw>();
        mFrag = new ArrayList<Frag>();
        
        // make dialog for stage clear noti. and goto next stage
        dialog.setTitle("CLEAR STAGE !!").setIcon(R.drawable.brownrice_logo_for_popup)
        .setMessage("score:" + "00" + "\nsec:" + "0000" + "\nGOTO NEXT STAGE ?")
        .setPositiveButton("NEXT", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                MakeGame(mContext);
                resumePlayThread();
            }
        })
        .setNegativeButton("QUIT", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                MatchTouchActivity.mExitHandler.sendEmptyMessage(0);
            }
        });
        
        // set sound and vibration 
        if(mStatusInfo.getSoundState() == 0 || mStatusInfo.getSoundState() == 1)
            bPlaySnd = true;
        else
            bPlaySnd = false;
        
        if(mStatusInfo.getVibrationState() == 0 || mStatusInfo.getVibrationState() == 1)
            bPlayVib = true;
        else 
            bPlayVib = false;
        
        DM.i(this, "in InitEnv()   ][   bPlayVib:" + bPlayVib);
        
        bDrawItemUfo = false;
        
    }
    
    // initialize paint ------------------------------------------------------------------------
    private void InitPaint()
    {
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(screen_height / 40);          // TODO : do correct
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setShadowLayer(0.5f, 1, 1, Color.BLACK);
        
        mAnswerCheckPaint.setAlpha(255);
    }
    
    // methods of Initialize() : InitImage(Context context) ---------------------------------------
    private void InitImage(Context context)
    {
        DM.i(this, "in InitImage()");
        
        // prepare background image 
        idxBackgroundImage = rnd.nextInt(5);
        imgBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.background01 + idxBackgroundImage);
        imgBackground = Bitmap.createScaledBitmap(imgBackground, screen_width, screen_height, true);
        
        // prepare countdown background image
        imgCountDownBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.countdown_background);
        imgCountDownBackground = Bitmap.createScaledBitmap(imgCountDownBackground, screen_width, screen_height, true);
        
        // prepare question change progress'
        // make another class
        /*imgQuestionChangeProgressBar = BitmapFactory.decodeResource(context.getResources(), R.drawable.question_change_progress);*/      
        
        // prepare answer checking image
        // make another  class
        /*imgAnswerRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.answer_check_right);
        imgAnswerWrong = BitmapFactory.decodeResource(context.getResources(), R.drawable.answer_check_wrong);*/
        
        // prepare end game popup image
        imgPopupEndGame = BitmapFactory.decodeResource(context.getResources(), R.drawable.popup_endgame);
        
        // prepare clear all stages popup image
        imgPopupClearAllStages = BitmapFactory.decodeResource(context.getResources(), R.drawable.popup_clearallstages);
        
        // prepare life image
        imgLife = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        imgLife = Bitmap.createScaledBitmap(imgLife, imgLife.getWidth() * 3 / 4, imgLife.getWidth() * 3 / 4, true);
    }
    
    // MakeGame function ==========================================================================
    private void MakeGame(Context context)
    {
        DM.i(this, "in MakeGame()   ][   STAGE:" + stage_number + "   ][   StateData.Stages[] length:" + StageData.Stages.length);
        
        idxBackgroundImage = rnd.nextInt(5);
        imgBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.background01 + idxBackgroundImage);
        imgBackground = Bitmap.createScaledBitmap(imgBackground, screen_width, screen_height, true);
        
        // init score
        score_progress_tick = score_combo = score_combo_check = score_time = score_time_add_check = 0;
        
        // init handler
        // mQuestionTileChangeHandler.removeMessages(StaticInfo.HANDLER_ID_DEFAULT);
        mQuestionTileChangeHandler.removeCallbacksAndMessages(null);
        mCountDownHandler.removeCallbacksAndMessages(null);
        
        mTickHandler.removeMessages(StaticInfo.HANDLER_ID_FOR_QUESTION_IMAGE_PROGRESS_TICK);
        mStatusInfo.setGameState(StaticInfo.GAME_STATE_PLAYING);
        
        // mSoundEffect.playSfxTictocLoop();
        
        // mItemUfo = new ItemUfo(mContext, screen_width, screen_width);
        
        MakeLifeCount();
        MakeCountDown(context);
        MakeQuestionTiles(context, StageData.Stages[stage_number][1], stage_number);
        MakeAnswerTile(context, stage_number);
        
        // init. item
        bDrawItemUfo = false;
        mItemUfo = null; 
    }
    
    // methods of MakeGame() : MakeLifeCount()
    private void MakeLifeCount()
    {
        // setting life count
        if(stage_number % 40 < 16)                          // easy
            life_count = StaticInfo.LIFE_COUNT_EASY;
        else if(stage_number % 40 < 31)                     // normal
            life_count = StaticInfo.LIFE_COUNT_NORMAL;
        else                                                // hard
            life_count = StaticInfo.LIFE_COUNT_HARD;
    }
    
    // methods of MakeGame() : MakeCountDown(Context context) -------------------------------------
    private void MakeCountDown(Context context)
    {
        DM.i(this, "in MakeCountDown()   /   startGameCount:" + startGameCount);
        
        // start countdown
        startGameCount = 4;
        startGameFlag = false;
        mCountDownHandler.sendEmptyMessage(StaticInfo.HANDLER_ID_DEFAULT);
    }
    
    // methods of MakeGame() : MakeQuestionTiles(Context context, int idx) ------------------------
    private void MakeQuestionTiles(Context context, int idx, int stage)
    {
        mQuestionTile = new QuestionTile(context, screen_half_width, question_tile_margin_top, idx, StageData.Stages[stage][4]);
        
        // progress bar position
        mQuestionTile.progress_x1 = screen_margin_left;
        mQuestionTile.progress_y1 = question_tile_margin_top + tile_h + question_tile_margin_top / 2;
        
        question_progress_w = tile_w * 5 + answer_tile_margin_between * 4;
        question_progress_change_w = question_progress_w / (StageData.Stages[stage][3] / StaticInfo.TIMER_TICK);
        
        DM.i(this, "question_progress_w:" + question_progress_w + "   ][   question_progress_change_w:" + question_progress_change_w);
        
        // move position in countdown handler
        /*mQuestionTileChangeHandler.sendEmptyMessage(0);
        mTickHandler.sendEmptyMessage(StaticInfo.HANDLER_ID_FOR_QUESTION_IMAGE_PROGRESS_TICK);*/
    }   
    
    // methods of MakeGame() : MakePlayingBoard(Context context, int stageIdx) ---------------------
    private void MakeAnswerTile(Context context, int stage)
    {
        DM.i(this, "in MakeAnswerTile()   ][   stage:" + stage + "   ]");
        
        int answer_posx = screen_margin_left - answer_tile_margin_between * 2;
        int answer_poxy = answer_tile_margin_top;
        
        for(int i = 0; i < StaticInfo.GAME_TILE_SIZE; i++)
        {
            mAnswerTile.add(new AnswerTile(context, answer_posx + tile_w * (i % 5), 
                    answer_poxy, rnd.nextInt(StageData.Stages[stage][0]) + StageData.Stages[stage][1], StageData.Stages[stage][4]));
            
            // 각 타일이 놓일 위치를 지정해주고 있습니다.
            if((i % 5) == 4)
            {
                answer_poxy = answer_poxy + tile_h + answer_tile_margin_between;
                answer_posx = screen_margin_left - answer_tile_margin_between * 2;
            }
            else
                answer_posx = answer_posx + answer_tile_margin_between;
        }
    }
    
    // DrawAll function ===========================================================================
    private void DrawAll(Canvas canvas)
    {
        DrawBackground(canvas);
        DrawQuestionTile(canvas);
        DrawLife(canvas);
        DrawAnswerTile(canvas);
        DrawCountDown(canvas);
        DrawInformation(canvas);
        DrawTileFragmentation(canvas);
        DrawAnswerCheckImage(canvas);
        DrawItemUfo(canvas);
        DrawGameEndPopupImage(canvas);
    }
    
    // methods of DrawAll()'s : DrawBackground(Canvas canvas) -------------------------------------
    // draw background image
    private void DrawBackground(Canvas canvas)
    {
        canvas.drawBitmap(imgBackground, 0, 0, null);
    }
    
    // methods of DrawAll()'s : DrawQuestionTile(Canvas canvas) ------------------------------------
    //draw question tile image
    private void DrawQuestionTile(Canvas canvas)
    {
        // Question Tile Frame
        // frame width : 110 / tiel width : 70
        canvas.drawBitmap(mQuestionTile.imgQuestionTileFrame, mQuestionTile.x1 - (tile_w / 5), 
                            mQuestionTile.y1 - (tile_h / 5), null);
        
        // Question Tile
        canvas.drawBitmap(mQuestionTile.imgQuestionTile, mQuestionTile.x1, mQuestionTile.y1, null);
        
        // Progress bar- using nine patch
        // canvas.drawBitmap(mQuestionTile.imgQuestionChangeProgressBar, mQuestionTile.progress_x1, mQuestionTile.progress_y1, null);
        mQuestionTile.mNinePatchGraphic.draw(canvas, mQuestionTile.pregressRect);
    }
    
    // methods of DrawAll()'s : DrawLife(Canvas canvas) -------------------------------------------
    // draw life image
    private void DrawLife(Canvas canvas)
    {
        for(int i = 0; i < life_count; i++)
            canvas.drawBitmap(imgLife, mQuestionTile.x2 + (i * imgLife.getWidth()) + imgLife.getWidth() / 2, mQuestionTile.y1, null);
    }
    
    // methods of DrawAll()'s : DrawAnswerTile(Canvas canvas) -------------------------------------
    //draw answers tile image
    private void DrawAnswerTile(Canvas canvas)
    {
        TouchCheckAll();
        
        for(AnswerTile tmp : mAnswerTile)
            canvas.drawBitmap(tmp.imgAnswerTile, tmp.x1, tmp.y1, null);
    }
    
    // methods of DrawAll()'s : DrawCountDown(Canvas canvas) --------------------------------------
    private void DrawCountDown(Canvas canvas)
    {
        if(startGameFlag == true)
            return;
        
        canvas.drawBitmap(imgCountDownBackground, 0, 0, null);
        canvas.drawBitmap(imgCountDown, (screen_width - imgCountDown.getWidth()) / 2, 
                (screen_height - imgCountDown.getHeight()) / 2, null);
    }
    
    // methods of DrawAll()'s : DrawInformation()
    private void DrawInformation(Canvas canvas)
    {
        int tmpStage = stage_number + 1;
        
        canvas.drawText("Stage:" + tmpStage, screen_width / 30, screen_height / 50, mPaint);
        canvas.drawText("Life:" + life_count, screen_width - ( screen_width / 5 ), screen_height / 50, mPaint);
        
        /*canvas.drawText("Bricks:" + score, brick_w * 5, brick_h * 2 / 3, paint);
        canvas.drawText("Score:" + totalScore, brick_w * 9, brick_h * 2 / 3, paint);*/
    }
    
    // methods of DrawAll()'s : DrawTileFragmentation()
    private void DrawTileFragmentation(Canvas canvas)
    {
        MoveTileFragmentation();
        for(Frag tmp : mFrag)
            canvas.drawBitmap(tmp.imgFrag, tmp.x, tmp.y, null);
    }
    
    // methods of DrawAll()'s : DrawAnswerCheckImage()
    private void DrawAnswerCheckImage(Canvas canvas)
    {
        MoveAnswerCheckImage();
        for(AnswerCheckImageDraw tmp : mAnswerCheckImageDraw)
        {
            mAnswerCheckPaint.setAlpha(tmp.imgAlpha);
            canvas.drawBitmap(tmp.imgAnswerCheck, tmp.x, tmp.y, mAnswerCheckPaint);
        }
    }
    
    // methods of DrawAll()'s : DrawItemUfo() 
    private void DrawItemUfo(Canvas canvas)
    {
        if( bDrawItemUfo == false)
            return;
        else
        {
            ItemUfoMoveAndCollisionCheck();
            canvas.drawBitmap( mItemUfo.imgUfo[mItemUfo.imgIdx], mItemUfo.x, mItemUfo.y, null );
        }
    }
    
    // methods of DrawAll()'s : DrawGameEndPopupImage()
    private void DrawGameEndPopupImage(Canvas canvas)
    {
        if( mStatusInfo.getGameState() == StaticInfo.GAME_STATE_END )
            canvas.drawBitmap(imgPopupEndGame, screen_half_width - imgPopupEndGame.getWidth() / 2, 
                    screen_half_height - imgPopupEndGame.getHeight() / 2, null);
        else if( mStatusInfo.getGameState() == StaticInfo.GAME_STATE_ALL_CLEAR )
            canvas.drawBitmap(imgPopupClearAllStages, screen_half_width - imgPopupClearAllStages.getWidth() / 2, 
                    screen_half_height - imgPopupClearAllStages.getHeight() / 2, null);
        
    }
    
    // methods of DrawAnswerCheckImage(Canvas canva) : MoveAnswerCheckImage()
    private void MoveAnswerCheckImage()
    {
        for(int i = 0; i < mAnswerCheckImageDraw.size(); i++)
        {
            mAnswerCheckImageDraw.get(i).MoveAnswerImage();
            
            if(mAnswerCheckImageDraw.get(i).bOutOfRange == true)
                mAnswerCheckImageDraw.remove(i);
        }
    }
    // methods of DrawAnswerTile()'s TouchCheckAll() ==============================================
    // answer tiles touch check -------------------------------------------------------------------
    private void TouchCheckAll()
    {
        if(startGameFlag == false)
            return;
        
        TouchCheckWithItems();
        TouchCheckWithAnswerTiles();  
        
    }
    
    // methods of TouchCheckAll() : TouchCheckWithAnswerTiles() -----------------------------------
    private void TouchCheckWithAnswerTiles()
    {
        if(StageClearCheck() == true)   // stage clear and goto next stage !
        {
        	if(bPlaySnd == true)
        		mSoundEffect.playSfxRight();
        	
            stage_number = stage_number + 1;
            
            if( stage_number >= StageData.Stages.length )
            {
                mStatusInfo.setGameState( StaticInfo.GAME_STATE_ALL_CLEAR );
                
                mQuestionTileChangeHandler.removeCallbacksAndMessages(null);
                mCountDownHandler.removeCallbacksAndMessages(null);
                mTickHandler.removeMessages(StaticInfo.HANDLER_ID_FOR_QUESTION_IMAGE_PROGRESS_TICK);
                stopPlayThread();
                return;
            }
            else
            {
                pausePlayThread();
                
                score_time_dp = null;
                
                saveStage();
                total_score = StaticInfo.STAGE_INIT_SCORE - (score_progress_tick * 100) + (score_combo * 100) + (life_count * 500)
                		 	- (score_time * 200) ;
                
                sec1 = score_time % 10;
                sec10 = (score_time % 60) / 10;
                min1 = (score_time / 60) % 10;
                min10 = (score_time / 60) / 10;
                score_time_dp  =String.format("%d%d:%d%d", min10, min1, sec10, sec1);
                
                dialog.setMessage(" Time  :  " + score_time_dp + "\n Combo  :  " + score_combo + "\n Total Score  :  " + total_score );
                
                // save score - begin
                ScoreDBHelper mScoreDB = new ScoreDBHelper(mContext);
                SQLiteDatabase db = mScoreDB.getWritableDatabase();
                ContentValues cv = new ContentValues();
                
                cv.put("stage", "" + stage_number);
                cv.put("score", "" + total_score);
                cv.put("time", "" + score_time);
                cv.put("combo", "" + score_combo);
                db.insert("score", null, cv);
                
                mScoreDB.close();
                // save score - end
                
                mDialogHandler.sendEmptyMessage(0);
                return;
            }
        }
            
        
        for(AnswerTile tmp : mAnswerTile)
        {
            // DM.i("tmp is :" + mAnswerTile.indexOf(tmp));
            
            if(tmp.rect.contains(touch_x, touch_y))
            {
                if(tmp.answer == mQuestionTile.answer) // 정답 타일을 터치 하였을 경우
                {
                    
                    if(bPlayVib == true)
                        mViberator.vibrate(StaticInfo.VIB_ANSWER_TILE_TOUCH);
                    
                    if(bPlaySnd == true)
                    	mSoundEffect.playSfxRight();
                    
                    if(score_combo_check == 1)
                    {
                        score_combo += 1;
                        DM.e("THIS IS COMBO !!!    /    score_combo:" + score_combo);
                        
                        if( score_combo % 3 == 0 && bDrawItemUfo == false ) // item ufo ���� ����
                        {
                            DM.e("Make Item - The UFO !!!");
                            
                            // ufo ����
                            bDrawItemUfo = true;
                            
                            int nPattern = rnd.nextInt(5);
                            int ufo_x = 0, ufo_y = 0;
                            
                            DM.i("ufo moving pattern : " + nPattern);
                            if( nPattern == 0 || nPattern == 1 || nPattern == 2)
                            {
                                ufo_x = screen_width + tile_w;
                                if( nPattern == 0 ) ufo_y = answer_tile_margin_top;
                                if( nPattern == 1 ) ufo_y = answer_tile_margin_top + tile_h * 5;
                                if( nPattern == 2 ) ufo_y = answer_tile_margin_top + tile_h * 3;
                            }
                            else if( nPattern == 3 || nPattern == 4 || nPattern == 5 )
                            {
                                ufo_x = -(tile_w * 2);
                                if( nPattern == 3 ) ufo_y = answer_tile_margin_top;
                                if( nPattern == 4 ) ufo_y = answer_tile_margin_top + tile_h * 5;
                                if( nPattern == 5 ) ufo_y = answer_tile_margin_top + tile_h * 3;
                            }
                            else
                            {
                                ufo_x = -(tile_w * 2);
                                ufo_y = answer_tile_margin_top;
                            }
                                
                            mItemUfo = new ItemUfo( mContext, screen_width, ufo_x, ufo_y,  nPattern);
                        }
                    }
                    score_combo_check = 1;
                    
                    MakeTileFragmentation(tmp.x1 + tmp.imgAnswerTile.getWidth() / 2, tmp.y1 + tmp.imgAnswerTile.getHeight() / 2);
                    mAnswerCheckImageDraw.add(new AnswerCheckImageDraw(mContext, tmp.x1, tmp.y1, 0, 0, true));
                   
                    mAnswerTile.remove(tmp);
                    DM.i( "in TouchCheckWithAnswerTiles() / mAnswerTile size after remove:" + mAnswerTile.size() );
                }
                else // 오답 타일을 터치 하였을 경우
                {
                    
                    if(bPlayVib == true)
                        mViberator.vibrate(StaticInfo.VIB_ANSWER_TILE_TOUCH_WRONG);
                    
                    if(bPlaySnd == true)
                    	mSoundEffect.playSfxWrong();
                    
                    life_count -= 1;
                    if( life_count < 0 )
                    {
                        if(bPlaySnd == true)
                            mSoundEffect.playSfxStageClear();
                        
                        mStatusInfo.setGameState(StaticInfo.GAME_STATE_END);
                        
                        // mQuestionTileChangeHandler.removeMessages(StaticInfo.HANDLER_ID_DEFAULT);
                        mQuestionTileChangeHandler.removeCallbacksAndMessages(null);
                        mCountDownHandler.removeCallbacksAndMessages(null);
                        
                        stopPlayThread();
                        return;
                    }
                    life_count -= 0;
                    score_combo_check = 0;
                    mAnswerCheckImageDraw.add(new AnswerCheckImageDraw(mContext, tmp.x1, tmp.y1, 0, 0, false));
                }
                touch_x = 0;
                touch_y = 0;
                break;
            }
        }
    } // TouchCheckWithAnswerTiles() end.
    
    // methods of TouchCheckWithAnswerTiles() : StageClearCheck()
    private boolean StageClearCheck()
    {
        if(mAnswerTile.size() == 0 && mFrag.size() == 0 && mAnswerCheckImageDraw.size() == 0)
            return true;
            
        return false;
    }
    
    // methods of TouchCheckWithAnswerTiles() : MakeTileFragmentation()
    private void MakeTileFragmentation(int x, int y)
    {
        frag_count = rnd.nextInt(15) + 10;
        
        for(int i = 0; i <= frag_count; i++)
        {
            frag_angle = rnd.nextInt(360);            
            mFrag.add(new Frag(mContext, x, y, frag_angle, tile_w, screen_width, screen_height, 0));
        }
    }
    
    // methods of DrawTileFragmentation : MoveTileFragmentation()
    private void MoveTileFragmentation()
    {
        for(int i = 0; i < mFrag.size(); i++)
        {
            mFrag.get(i).MoveFrag();
            if(mFrag.get(i).bOutOfRange == true)
                mFrag.remove(i);
        }
    }
    
    
    // methods of TouchCheckAll() : TouchWithItems() ----------------------------------------------
    private void TouchCheckWithItems()
    {
        if( mItemUfo != null && mItemUfo.rect.contains( touch_x, touch_y ) ) 
        {
            if(bPlayVib == true)
                mViberator.vibrate(StaticInfo.VIB_ANSWER_TILE_TOUCH);
            
            if(bPlaySnd == true)
                mSoundEffect.playSfxRight();
            
            MakeTileFragmentation( touch_x, touch_y );
            
            int i = 0;
            for( AnswerTile tmp : mAnswerTile )
            {
                /*DM.i( ">>>>>>>> index:" + mAnswerTile.indexOf(tmp) + "  /  ufo x:" + mItemUfo.x + "  /  ufo y:" + mItemUfo.y 
                        + "      / ufo x width:" + mItemUfo.imgUfo[0].getWidth() + "  /  ufo y height:" + mItemUfo.imgUfo[0].getHeight()
                        + "      / tmp x1:" + tmp.x1 + "  /  tmp y1:" + tmp.y1 + "  /  tmp x2:" + tmp.x2 + "  /  tmp y2:" + tmp.y2
                        + "      / mAnswerTile size:" + mAnswerTile.size() );*/
                
                if( Rect.intersects(mItemUfo.rect, tmp.rect ) )
                {
                    indexData[i] = mAnswerTile.indexOf( tmp );
                    i++;
                }
            }
            
            for( int j = (i - 1); j >= 0; j-- )
            {
                if(bPlayVib == true)
                    mViberator.vibrate(StaticInfo.VIB_ANSWER_TILE_TOUCH);
                
                if(bPlaySnd == true)
                    mSoundEffect.playSfxRight();
                
                MakeTileFragmentation( mAnswerTile.get(indexData[j]).x1 + mAnswerTile.get(indexData[j]).imgAnswerTile.getWidth() / 2, 
                        mAnswerTile.get(indexData[j]).y1 + mAnswerTile.get(indexData[j]).imgAnswerTile.getHeight() / 2 );
                mAnswerCheckImageDraw.add( new AnswerCheckImageDraw(mContext, mAnswerTile.get(indexData[j]).x1, mAnswerTile.get(indexData[j]).y1, 0, 0, true) );
                mAnswerTile.remove( indexData[j] );
            }
                        
            touch_x = 0;
            touch_y = 0;
            bDrawItemUfo = false;
            mItemUfo = null; 
        }
    } // TouchCheckWithItems()
    
    
    // methods of DrawItemUfo : ItemUfoMoveAndCollisionCheck() ------------------------------------
    private void ItemUfoMoveAndCollisionCheck()
    {
        if( mItemUfo.MoveItemUfoImage(mItemUfo.moving_pattern) == false )
        {
            mItemUfo = null;
            return;
        }
        else
        {
            // DM.i("draw ufo / in ItemUfoMoveAndCollisionCheck()");
        }
        
        // DM.e( "in ItemUfoMoveAndCollisionCheck");
    }
    
    
    // public methods for communication ===========================================================
    // stop PlayThread ----------------------------------------------------------------------------
    public void stopPlayThread()
    {
        DM.i(this, "in stopPlayThread()");
        
        threadRun = false;
        
        synchronized(this)
        {
            this.notify();
        }
    }
    
    // pause PlayThread ---------------------------------------------------------------------------
    public void pausePlayThread()
    {
        DM.i(this, "in pausePlayThread()");
        threadWait = true;
        
        synchronized(this)
        {
            this.notify();
        }
    }
    
    // resume PlayThread ------------------------------------------------------------------------------
    public void resumePlayThread()
    {
        threadWait = false;
        synchronized(this)
        {
            this.notify();
        }
    }
}
