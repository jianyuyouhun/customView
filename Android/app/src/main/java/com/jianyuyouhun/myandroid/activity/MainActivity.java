package com.jianyuyouhun.myandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianyuyouhun.myandroid.R;
import com.jianyuyouhun.myandroid.application.BaseActivity;
import com.jianyuyouhun.myandroid.tools.DividerItemDecoration;

public class MainActivity extends BaseActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    String[] titles={"switchBtn","loadingView","tagView","testView","taiji","searchView", "bezierView", "testBezier"};
    MyAdapter adapter;

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
        adapter = new MyAdapter(this, titles);
        adapter.setOnItemClickLitener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (titles[position]){
                    case "switchBtn":
                        startActivity(new Intent(getApplicationContext(), SwitchButtonUI.class));
                        break;
                    case "loadingView":
                        startActivity(new Intent(getApplicationContext(), LoadingActivity.class));
                        break;
                    case "tagView":
                        startActivity(new Intent(getApplicationContext(), TagActivity.class));
                        break;
                    default:
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), TestActivity.class);
                        intent.putExtra("type", titles[position]);
                        startActivity(intent);
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        adapter.notifyDataSetChanged();
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        Context context;
        String[] list;
        public MyAdapter(Context context, String[] list){
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
            holder.textView.setText(list[position]);
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
            return list.length;
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
