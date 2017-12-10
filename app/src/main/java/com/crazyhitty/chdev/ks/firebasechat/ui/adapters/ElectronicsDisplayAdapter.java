package com.crazyhitty.chdev.ks.firebasechat.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazyhitty.chdev.ks.firebasechat.R;
import com.crazyhitty.chdev.ks.firebasechat.models.Electronics;

import java.util.List;

/**
 * Created by Abhinav Dixit on 09-Oct-17.
 */

public class ElectronicsDisplayAdapter extends RecyclerView.Adapter<ElectronicsDisplayAdapter.MyViewHolder>{
    List<Electronics> list;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_electronic, parent, false);

        return new ElectronicsDisplayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Electronics electronics = list.get(position);
        holder.condition.setText(electronics.getCondition());
        holder.category.setText(electronics.getCategory());
        holder.price.setText(electronics.getPrice());
        holder.model.setText(electronics.getModel());
        holder.user.setText(electronics.getUser());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView model, category, price, user, condition;

        public MyViewHolder(View itemView) {
            super(itemView);
            model = (TextView) itemView.findViewById(R.id.electronicModel);
            category = (TextView) itemView.findViewById(R.id.electronicCategory);
            price = (TextView) itemView.findViewById(R.id.electronicPrice);
            user = (TextView) itemView.findViewById(R.id.electronicUser);
            condition = (TextView) itemView.findViewById(R.id.electronicCondition);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }
    }

    public ElectronicsDisplayAdapter(List<Electronics> list) {
        this.list = list;
    }
}
