package com.example.coolnews.tool;

import android.app.Activity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by xiong on 17-5-24.
 */

public class Jsouputil {
    public static Document getUrlDocument(final String url, final Activity activity, final JsoupListener jsoupListener){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    final Document document=Jsoup.connect(url).ignoreContentType(true).userAgent("Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M032 Build/IML74K) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/4.1 Mobile Safari/533.1").get();
                    //这儿是耗时操作，完成之后更新UI；
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            jsoupListener.onFinish(document);
                        }
                    });
                } catch (final IOException e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            jsoupListener.onError(e);
                        }
                    });
                }
            }
        }.start();
        return null;
    }
    public interface JsoupListener{
        void onFinish(Document document);

        void onError(Exception e);
    }
}
