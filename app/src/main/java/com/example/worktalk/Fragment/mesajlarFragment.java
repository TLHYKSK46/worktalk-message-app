package com.example.worktalk.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Activity.KisilerListesiActivity;
import com.example.worktalk.Adapters.MesajlarimAdapter;
import com.example.worktalk.Models.UserModel;
import com.example.worktalk.Models.MesajlarımModel;
import com.example.worktalk.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class mesajlarFragment extends Fragment {

    FirebaseDatabase databaseFb;
    DatabaseReference referenceDb;
    FirebaseAuth auth;
    FirebaseUser user;
    private MesajlarımModel mesajlarımModel;
    private List<MesajlarımModel> listMesajlarımModel;
    List<String> listUserkey;
    String userName, otherName, otherKey;
    RecyclerView mesajlarRecyView;
    MesajlarimAdapter mesajlarimAdapter;
    UserModel userModel;
    View view;
    Activity activity;
    RecyclerView mesajCardLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mesajlar, container, false);
        tanimla();
        fabActionButon();
        mesajlarilistele();


        return view;
    }

    public void tanimla() {

        //otherName=mesajModel.getOtherName();
        //fab_iconKisilist = fab_iconKisilist.findViewById(R.id.fab_iconKisilist);
        databaseFb = FirebaseDatabase.getInstance();
        referenceDb = databaseFb.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        listMesajlarımModel = new ArrayList<MesajlarımModel>();
        listUserkey = new ArrayList<String>();

        //Sohbet list sayfası

        mesajlarRecyView = view.findViewById(R.id.mesajlarRecyView);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        mesajlarRecyView.setLayoutManager(layoutManager);
        // mesajlarimAdapter = new MesajlarimAdapter(this.getContext(),listUserkey<>listUserkey, activity, userName);
        mesajlarimAdapter = new MesajlarimAdapter(listMesajlarımModel, (FragmentActivity) activity, getContext(), listUserkey);

        mesajlarRecyView.setAdapter(mesajlarimAdapter);
        mesajCardLayout = view.findViewById(R.id.mesajCardLayout);
       /* mesajCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, ChatActivity.class);
            }
        });*/

    }


    public void mesajlarilistele() {
        //otherKey = "5XGwCjsIJlXdPIsIUNP9qDRHg9r2";
        referenceDb.child("SohbetListem").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                MesajlarımModel mesajlarımModel = dataSnapshot.getValue(MesajlarımModel.class);
                String userKey=dataSnapshot.child("userKey").getValue().toString();

                //burada eger kendimi listelememek ve sadece benmnim mesajlaştıgım kimseleri getir demektir..
                if (!dataSnapshot.getKey().equals(user.getUid())&& user.getUid().equals(userKey)) {

                    listMesajlarımModel.add(mesajlarımModel);
                    mesajlarimAdapter.notifyDataSetChanged();

                    listUserkey.add(dataSnapshot.getKey());
                    //scroll
                    mesajlarRecyView.scrollToPosition(listMesajlarımModel.size() - 1);


                }
                //Log.i("Kullanıcı:",dataSnapshot.getKey());

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

    public void fabActionButon() {
        FloatingActionButton fab_iconKisilist = (FloatingActionButton) view.findViewById(R.id.fab_iconKisilist);
        fab_iconKisilist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), KisilerListesiActivity.class);
                startActivity(intent);

            }
        });
    }
}
