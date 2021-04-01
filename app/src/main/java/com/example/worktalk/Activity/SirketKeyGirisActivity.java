package com.example.worktalk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.worktalk.R;

public class SirketKeyGirisActivity extends AppCompatActivity {
    private Button sonrakiSirketKeyActivityButton, yeniSirketKeyButon;
    String userId, telefonNo, token, sirketKey;

    private EditText sirketKeyActivityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sirket_key_giris);
        tanimla();
        CreateSirketIntentAl();
        loginIntentAl();
    }

    public void tanimla() {
        sirketKeyActivityEditText = (EditText) findViewById(R.id.sirketKeyActivityEditText);
        sonrakiSirketKeyActivityButton = (Button) findViewById(R.id.sonrakiSirketKeyActivityButton);
        sonrakiSirketKeyActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CreateUserActivity sayfasına gidiyor Onay butonu
                // Burada Eğer Kullanıcının girdiği key doğruysa bir sonraki kullanıcı bilgilerinin istenen
                //activtye gidiyor
                Intent intent = new Intent(SirketKeyGirisActivity.this, CreateUserActivity.class);
                intent.putExtra("telefonno", telefonNo);
                intent.putExtra("userid", userId);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });
        yeniSirketKeyButon = (Button) findViewById(R.id.yeniSirketKeyButon);
        yeniSirketKeyButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SirketKeyGirisActivity.this, SirketCreateActivity.class);
                startActivity(intent);
            }
        });
    }

    public void CreateSirketIntentAl() {
        Bundle intent = getIntent().getExtras();
        sirketKeyActivityEditText.setText(intent.getString("sirketKey"));

//        userId = intent.getString("userid");
        //userName=intent.getString("userid");
        //telefonNo = intent.getString("telefonno");
        // token = intent.getString("token");
        // sirketKey = intent.getString("sirketKey");
    }

    public void loginIntentAl() {
        Bundle intent = getIntent().getExtras();
        userId = intent.getString("userid");
        //userName=intent.getString("userid");
        telefonNo = intent.getString("telefonno");
        token = intent.getString("token");
    }

}
