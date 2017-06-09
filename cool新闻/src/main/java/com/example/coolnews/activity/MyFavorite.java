package com.example.coolnews.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.coolnews.MyApplication;
import com.example.coolnews.R;
import com.example.coolnews.adapter.FavoriteAdapter;
import com.example.coolnews.tool.SharedPreferenceUtil;
import com.example.coolnews.view.WrapContentLinearLayoutManager;

public class MyFavorite extends BaseActivity {
    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private FavoriteAdapter quickAdapter;


    @Override
    public void viewClick(View v) {

    }

    @Override
    public void initParams(Bundle bundle) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_my_favorite;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = $(R.id.recyclerView);
        toolbar=$(R.id.toolbar);
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        Log.e("newlistsize",MyApplication.newsList.size()+"");
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(MyFavorite.this));
        mRecyclerView.setHasFixedSize(true);
        quickAdapter=new FavoriteAdapter(MyFavorite.this, MyApplication.newsList);
        mRecyclerView.setAdapter(quickAdapter);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(quickAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        quickAdapter.enableSwipeItem();
        quickAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                /*MyApplication.newsList=quickAdapter.getData();
                SharedPreferenceUtil.setFavoriteNewlsit(MyFavorite.this);*/
                //quickAdapter.notifyItemRemoved(pos);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mRecyclerView!=null){
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.newsList=quickAdapter.getData();
        SharedPreferenceUtil.setFavoriteNewlsit(MyFavorite.this);
    }
}
