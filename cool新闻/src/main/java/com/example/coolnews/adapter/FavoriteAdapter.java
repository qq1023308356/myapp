package com.example.coolnews.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.coolnews.MyApplication;
import com.example.coolnews.R;
import com.example.coolnews.activity.MyWebview;
import com.example.coolnews.entity.news;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xiong on 17-6-6.
 */

public class FavoriteAdapter extends BaseItemDraggableAdapter<news, BaseViewHolder>{
    private Activity activity;
    public  FavoriteAdapter(Activity activity,List<news> list) {
        super(R.layout.carditme,list);
        this.activity=activity;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final news item) {
        viewHolder.setText(R.id.title,item.getTitle()).setText(R.id.source,item.getSource());
        CardView cardView=viewHolder.getView(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.newsweb=item;
                MyApplication.newsweb.setItemType(1);
                activity.startActivity(new Intent(activity,MyWebview.class));
            }
        });
        if(!"".equals(item.getImgsrc())) {
            Picasso.with(activity).load(item.getImgsrc()).placeholder(R.drawable.logo_white).into((ImageView) viewHolder.getView(R.id.img1));
        }

    }
}
