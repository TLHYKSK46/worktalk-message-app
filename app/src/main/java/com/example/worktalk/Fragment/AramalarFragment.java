package com.example.worktalk.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Activity.KisilerListesiActivity;
import com.example.worktalk.Adapters.CallAdapter;
import com.example.worktalk.Models.CallModel;
import com.example.worktalk.R;
import com.example.worktalk.Utils.GetDate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AramalarFragment extends Fragment {
    private FirebaseDatabase databaseFb;
    private DatabaseReference referenceDb;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private CallModel callModel;
    private List<CallModel> listCallModel;
    private List<String> listUserkey;
    public  String userName, otherName, otherKey, telefonNo, zaman, aramaTipi;
    private RecyclerView callListLayoutRecyView;
    private CallAdapter callAdapter;
    private View view;
    private Activity activity;
    RecyclerView mesajCardLayout;
    private FloatingActionButton callAdd_fabButon;
    private ImageButton callImgButon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_aramalar, container, false);
        callAdd_fabButon();
        tanimla();

      //  AramalariListele();
        Log.i("asd", "child55:" + otherKey);

        return view;
    }

    public void tanimla() {

        //otherName=mesajModel.getOtherName();
        //fab_iconKisilist = fab_iconKisilist.findViewById(R.id.fab_iconKisilist);
        databaseFb = FirebaseDatabase.getInstance();
        referenceDb = databaseFb.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        listCallModel = new ArrayList<CallModel>();
        listUserkey = new ArrayList<String>();
        //--------------------------------------------------------
        callImgButon = (ImageButton) view.findViewById(R.id.callImgButon);

        callListLayoutRecyView = view.findViewById(R.id.callListLayoutRecyView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        callListLayoutRecyView.setLayoutManager(layoutManager);
        // mesajlarimAdapter = new MesajlarimAdapter(this.getContext(),listUserkey<>listUserkey, activity, userName);
        callAdapter = new CallAdapter(listCallModel, getActivity(), getContext(), listUserkey);

        callListLayoutRecyView.setAdapter(callAdapter);
        //-----------------------------------

      //  SesliAramalarBilgiAl();
        AramalariListele();

    }
    public void AramalariListele() {
        referenceDb.child("SesliAramalar").child(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               otherKey = dataSnapshot.getKey();
               //burayı sonradan ekledim
                referenceDb.child("SesliAramalar").child(user.getUid()).child(otherKey).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        CallModel callModel=dataSnapshot.getValue(CallModel.class);
                        listUserkey.add(dataSnapshot.getKey());
                        callAdapter.notifyDataSetChanged();
                        listCallModel.add(callModel);
                        callListLayoutRecyView.scrollToPosition(listCallModel.size() - 1);
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


    public void callImgButonAction() {


        //referenceDb = firebaseDatabase.getReference().child("SohbetListem").child(otherKey);
        zaman = GetDate.getDate();
        final Map messageMap = new HashMap();
        messageMap.put("userKey", user.getUid());
        messageMap.put("otherKey", otherKey);
        messageMap.put("arananKisiAdiSoyadi", userName);
        messageMap.put("othername", otherName);
        messageMap.put("telefonNo", telefonNo);
        messageMap.put("zaman", zaman);
        messageMap.put("aramaTipi", aramaTipi);
        messageMap.put("profilFoto", "null");
        // referenceDb.setValue(messageMap);
        referenceDb.child("SesliAramalar").child(user.getUid()).child(otherKey).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


            }
        });
    }

    public void callAdd_fabButon() {
        FloatingActionButton callAdd_fabButon = (FloatingActionButton) view.findViewById(R.id.callAdd_fabButon);
        callAdd_fabButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), KisilerListesiActivity.class);
                startActivity(intent);

            }
        });


    }

}
