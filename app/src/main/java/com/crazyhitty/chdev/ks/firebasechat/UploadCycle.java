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

import com.crazyhitty.chdev.ks.firebasechat.models.Cycle;
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

public class UploadCycle extends AppCompatActivity {

    Cycle c1;
    EditText cycleBrand,cycleCondition,cyclePrice;
    String brand,condition,price,UserID,download;
    TextView cycleUser;
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
        Intent intent = new Intent(context, UploadCycle.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, UploadCycle.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_cycle);

        UploadButton=(Button)findViewById(R.id.uploadCycle);
        cycleBrand = (EditText) findViewById(R.id.brandOfCycle);

        cycleCondition = (EditText) findViewById(R.id.conditionOfCycle);
        cyclePrice = (EditText) findViewById(R.id.priceOfCycle);
        cycleUser=(TextView) findViewById(R.id.userOfCycle);

        uploadImg=(Button)findViewById(R.id.upload_cycle_image);
        chooseImg=(Button)findViewById(R.id.choose_cycle_image);

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

        cycleUser.setText(UserID);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        final DatabaseReference usersRef=ref.child("Cycles");
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brand=cycleBrand.getText().toString();

                condition=cycleCondition.getText().toString();
                price=cyclePrice.getText().toString();
                if(!brand.equals("") && !condition.equals("") && !price.equals("")  && t==1 && p==1) {
                    download = cycleBrand.getText().toString() + "_" + UserID + ".jpg";

                    final Cycle c1 = new Cycle(brand, condition, price, UserID, download);
                    //final DatabaseReference usersRef = ref.child("Books");

                    //  MyBook b1=new MyBook("gh","jbh","huv","vhv","hvgh","vhvk");

//                usersRef.push().setValue(b1);
                    usersRef.push().setValue(c1);
                    //  usersRef.push().setValue("hey","hello");
                    Toast.makeText(UploadCycle.this, "Upload successful", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(UploadCycle.this, "Upload failed.....Above entries are unfilled or image is not loaded", Toast.LENGTH_SHORT).show();





            }
        });

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brand=cycleBrand.getText().toString();

                condition=cycleCondition.getText().toString();
                price=cyclePrice.getText().toString();
                if(!brand.equals("") && !condition.equals("") && !price.equals("") ) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);


                    startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                    t = 1;
                }
                else
                    Toast.makeText(UploadCycle.this, "Choose Image failed...Above entries are unfilled", Toast.LENGTH_SHORT).show();

            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brand=cycleBrand.getText().toString();

                condition=cycleCondition.getText().toString();
                price=cyclePrice.getText().toString();
                if(filePath != null && !brand.equals("") && !condition.equals("") && !price.equals("") && t==1) {
                    pd.show();

                    StorageReference childRef = mStorageRef.child(cycleBrand.getText().toString()+"_"+UserID);
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
                            Toast.makeText(UploadCycle.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            p=1;
                            for (int i = 0; i < 100; i++) {
                                System.out.println(st);
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UploadCycle.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else {
                    Toast.makeText(UploadCycle.this, "Select an image", Toast.LENGTH_SHORT).show();
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
                //Bitmap compressedImageBitmap = new Compressor(this).compressToBitmap(new File(filePath.toString()));
                // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // bitmap=getResizedBitmap(bitmap,50);
                //Setting image to ImageView

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
