package com.crazyhitty.chdev.ks.firebasechat.gridView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.crazyhitty.chdev.ks.firebasechat.HomeActivity;
import com.crazyhitty.chdev.ks.firebasechat.R;
import com.crazyhitty.chdev.ks.firebasechat.cardView.CardViewActivity;
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.SingleBookDisplayActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailsGridLayout extends AppCompatActivity {

    static List<String> list;
    public static String item_name;

    GridView grid_view;
    Integer[] img_home;
    public Integer image_id[] = { R.drawable.book_main, R.drawable.cycle_main,  R.drawable.electronics_main};
    public String names[]={"name","brand","model"};
    ArrayList<Integer> image_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_grid_layout);
        grid_view = (GridView) findViewById(R.id.gridview);
        list = new ArrayList<String>();
        image_home=new ArrayList<Integer>();
        initialize_list();
/*
        list.add("harry potter and the game of thrones");
        list.add("asklfsf");
        list.add("aslfasfasffasf");
        list.add("asklfsf");
        list.add("aslfasfasffasf");
        list.add("asklfsf");
        list.add("aslfasfasffasf");
        list.add("asklfsf");
        list.add("aslfasfasffasf");
*/
        //grid_view.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.textadapter, list));

        //soc's code
       /* ImageAdaptar adapter2 = new ImageAdaptar(this, image_id, list);
        grid_view.setAdapter(adapter2);*/
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                item_name=list.get(arg2);
                int img=0;
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

                if(HomeActivity.item_type.equals("book")) {
                    ref = FirebaseDatabase.getInstance().getReference().child("Books");
                    img=0;

                }
                else if(HomeActivity.item_type.equals("cycle")) {
                    ref = FirebaseDatabase.getInstance().getReference().child("Cycles");
                    img=1;

                }
                else if(HomeActivity.item_type.equals("electronic")) {
                    ref = FirebaseDatabase.getInstance().getReference().child("Electronics");
                    img=2;

                }
                Query query = ref.orderByChild(names[img]);
                final int finalImg = img;
                ref.addValueEventListener(
                        new ValueEventListener() {
                            int pos=0;
                            @Override

                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    // Book b= appleSnapshot.getValue(Book.class);
                                    String s="";

                                    s=appleSnapshot.child(HomeActivity.item_subtype).getValue().toString();
                                    String s2=appleSnapshot.child(names[finalImg]).getValue().toString();
                                    if(s.equals(CardViewActivity.val) && s2.equals(item_name))
                                    {
                                        Intent i = new Intent(DetailsGridLayout.this, SingleBookDisplayActivity.class);
                                        i.putExtra("itemPosition", pos);
                                        startActivity(i);
                                        break;

                                    }
                                    else
                                        pos++;

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
    public void initialize_list()
    {
        int img = 0;
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

        if(HomeActivity.item_type.equals("book")) {
            ref = FirebaseDatabase.getInstance().getReference().child("Books");
            img=0;

        }
        else if(HomeActivity.item_type.equals("cycle")) {
            ref = FirebaseDatabase.getInstance().getReference().child("Cycles");
            img=1;

        }
        else if(HomeActivity.item_type.equals("electronic")) {
            ref = FirebaseDatabase.getInstance().getReference().child("Electronics");
            img=2;

        }
        Query query = ref.orderByChild(HomeActivity.item_subtype).equalTo(CardViewActivity.val);
        final int finalImg = img;
        query.addValueEventListener(
                new ValueEventListener() {
                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            // Book b= appleSnapshot.getValue(Book.class);
                            String s="";
                            if(finalImg ==0)
                                s=appleSnapshot.child("name").getValue().toString();
                            else if(finalImg==1)
                                s=appleSnapshot.child("brand").getValue().toString();
                            else if(finalImg==2)
                                s=appleSnapshot.child("model").getValue().toString();
                                list.add(s);
                        }
                        img_home=new Integer[list.size()];

                        for(int i=0;i<list.size();i++)
                        {
                            image_home.add(image_id[finalImg]);
                            img_home[i]=image_id[finalImg];

                        }
                        ImageAdaptar adapter2 = new ImageAdaptar(DetailsGridLayout.this, img_home, list);
                        grid_view.setAdapter(adapter2);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        //    img_home= (Integer[]) image_home.toArray();

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

    }


    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailsGridLayout.this, MainActivity.class);
        startActivity(intent);
    }*/
}
