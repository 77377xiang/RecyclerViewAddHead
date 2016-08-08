package com.xiang.generalexercisesproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    List<Photo> photos = new ArrayList<>();
    Context context;
    ListViewClick listViewClick;
    //定义  判断要传入的布局标记
    static final int TYPE_HODE = 0;
    static final int TYPE_NOMAL = 1;
    //要添加的布局
    View headView;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    //从activity传入需要 添加的布局
    public void setHeadView(View headView) {
        this.headView = headView;
        notifyItemInserted(0);//在position位置插入数据的时候更新
    }

    //  添加数据   注意该添的数据默认添加在list<t>最后
    public void addDatas(List<Photo> datas) {
        Photo photo = new Photo();
        /*photo.setName("小王");
        photo.setAge("20");
        datas.add(photo);
        photos.addAll(datas);*/
        notifyDataSetChanged();//更新数据
    }

    //返回 item 布局个数   添加的头也是一个item 因此需要加1
    @Override
    public int getItemCount() {
        return headView == null ? photos.size() : photos.size() + 1;

    }

    //  返回多布局样式 ，与定义type 标记一样对应
    @Override
    public int getItemViewType(int position) {
        if (headView == null) return TYPE_NOMAL;
        if (position == 0) return TYPE_HODE;
        return TYPE_NOMAL;
    }

    // 生成为每个Item inflater出一个View，  法返回的是一个ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headView != null && viewType == TYPE_HODE) return new MyViewHolder(headView);
        View holder = LayoutInflater.from(context).inflate(R.layout.item_recycle, parent, false);
        return new MyViewHolder(holder);
    }

    //该方法主要是对item 下标进行判断，当添加有头，position - 1 ，在以后调用就不用考虑position所在位置
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headView == null ? position : position - 1;
    }

    //对获得布局里控件进行操作，传入数据，监听
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HODE) {
            return;
        }
        final int pos = getRealPosition(holder);
        if (holder instanceof MyViewHolder) {
            holder.nameTV.setText(photos.get(pos).getName());
            holder.ageTV.setText(photos.get(pos).getAge());
        }
        //短按
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //自己定义变量是因为自己可以可以增加头和未 这里的点击位置保持
                int pos = holder.getLayoutPosition();
                listViewClick.listItemClick(holder.itemView, pos);

            }
        });
        //长按
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int pos = holder.getLayoutPosition();
                listViewClick.listItemLongClick(holder.itemView, pos);
                return false;
            }
        });


    }

    //内部类初始化item 布局控件
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;
        TextView ageTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView == headView) return;
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            ageTV = (TextView) itemView.findViewById(R.id.ageTV);
        }
    }

    //定义接口
    public interface ListViewClick {
        void listItemClick(View view, int position);

        void listItemLongClick(View view, int position);
    }

    //定义方法，暴露接口（参数为接口方法）
    public void setListViewClick(ListViewClick listViewClick) {
        this.listViewClick = listViewClick;
    }

}
