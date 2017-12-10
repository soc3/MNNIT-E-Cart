package com.crazyhitty.chdev.ks.firebasechat;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.crazyhitty.chdev.ks.firebasechat.cardView.CardViewActivity;
import com.crazyhitty.chdev.ks.firebasechat.ui.NavigationDrawer;
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    ProgressDialog pd;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    ArrayList<String> search_data;
    String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
    private static final int REQ_CODE_SPEECH_INPUT = 100;

    static public String item_type="book",item_subtype="author";

     ItemLayout layout;
    String name_single;
    Uri uri_temp;

    public ArrayList<com.crazyhitty.chdev.ks.firebasechat.ItemLayout> search_item;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private ImageButton mSpeakBtn;
    AutoCompleteTextView actv;
    ArrayList<ItemLayout> items;
    Button home_author,home_price,home_category,home_condition,home_name;
    ImageButton searchButton;
    int[] image_id = {R.drawable.book, R.drawable.cycle, R.drawable.electronics};
    static String[] description;
    ArrayList<String> names = new ArrayList<String>();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        items=new ArrayList<>();
        search_item=new ArrayList<>();

        if(NavigationDrawer.position==0)
            item_type="book";
        else if(NavigationDrawer.position==1)
            item_type="cycle";
        else if(NavigationDrawer.position==2)
            item_type="electronic";

        if(item_type.equals("book"))
            home_condition.setVisibility(View.GONE);
        else if(item_type.equals("cycle")) {
            home_author.setVisibility(View.GONE);
            home_category.setVisibility(View.GONE);
        }
        else if(item_type.equals("electronic"))
            home_author.setVisibility(View.GONE);

        pd = new ProgressDialog(this);
        pd.setMessage("Retrieving from database....");

        search_data=new ArrayList<String>();
     //   get_search_data(search_data);


        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,search_data);
        //Getting the instance of AutoCompleteTextView

        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);
        items = new ArrayList<ItemLayout>();
        description = new String[5];
        ArrayList<String> temp = new ArrayList<String>();
        getBooksDescription();
