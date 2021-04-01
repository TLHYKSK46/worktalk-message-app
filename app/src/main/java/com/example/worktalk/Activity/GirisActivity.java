package com.example.worktalk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worktalk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class GirisActivity extends AppCompatActivity {
    /*EditText kullaniciadiEditText;
    Button KayitOlButton;
    */
    private EditText login_mail, login_sifre;
    private Button girisYapButton;
    private TextView kayıtOlText;

    private  FirebaseDatabase firebaseDatabase;

    private  FirebaseAuth auth;
    private  FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_giris );
        tanimla();

    }

    public void tanimla() {
        login_mail = (EditText) findViewById( R.id.login_mail );
        login_sifre = (EditText) findViewById( R.id.login_sifre );
        girisYapButton = (Button) findViewById( R.id.girisYapButton );
        kayıtOlText = (TextView) findViewById( R.id.kayıtOlText );
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        girisYapButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_mail.getText().toString();
                String parola = login_sifre.getText().toString();
                /*String email = "asd@asd.com";
                String parola = "123456";
                */
                if (!email.equals("") && !parola.equals("")) {
                    sistemeGiris( email, parola );

                } else {
                    Toast.makeText( getApplicationContext(), "Lütfen Boşlukları Doldurunuz!", Toast.LENGTH_LONG ).show();
                }
            }
        } );
        kayıtOlText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( GirisActivity.this, KayitOlActivity.class );
                startActivity( intent );
                finish();
            }
        } );
    }

    public void sistemeGiris(final String email, String parola) {
        auth.signInWithEmailAndPassword( email, parola ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent( GirisActivity.this, MainActivity.class );
                    startActivity( intent );
                    finish();
                } else {


                    Toast.makeText( getApplicationContext(), "Hatalı Bilgi Girdiniz!",Toast.LENGTH_LONG ).show();
                    //bu kısım silinmesi gerekyor hatadan dolayı ekledim....
                    Intent intent = new Intent( GirisActivity.this, MainActivity.class );
                    startActivity( intent );
                    //finish();
                }
            }
        } );
    }

    /* public void tanimla() {
         kullaniciadiEditText = (EditText) findViewById(R.id.kullaniciadiEditText);
         KayitOlButton = (Button) findViewById(R.id.KayitOlButton);
         firebaseDatabase = FirebaseDatabase.getInstance();
         reference = firebaseDatabase.getReference();
         KayitOlButton.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View view) {
                 String username = kullaniciadiEditText.getText().toString();
                 kullaniciadiEditText.setText("");
                 ekle(username);
             }
         });
     }

     public void ekle(final String kadi) {
       // String key= reference.child( "Kullanıcılar" ).child( "kadi" ).push().getKey();
         reference.child("Kullanıcılar").child(kadi).child("kullaniciadi").setValue(kadi).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()) {
                     Toast.makeText(getApplicationContext(), "Giriş Başarılı!", Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(GirisActivity.this, MainActivity.class);
                     intent.putExtra("kadi",kadi);
                     startActivity(intent);
                 }
             }
         });
     }
 */


}
