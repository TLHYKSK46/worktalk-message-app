package com.example.worktalk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.worktalk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateUserActivity extends AppCompatActivity {
    String userId, telefonNo, token, sirketKey;
    MaterialDialog process_dialog;
    private EditText adsoyadCreateUser, emailEditTextCreateUser, telefonNoCreateUser,
            departmanEditTextCreateUser, statüEditTextCreateUser, sirketKeyEditTextCreateUser;
    private TextView userIdTextViewCreateUser, sirketCreateGit;
    private Button kaydetButonCreateUser;
    private FirebaseDatabase databaseFb;
    private FirebaseAuth auth;
    private DatabaseReference referenceDt;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        try {
            intentVeriAl();
            tanimla();
            kontrolEt();
        /*  if (user.getUid().equals("U8Qn4trcSKfiiwKCduZBb8wvY413") ||user.getUid().equals("UGbUvU5h3wfS1JpHiL33CyOQdtk2")) {
                Intent intent = new Intent(this, MainActivity.class);

                startActivity(intent);
                finish();
            }*/

        } catch (Exception e) {

            Toast.makeText(this, "Hata!:" + auth.getCurrentUser(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Hata!:" + e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("hata", "kayıthatası" + e.toString());
        }
    }

    public void tanimla() {
        databaseFb = FirebaseDatabase.getInstance();
        referenceDt = databaseFb.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //--------
        telefonNoCreateUser = (EditText) findViewById(R.id.telefonNoCreateUser);
        adsoyadCreateUser = (EditText) findViewById(R.id.adsoyadCreateUser);
        emailEditTextCreateUser = (EditText) findViewById(R.id.emailEditTextCreateUser);
        departmanEditTextCreateUser = (EditText) findViewById(R.id.departmanEditTextCreateUser);
        statüEditTextCreateUser = (EditText) findViewById(R.id.statüEditTextCreateUser);
        sirketKeyEditTextCreateUser = (EditText) findViewById(R.id.sirketKeyEditTextCreateUser);
        sirketCreateGit = (TextView) findViewById(R.id.sirketCreateGit);
        //---------------
        userIdTextViewCreateUser = (TextView) findViewById(R.id.userIdTextViewCreateUser);
        kaydetButonCreateUser = (Button) findViewById(R.id.kaydetButonCreateUser);
        //------------

        userIdTextViewCreateUser.setText("Kullanıcı ID: " + userId);
        telefonNoCreateUser.setText(telefonNo);
        telefonNoCreateUser.isEnabled();
        //emailEditTextCreateUser.setText(token);
       // sirketCreateGit.setText(sirketKey.toString());
        sirketCreateGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateUserActivity.this, SirketCreateActivity.class);
                startActivity(intent);
            }
        });
        kaydetButonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bilgileriFirebaseKayıt();

            }
        });

    }

    public void bilgileriFirebaseKayıt() {

        referenceDt = databaseFb.getReference().child("UserModel").child(auth.getUid());
        Map map = new HashMap();
        map.put("profilFoto", "null");
        map.put("adSoyad", adsoyadCreateUser.getText().toString());
        map.put("telefonNo", telefonNoCreateUser.getText().toString());
        map.put("email", emailEditTextCreateUser.getText().toString());
        map.put("departman", departmanEditTextCreateUser.getText().toString());
        map.put("statü", statüEditTextCreateUser.getText().toString());
        map.put("sirketKey", sirketKeyEditTextCreateUser.getText().toString());
        referenceDt.setValue(map);
    /*    MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Bilgiler Kaydediliyor")
                .content("Lütfen Bekleyiniz")
                .progress(true, 0);

        process_dialog = builder.build();
        process_dialog.show();
       */
        Toast.makeText(this, "Bilgileriniz Başarıyla Kaydedildi!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    public void intentVeriAl() {
        Bundle intent = getIntent().getExtras();
        userId = intent.getString("userid");
        //userName=intent.getString("userid");
        telefonNo = intent.getString("telefonno");
        token = intent.getString("token");
       // sirketKey = intent.getString("sirketKey");
    }

    public void kontrolEt() {
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            //intent.putExtra("adSoyad",adsoyadCreateUser.toString());
            startActivity(intent);
            finish();
        }
    }
}
