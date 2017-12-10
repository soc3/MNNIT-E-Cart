package com.crazyhitty.chdev.ks.firebasechat.gridView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazyhitty.chdev.ks.firebasechat.R;

import java.util.List;

/**
 * Created by sushant oberoi on 09-10-2017.
 */

public class ImageAdaptar extends BaseAdapter {
    private Context context;
    private Integer image_id[] ;
    private List<String> str;

    public ImageAdaptar(Context CTX, Integer[] image_id, List<String> str) {

        context = CTX;
        this.str = str;
        this.image_id = image_id;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {

        return image_id.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.griditem, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            TextView txt = (TextView)gridView.findViewById(R.id.grid_text);
            txt.setText(str.get(arg0));
            imageView.setImageResource(image_id[arg0]);


        } else {
            gridView = (View) convertView;
        }

        return gridView;

        // TODO Auto-generated method stub
        // return null;
    }
}
