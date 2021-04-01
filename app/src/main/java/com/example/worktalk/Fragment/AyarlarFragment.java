package com.example.worktalk.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.worktalk.Activity.ProfileActivity;
import com.example.worktalk.Models.UserModel;
import com.example.worktalk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class AyarlarFragment extends Fragment {
    LinearLayout profileLayout;
    CircleImageView profileFotoAyarlar;
    TextView adsoyadAyarlarFragment;
    View view;
Activity activity;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseDatabase databaseFb;
    private DatabaseReference referenceDt;


    public AyarlarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_ayarlar, container, false );
        tanımla();
        kullaniciyiGetir();
        return view;
    }

    public void tanımla() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseFb = FirebaseDatabase.getInstance();
        adsoyadAyarlarFragment=(TextView)view.findViewById(R.id.adsoyadAyarlarFragment);
        profileFotoAyarlar=(CircleImageView)view.findViewById(R.id.profileFotoAyarlar);
        referenceDt = databaseFb.getReference().child("UserModel").child(user.getUid());



        profileLayout = (LinearLayout) view.findViewById( R.id.profilLayout );
        profileLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent( getActivity(), ProfileActivity.class);
                startActivity(intent);



            }
        } );
    }
public  void kullaniciyiGetir(){
        referenceDt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel =dataSnapshot.getValue( UserModel.class);
                adsoyadAyarlarFragment.setText( userModel.getAdSoyad());
                if (!userModel.getProfilFoto().equals("null")) {
                    Picasso.get().load( userModel.getProfilFoto()).into(profileFotoAyarlar);
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}

}




