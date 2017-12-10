package com.crazyhitty.chdev.ks.firebasechat;


/**
 * Created by sushant oberoi on 02-09-2017.
 */

public class ItemLayout {
    private int image_id;
    private String description;
    public ItemLayout(int image_id, String description){
        this.image_id = image_id;
        this.description = description;
    }

    public int getImage_id() {
        return image_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getPosition(){return 0;}
}
