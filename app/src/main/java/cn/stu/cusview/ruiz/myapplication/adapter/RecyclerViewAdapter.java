package cn.stu.cusview.ruiz.myapplication.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.stu.cusview.ruiz.myapplication.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {





    @Override
    public int getItemCount() {
        return 50;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int i) {
        StringBuilder te = new StringBuilder("HAHAH");
        for (int po = 0; po <=i; po++){
            te.append("jdfsahkdjshk");
        }
        viewHolder.title.setText(te.toString() + i);

    }


    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView;
        }

    }

}
