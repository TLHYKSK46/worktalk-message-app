package com.example.worktalk.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worktalk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class KayitOlActivity extends AppCompatActivity {
   private FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private EditText register_sifre, register_email;
    private Button register_buton;
    private TextView hesapVarText;
    private Object KayitOlActivityy= com.example.worktalk.Activity.KayitOlActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_kayit_ol );
        tanimla();
    }

    public void tanimla() {
        register_sifre = (EditText) findViewById( R.id.register_sifre );
        register_email = (EditText) findViewById( R.id.register_email );
        register_buton = (Button) findViewById( R.id.register_buton );
        hesapVarText=(TextView)findViewById( R.id.hesapVarText );
        String token="worktalkdb";
        boolean b = true;
        auth = FirebaseAuth.getInstance();

        register_buton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = register_email.getText().toString();
                String parola = register_sifre.getText().toString();
                hesapVarText.setText( email );
                if(!email.equals( "" ) && !parola.equals( "" )) {
                    register_email.setText( "" );
                    register_sifre.setText( "" );
                    kayitOl( email, parola );

                }else{
                    Toast.makeText( getApplicationContext(), "Hata Oluştu! Bilgileri Kontrol Ediniz..", Toast.LENGTH_LONG ).show();
                }
            }
        } );
        hesapVarText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( KayitOlActivity.this, GirisActivity.class );
                startActivity( intent );
                finish();
            }
        } );
    }

    public void kayitOl(final String email, String parola) {

        auth.createUserWithEmailAndPassword( email, parola ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    boolean emailVerified = user.isEmailVerified();
                    String uid = user.getUid();
                    reference=firebaseDatabase.getReference().child( "UserModel" ).child( uid);
                    Map map=new HashMap();
                    map.put( "adsoyad","null" );
                    map.put( "fotoProfile","null" );
                    map.put( "email","null");
                    map.put( "telefon","null" );
                    map.put( "departman","null" );
                    map.put( "statü","null" );
                    reference.setValue( map );
                    Intent intent = new Intent( KayitOlActivity.this, MainActivity.class );
                    startActivity( intent );
                    finish();
                } else {
                    Toast.makeText( getApplicationContext(), "Kayıt Olurken Hata Oluştu!" ,Toast.LENGTH_LONG ).show();
                    task.addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("asdfa","asdf"+e+auth);
                        }
                    } );
                }
            }
        } );


    }
}
