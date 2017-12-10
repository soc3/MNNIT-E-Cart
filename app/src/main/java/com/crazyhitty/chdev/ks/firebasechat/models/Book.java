package com.crazyhitty.chdev.ks.firebasechat.models;

/**
 * Created by Abhinav Dixit on 30-Sep-17.
 */

public class Book {

    public String name,category, author,description, price,user,download;

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
    public Book(String name,String category, String price, String author, String description,String user,String download) {
        this.category = category;
        this.price = price;
        this.author = author;
        this.description = description;
        this.user=user;
        this.name=name;
        this.download=download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownload() {

        return download;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
