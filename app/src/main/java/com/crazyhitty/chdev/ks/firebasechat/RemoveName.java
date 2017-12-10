package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RemoveName extends AppCompatActivity {

    Spinner s2;
    ArrayList<String> names;
    String item, user,name;
    Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_name);


        remove=(Button)findViewById(R.id.remove_name);
        names=new ArrayList<>();

        Intent intent=getIntent();
        item = intent.getStringExtra("item");

        FirebaseUser userID = FirebaseAuth.getInstance().getCurrentUser();
        user = userID.getEmail();

        inits2();

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(dataAdapter2);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                name = parent.getItemAtPosition(position).toString();



            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });






    }
    public void inits2() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(item);
        Query query = ref.orderByChild("user").equalTo(user);
        names.clear();
        query.addValueEventListener(
                new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            // Book b= appleSnapshot.getValue(Book.class);

                            String name = "";
                            if (item.equals("Books"))
                                name = appleSnapshot.child("name").getValue().toString();
                            else if (item.equals("Cycles"))
                                name = appleSnapshot.child("brand").getValue().toString();
                            else if (item.equals("Electronics"))
                                name = appleSnapshot.child("model").getValue().toString();
                            names.add(name);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });


    }
}
