package com.crazyhitty.chdev.ks.firebasechat.models;

/**
 * Created by Abhinav Dixit on 07-Oct-17.
 */

public class Cycle {
    private String brand,condition,price,user,download;
    public Cycle()
    {

    }
    public Cycle(String brand, String condition, String price, String user)
    {
        this.brand=brand;
        this.condition=condition;
        this.price=price;
        this.user=user;
    }
    public Cycle(String brand, String condition, String price, String user,String download)
    {
        this.brand=brand;
        this.condition=condition;
        this.price=price;
        this.user=user;
        this.download=download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownload() {

        return download;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
