package com.example.worktalk.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Adapters.UserAdapter;
import com.example.worktalk.Models.UserModel;
import com.example.worktalk.Models.SirketModel;
import com.example.worktalk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KisilerListesiActivity extends AppCompatActivity {
    FirebaseDatabase databaseFb;
    DatabaseReference referenceDb;
    LinearLayout userCardLayout;
    FirebaseUser user;
    FirebaseAuth auth;
    List<String> listUserkey;
    RecyclerView userListRecyView;
    UserAdapter userAdapter;
    Activity activity;
    View view;
    ImageView geriDönImageView;
    ImageButton callButonKisiler;
    UserModel userModel;
    String sirketKey, usersirketKey;
    String userName, otherName, otherKey, userKey, telefonNo, zaman, aramaTipi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisiler_listesi);
        tanimla();
        sirketBilgileriAl();
        kullanicilarıListele();

    }

    public void tanimla() {

        //userName = (String) getIntent().getExtras().getString("kadi");
        databaseFb = FirebaseDatabase.getInstance();
        referenceDb = databaseFb.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        listUserkey = new ArrayList<>();
        geriDönImageView = (ImageView) findViewById(R.id.geriDönImageView);
        //KUllanıcı list sayfası
        userListRecyView = (RecyclerView) findViewById(R.id.userListRecyView);
        //kullanıcılar listeleme kısmı 2 i 1 yapacaksın unutma!!
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        userListRecyView.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(KisilerListesiActivity.this, listUserkey, activity, userName);
        userListRecyView.setAdapter(userAdapter);
        geriDönImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KisilerListesiActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }



    public void sirketBilgileriAl() {
        referenceDb.child("Sirketler").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                sirketKey = dataSnapshot.getKey();
                SirketModel sirketModel = dataSnapshot.getValue(SirketModel.class);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void kullanicilarıListele() {
        referenceDb.child("UserModel").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // if (!dataSnapshot.getKey().equals(userName)) {
                referenceDb.child("UserModel").child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserModel userModel = dataSnapshot.getValue( UserModel.class);
                        usersirketKey = dataSnapshot.child("sirketKey").getValue().toString();
                        otherName = dataSnapshot.child("adSoyad").getValue().toString();
                        telefonNo = dataSnapshot.child("telefonNo").getValue().toString();
                        userName = userModel.getAdSoyad();
                      //  Log.i("asdf","sadsf"+dataSnapshot.getKey());


                        //Aşagıdaki şartın amacı:
                        //1-Adsoyad girilmemiş kişiler listelenmemesi
                        //2-Aktif kullanıcının(yani kendisini) hesabını listelememek.

                        if (!userModel.getAdSoyad().equals("") && !dataSnapshot.getKey().equals(user.getUid())
                                && usersirketKey.equals(sirketKey)) {
                            //&& usersirketKey.equals(sirketKey)
                            listUserkey.add(dataSnapshot.getKey());
                            userAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            //Log.i("Kullanıcı:",dataSnapshot.getKey());

            //}

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
