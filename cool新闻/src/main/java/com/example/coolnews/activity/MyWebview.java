package com.example.coolnews.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.coolnews.MyApplication;
import com.example.coolnews.R;
import com.example.coolnews.entity.news;
import com.example.coolnews.tool.Jsouputil;
import com.example.coolnews.tool.SharedPreferenceUtil;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.example.coolnews.MyApplication.newsList;

public class MyWebview extends BaseActivity {
    private Toolbar toolbar;
    private WebView webView;
    private WebSettings webSettings;
    private String url;
    private ProgressBar progressBar;
    private boolean favoriteitem;


    @Override
    public void viewClick(View v) {

    }

    @Override
    public void initParams(Bundle bundle) {
        /*if (bundle!=null){
            url=bundle.getString("url");
        }*/
        url= MyApplication.newsweb.getUrl();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_mywebview;
    }

    @Override
    public void initView(View view) {
        toolbar=$(R.id.toolbar);
        webView=$(R.id.webview);
        progressBar=$(R.id.progressBar);
        webSettings=webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
        //menuItem.setCheckable(false);
        //Log.e("url", url);
        toolbar.setTitle("加载中...");
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack()){
                    webView.goBack();
                }else {
                    finish();
                }
            }
        });
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

       /* //支持插件
        webSettings.setPluginsEnabled(true);*/

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setAppCacheEnabled(true);//是否使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //缓存
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setJavaScriptEnabled(true);//是否允许执行js，
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                    toolbar.setTitle(webView.getTitle());
                    //menuItem.setCheckable(true);
                }

            }
        });
        if (url==null){
            toolbar.setTitle("地址有误");
        }else {
            Log.e("url","http://3g.163.com/news/article/"+url+".html");
            Jsouputil.getUrlDocument("http://3g.163.com/news/article/"+url+".html", MyWebview.this, new Jsouputil.JsoupListener() {
                @Override
                public void onFinish(Document document) {
                    Elements scripts=document.select("script[src]");
                    //Log.e("srcsize",scripts.size()+"");
                    for (int i=0;i<scripts.size();i++){
                        scripts.get(i).attr("src",scripts.get(i).attr("abs:src"));
                        //Log.e("src",scripts.get(i).attr("src"));
                    }
                    String src=document.select("div.video").select("video").attr("data-src");
                    if (document.select("div.video").size()>0) {
                        document.select("div.video").first().html("<video width=\"320\" height=\"240\" controls controls>\n" +
                                "  <source src=\"" + src + "\" type=\"video/mp4\">\n" +
                                "</video>");
                        Log.e("src", src);
                    }
                    document.select("div.topbar").remove();
                    document.select("div.a_adtemp").remove();
                    document.select("div.container").remove();
                    document.select("div.pic_container").remove();
                    document.select("div.foot_nav").remove();
                    document.select("div.copyright").remove();
                    document.select("div.hot_news").remove();
                    document.select("div.spinfo").remove();//header
                    document.select("div.relative_doc").remove();
                    document.select("span.wakeup_bar").remove();
                    document.select("span.wakeup_desc").remove();
                    document.select("a.bot_word").remove();
                    document.select("header").remove();
                    // 设置WevView要显示的网页
                    webView.loadDataWithBaseURL(null, document.html(), "text/html", "utf-8", null);
                    //webView.loadUrl(src);
                }

                @Override
                public void onError(Exception e) {
                    toolbar.setTitle("地址有误");
                }
            });
        }
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mywebitem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //menu.findItem(R.id.favorite).setChecked(false);
        /*if(newsList.contains(MyApplication.newsweb)){
            menu.findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_black_24dp);
            favoriteitem=true;
        }*/
        for(news s :newsList){
            if (s.getUrl().equals(url)){
                menu.findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_black_24dp);
                favoriteitem=true;
                break;
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fuzhi:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(webView.getUrl());
                Toast.makeText(MyWebview.this, "复制成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.fenxiang:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
                shareIntent.setType("text/plain");
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
            case R.id.qita:
                webView.reload();
                break;
            case R.id.favorite:
                if (favoriteitem){
                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                    //newsList.remove(MyApplication.newsweb);
                    for(int i=0;i<newsList.size();i++){
                        if (newsList.get(i).getUrl().equals(url)){
                            newsList.remove(i);
                            break;
                        }
                    }
                    SharedPreferenceUtil.setFavoriteNewlsit(MyWebview.this);
                    favoriteitem=false;
                }else {
                    Snackbar.make(webView,"收藏成功",Snackbar.LENGTH_SHORT).show();
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                    newsList.add(0,MyApplication.newsweb);
                    SharedPreferenceUtil.setFavoriteNewlsit(MyWebview.this);
                    favoriteitem=true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
            webView.goBack();
            return true;
        }
        else {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
