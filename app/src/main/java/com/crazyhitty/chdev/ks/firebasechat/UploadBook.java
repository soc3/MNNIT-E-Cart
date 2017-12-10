package com.crazyhitty.chdev.ks.firebasechat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crazyhitty.chdev.ks.firebasechat.models.MyBook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class UploadBook extends AppCompatActivity {
    MyBook b1;
    EditText bookName,bookCategory,bookAuthor,bookDescription,bookPrice;
    String name,author,category,description,price,UserID,download;
    TextView bookUser;
    int PICK_IMAGE_REQUEST = 111;

    Bitmap compressedImageBitmap;

    int t=0,p=0;

    Button UploadButton,chooseImg, uploadImg;
    Uri filePath;
    Uri downloadlink;
    ProgressDialog pd;
    String url;
    private StorageReference mStorageRef;

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


        UploadButton=(Button)findViewById(R.id.uploadBook);
        bookName = (EditText) findViewById(R.id.nameOfBook);
        bookAuthor = (EditText) findViewById(R.id.authorOfBook);
        bookDescription = (EditText) findViewById(R.id.descriptionOfBook);
        bookCategory = (EditText) findViewById(R.id.categoryOfBook);
        bookPrice = (EditText) findViewById(R.id.priceOfBook);
        bookUser=(TextView) findViewById(R.id.userOfBook);

        uploadImg=(Button)findViewById(R.id.upload_book_image);
        chooseImg=(Button)findViewById(R.id.choose_book_image);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");



        FirebaseUser username = FirebaseAuth.getInstance().getCurrentUser();
        if (username != null) {
            // Name, email address, and profile photo Url
            //     String name = user.getDisplayName();
            String email = username.getEmail();
            UserID = email;
            //     Uri photoUrl = user.getPhotoUrl();
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = username.getUid();

        }

        bookUser.setText(UserID);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();






        final DatabaseReference usersRef=ref.child("Books");
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=bookName.getText().toString();
                author=bookAuthor.getText().toString();
                description=bookDescription.getText().toString();
                category=bookCategory.getText().toString();
                price=bookPrice.getText().toString();
                if(!category.equals("") && !description.equals("") && !price.equals("") && !author.equals("") && t==1 && p==1) {
                    download = bookName.getText().toString() + "_" + UserID + ".jpg";

                    final MyBook b1 = new MyBook(name, category, price, author, description, UserID, download);
                    //final DatabaseReference usersRef = ref.child("Books");

                    //  MyBook b1=new MyBook("gh","jbh","huv","vhv","hvgh","vhvk");

//                usersRef.push().setValue(b1);
                    usersRef.push().setValue(b1);
                    Toast.makeText(UploadBook.this, "Upload successful", Toast.LENGTH_SHORT).show();

                    //  usersRef.push().setValue("hey","hello");
                }
                else
                {
                    Toast.makeText(UploadBook.this, "Upload failed.....Above entries are unfilled or image is not loaded", Toast.LENGTH_SHORT).show();
                }





            }
        });
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=bookName.getText().toString();
                author=bookAuthor.getText().toString();
                description=bookDescription.getText().toString();
                category=bookCategory.getText().toString();
                price=bookPrice.getText().toString();

                if(!category.equals("") && !author.equals("") && !price.equals("") && !description.equals("") && !name.equals("")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);


                    startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                    t=1;
                }
                else
                    Toast.makeText(UploadBook.this, "Choose Image failed...Above entries are unfilled", Toast.LENGTH_SHORT).show();

            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=bookName.getText().toString();
                author=bookAuthor.getText().toString();
                description=bookDescription.getText().toString();
                category=bookCategory.getText().toString();
                price=bookPrice.getText().toString();

                if(filePath != null && !category.equals("") && !author.equals("") && !price.equals("") && !description.equals("") && !name.equals("") && t==1) {
                    pd.show();

                    StorageReference childRef = mStorageRef.child(bookName.getText().toString()+"_"+UserID);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    byte[] data = baos.toByteArray();

                    //uploading the image

                    // do your stuff..


                  //  Uri f= Uri.parse(compressedImageBitmap.toString());

                    UploadTask uploadTask = childRef.putBytes(data);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri s = taskSnapshot.getDownloadUrl();
                            String st = s.toString();


                            pd.dismiss();
                            Toast.makeText(UploadBook.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            p=1;
                            for (int i = 0; i < 100; i++) {
                                System.out.println(st);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UploadBook.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {
                    Toast.makeText(UploadBook.this, "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
        filePath = data.getData();

        try {
        //getting image from gallery


         compressedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
       //  bitmap=getResizedBitmap(bitmap,50);
        //Setting image to ImageView

        } catch (Exception e) {
        e.printStackTrace();
        }
        }
        }
}
