package com.crazyhitty.chdev.ks.firebasechat.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.crazyhitty.chdev.ks.firebasechat.R;
import com.crazyhitty.chdev.ks.firebasechat.models.Cycle;
import com.crazyhitty.chdev.ks.firebasechat.ui.adapters.CyclesDisplayAdapter;

import java.util.ArrayList;
import java.util.List;

public class CyclesDisplayActivity extends AppCompatActivity {
    String brand, condition, price, user;

    private List<Cycle> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private CyclesDisplayAdapter cyclesDisplayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycles_display);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_cycles);
        cyclesDisplayAdapter = new CyclesDisplayAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(cyclesDisplayAdapter);

        Intent intent = getIntent();
        String s = intent.getStringExtra("email");
        user = s;
    }

    private void prepareCycleData() {

    }
}
