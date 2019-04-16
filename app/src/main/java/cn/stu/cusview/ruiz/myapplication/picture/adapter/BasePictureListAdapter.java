package cn.stu.cusview.ruiz.myapplication.picture.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import cn.stu.cusview.ruiz.myapplication.picture.cache.LruCache;

public abstract class BasePictureListAdapter<T> extends RecyclerView.Adapter{

    protected List<T> dataList;
    public BasePictureListAdapter() {
        this(null);
    }
    protected LruCache mLruCache;

    public BasePictureListAdapter(List<T> dataList) {
        this.dataList = dataList;
        mLruCache = new LruCache((int)(Runtime.getRuntime().maxMemory()/100));
    }

    @Override
    public int getItemCount() {
        return dataList==null? 0:dataList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return inflateViewHolder(viewGroup,i);
    }

    protected abstract RecyclerView.ViewHolder inflateViewHolder(ViewGroup viewGroup, int i);


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        convert(viewHolder,i,dataList.get(i));
    }


    protected abstract void convert(RecyclerView.ViewHolder viewHolder,int index,T t);
}
