package com.crazyhitty.chdev.ks.firebasechat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.crazyhitty.chdev.ks.firebasechat.HomeActivity;
import com.crazyhitty.chdev.ks.firebasechat.R;
import com.crazyhitty.chdev.ks.firebasechat.UploadBook;
import com.crazyhitty.chdev.ks.firebasechat.UploadCycle;
import com.crazyhitty.chdev.ks.firebasechat.UploadElectronics;
import com.crazyhitty.chdev.ks.firebasechat.UserDetails;
import com.crazyhitty.chdev.ks.firebasechat.global.GlobalChat;
import com.crazyhitty.chdev.ks.firebasechat.listView.ItemAdapter;
import com.crazyhitty.chdev.ks.firebasechat.listView.ItemDataProvider;
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.LoginActivity;
import com.crazyhitty.chdev.ks.firebasechat.ui.activities.UserListingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static int position;
    ListView mListView;
    //GridView grid_view;
    TextView nav_textview;
    View mListTouchInterceptor;
    FrameLayout mDetailsLayout;
    com.alexvasilkov.foldablelayout.UnfoldableView mUnfoldableView;
    int[] mobileArray = {R.drawable.book_main, R.drawable.cycle_main, R.drawable.electronics_main};
    ItemAdapter adapter;
    private Integer image_id[] = { R.drawable.cycle_main, R.drawable.cycle_main,  R.drawable.cycle_main,  R.drawable.cycle_main,
            R.drawable.cycle_main, R.drawable.cycle_main, R.drawable.cycle_main, R.drawable.cycle_main, R.drawable.cycle_main};


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NavigationDrawer.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, NavigationDrawer.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.textView_navbar);
        FirebaseUser username = FirebaseAuth.getInstance().getCurrentUser();
        nav_user.setText(username.getEmail().toString());

        mAuth = FirebaseAuth.getInstance();
        mListView = (ListView) findViewById(R.id.list_view);
        adapter = new ItemAdapter(getApplicationContext(), R.layout.activity_listview);
        for(int i=0; i<3; i++){
            ItemDataProvider item = new ItemDataProvider(mobileArray[i]);
            adapter.add(item);
        }
        mListView.setAdapter(adapter);
        mListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int position  = mListView.pointToPosition((int)event.getX(), (int)event.getY());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // v.setBackground(view.getResources().getDrawable(R.drawable.border_clicked));
                        break;
                    case MotionEvent.ACTION_UP:
                        // v.setBackground(view.getResources().getDrawable(R.drawable.border));
                        // v.performClick();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        //v.setBackground(view.getResources().getDrawable(R.drawable.border));
                        break;
                }

                mUnfoldableView.unfold(v, mDetailsLayout);
                mUnfoldableView.changeCoverView(v);

                position++;
                NavigationDrawer.position = position;
                for(int i=0;i<100;i++)
                    System.out.println("position " + NavigationDrawer.position);
                return false;
            }
        });

        mListTouchInterceptor = findViewById(R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);

        mDetailsLayout = (FrameLayout) findViewById(R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);

        mUnfoldableView = (com.alexvasilkov.foldablelayout.UnfoldableView) findViewById(R.id.unfoldable_view);

        mUnfoldableView.setOnFoldingListener(new com.alexvasilkov.foldablelayout.UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(com.alexvasilkov.foldablelayout.UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(com.alexvasilkov.foldablelayout.UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
                Intent intent = new Intent(NavigationDrawer.this, HomeActivity.class);

                startActivity(intent);
            }

            @Override
            public void onFoldingBack(com.alexvasilkov.foldablelayout.UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(com.alexvasilkov.foldablelayout.UnfoldableView unfoldableView) {
                // Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                mListTouchInterceptor.setClickable(false);
                mDetailsLayout.setVisibility(View.INVISIBLE);
                //startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            LoginActivity.startIntent(NavigationDrawer.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            UserDetails.startActivity(NavigationDrawer.this);
            // Handle the camera action
        } else if (id == R.id.nav_upload_book) {
            UploadBook.startActivity(NavigationDrawer.this);
        } else if (id == R.id.nav_upload_cycle) {
            UploadCycle.startActivity(NavigationDrawer.this);
        } else if (id == R.id.nav_upload_electronics) {
            UploadElectronics.startActivity(NavigationDrawer.this);

        }
        else if(id==R.id.chat_list)
        {
            UserListingActivity.startActivity(NavigationDrawer.this);
        }
        else if(id==R.id.global_chat)
        {
            Intent intent=new Intent(NavigationDrawer.this,GlobalChat.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}
