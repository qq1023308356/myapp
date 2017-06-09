package com.example.coolnews.tool;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.coolnews.entity.NewsList;
import com.example.coolnews.entity.news;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.coolnews.MyApplication.newsList;

/**
 * Created by xiong on 17-5-26.
 */

public class SharedPreferenceUtil {
    public static void setNewlsit(Activity activity,List<NewsList> mUserList,List<NewsList> mOtherList){
        Gson gson=new Gson();
        SharedPreferences preferences=activity.getSharedPreferences("newslist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("mUserList", gson.toJson(mUserList));
        editor.putString("mOtherList", gson.toJson(mOtherList));
        editor.commit();
    }
    public static void setFavoriteNewlsit(Activity activity){
        Gson gson=new Gson();
        SharedPreferences preferences=activity.getSharedPreferences("newslist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("FavoriteNewsList", gson.toJson(newsList));
        editor.commit();
    }
    public static List<news> getFavoriteNewlsit(Activity activity){
        Gson gson=new Gson();
        SharedPreferences preferences=activity.getSharedPreferences("newslist", Context.MODE_PRIVATE);
        String json=preferences.getString("FavoriteNewsList", "");
        if ("".equals(json)){
            return null;
        }else {
            return gson.fromJson(json, new TypeToken<List<news>>(){}.getType());
        }
    }
    public static List<NewsList> getmUserList(Activity activity){
        Gson gson=new Gson();
        SharedPreferences preferences=activity.getSharedPreferences("newslist", Context.MODE_PRIVATE);
        String json=preferences.getString("mUserList", "");
        if ("".equals(json)){
            return null;
        }else {
            return gson.fromJson(json, new TypeToken<List<NewsList>>(){}.getType());
        }
    }
    public static List<NewsList> getmOtherList(Activity activity){
        Gson gson=new Gson();
        SharedPreferences preferences=activity.getSharedPreferences("newslist", Context.MODE_PRIVATE);
        String json=preferences.getString("mOtherList", "");
        if ("".equals(json)){
            return null;
        }else {
            //List<NewsList> lists = gson.fromJson(json, new TypeToken<List<NewsList>>(){}.getType());
            /*try {
                JSONArray jsonArray=new JSONArray(json);
                List<NewsList> lists=new ArrayList<>();
                for (int i=0;i<jsonArray.length();i++){
                    lists.add(gson.fromJson(json,NewsList.class));
                }
                return gson.fromJson(NewsList,"newslist");
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            return gson.fromJson(json, new TypeToken<List<NewsList>>(){}.getType());
        }
    }
}
