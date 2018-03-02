package com.hxh.component.utils;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

/**
 * Created by hxh on 2018/2/3.
 */

public class Utils {
    public static void back()
    {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                    System.out.println("Exception when onBack" + e.toString());
                }
            }
        });
    }


    public static void setBackgroundAlpha(Context mContext, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public static int getColor(Context context,int id)
    {
        try {
            int color = ContextCompat.getColor(context, id);
            return color;
        } catch (Exception e) {
            return id;
        }
    }

    public static String getDefaultAddressJson(Context context)
    {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("china_city_data.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }




}
