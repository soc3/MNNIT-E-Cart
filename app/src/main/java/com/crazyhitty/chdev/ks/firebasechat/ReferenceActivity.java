package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.crazyhitty.chdev.ks.firebasechat.ui.activities.UserListingActivity;
import com.google.firebase.auth.FirebaseAuth;

import static com.crazyhitty.chdev.ks.firebasechat.R.id.chat_button;
import static com.crazyhitty.chdev.ks.firebasechat.R.id.home_button;

public class ReferenceActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    Button HomeButton, ChatButton,DeleteButton;
    Button UserActivityButton;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ReferenceActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, ReferenceActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);

        HomeButton = (Button) findViewById(home_button);
        ChatButton = (Button) findViewById(chat_button);

        DeleteButton=(Button)findViewById(R.id.delete_book);

        UserActivityButton=(Button)findViewById(R.id.UserActivityButton);
        mAuth = FirebaseAuth.getInstance();

        class Book {

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


        ChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserListingActivity.startActivity(ReferenceActivity.this);


            }
        });




        UserActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDetails.startActivity(ReferenceActivity.this);


            }
        });


        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.startActivity(ReferenceActivity.this);


            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReferenceActivity.this,RemoveItem.class);
                startActivity(intent);

              /*  DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");

                Query query=ref.orderByChild("name").equalTo("Harry Potter");
                query.addValueEventListener(
                        new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                                    appleSnapshot.getRef().removeValue();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });*/
            }
        });


    }

}


