package com.crazyhitty.chdev.ks.firebasechat.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazyhitty.chdev.ks.firebasechat.R;
import com.crazyhitty.chdev.ks.firebasechat.models.Cycle;

import java.util.List;

public class CyclesDisplayAdapter extends RecyclerView.Adapter<CyclesDisplayAdapter.MyViewHolder> {
    List<Cycle> cycleList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cycle, parent, false);

        return new CyclesDisplayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cycle cycle = cycleList.get(position);
        holder.cycleBrand.setText(cycle.getBrand());
        holder.cycleCondition.setText(cycle.getCondition());
        holder.cyclePrice.setText(cycle.getPrice());
        holder.cycleUser.setText(cycle.getUser());
    }

    @Override
    public int getItemCount() {
        return cycleList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView cycleBrand, cycleCondition, cyclePrice, cycleUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            cycleBrand = (TextView) itemView.findViewById(R.id.cycleBrand);
            cycleCondition = (TextView) itemView.findViewById(R.id.cycleCondition);
            cyclePrice = (TextView) itemView.findViewById(R.id.cyclePrice);
            cycleUser = (TextView) itemView.findViewById(R.id.cycleUser);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    System.out.println("Long click:");
                    return true;
                }
            });
        }
    }

    public CyclesDisplayAdapter(List<Cycle> cycleList) {
        this.cycleList = cycleList;
    }
}
