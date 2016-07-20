package com.jianyuyouhun.myandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianyuyouhun.myandroid.application.BaseActivity;
import com.jianyuyouhun.myandroid.tools.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<String> list;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void bindView() {
        super.bindView();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void bindData() {
        super.initData();
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        adapter.setOnItemClickLitener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (list.get(position)){
                    case "loadingView":
                        startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
                        break;
                    case "pieChart":
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        list.add("loadingView");
        list.add("pieChart");
        adapter.notifyDataSetChanged();
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        Context context;
        List<String> list;
        public MyAdapter(Context context, List<String> list){
            this.context = context;
            this.list = list;
        }

        public interface OnItemClickListener{
            void onItemClick(View view, int position);
        }
        private OnItemClickListener onItemClickListener;

        public void setOnItemClickLitener(OnItemClickListener itemClickListener) {
            this.onItemClickListener = itemClickListener;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater
                    .from(context).inflate(R.layout.layout_list_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyAdapter.MyViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
            if (onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }

}
