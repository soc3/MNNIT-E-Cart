package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.crazyhitty.chdev.ks.firebasechat.droidmentor.tabwithviewpager.ViewPager.CustomTabActivity;
import com.crazyhitty.chdev.ks.firebasechat.ui.NavigationDrawer;
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDetails extends AppCompatActivity {
    String email;
    Button pass;
    Button bookDisplay,cycleButton,electronicButton,fragmentButton,RemoveActivityButton;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserDetails.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, UserDetails.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);


        pass=(Button)findViewById(R.id.change_password_button);
        bookDisplay=(Button)findViewById(R.id.booksUploaded);
        RemoveActivityButton=(Button)findViewById(R.id.delete_item_activity);
        //cycleButton=(Button)findViewById(R.id.cycle_upload);
        //electronicButton=(Button)findViewById(R.id.electronic_upload);
        //fragmentButton=(Button)findViewById(R.id.fragment_button);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            //     String name = user.getDisplayName();
            email = user.getEmail();
            //     Uri photoUrl = user.getPhotoUrl();
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            for(int i=0;i<100;i++)
                System.out.println(email);

        }
        //DisplayEmail.setText(email);

        bookDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabActivity.startActivity(UserDetails.this);

            }
        });
        RemoveActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(UserDetails.this,RemoveItem.class);
                startActivity(i);


            }
        });

        /*cycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadCycle.startActivity(UserDetails.this);


            }
        });*/

        /*electronicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadElectronics.startActivity(UserDetails.this);


            }
        });*/

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword.startActivity(UserDetails.this);
            }
        });

        /*fragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomTabActivity.startActivity(UserDetails.this);


            }
        });*/


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_listing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            LoginActivity.startIntent(UserDetails.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
