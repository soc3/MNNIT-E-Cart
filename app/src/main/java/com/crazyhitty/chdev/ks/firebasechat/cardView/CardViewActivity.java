package com.crazyhitty.chdev.ks.firebasechat.cardView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.crazyhitty.chdev.ks.firebasechat.HomeActivity;
import com.crazyhitty.chdev.ks.firebasechat.R;
import com.crazyhitty.chdev.ks.firebasechat.ui.NavigationDrawer;
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class CardViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    static ArrayList<ItemLayout> items;

    public static String val="";
    TreeSet<String> treeSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        treeSet=new TreeSet<String>();
        items=new ArrayList<ItemLayout>();
        initialize_list();
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
            LoginActivity.startIntent(CardViewActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void initialize_list()
    {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();

        if(HomeActivity.item_type.equals("book")) {
            ref = FirebaseDatabase.getInstance().getReference().child("Books");

        }
        else if(HomeActivity.item_type.equals("cycle")) {
            ref = FirebaseDatabase.getInstance().getReference().child("Cycles");

        }
        else if(HomeActivity.item_type.equals("electronic")) {
            ref = FirebaseDatabase.getInstance().getReference().child("Electronics");


        }
        Query query = ref.orderByChild(HomeActivity.item_subtype);
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                        ArrayList<String> check=new ArrayList<String>();
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            // Book b= appleSnapshot.getValue(Book.class);
                            String s=appleSnapshot.child(HomeActivity.item_subtype).getValue().toString();
                            int i;
                            for(i=0;i<check.size();i++)
                            {
                                if(check.get(i).equals(s))
                                    break;
                            }
                            if(i==check.size()) {
                                items.add(new ItemLayout(s));
                                check.add(s);
                            }
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        /*for(int i=0;i<items.size();i++)
        {
            ItemLayout itemLayout = items.get(i);
            treeSet.add(items.get(i).getDescription());
        }
        items.clear();
        for(int i=0;i<100;i++)
            System.out.println(treeSet.size());
        for(String s:treeSet)
            items.add(new ItemLayout(s));*/
        for(int i=0;i<100;i++)
            System.out.println(HomeActivity.item_type+" "+HomeActivity.item_subtype);



        recyclerView = (RecyclerView) findViewById(R.id.home_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new com.crazyhitty.chdev.ks.firebasechat.cardView.ItemLayoutAdapter(items);
        recyclerView.setAdapter(adapter);

    }
}
