package com.crazyhitty.chdev.ks.firebasechat.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.crazyhitty.chdev.ks.firebasechat.HomeActivity;
import com.crazyhitty.chdev.ks.firebasechat.R;
import com.crazyhitty.chdev.ks.firebasechat.ui.adapters.SingleBookDisplayAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SingleBookDisplayActivity extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private SingleBookDisplayAdapter singleBookDisplayAdapter;
    int position;
    String name,category,author,description,price,user,download;
    Button ChatUserButton;
    ImageView imageViewBook;
    ProgressBar mProgressBar;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SingleBookDisplayActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, SingleBookDisplayActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_book_display);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        imageViewBook=(ImageView)findViewById(R.id.imageViewBook);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        singleBookDisplayAdapter = new SingleBookDisplayAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(singleBookDisplayAdapter);

        Intent intent=getIntent();
        int pos = intent.getIntExtra("itemPosition", 0);
        position=pos;
        ChatUserButton=(Button)findViewById(R.id.button3);

        //mProgressBar.setVisibility(View.VISIBLE);
        if(HomeActivity.item_type.equals("book")) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");
            Query query = ref.orderByChild("name");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot
                            int count = 0;
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                // Book b= appleSnapshot.getValue(Book.class);
                                if (count == position) {
                                    name = appleSnapshot.child("name").getValue().toString();
                                    category = appleSnapshot.child("category").getValue().toString();
                                    author = appleSnapshot.child("author").getValue().toString();
                                    description = appleSnapshot.child("description").getValue().toString();
                                    price = appleSnapshot.child("price").getValue().toString();
                                    user = appleSnapshot.child("user").getValue().toString();


                                    list.add("Name:           "+name);
                                    list.add("Category:       "+category);
                                    list.add("Author:         "+author);
                                    list.add("Description:    "+description);
                                    list.add("Price:          "+price);
                                    list.add("User:           "+user);

                                    download = appleSnapshot.child("download").getValue().toString();
                                    String download_child = download + ".jpg";
                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                    StorageReference islandRef = storageRef.child(download_child);

                                    File localFile = null;
                                    try {
                                        localFile = File.createTempFile(download, "jpg");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    final File finalLocalFile = localFile;
                                    islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Uri uri = Uri.fromFile(finalLocalFile);
                                            imageViewBook.setImageURI(uri);


                                            // Local temp file has been created
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });


                                    break;


                                } else
                                    count++;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        else if(HomeActivity.item_type.equals("cycle")) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cycles");
            Query query = ref.orderByChild("brand");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot
                            int count = 0;
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                // Book b= appleSnapshot.getValue(Book.class);
                                if (count == position) {
                                    name = appleSnapshot.child("brand").getValue().toString();
                                    category = appleSnapshot.child("condition").getValue().toString();

                                    price = appleSnapshot.child("price").getValue().toString();
                                    user = appleSnapshot.child("user").getValue().toString();


                                    list.add("Name: "+name);
                                    list.add("Category: "+category);
                                    list.add("Price:  "+price);
                                    list.add("User: "+user);

                                    download = appleSnapshot.child("download").getValue().toString();
                                    String download_child = download + ".jpg";
                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                    StorageReference islandRef = storageRef.child(download_child);

                                    File localFile = null;
                                    try {
                                        localFile = File.createTempFile(download, "jpg");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    final File finalLocalFile = localFile;
                                    islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Uri uri = Uri.fromFile(finalLocalFile);
                                            imageViewBook.setImageURI(uri);


                                            // Local temp file has been created
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });


                                    break;


                                } else
                                    count++;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        if(HomeActivity.item_type.equals("electronic")) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Electronics");
            Query query = ref.orderByChild("model");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot
                            int count = 0;
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                // Book b= appleSnapshot.getValue(Book.class);
                                if (count == position) {
                                    name = appleSnapshot.child("model").getValue().toString();
                                    category = appleSnapshot.child("category").getValue().toString();
                                    author = appleSnapshot.child("condition").getValue().toString();

                                    price = appleSnapshot.child("price").getValue().toString();
                                    user = appleSnapshot.child("user").getValue().toString();


                                    list.add("Name: "+name);
                                    list.add("Category: "+category);
                                    list.add("Author:    "+author);

                                    list.add("Price:    "+price);
                                    list.add("User: "+user);

                                    download = appleSnapshot.child("download").getValue().toString();
                                    String download_child = download + ".jpg";
                                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                    StorageReference islandRef = storageRef.child(download_child);

                                    File localFile = null;
                                    try {
                                        localFile = File.createTempFile(download, "jpg");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    final File finalLocalFile = localFile;
                                    islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Uri uri = Uri.fromFile(finalLocalFile);
                                            imageViewBook.setImageURI(uri);


                                            // Local temp file has been created
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                        }
                                    });


                                    break;


                                } else
                                    count++;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        mProgressBar.setVisibility(View.GONE);
        this.prepareBookData();

        ChatUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

                Query query=ref.orderByChild("email").equalTo(user);
                query.addValueEventListener(
                        new ValueEventListener() {
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    // Book b= appleSnapshot.getValue(Book.class);
                                    String uid=appleSnapshot.child("uid").getValue().toString();
                                    String token=appleSnapshot.child("firebaseToken").getValue().toString();

                                    ChatActivity.startActivity(SingleBookDisplayActivity.this,user,uid,token);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });
            }
        });


    }

    private void prepareBookData() {




    }


}
