package com.brownrice.matchtouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AnswerCheckImageDraw
{
    public Bitmap                                       imgAnswerCheck;
    
    public boolean                                      bAnswerCheck, bOutOfRange;
    public int                                          x, y, cx, cy, start_posy,/*sx,*/ sy, imgAlpha;    
    
    public AnswerCheckImageDraw(Context context, int _x, int _y, int _w, int _h, boolean _answer_check)
    {
        x = _x;
        imgAlpha = 255;
        start_posy = y = _y;
        bAnswerCheck = _answer_check;
        
        if(bAnswerCheck == true)        
            imgAnswerCheck = BitmapFactory.decodeResource(context.getResources(), R.drawable.answer_check_right);
        else
            imgAnswerCheck = BitmapFactory.decodeResource(context.getResources(), R.drawable.answer_check_wrong);
        
        sy = imgAnswerCheck.getHeight() / 15;
    }
    
    public void MoveAnswerImage()
    {
        y = y - sy;
        imgAlpha = imgAlpha - sy;
        if(y < start_posy - imgAnswerCheck.getHeight() * 2)
            bOutOfRange = true;
    }
}
