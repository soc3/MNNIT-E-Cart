package com.crazyhitty.chdev.ks.firebasechat.models;

/**
 * Created by Abhinav Dixit on 30-Sep-17.
 */

public class Book {

    public String name,category, author,description, price,user;

    public Book()
    {

    }

    public Book(String name,String category, String price, String author, String description,String user) {
        this.category = category;
        this.price = price;
        this.author = author;
        this.description = description;
        this.user=user;
        this.name=name;
    }
}
