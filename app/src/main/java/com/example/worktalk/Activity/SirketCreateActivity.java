package com.example.worktalk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worktalk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SirketCreateActivity extends AppCompatActivity {
    private EditText sirketAdiCreate, sirketUnvaniCreate, sirketFaliyetAlaniCreate, sirketTanimCreate, sirketAdresiCreate;
    private Button sirketCreateButon;
    private FirebaseDatabase databaseFb;
    private FirebaseAuth auth;
    private DatabaseReference referenceDt;
    private FirebaseUser user;
    String asd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sirket_create);
        tanimla();
    }

    void tanimla() {
        databaseFb = FirebaseDatabase.getInstance();
        referenceDt = databaseFb.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //--------
        sirketAdiCreate = (EditText) findViewById(R.id.sirketAdiCreate);
        sirketUnvaniCreate = (EditText) findViewById(R.id.sirketUnvaniCreate);
        sirketFaliyetAlaniCreate = (EditText) findViewById(R.id.sirketFaliyetAlaniCreate);
        sirketTanimCreate = (EditText) findViewById(R.id.sirketTanimCreate);
        sirketAdresiCreate = (EditText) findViewById(R.id.sirketAdresiCreate);
        sirketCreateButon = (Button) findViewById(R.id.sirketCreateButon);
        sirketCreateButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sirketCreate();
                Intent intent = new Intent(SirketCreateActivity.this, SirketKeyGirisActivity.class);
                intent.putExtra("sirketKey", asd);
                startActivity(intent);
                finish();
            }
        });

    }

    public void sirketCreate() {
        final String sirketKey = referenceDt.child("Sirketler").child(user.getUid()).push().getKey().toString();

       /* SirketModel sirketModel = new SirketModel();
        sirketModel.setSirketAdi(sirketModel.getSirketAdi().toString());
        sirketModel.setSirketUnvani(sirketUnvaniCreate.getText().toString());
        sirketModel.setSirketFaliyetAlani(sirketFaliyetAlaniCreate.getText().toString());
        sirketModel.setSirketTanim(sirketTanimCreate.getText().toString());
        sirketModel.setSirketadresi(sirketAdresiCreate.getText().toString());
        sirketModel.setSirketKey(sirketKey);*/
        //referenceDt.push().setValue(sirketModel);

        final Map map = new HashMap();
        map.put("sirketAdi", sirketAdiCreate.getText().toString());
        map.put("sirketUnvani", sirketUnvaniCreate.getText().toString());
        map.put("sirketFaliyetAlani", sirketFaliyetAlaniCreate.getText().toString());
        map.put("sirketTanım", sirketTanimCreate.getText().toString());
        map.put("sirketAdresi", sirketAdresiCreate.getText().toString());
        map.put("sirketKey", sirketKey.toString());

        // referenceDt.setValue(map);
        referenceDt.child("Sirketler").child(sirketKey).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    asd = sirketKey.toString();
                    Intent intent = new Intent(SirketCreateActivity.this, SirketKeyGirisActivity.class);
                    intent.putExtra("sirketKey", sirketKey);
                    startActivity(intent);
                    finish();
                }
            }

        });
        Toast.makeText(this, "Bilgileriniz Başarıyla Kaydedildi!", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Sirket Key: '" + sirketKey + "' Lütfen şirket Keyinizi Kaybetbeyiniz!", Toast.LENGTH_SHORT).show();


    }
}
