package com.brownrice.matchtouch;

import com.brownrice.utils.DM;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;

public class QuestionTile
{
    public Rect                 rect, pregressRect;
    public Bitmap               imgQuestionTile, imgQuestionNextTile, imgQuestionChangeProgressBar, imgQuestionTileFrame;
    public NinePatch            mNinePatchGraphic;
    public int                  x1, y1, x2, y2, score, answer, tile_category;
    public int                  progress_x1, progress_y1;
    
    public QuestionTile(Context context, float  x, float y, int question_idx, int question_tile_image_category)
    {
        pregressRect = new Rect();
        
        x1 = (int)x - PlayThread.tile_half_w;
        y1 = (int)y/* - PlayThread.tile_half_h*/;
        x2 = x1 + PlayThread.tile_w;
        y2 = y1 + PlayThread.tile_h;
        
        // DM.i(this, "in QuestionTile class constuctor   ][   x1:" + x1 + "   ][   y1:" + y1 + "   ][   y:" + y);
        
        answer = question_idx;
        tile_category = question_tile_image_category;
        imgQuestionTile = BitmapFactory.decodeResource(context.getResources(), question_tile_image_category + question_idx);
        imgQuestionTile = Bitmap.createScaledBitmap(imgQuestionTile, PlayThread.tile_w, PlayThread.tile_h, true);
        
        // progressbar - use ninepatch
        imgQuestionChangeProgressBar = BitmapFactory.decodeResource(context.getResources(), R.drawable.question_change_progress);
        mNinePatchGraphic = new NinePatch(imgQuestionChangeProgressBar, imgQuestionChangeProgressBar.getNinePatchChunk(), "");
        
        imgQuestionTileFrame = BitmapFactory.decodeResource(context.getResources(), R.drawable.frame_tile_background);
        imgQuestionTileFrame = Bitmap.createScaledBitmap(imgQuestionTileFrame, PlayThread.tile_w + PlayThread.tile_w / 6 * 2 + 4, 
                                                            PlayThread.tile_h + PlayThread.tile_h / 6 * 2 + 4, true);
        
        /*DM.i(this, "PlayThread.tile_w:" + PlayThread.tile_w + "     ][      imgQuestionTileFrme width:" + imgQuestionTileFrame.getWidth()
                + "     ][      ");*/
    }
    
    public void ChangeQuestionTileImage(Context context, int question_idx, int question_tile_image_category)
    {
        // DM.i(this, "in ChangeQuestionTileImage() | question_idx:" + question_idx);
        
        answer = question_idx;
        tile_category = question_tile_image_category;
        imgQuestionTile = BitmapFactory.decodeResource(context.getResources(), tile_category + question_idx);
        imgQuestionTile = Bitmap.createScaledBitmap(imgQuestionTile, PlayThread.tile_w, PlayThread.tile_h, true);
    }
    
    public void ChangeQuestionProgressImage(Context context, int width, int height)
    {
        //imgQuestionChangeProgressBar = Bitmap.createScaledBitmap(imgQuestionChangeProgressBar, width, height, true);
        
        // mNinePatchGraphic's rect.
        pregressRect.set(progress_x1, progress_y1, progress_x1 + width, progress_y1 + height);
    }
}
