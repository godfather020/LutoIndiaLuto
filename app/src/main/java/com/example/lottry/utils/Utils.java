package com.example.lottry.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

  public static int getPixels(Context context, int valueInDp) {
        Resources r = context.getResources();
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, r.getDisplayMetrics());
        return (int) px;
    }

    public static int getPixels(Context context, float valueInDp) {
        Resources r = context.getResources();
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, r.getDisplayMetrics());
        return (int) px;
    }

   public static int getPixelsSp(Context context, int valueInSp) {
        Resources r = context.getResources();
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, valueInSp, r.getDisplayMetrics());
        return (int) px;
    }

    public static int getPixelsSp(Context context, float valueInSp) {
        Resources r = context.getResources();
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, valueInSp, r.getDisplayMetrics());
        return (int) px;
    }
}
