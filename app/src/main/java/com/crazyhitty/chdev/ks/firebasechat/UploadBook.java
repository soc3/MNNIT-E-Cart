package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UploadBook extends AppCompatActivity {

    Button UploadButton;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UploadBook.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, UploadBook.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_book);

        UploadButton=(Button)findViewById(R.id.upload_button);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        class Book {

            public String name,category, author,description, price,user;


            public Book(String name,String category, String price, String author, String description,String user) {
                this.category = category;
                this.price = price;
                this.author = author;
                this.description = description;
                this.user=user;
                this.name=name;
            }
        }

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference usersRef = ref.child("Books");

                Book b1=new Book("Harry Potter","fiction", "200","rowling","very fuckall","abhidix523@gmail.com");
                Book b2=new Book("GOT","fiction", "200","rowling","very fuckall","abhidix523@gmail.com");
                Book b3=new Book("LOTR","fiction", "200","rowling","very fuckall","abhidix523@gmail.com");
                Book b4=new Book("twilight","fiction", "200","rowling","very fuckall","abhinav23052@gmail.com");
                Book b5=new Book("fifty shades of grey","fiction", "200","rowling","very fuckall","abhinav23052@gmail.com");
                usersRef.push().setValue(b1);
                usersRef.push().setValue(b2);
                usersRef.push().setValue(b3);
                usersRef.push().setValue(b4);
                usersRef.push().setValue(b5);



            }
        });
    }


}
