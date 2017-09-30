package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sushant oberoi on 02-09-2017.
 */

public class ItemLayoutAdapter extends RecyclerView.Adapter<ItemLayoutAdapter.ItemLayoutViewHolder> {
    ArrayList<ItemLayout> itemLayouts = new ArrayList<ItemLayout>();
    public ItemLayoutAdapter(ArrayList<ItemLayout> itemLayouts){
        this.itemLayouts = itemLayouts;
    }
    @Override
    public ItemLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        ItemLayoutViewHolder itemLayoutViewHolder = new ItemLayoutViewHolder(view);
        return itemLayoutViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemLayoutViewHolder holder, int position) {
        ItemLayoutViewHolder.itemPosition  = position;
        /*holder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), BookDescription.class));
            }
        });*/
        ItemLayout ITEM = itemLayouts.get(position);
        holder.item_image.setImageResource(ITEM.getImage_id());
        holder.description.setText(ITEM.getDescription());
    }

    @Override
    public int getItemCount() {
        return itemLayouts.size();
    }
    public static class ItemLayoutViewHolder extends RecyclerView.ViewHolder{
        ImageView item_image;
        TextView description;
        static int itemPosition;
        public ItemLayoutViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), BookDescription.class);
                    i.putExtra("itemPosition", ItemLayoutViewHolder.itemPosition);
                    view.getContext().startActivity(i);
                }
            });
            item_image = (ImageView)itemView.findViewById(R.id.imageView);
            description =(TextView)itemView.findViewById(R.id.description);
        }
    }
}
