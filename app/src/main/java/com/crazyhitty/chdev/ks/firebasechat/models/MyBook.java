package com.crazyhitty.chdev.ks.firebasechat.models;

/**
 * Created by Abhinav Dixit on 06-Oct-17.
 */

public class MyBook {
    public String name,category, author,description, price,user,download;

    public MyBook()
    {

    }

    public MyBook(String name,String category, String price, String author, String description,String user) {
        this.category = category;
        this.price = price;
        this.author = author;
        this.description = description;
        this.user=user;
        this.name=name;
    }
    public MyBook(String name,String category, String price, String author, String description,String user,String download) {
        this.category = category;
        this.price = price;
        this.author = author;
        this.description = description;
        this.user=user;
        this.name=name;
        this.download=download;
    }

}
