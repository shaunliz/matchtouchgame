package com.brownrice.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

public class BSRINFO
{
    public static int getWindowWidth(Activity A)
    {
        WindowManager mWindowManager = (WindowManager)A.getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager.getDefaultDisplay().getWidth();
    }
    
    public static int getWindowHeight(Activity A)
    {
        WindowManager mWindowManager = (WindowManager)A.getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager.getDefaultDisplay().getHeight();
    }
}
