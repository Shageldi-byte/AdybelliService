package com.android.adybelliservice.SmsApplication.Common;

import android.content.Context;
import android.graphics.Typeface;

public class AppFont {
    public static Typeface getRegularFont(Context context){
        return  Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica.ttf");
    }
    public static Typeface getBoldFont(Context context){
        return  Typeface.createFromAsset(context.getAssets(), "fonts/Helvetica-Bold.ttf");
    }
    public static Typeface getCompressedFont(Context context){
        return  Typeface.createFromAsset(context.getAssets(), "fonts/helvetica-compressed-5871d14b6903a.otf");
    }
    public static Typeface getLightFont(Context context){
        return  Typeface.createFromAsset(context.getAssets(), "fonts/helvetica-light-587ebe5a59211.ttf");
    }
    public static Typeface getSemiBoldFont(Context context){
        return  Typeface.createFromAsset(context.getAssets(), "fonts/helvetica-rounded-bold-5871d05ead8de.otf");
    }


}