*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        searchButton=(ImageButton)findViewById(R.id.searchItem);
        home_author=(Button)findViewById(R.id.book_authors);
        home_price=(Button)findViewById(R.id.book_price);
        home_category=(Button)findViewById(R.id.book_category);
        home_condition=(Button)findViewById(R.id.book_condition);
        home_name=(Button)findViewById(R.id.book_names);




        items=new ArrayList<>();

        search_item=new ArrayList<>();

        if(NavigationDrawer.position==0)
            item_type="book";
        else if(NavigationDrawer.position==1)
            item_type="cycle";
        else if(NavigationDrawer.position==3)
            item_type="electronic";

        if(item_type.equals("book"))
            home_condition.setVisibility(View.GONE);
        else if(item_type.equals("cycle")) {
            home_author.setVisibility(View.GONE);
            home_category.setVisibility(View.GONE);
        }
        else if(item_type.equals("electronic"))
            home_author.setVisibility(View.GONE);

        pd = new ProgressDialog(this);
        pd.setMessage("Retrieving from database....");


        System.out.println("pd before");;
       // pd.show();

        System.out.println("pd after");

        search_data=new ArrayList<String>();
    //    get_search_data(search_data);


        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,search_data);
        //Getting the instance of AutoCompleteTextView
        actv = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);
        items = new ArrayList<ItemLayout>();
        description = new String[5];
        ArrayList<String> temp = new ArrayList<String>();
        getBooksDescription();

       // pd.dismiss();


        mSpeakBtn = (ImageButton) findViewById(R.id.btnspeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

        searchButton.setVisibility(View.GONE);
        mSpeakBtn.setVisibility(View.GONE);
        actv.setVisibility(View.GONE);

        home_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item_type.equals("book"))
                    item_subtype="name";
                else if(item_type.equals("cycle"))
                    item_subtype="brand";
                else if(item_type.equals("electronic"))
                    item_subtype="model";
                Intent intent=new Intent(HomeActivity.this, CardViewActivity.class);
                startActivity(intent);

            }
        });


        home_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_subtype="author";
                Intent intent=new Intent(HomeActivity.this, CardViewActivity.class);
                startActivity(intent);

            }
        });
        home_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_subtype="category";
                Intent intent=new Intent(HomeActivity.this, CardViewActivity.class);
                startActivity(intent);

            }
        });
        home_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_subtype="condition";
                Intent intent=new Intent(HomeActivity.this, CardViewActivity.class);
                startActivity(intent);

            }
        });
        home_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_subtype="price";
                Intent intent=new Intent(HomeActivity.this, CardViewActivity.class);
                startActivity(intent);

            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    getBooksDescription(actv.getText().toString());

            }
        });




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
            LoginActivity.startIntent(HomeActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void get_search_data(final ArrayList<String> search_data)
    {
        DatabaseReference ref = null;
        if(item_type.equals("book"))
            ref = FirebaseDatabase.getInstance().getReference().child("Books");
        else if(item_type.equals("cycle"))
            ref = FirebaseDatabase.getInstance().getReference().child("Cycles");
        else if(item_type.equals("electronic"))
            ref = FirebaseDatabase.getInstance().getReference().child("Electronics");

        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot

                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            // Book b= appleSnapshot.getValue(Book.class);
                            String s="";
                            if(item_type.equals("book"))
                                s = appleSnapshot.child("name").getValue().toString();
                            else if(item_type.equals("cycle"))
                                s = appleSnapshot.child("brand").getValue().toString();
                            if(item_type.equals("electronic"))
                                s = appleSnapshot.child("model").getValue().toString();
                            search_data.add(s);



                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }
    public void getBooksDescription(final String str) {
        pd.show();

        if(item_type.equals("book")) {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");
            final ArrayList<String> d = new ArrayList<String>();
            final ArrayList<String> d2 = new ArrayList<String>();
            Query query = ref.orderByChild("name");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot

                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                // Book b= appleSnapshot.getValue(Book.class);
                                String s = appleSnapshot.child("name").getValue().toString();
                                if(s.equals(str))
                                d.add(s);



                            }

                            collectPhoneNumbers_search(d);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        else if(item_type.equals("cycle"))
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cycles");
            final ArrayList<String> d = new ArrayList<String>();

            Query query = ref.orderByChild("brand");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot

                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                // Book b= appleSnapshot.getValue(Book.class);
                                String s = appleSnapshot.child("brand").getValue().toString();
                                if(s.equals(str))
                                d.add(s);



                            }

                            collectPhoneNumbers_search(d);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        else if(item_type.equals("electronic"))
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Electronics");
            final ArrayList<String> d = new ArrayList<String>();

            Query query = ref.orderByChild("model");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot

                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                // Book b= appleSnapshot.getValue(Book.class);
                                String s = appleSnapshot.child("model").getValue().toString();
                                if(s.equals(str))
                                d.add(s);



                            }

                            collectPhoneNumbers_search(d);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });

        }

    }

    public void getBooksDescription() {
        pd.show();
        if(item_type.equals("book")) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");
            final ArrayList<String> d = new ArrayList<String>();
            final ArrayList<String> d2 = new ArrayList<String>();
            Query query = ref.orderByChild("name");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot

                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                // Book b= appleSnapshot.getValue(Book.class);
                                String s = appleSnapshot.child("name").getValue().toString();

                                d.add(s);



                            }

                            collectPhoneNumbers(d);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        else if(item_type.equals("cycle"))
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cycles");
            final ArrayList<String> d = new ArrayList<String>();

            Query query = ref.orderByChild("brand");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot

                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                // Book b= appleSnapshot.getValue(Book.class);
                                String s = appleSnapshot.child("brand").getValue().toString();

                                d.add(s);



                            }

                            collectPhoneNumbers(d);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        else if(item_type.equals("electronic"))
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Electronics");
            final ArrayList<String> d = new ArrayList<String>();

            Query query = ref.orderByChild("model");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot

                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                // Book b= appleSnapshot.getValue(Book.class);
                                String s = appleSnapshot.child("model").getValue().toString();

                                d.add(s);



                            }

                            collectPhoneNumbers(d);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });

        }

    }

    public void collectPhoneNumbers(ArrayList<String> d) {

        int cnt = 0;
        for (int i = 0; i < d.size(); i++) {

            names.add(d.get(i));

         //   HomeActivity.description[cnt++] = d.get(i);

        }

        int count = 0;
        for (int i = 0; i < names.size(); i++) {
        /*
            String childVal=d2.get(i);
            String download_child=childVal+".jpg";
       //    String download_child="Got_rohan23chhabra@gmail.com.jpg";

            StorageReference storageRef= FirebaseStorage.getInstance().getReference();
            StorageReference islandRef = storageRef.child(download_child);

            File localFile = null;
            try {
                localFile = File.createTempFile(childVal,"jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            name_single=names.get(i);

            final File finalLocalFile = localFile;
            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Uri uri=Uri.fromFile(finalLocalFile);

                    HomeActivity.this.layout= new ItemLayout(uri, name_single);
                    HomeActivity.this.items.add(layout);
                    for(int j=0;j<100;j++)
                        System.out.println(items.size());

                    // Local temp file has been created
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            */
            count++;
            if(item_type.equals("book"))
                layout=new ItemLayout(image_id[0],names.get(i));
            if(item_type.equals("cycle"))
                layout=new ItemLayout(image_id[1],names.get(i));
            if(item_type.equals("electronic"))
                layout=new ItemLayout(image_id[2],names.get(i));
            items.add(layout);

            System.out.println(names.get(i));


        }
        pd.dismiss();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        /*recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                for(int i=0;i<100;i++)
                System.out.println("position "+ itemPosition);
                Intent booksActivity = new Intent(HomeActivity.this, BookDescription.class);
                booksActivity.putExtra("bookPosition", itemPosition);
                startActivity(booksActivity);
            }
        });*/
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ItemLayoutAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    public void collectPhoneNumbers_search(ArrayList<String> d) {

        search_item.clear();

        int cnt = 0;
        for (int i = 0; i < d.size(); i++) {

            names.add(d.get(i));

            HomeActivity.description[cnt++] = d.get(i);

        }

        int count = 0;
        for (int i = 0; i < names.size(); i++) {
        /*
            String childVal=d2.get(i);
            String download_child=childVal+".jpg";
       //    String download_child="Got_rohan23chhabra@gmail.com.jpg";

            StorageReference storageRef= FirebaseStorage.getInstance().getReference();
            StorageReference islandRef = storageRef.child(download_child);

            File localFile = null;
            try {
                localFile = File.createTempFile(childVal,"jpg");
            } catch (IOException e) {
                e.printStackTrace();
            }
            name_single=names.get(i);

            final File finalLocalFile = localFile;
            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Uri uri=Uri.fromFile(finalLocalFile);

                    HomeActivity.this.layout= new ItemLayout(uri, name_single);
                    HomeActivity.this.items.add(layout);
                    for(int j=0;j<100;j++)
                        System.out.println(items.size());

                    // Local temp file has been created
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            */
            count++;
            if(item_type.equals("book"))
                layout=new ItemLayout(image_id[0],names.get(i));
            if(item_type.equals("cycle"))
                layout=new ItemLayout(image_id[1],names.get(i));
            if(item_type.equals("electronic"))
                layout=new ItemLayout(image_id[2],names.get(i));
            search_item.add(layout);


        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        /*recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                for(int i=0;i<100;i++)
                System.out.println("position "+ itemPosition);
                Intent booksActivity = new Intent(HomeActivity.this, BookDescription.class);
                booksActivity.putExtra("bookPosition", itemPosition);
                startActivity(booksActivity);
            }
        });*/
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ItemLayoutAdapter(search_item);
        recyclerView.setAdapter(adapter);
        pd.dismiss();
    }


    /*private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
*/
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }*/
    /* Called whenever we call invalidateOptionsMenu() */
    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }*/

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    actv.setText(result.get(0));
                }
                break;
            }

        }
    }
}
