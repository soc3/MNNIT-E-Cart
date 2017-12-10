package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BookDescription extends AppCompatActivity {
    int position;
    String name,category,author,description,price,user;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);
        Intent intent=getIntent();
        int pos = intent.getIntExtra("itemPosition", 0);
        position=pos;


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");
        Query query = ref.orderByChild("name");
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        int count=0;
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            // Book b= appleSnapshot.getValue(Book.class);
                            if(count==position)
                            {
                                name= appleSnapshot.child("name").getValue().toString();
                                category=appleSnapshot.child("category").getValue().toString();
                                author=appleSnapshot.child("author").getValue().toString();
                                description=appleSnapshot.child("").getValue().toString();
                                price=appleSnapshot.child("price").getValue().toString();
                                user=appleSnapshot.child("user").getValue().toString();


                            }
                            else
                                count++;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }

}
