package com.brownrice.matchtouch;

import java.util.Random;

import com.brownrice.utils.DM;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


// 게임중 나타나는 UFO 아이템을 위한 클래스 입니다.
public class ItemUfo
{

    public Random                       rnd = new Random();
    public Rect                         rect;
    Bitmap imgUfo[] = new Bitmap[12];
    
    public int                          x, y, sx, sy, width, height, imgIdx, moving_pattern;
    public boolean                      isMoveItemUfo;
    
    public ItemUfo(Context context, int _screen_width, int _x, int _y, int pattern)
    {
        DM.e("Create 'ItemUfo' / pattern:" + pattern);
        
        x = _x;
        y = _y;
        width = _screen_width;
        imgIdx = 0;
        rect = new Rect();
        isMoveItemUfo = true;
        moving_pattern = pattern;
        
        for(int i = 0; i < 12; i++)
        {
            imgUfo[i] = BitmapFactory.decodeResource( context.getResources(), R.drawable.item_ufo_01 + i );
            imgUfo[i] = Bitmap.createScaledBitmap( imgUfo[i], imgUfo[i].getWidth() * 2, imgUfo[i].getHeight() * 2, true );
        }
        
        rect.set( x, y, x + imgUfo[0].getWidth(), y + imgUfo[0].getHeight() );
    }
    
    public boolean MoveItemUfoImage(int pattern)
    {
        if( isMoveItemUfo == false ) return false;
        
        if( width < 500 )
        {
            sy = rnd.nextInt(5);
            sx = rnd.nextInt(6);
        }
        else if( width < 900 )
        {
            sy = rnd.nextInt(5) + 2;
            sx = rnd.nextInt(6) + 2;
        }
        else
        {
            sy = rnd.nextInt(5) + 4;
            sx = rnd.nextInt(6) + 4;
        }
        
        
        if( pattern == 0 || pattern == 1 || pattern == 2 )
        {
            x = x - sx;
            if( pattern == 0 ) y = y + sy;
            if( pattern == 2 ) y = y - sy;
        }
        else if ( pattern == 3 || pattern == 4 || pattern == 5 )
        {
            x = x + sx;
            if( pattern == 3 ) y = y + sy;
            if( pattern == 5 ) y = y - sy;
        }
        else
        {
            x = x - sx;
            y = y + sy;
        }
        
        imgIdx++;
        if(imgIdx > 11) imgIdx = 0;
        
        rect.set( x, y, x + imgUfo[0].getWidth(), y + imgUfo[0].getHeight() );
        
        // if( x < 0 || y > PlayThread.screen_height )
        if( x < -(PlayThread.tile_w * 3) || x > (PlayThread.screen_width + PlayThread.tile_w * 2)
                || y < 0 || y > PlayThread.screen_height)
        {
            isMoveItemUfo = false;
            PlayThread.bDrawItemUfo = false;
        }
        
        return true;
    }
    
}
