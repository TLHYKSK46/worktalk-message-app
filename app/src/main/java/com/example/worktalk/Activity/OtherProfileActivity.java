package com.example.worktalk.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worktalk.Models.UserModel;
import com.example.worktalk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfileActivity extends AppCompatActivity {
    private CircleImageView fotoOtherProfile;
    private EditText adsoyadOtherProfile, emailOtherProfile, telefonOtherProfile, departmanOtherProfile, statüOtherProfile;
    String otherName,otherNameback;
    private LinearLayout geriDönButon;
    private String fotoUrl;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseDatabase databaseFb;
    private DatabaseReference referenceDt;
    private StorageReference storageRef;
    private FirebaseStorage StorageFb;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        tanimla();
        otherProfilBilgileriGetir();
    }

    public void tanimla() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseFb = FirebaseDatabase.getInstance();
        referenceDt = databaseFb.getReference();
        StorageFb = FirebaseStorage.getInstance();
        storageRef = StorageFb.getReference();

        geriDönButon = (LinearLayout) findViewById(R.id.geriDönButonOtherProfile);

        fotoOtherProfile = (CircleImageView) findViewById(R.id.fotoOtherProfile);
        adsoyadOtherProfile = (EditText) findViewById(R.id.adsoyadOtherProfile);
        emailOtherProfile = (EditText) findViewById(R.id.emailotherProfile);
        telefonOtherProfile = (EditText) findViewById(R.id.telefonOtherProfile);
        departmanOtherProfile = (EditText) findViewById(R.id.departmanOtherProfile);
        statüOtherProfile = (EditText) findViewById(R.id.statüOtherProfile);
        geriDönButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("otherName",otherNameback.toString());
                intent.putExtra("otherKey",getOtherKey());
                startActivity(intent);
            }
        });


        fotoOtherProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // profilFotosecGaleriden();
            }
        });
    }



    public void otherProfilBilgileriGetir() {
        referenceDt.child("UserModel").child(getOtherKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserModel userModel = dataSnapshot.getValue( UserModel.class);
                otherNameback= userModel.getAdSoyad();
                adsoyadOtherProfile.setText( userModel.getAdSoyad());
                emailOtherProfile.setText( userModel.getEmail());
                telefonOtherProfile.setText( userModel.getTelefonNo());
                departmanOtherProfile.setText( userModel.getDepartman());
                statüOtherProfile.setText( userModel.getStatü());
                if (!userModel.getProfilFoto().equals("null")) {
                    Picasso.get().load( userModel.getProfilFoto()).into(fotoOtherProfile);
                    //fotoOtherProfile.setImageURI(fotoUrl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public String getOtherName() {
        //böylede veri alınır ıntentlerle
        Bundle bundle = getIntent().getExtras();
        otherName = bundle.getString("otherName").toString();
        return otherName;
    }

    public String getOtherKey() {
        //ıntentlerle böylede veri alabiliriz
        String otherKey = getIntent().getExtras().getString("otherKey").toString();
        return otherKey;
    }
}
