package com.crazyhitty.chdev.ks.firebasechat.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazyhitty.chdev.ks.firebasechat.R;

import org.w3c.dom.Text;

import java.util.List;

public class SingleBookDisplayAdapter extends RecyclerView.Adapter<SingleBookDisplayAdapter.MyViewHolder>{
    List<String> bookAttributes;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String attribute = bookAttributes.get(position);
        holder.field.setText(attribute);
    }

    @Override
    public int getItemCount() {
        return bookAttributes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView field;
        public MyViewHolder(View itemView) {
            super(itemView);
            field = (TextView) itemView.findViewById(R.id.field);
        }
    }

    public SingleBookDisplayAdapter(List<String> bookAttributes) {
        this.bookAttributes = bookAttributes;
    }
}
