package com.alfikri.authenticationfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    FloatingActionButton fab_add;
    RecyclerAdapter recyclerAdapter;
    String url = "https://authenticationfirebase-33434-default-rtdb.firebaseio.com";
    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
    ArrayList<Mahasiswa> listMahasiswa;
    RecyclerView rv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        fab_add = findViewById(R.id.fb_add);
        rv_view = findViewById(R.id.rv_view);


        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(this);
        rv_view.setLayoutManager(mLayout);
        rv_view.setItemAnimator(new DefaultItemAnimator());

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm dialogForm = new DialogForm("","","","","","Tambah");
                dialogForm.show(getSupportFragmentManager(),"form");
            }
        });

        showData();
    }

    private void showData(){
        database.child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMahasiswa = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    Mahasiswa mhs = item.getValue(Mahasiswa.class);
                    mhs.setKey(item.getKey());
                    listMahasiswa.add(mhs);
                }
                recyclerAdapter = new RecyclerAdapter(listMahasiswa, DashboardActivity.this);
                rv_view.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
