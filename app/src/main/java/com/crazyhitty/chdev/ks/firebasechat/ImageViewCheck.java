package com.crazyhitty.chdev.ks.firebasechat;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ImageViewCheck extends AppCompatActivity {
    ImageView img;
    Button doit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_check);

        img = (ImageView) findViewById(R.id.check_image);
        doit = (Button) findViewById(R.id.doit);


        doit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StorageReference storageRef=FirebaseStorage.getInstance().getReference();
                StorageReference islandRef = storageRef.child("Harry Potter_rohan23chhabra@gmail.com.jpg");

                File localFile = null;
                try {
                    localFile = File.createTempFile("Harry Potter_rohan23chhabra@gmail.com", "jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final File finalLocalFile = localFile;
                islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Uri uri=Uri.fromFile(finalLocalFile);
                        img.setImageURI(uri);

                        // Local temp file has been created
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

            }
        });
    }
}
