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

import com.crazyhitty.chdev.ks.firebasechat.models.Electronics;
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

public class UploadElectronics extends AppCompatActivity {
    Electronics e1;
    EditText electronicModel,electronicCondition,electronicPrice,electronicCategory;
    String model,condition,price,UserID,category,download;
    TextView electroicUser;
    int PICK_IMAGE_REQUEST = 111;
    int t=0,p=0;

    Bitmap compressedImageBitmap;

    Button UploadButton,chooseImg, uploadImg;
    Uri filePath;
    Uri downloadlink;
    ProgressDialog pd;
    String url;
    private StorageReference mStorageRef;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UploadElectronics.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, UploadElectronics.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_electronics);

        UploadButton=(Button)findViewById(R.id.uploadElectronic);

        electronicModel = (EditText) findViewById(R.id.modelOfElectronic);
        electronicCategory=(EditText)findViewById(R.id.categoryOfElectronic);
        electronicCondition = (EditText) findViewById(R.id.conditionOfElectronic);
        electronicPrice = (EditText) findViewById(R.id.priceOfElectronic);
        electroicUser=(TextView) findViewById(R.id.userOfElectronic);

        uploadImg=(Button)findViewById(R.id.upload_electronics_image);
        chooseImg=(Button)findViewById(R.id.choose_electronics_image);



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


        electroicUser.setText(UserID);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        final DatabaseReference usersRef=ref.child("Electronics");
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                category=electronicCategory.getText().toString();

                condition=electronicCondition.getText().toString();
                price=electronicPrice.getText().toString();
                model=electronicModel.getText().toString();
                if(!category.equals("") && !condition.equals("") && !price.equals("") && !model.equals("") && t==1 && p==1) {
                    download = electronicModel.getText().toString() + "_" + UserID + ".jpg";
                    final Electronics e1 = new Electronics(category, model, condition, price, UserID, download);
                    //final DatabaseReference usersRef = ref.child("Books");

                    //  MyBook b1=new MyBook("gh","jbh","huv","vhv","hvgh","vhvk");

//                usersRef.push().setValue(b1);
                    usersRef.push().setValue(e1);
                    Toast.makeText(UploadElectronics.this, "Item uploaded", Toast.LENGTH_LONG).show();

                    //  usersRef.push().setValue("hey","hello");


                }
                else
                {
                    Toast.makeText(UploadElectronics.this, "Upload failed.....Above entries are unfilled or image is not loaded", Toast.LENGTH_SHORT).show();
                }


            }
        });

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category=electronicCategory.getText().toString();

                condition=electronicCondition.getText().toString();
                price=electronicPrice.getText().toString();
                model=electronicModel.getText().toString();
                if(!category.equals("") && !condition.equals("") && !price.equals("") && !model.equals("")) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);


                    startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                    t=1;
                }
                else
                    Toast.makeText(UploadElectronics.this, "Choose Image failed...Above entries are unfilled", Toast.LENGTH_SHORT).show();


            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category=electronicCategory.getText().toString();

                condition=electronicCondition.getText().toString();
                price=electronicPrice.getText().toString();
                model=electronicModel.getText().toString();
                if(filePath != null && !category.equals("") && !condition.equals("") && !price.equals("") && !model.equals("") && t==1) {
                    pd.show();

                    StorageReference childRef = mStorageRef.child(electronicModel.getText().toString()+"_"+UserID);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    byte[] data = baos.toByteArray();

                    //uploading the image

                    // do your stuff..

                    UploadTask uploadTask = childRef.putBytes(data);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri s = taskSnapshot.getDownloadUrl();
                            String st = s.toString();


                            pd.dismiss();
                            Toast.makeText(UploadElectronics.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            p=1;
                            for (int i = 0; i < 100; i++) {
                                System.out.println(st);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UploadElectronics.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {
                    Toast.makeText(UploadElectronics.this, "Upload image failed....Select an image or entries are unfilled", Toast.LENGTH_SHORT).show();
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
                compressedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //getting image from gallery
               // Bitmap compressedImageBitmap = new Compressor(this).compressToBitmap(new File(filePath.toString()));
                // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // bitmap=getResizedBitmap(bitmap,50);
                //Setting image to ImageView

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
