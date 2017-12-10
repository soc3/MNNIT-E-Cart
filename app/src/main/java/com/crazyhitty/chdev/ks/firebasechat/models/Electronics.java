package com.crazyhitty.chdev.ks.firebasechat.models;

/**
 * Created by Abhinav Dixit on 07-Oct-17.
 */

public class Electronics {
    String category, model, condition, price, user,download;

    public Electronics() {

    }

    public Electronics(String category, String model, String condition, String price, String user) {
        this.category = category;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.user = user;
    }
    public Electronics(String category, String model, String condition, String price, String user,String download) {
        this.category = category;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.user = user;
        this.download=download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownload() {

        return download;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
