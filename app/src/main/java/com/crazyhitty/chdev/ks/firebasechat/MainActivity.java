package com.crazyhitty.chdev.ks.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.crazyhitty.chdev.ks.firebasechat.listView.ItemAdapter;
import com.crazyhitty.chdev.ks.firebasechat.listView.ItemDataProvider;


public class MainActivity extends AppCompatActivity {

    ListView mListView;
    //GridView grid_view;
    View mListTouchInterceptor;
    FrameLayout mDetailsLayout;
    com.alexvasilkov.foldablelayout.UnfoldableView mUnfoldableView;
    int[] mobileArray = {R.drawable.book_main, R.drawable.cycle_main, R.drawable.electronics_main};
    ItemAdapter adapter;
    private Integer image_id[] = { R.drawable.cycle_main, R.drawable.cycle_main,  R.drawable.cycle_main,  R.drawable.cycle_main,
            R.drawable.cycle_main, R.drawable.cycle_main, R.drawable.cycle_main, R.drawable.cycle_main, R.drawable.cycle_main};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                int position  = adapter.getPosition(v);
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
                System.out.println("position " + position);
                return false;
            }
        });
       // grid_view = (GridView) findViewById(R.id.gridview);
        /*List<String> list = new ArrayList<String>();
        list.add("harry potter and the game of thrones");
        list.add("asklfsf");
        list.add("aslfasfasffasf");
        list.add("asklfsf");
        list.add("aslfasfasffasf");
        list.add("asklfsf");
        list.add("aslfasfasffasf");
        list.add("asklfsf");
        list.add("aslfasfasffasf");

        grid_view.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.textadapter, list));
        ImageAdaptar adapter2 = new ImageAdaptar(this, image_id, list);
        grid_view.setAdapter(adapter2);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Toast.makeText(getBaseContext(), "You click on image "+arg2, Toast.LENGTH_LONG).show();
            }


        });*/
        /*mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mUnfoldableView.unfold(getViewByPosition(position, mListView), mDetailsLayout);
               // String value = mListView.getItemAtPosition(position).toString();
                //display value here
            }
        });*/
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
                 Intent intent = new Intent(MainActivity.this, HomeActivity.class);

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
        /*if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
            mUnfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }*/
        //mUnfoldableView.foldBack();
        super.onBackPressed();
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
