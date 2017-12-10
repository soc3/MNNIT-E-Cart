package com.crazyhitty.chdev.ks.firebasechat.listView;

/**
 * Created by sushant oberoi on 08-10-2017.
 */

public class ItemDataProvider {
    private int itemId;

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {

        return itemId;
    }
    public ItemDataProvider(int itemId){
        this.setItemId(itemId);
    }
}
