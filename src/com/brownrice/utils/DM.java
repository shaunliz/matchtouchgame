package com.brownrice.utils;

import android.util.Log;

public class DM
{
    public static boolean isDebug = true;
    public static String TAG = "BRS";
    
    // information log
    public static void i(Object o, String s)
    {
        if(isDebug)
            Log.i(TAG, o.getClass().getSimpleName() + "   ][   " + s);
    }
    
    public static void i(String Tag, String s)
    {
        if(isDebug)
            Log.i(Tag, s);
    }
    
    public static void i(String s)
    {
        if(isDebug)
            Log.i(TAG, s);
    }
    
    // error log
    public static void e(Object o, String s)
    {
        if(isDebug)
            Log.e(TAG, o.getClass().getSimpleName() + "   ][   " + s);
    }
    
    public static void e(String Tag, String s)
    {
        if(isDebug)
            Log.e(Tag, s);
    }
    
    public static void e(String s)
    {
        if(isDebug)
            Log.e(TAG, s);
    }
    
    // verbos log
    public static void v(Object o, String s)
    {
        if(isDebug)
            Log.v(TAG, o.getClass().getSimpleName() + "   ][   " + s);
    }
    
    public static void v(String Tag, String s)
    {
        if(isDebug)
            Log.v(Tag, s);
    }
    
    public static void v(String s)
    {
        if(isDebug)
            Log.v(TAG, s);
    }
    
}
