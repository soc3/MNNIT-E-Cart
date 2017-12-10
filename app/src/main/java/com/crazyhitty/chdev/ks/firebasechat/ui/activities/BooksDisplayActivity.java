package com.crazyhitty.chdev.ks.firebasechat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.crazyhitty.chdev.ks.firebasechat.R;
import com.crazyhitty.chdev.ks.firebasechat.models.Book;
import com.crazyhitty.chdev.ks.firebasechat.ui.adapters.BooksDisplayAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BooksDisplayActivity extends AppCompatActivity {
    String name, category, author, description, price, user;


    public static ArrayList<Book> list = new ArrayList<Book>();
    private RecyclerView recyclerView;
    private BooksDisplayAdapter booksDisplayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_display);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        booksDisplayAdapter = new BooksDisplayAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(booksDisplayAdapter);

        Intent intent = getIntent();
        String s = intent.getStringExtra("email");
        user = s;


        this.prepareBookData();


    /*    for(int i=0;i<list.size();i++)
        {
            Book b=list.get(i);
            for(int j=1;j<100;j++)
                System.out.println(b.getName());
        }*/

    }

    private void prepareBookData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");

        Query query = ref.orderByChild("user").equalTo(user);
        query.addValueEventListener(
                new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            // Book b= appleSnapshot.getValue(Book.class);

                            name = appleSnapshot.child("name").getValue().toString();
                            category = appleSnapshot.child("category").getValue().toString();
                            author = appleSnapshot.child("author").getValue().toString();
                            description = appleSnapshot.child("description").getValue().toString();
                            price = appleSnapshot.child("price").getValue().toString();

                            Book b = new Book(name, category, price, author, description, user);
                            BooksDisplayActivity.list.add(b);


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }
}
