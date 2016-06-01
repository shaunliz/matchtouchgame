package com.brownrice.matchtouch;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Frag
{
    public Bitmap                                       imgFrag;
    
    public int                                          x, y, rad;
    public boolean                                      bOutOfRange;
    
    private int                                         width, height;
    private int                                         cx, cy, crad;
    private double                                      radian;     
    private int                                         speed;
    private int                                         num;
    private int                                         life;
    
    public Frag(Context context, int _cx, int _cy, int angle, int _tile_width, int _width, int _height, int _resolution)
    {
        cx = _cx;
        cy = _cy;
        width = _width;
        height = _height;
        radian = angle * Math.PI / 180;
        
        // 파티클들이 흩어지기 위한 방향과 속도를 랜덤하게 결정하는 부분 입니다.
        Random rnd = new Random();
        speed = rnd.nextInt(_tile_width / 15) + 2;
        
        rad = rnd.nextInt(_tile_width) / 10 + 2;
        
        num = rnd.nextInt(10);  // 0 ~ 9
        life = rnd.nextInt(35) + 20;
        
        imgFrag = BitmapFactory.decodeResource(context.getResources(), R.drawable.frag_00 + num);
        imgFrag = Bitmap.createScaledBitmap(imgFrag, rad * 2, rad * 2, false);
        
        crad = _tile_width / 15;
        
        
    }
    
    public void MoveFrag()
    {
        life--;
        crad += speed;
        
        x = (int)(cx + Math.cos(radian) * crad);
        y = (int)(cy + Math.sin(radian) * crad);
        
        if( x < -rad || x > width + rad || y < -rad || y > height + rad || life <= 0)
            bOutOfRange = true;
    }
}
