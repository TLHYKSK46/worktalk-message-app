package com.example.worktalk.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.worktalk.Models.UserModel;
import com.example.worktalk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView profilFoto;
    private EditText adsoyadProfil, emailEditTextProfil, telefonEditTextProfil, departmanEditTextProfil, statüEditTextProfil;
    private Button kaydetButonProfile;
    private LinearLayout geriDönButon;
    private String fotoUrl;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseDatabase databaseFb;
    private DatabaseReference referenceDt;
    private StorageReference storageRef;
    private FirebaseStorage StorageFb;
    private Uri filePath;
    View view;
    MaterialDialog process_dialog;
    Context context;
    private static final int PICK_IMAGE_REQUEST = 5;
    ImageButton profilBilgiDüzenleimg;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Log.i("asd", "asd:" + filePath);
            StorageReference ref = storageRef.child("KullaniciProfilFoto").child(auth.getCurrentUser().getUid()).child(filePath.getLastPathSegment());
            ref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                }
            });
            //Picasso.get().load(filePath).fit().centerCrop().into(profilFoto);
           /* StorageReference ref = storageRef.child("KullanicilarProfilFoto").child(user.getUid());
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(profilFoto);
                }
            });*/
        }
    }
    //-----------------------------------------------------------------------------------Hata burda!!!!!
         /*   ref.putFile(filePath).addOnCompleteListener(this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Başardın", Toast.LENGTH_SHORT).show();
                        String adSoyad = adsoyadProfil.getText().toString();
                        //String profilFoto=.getText().toString();
                        String telefonNo = telefonEditTextProfil.getText().toString();
                        String departman = departmanEditTextProfil.getText().toString();
                        String statü = statüEditTextProfil.getText().toString();
                        String email = emailEditTextProfil.getText().toString();

                        referenceDt = databaseFb.getReference().child("UserModel").child(auth.getUid());
                       String asd=task.getResult().getStorage().getDownloadUrl().toString();
                       Log.i("asd","asd");
                        Map map = new HashMap();
                        map.put("profilFoto", task.getResult().getStorage().getDownloadUrl().toString());
                        map.put("adSoyad", adSoyad);
                        map.put("telefonNo", telefonNo);
                        map.put("email", email);
                        map.put("departman", departman);
                        map.put("statü", statü);
                        referenceDt.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    ProfileActivity profileActivity = new ProfileActivity();
                                    Toast.makeText(getApplicationContext(), "Bilgileriniz Başarıyla Kaydedildi!", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Bilgileriniz Kaydedilmedi:( \n Tekrar Deneyiniz!", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "yine olmadı", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
//----------------------------------------------------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        try {
            tanimla();
            profilBilgileriGetir();
        } catch (Exception e) {
            Log.i("hata", "Hata:" + e + user.getPhoneNumber());
        }

    }

    public void tanimla() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseFb = FirebaseDatabase.getInstance();
        StorageFb = FirebaseStorage.getInstance();
        storageRef = StorageFb.getReference();

        geriDönButon = (LinearLayout) findViewById(R.id.geriDönButon);
        profilBilgiDüzenleimg = (ImageButton) findViewById(R.id.profilBilgiDüzenleimg);
        profilFoto = (CircleImageView) findViewById(R.id.profile_image);
        adsoyadProfil = (EditText) findViewById(R.id.adsoyadProfil);
        emailEditTextProfil = (EditText) findViewById(R.id.emailEditTextProfil);
        telefonEditTextProfil = (EditText) findViewById(R.id.telefonEditTextProfil);
        departmanEditTextProfil = (EditText) findViewById(R.id.departmanEditTextProfil);
        statüEditTextProfil = (EditText) findViewById(R.id.statüEditTextProfil);
        kaydetButonProfile = (Button) findViewById(R.id.kaydetButonProfile);
        profilFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilFotosecGaleriden();
            }
        });

        geriDönButon.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
              /* ChangeFragment changeFragment=new ChangeFragment(view.getContext());
               changeFragment.change(new AyarlarFragment());*/
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);

                /*
                Fragment fragment=new AyarlarFragment();
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.add(R.layout.fragment_ayarlar,fragment,"tag");
                fragmentTransaction.commit();
                */


            }
        });
        referenceDt = databaseFb.getReference().child("UserModel").child(user.getUid());

        profilBilgiDüzenleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAktifEt();
            }
        });

        kaydetButonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profilBilgileriGüncelle();
                editTextPasifEt();

            }
        });


    }

    public void profilFotosecGaleriden() {//Önemli telefondaki özellkikleri kullanıyor
      /*  Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 5);*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Resim Seçiniz"), PICK_IMAGE_REQUEST);


    }


    public void profilBilgileriGetir() {
        referenceDt.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //profil bilgilerini getirme 1. yöntem
                UserModel userModel = dataSnapshot.getValue( UserModel.class);

                adsoyadProfil.setText( userModel.getAdSoyad());
                emailEditTextProfil.setText( userModel.getEmail());
                telefonEditTextProfil.setText( userModel.getTelefonNo());
                departmanEditTextProfil.setText( userModel.getDepartman());
                statüEditTextProfil.setText( userModel.getStatü());

                fotoUrl = userModel.getProfilFoto();

                if (!fotoUrl.equals("null")) {

                    Picasso.get().load( userModel.getProfilFoto()).into(profilFoto);

                } else {
                }

                //2.yöntem
              /*  String adSoyad=  dataSnapshot.child("adSoyad").getValue().toString();
                String profilFoto=dataSnapshot.child("profilFoto").getValue().toString();
                String telefonNo=dataSnapshot.child("telefonNo").getValue().toString();
                String departman= dataSnapshot.child("departman").getValue().toString();
                String statü= dataSnapshot.child("statü").getValue().toString();
                String email= dataSnapshot.child("email").getValue().toString();
*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void profilBilgileriGüncelle() {
        String adSoyad = adsoyadProfil.getText().toString();
        //String profilFoto=.getText().toString();
        String telefonNo = telefonEditTextProfil.getText().toString();
        String departman = departmanEditTextProfil.getText().toString();
        String statü = statüEditTextProfil.getText().toString();
        String email = emailEditTextProfil.getText().toString();
        referenceDt = databaseFb.getReference().child("UserModel").child(auth.getUid());
        Map map = new HashMap();
        map.put("profilFoto", "null");
        map.put("adSoyad", adSoyad);
        map.put("telefonNo", telefonNo);
        map.put("email", email);
        map.put("departman", departman);
        map.put("statü", statü);
        if (fotoUrl.equals("")) {
            map.put("profilFoto", "null");
        } else {
            map.put("profilFoto", fotoUrl);

        }
        referenceDt.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ProfileActivity profileActivity = new ProfileActivity();
                    Toast.makeText(getApplicationContext(), "Bilgileriniz Başarıyla Kaydedildi!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Bilgileriniz Kaydedilmedi:( \n Tekrar Deneyiniz!", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    public void editTextAktifEt() {
        adsoyadProfil.setEnabled(true);
        emailEditTextProfil.setEnabled(true);
        telefonEditTextProfil.setEnabled(true);
        departmanEditTextProfil.setEnabled(true);
        statüEditTextProfil.setEnabled(true);
        kaydetButonProfile.setVisibility(View.VISIBLE);

    }

    public void editTextPasifEt() {
        adsoyadProfil.setEnabled(false);
        emailEditTextProfil.setEnabled(false);
        telefonEditTextProfil.setEnabled(false);
        departmanEditTextProfil.setEnabled(false);
        statüEditTextProfil.setEnabled(false);
        kaydetButonProfile.setVisibility(View.GONE);

    }

}
