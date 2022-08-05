package com.android.adybelliservice.StoreApplication.Common;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.speech.tts.UtteranceProgressListener;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.adybelliservice.StoreApplication.Model.Message;
import com.android.adybelliservice.R;

import java.util.Locale;

public class Utils {
    public static void hideAdd(Fragment fragment, String tagFragmentName, FragmentManager mFragmentManager, int frame) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(frame, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }



    public static void removeShow(Fragment fragment, String tagFragmentName, FragmentManager mFragmentManager, int frame) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(frame, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    public static Typeface getBoldFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Ubuntu-Bold.ttf");
        return font;
    }

    public static Typeface getLightFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Ubuntu-Light.ttf");
        return font;
    }

    public static Typeface getMediumFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Ubuntu-Medium.ttf");
        return font;
    }

    public static Typeface getRegularFont(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Ubuntu-Regular.ttf");
        return font;
    }

    public static void setLocale(String lang, Context context) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        //saved data to shared preferences
        SharedPreferences.Editor editor = context.getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    // load language saved in shared preferences
    public static void loadLocal(Context context) {
        SharedPreferences share = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String languages = share.getString("My_Lang", "");
        Utils.setLocale(languages, context);
    }


    public static String getLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Settings", MODE_PRIVATE);
        String value = prefs.getString("My_Lang", "tm");
        return value;
    }

    public static void setPreference(String name,String value,Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(name, MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getSharedPreference(Context context, String name) {
        SharedPreferences prefs = context.getSharedPreferences(name, MODE_PRIVATE);
        String value = prefs.getString(name, "");
        return value;
    }

    public static String getStringResource(int id, Context context) {
        return context.getResources().getString(id);
    }

    public static String checkMessage(Message message,Context context){
        if(message==null){
            return "";
        }
        String messageText=message.getText();
        if(Utils.getLanguage(context).equals("ru")){
            messageText=message.getText_ru();
        }
        return messageText;
    }

}
