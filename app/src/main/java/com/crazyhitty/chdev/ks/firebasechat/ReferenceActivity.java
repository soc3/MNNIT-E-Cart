package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.crazyhitty.chdev.ks.firebasechat.models.Book;
import com.crazyhitty.chdev.ks.firebasechat.models.User;
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.ChatActivity;
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.SplashActivity;
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.UserListingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static com.crazyhitty.chdev.ks.firebasechat.R.id.chat_button;
import static com.crazyhitty.chdev.ks.firebasechat.R.id.home_button;

public class ReferenceActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    Button HomeButton, ChatButton,UploadButton,DeleteButton,ListButton,InfoButton,ChatUserButton,UploadFileButton;

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
        UploadButton=(Button)findViewById(R.id.upload_book);
        DeleteButton=(Button)findViewById(R.id.delete_book);
        ListButton=(Button)findViewById(R.id.list_book);
        InfoButton=(Button)findViewById(R.id.info_button);
        ChatUserButton=(Button)findViewById(R.id.chat_user);
        UploadFileButton=(Button)findViewById(R.id.upload_file);

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

        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadBook.startActivity(ReferenceActivity.this);


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

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");

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
                        });
            }
        });

        ListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");

                Query query=ref.orderByChild("user").equalTo("abhinav23052@gmail.com");
                query.addValueEventListener(
                        new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                 // Book b= appleSnapshot.getValue(Book.class);
                                    String s=appleSnapshot.child("name").getValue().toString();
                                   for(int i=1;i<100;i++)
                                   {
                                       System.out.println(s);
                                   }
                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });
            }
        });
        InfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
               //     String name = user.getDisplayName();
                    String email = user.getEmail();
               //     Uri photoUrl = user.getPhotoUrl();
                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    String uid = user.getUid();
                    for(int i=0;i<100;i++)
                        System.out.println(email);
                }

            }
        });

        ChatUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

                Query query=ref.orderByChild("email").equalTo("abhidix523@gmail.com");
                query.addValueEventListener(
                        new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    // Book b= appleSnapshot.getValue(Book.class);
                                    String uid=appleSnapshot.child("uid").getValue().toString();
                                    String token=appleSnapshot.child("firebaseToken").getValue().toString();

                                    ChatActivity.startActivity(ReferenceActivity.this,"abhidix523@gmail.com",uid,token);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });
            }
        });

        UploadFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

}


