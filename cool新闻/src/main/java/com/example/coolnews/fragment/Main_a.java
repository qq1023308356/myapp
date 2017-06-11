package com.example.coolnews.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.example.coolnews.R;
import com.example.coolnews.adapter.QuickAdapter;
import com.example.coolnews.entity.news;
import com.example.coolnews.tool.Jsouputil;
import com.example.coolnews.tool.SharedPreferenceUtil;
import com.example.coolnews.view.WrapContentLinearLayoutManager;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiong on 17-5-24.
 */

public class Main_a extends BaseFragment {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private QuickAdapter quickAdapter;
    private List<news> items = new ArrayList<>();
    private List<news> item = new ArrayList<>();
    private int [] listanim={0x00000001,0x00000002,0x00000003,0x00000004,0x00000005};
    private String url = "";
    private int pos=0;

    @SuppressLint("ValidFragment")
    private Main_a () {
    }

    public static Main_a newInstance(String url) {
        Main_a newFragment = new Main_a();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = $(R.id.recyclerView);
        mSwipeRefreshLayout = $(R.id.swipeLayout);
        Bundle args = getArguments();
        if (args != null) {
            url = args.getString("url");
        }
    }

    @Override
    public void viewClick(View v) {

    }

    @Override
    public void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //items.clear();
                pos=0;
                quickAdapter.setEnableLoadMore(false);
                setNewList(0);
            }
        });

    }

    @Override
    public void doBusiness(Context mContext) {
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setRefreshing(true);
        //setup materialviewpager
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setHasFixedSize(true);
        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        if (!url.equals("")) {
            setNewList(0);
        }
    }

    private void setNewList(final int type) {
        if (type==0&&quickAdapter!=null){
            quickAdapter.notifyItemRangeRemoved(0,item.size()+items.size());
            items.clear();
        }
        item.clear();
        //item=new ArrayList<>();
        Jsouputil.getUrlDocument("http://c.m.163.com/nc/article/headline/"+url+"/"+pos+"-20.html", getActivity(), new Jsouputil.JsoupListener() {
            @Override
            public void onFinish(Document document) {
                //Log.e("one", one+":"+items.size());
                try {
                    JSONObject jsonObject = new JSONObject(document.text().toString());
                    JSONArray jsonArray = jsonObject.getJSONArray(url);
                    //Log.e("json",jsonArray.get(1).toString());
                    for (int i = 1; i < jsonArray.length(); i++) {
                        news news = null;
                        if (i==1&&type==0) {
                            news=new news(0);
                            List<news> newsList = new ArrayList<>();
                            for (; i <= 5; i++) {
                                news newitem = new news(0);
                                JSONObject object = jsonArray.getJSONObject(i);
                                if (object.has("postid")) {
                                    newitem.setTitle(object.getString("title"));
                                    newitem.setImgsrc(object.getString("imgsrc"));
                                    newitem.setUrl(object.optString("postid"));
                                    newitem.setSource(object.getString("source"));
                                    newsList.add(newitem);
                                }
                            }
                            news.setNewsList(newsList);
                            //if (newsList.size()>0){items.add(news);}
                            items.add(news);
                        } else {
                            news=new news(1);
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("postid")) {
                                news.setTitle(object.optString("title"));
                                news.setImgsrc(object.optString("imgsrc"));
                                news.setUrl(object.optString("postid"));//docid
                                news.setSource(object.optString("source"));
                                item.add(news);
                            }
                        }
                        //if (type==0){items.add(news);}
                        //else {item.add(news);}
                    }
                   /* if (type==0&&quickAdapter!=null) {
                        quickAdapter.notifyItemRangeChanged(0,items.size(),items);
                        quickAdapter.addData(item);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    else */if (type==0) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        quickAdapter = new QuickAdapter(getActivity(), items);
                        quickAdapter.addData(item);
                        mRecyclerView.setAdapter(quickAdapter);
                        quickAdapter.isFirstOnly(SharedPreferenceUtil.getanimBoolean(getActivity()));
                        //quickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//SCALEIN
                        if (SharedPreferenceUtil.getlistanim(getActivity())==0){
                            quickAdapter.openLoadAnimation(new BaseAnimation() {
                                @Override
                                public Animator[] getAnimators(View view) {
                                    return new Animator[]{
                                            ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                                            ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
                                    };
                                }
                            });
                        }else {
                            //int listanim= Integer.parseInt("0x0000000"+ SharedPreferenceUtil.getlistanim(getActivity()));
                            //Log.e("listanim",SharedPreferenceUtil.getlistanim(getActivity())+"");
                            quickAdapter.openLoadAnimation(listanim[SharedPreferenceUtil.getlistanim(getActivity())-1]);
                        }
                        quickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                            @Override
                            public void onLoadMoreRequested() {
                                mSwipeRefreshLayout.setEnabled(false);
                                setNewList(1);
                            }
                        }, mRecyclerView);
                        pos+=20;
                    }else if (item.size()==0){
                        //数据全部加载完毕
                        quickAdapter.loadMoreEnd();
                    }else {
                        //quickAdapter.notifyItemRangeChanged(quickAdapter.getData().size(),item.size());
                        //quickAdapter.notifyItemRangeInserted(quickAdapter.getData().size(),item.size());
                        //Log.e("json",item.size()+"");
                        //quickAdapter.loadMoreEnd();
                        quickAdapter.addData(item);
                        quickAdapter.loadMoreComplete();
                        mSwipeRefreshLayout.setEnabled(true);
                        //quickAdapter.setEnableLoadMore(true);
                        pos+=20;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //quickAdapter.loadMoreFail();
                }
            }

            @Override
            public void onError(Exception e) {
                Snackbar.make(mRecyclerView,"网络错误",Snackbar.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
                quickAdapter = new QuickAdapter(getActivity(), null);
                mRecyclerView.setAdapter(quickAdapter);
                quickAdapter.isFirstOnly(false);
                //quickAdapter.loadMoreFail();
            }
        });
    }
}
