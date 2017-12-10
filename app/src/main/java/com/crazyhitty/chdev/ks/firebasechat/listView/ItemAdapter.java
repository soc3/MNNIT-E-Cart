package com.crazyhitty.chdev.ks.firebasechat.listView;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.crazyhitty.chdev.ks.firebasechat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushant oberoi on 08-10-2017.
 */

public class ItemAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public ItemAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
    static class DataHandler{
        ImageView item;
    }
    @Override
    public void add(Object object){
        super.add(object);
        list.add(object);
    }
    @Override
    public int getCount(){
        return this.list.size();
    }

    @Override
    public Object getItem(int position){
        return this.list.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row;
        row = convertView;
        DataHandler handler;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_listview, parent, false);
            handler = new DataHandler();
            handler.item = (ImageView) row.findViewById(R.id.label);
            row.setTag(handler);
        }
        else{
            handler = (DataHandler) row.getTag();
        }
        ItemDataProvider dataProvider;
        dataProvider =  (ItemDataProvider) this.getItem(position);
        handler.item.setImageResource(dataProvider.getItemId());
        return row;
    }
}
