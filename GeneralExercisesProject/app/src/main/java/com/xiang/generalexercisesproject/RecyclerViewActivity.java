package com.xiang.generalexercisesproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/7.
 */
public class RecyclerViewActivity extends Activity {
    RecyclerView recyclerRV;
    List<Photo> photos = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerRV = (RecyclerView) findViewById(R.id.recyclerRV);
        initData();
        //设置布局显示方式
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerRV.setLayoutManager(mLayoutManager);
        //设置添加删除item时候的动画
        recyclerRV.setItemAnimator(new DefaultItemAnimator());

        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerRV.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.addDatas(photos);
        setHeader();

        //接口回调实现监听
        recyclerViewAdapter.setListViewClick(new RecyclerViewAdapter.ListViewClick() {
            @Override
            public void listItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void listItemLongClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "长按了" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }
//头部添加
    private void setHeader() {
        View header = LayoutInflater.from(this).inflate(R.layout.rv_item, null);
        recyclerViewAdapter.setHeadView(header);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            Photo photo = new Photo();
            photo.setName("名字");
            photo.setAge("15");
            photos.add(photo);
        }
    }
}
