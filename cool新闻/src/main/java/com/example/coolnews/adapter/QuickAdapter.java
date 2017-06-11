package com.example.coolnews.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.coolnews.MyApplication;
import com.example.coolnews.R;
import com.example.coolnews.activity.MyWebview;
import com.example.coolnews.entity.news;
import com.example.coolnews.tool.SharedPreferenceUtil;
import com.squareup.picasso.Picasso;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiong on 17-5-24.
 */

public class QuickAdapter extends BaseMultiItemQuickAdapter<news, BaseViewHolder> {
    private Activity activity;
    private XBanner mBannerNet;
    public  QuickAdapter(Activity activity,List<news> list) {
        super(list);
        this.activity=activity;
        addItemType(news.TEXT, R.layout.banner);
        addItemType(news.IMG, R.layout.carditme);
    }

    @Override
    protected void convert(final BaseViewHolder viewHolder, final news item) {
        switch (viewHolder.getItemViewType()) {
            case news.TEXT:
                final List<String> imgesUrl = new ArrayList<>();
                final List<String> tips = new ArrayList<>();
                for (int i=0;i<item.getNewsList().size();i++){
                    imgesUrl.add(item.getNewsList().get(i).getImgsrc());
                    tips.add(item.getNewsList().get(i).getTitle());
                }
                mBannerNet=viewHolder.getView(R.id.banner_1);
                //添加广告数据
                mBannerNet.setData(imgesUrl,tips);//第二个参数为提示文字资源集合
                mBannerNet.setPageTransformer(Transformer.Accordion);
                mBannerNet.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(activity).load(imgesUrl.get(position)).into((ImageView) view);
                    }
                });
                mBannerNet.setOnItemClickListener(new XBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(XBanner banner, int position) {
                        /*Intent intent=new Intent();
                        intent.putExtra("url",item.getUrl());
                        intent.setClass(activity, MyWebview.class);
                        activity.startActivity(intent);*/
                        MyApplication.newsweb=item.getNewsList().get(position);
                        MyApplication.newsweb.setItemType(1);
                        activity.startActivity(new Intent(activity,MyWebview.class));
                    }
                });
                break;
            case news.IMG:
                viewHolder.setText(R.id.title,item.getTitle()).setText(R.id.source,item.getSource());
                CardView cardView=viewHolder.getView(R.id.cardView);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    /*    Intent intent=new Intent();
                        intent.putExtra("url",item.getUrl());
                        intent.setClass(activity, MyWebview.class);
                        activity.startActivity(intent);
                        Log.e("url", item.getUrl()+"");*/
                        MyApplication.newsweb=item;
                        MyApplication.newsweb.setItemType(1);
                        activity.startActivity(new Intent(activity,MyWebview.class));
                    }
                });
                if(!"".equals(item.getImgsrc())&& SharedPreferenceUtil.getWifiBoolean(activity)) {
                    Picasso.with(activity).load(item.getImgsrc()).placeholder(R.drawable.logo_white).into((ImageView) viewHolder.getView(R.id.img1));
                }
                break;

        }
    }
}