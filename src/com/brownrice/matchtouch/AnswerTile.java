package com.brownrice.matchtouch;

import com.brownrice.utils.DM;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class AnswerTile
{
    public Bitmap                   imgAnswerTile;
    public Rect                     rect;
    public boolean                  isShow;
    
    public int                     x1, y1, x2, y2, score, answer, tile_category;
    
    public AnswerTile(Context context, float x, float y, int answer_idx, int answer_tile_image_cagetory)
    {
    
        x1 = (int)x;
        y1 = (int)y;
        x2 = x1 + PlayThread.tile_w;
        y2 = y1 + PlayThread.tile_h;
        answer = answer_idx;
        tile_category = answer_tile_image_cagetory;
        rect = new Rect(x1, y1, x2, y2);
        
        imgAnswerTile = BitmapFactory.decodeResource(context.getResources(), tile_category + answer_idx);
        imgAnswerTile = Bitmap.createScaledBitmap(imgAnswerTile, PlayThread.tile_w, PlayThread.tile_h, true);
    }
}
