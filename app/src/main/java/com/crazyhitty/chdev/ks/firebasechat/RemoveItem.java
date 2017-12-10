package com.crazyhitty.chdev.ks.firebasechat;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RemoveItem extends AppCompatActivity {


    ArrayList<String> items, names;
    String item, user, name;
    AutoCompleteTextView ac1, ac2;
    Button b1, b2;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_item);

        items = new ArrayList<>();
        names = new ArrayList<>();
        items.add("Books");
        items.add("Cycles");
        items.add("Electronics");
        ac1 = (AutoCompleteTextView) findViewById(R.id.ac_1);
        ac2 = (AutoCompleteTextView) findViewById(R.id.auto_name);
        b1 = (Button) findViewById(R.id.ac_search1);
        b2 = (Button) findViewById(R.id.ac_search2);

        pd = new ProgressDialog(this);
        pd.setMessage("Retrieving from database....");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, items);


        ac1.setThreshold(1);//will start working from first character
        ac1.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        ac1.setTextColor(Color.RED);

        /*
        if(s1 != null && s1.getSelectedItem() !=null) {
            item = s1.getSelectedItem().toString();
            Intent intent=new Intent(RemoveItem.this,RemoveName.class);
            intent.putExtra("item",item);

            FirebaseUser userID = FirebaseAuth.getInstance().getCurrentUser();
            user = userID.getEmail();

            inints2();
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, names);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s2.setAdapter(dataAdapter2);
            //   name = s2.getSelectedItem().toString();


            if (s2 != null && s2.getSelectedItem() != null) {
                name = s2.getSelectedItem().toString();
                for (int i = 0; i < 100; i++) {
                    System.out.println(item + " " + name);
                }
            }


        }*/

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = ac1.getText().toString();
                if (item.equals("Books") || item.equals("Cycles") || item.equals("Electronics"))
                    inits2();
                else
                    Toast.makeText(RemoveItem.this,"Enter valid item type",Toast.LENGTH_LONG).show();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ac2.getText().toString();
             //   pd.show();
                init_name();


            }
        });


    }

    public void inits2() {

        FirebaseUser userID = FirebaseAuth.getInstance().getCurrentUser();
        user = userID.getEmail();
        if(item.equals("Books")||item.equals("Cycles")||item.equals("Electronics")) {
            pd.show();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(item);
            Query query = ref.orderByChild("user").equalTo(user);
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
                            pd.dismiss();
                            for (int i = 0; i < 100; i++)
                                System.out.println(names.size());
                            for (int j = 0; j < names.size(); j++)
                                for (int i = 0; i < 100; i++)
                                    names.get(j);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                    (RemoveItem.this, android.R.layout.select_dialog_item, names);


                            ac2.setThreshold(1);//will start working from first character
                            ac2.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                            ac2.setTextColor(Color.RED);
                            pd.dismiss();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        else
            Toast.makeText(RemoveItem.this,"Incorrect choice of item",Toast.LENGTH_LONG).show();

    }

    public void init_name() {

        if(item.equals("Books")||item.equals("Cycles")||item.equals("Electronics")) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(item);

            Query query = null;
            if (item.equals("Books"))
                query = ref.orderByChild("name").equalTo(name);
            else if (item.equals("Cycles"))
                query = ref.orderByChild("brand").equalTo(name);
            else if (item.equals("Cycles"))
                query = ref.orderByChild("model").equalTo(name);
            query.addValueEventListener(
                    new ValueEventListener() {
                        int t = 0;

                        @Override

                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                t = 1;
                                appleSnapshot.getRef().removeValue();
                            }
                            pd.dismiss();
                            if (t == 0)
                                Toast.makeText(RemoveItem.this, "No such item exists", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(RemoveItem.this, "Item removed", Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        else
            Toast.makeText(RemoveItem.this,"Incorrect choice of item",Toast.LENGTH_LONG).show();
    }
}
