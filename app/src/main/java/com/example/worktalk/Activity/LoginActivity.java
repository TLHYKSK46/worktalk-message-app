package com.example.worktalk.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.worktalk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    MaterialDialog process_dialog;
    FirebaseDatabase firebaseDatabase;
    private EditText telefonNoText, gonderilenKodText;
    private Button koddogrulaButton, kodGonderButton;
    private TextView yenidenKodGonderTextview;
    private String uid;
    private Button sirketKeyBtn;
    private String ulkeKoduText;
    private String telefonDogrulamaId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken yeniToken;
    private FirebaseAuth auth;
    View view;
    Context context;
    String testtelefonno = "+901234567890";
    String testdogrulamakodu = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      /*  if ( !uid.equals("")){
            Intent intent= new Intent(LoginActivity.this,MainActivity.class);
       startActivity(intent);
        }*/
        tanimla();
    }


    public void tanimla() {
        sirketKeyBtn = (Button) findViewById(R.id.sirketKeyBtn);
        sirketKeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SirketKeyGirisActivity.class);
                intent.putExtra("sirketKey","");

                startActivity(intent);
            }
        });
        auth = FirebaseAuth.getInstance();
        telefonNoText = findViewById(R.id.telefonNoText);
        //telefonNoText.setText("1234567890");
        gonderilenKodText = findViewById(R.id.gonderilenKodText);
        // gonderilenKodText.setText("123456");
        koddogrulaButton = findViewById(R.id.koddogrulaButton);
        kodGonderButton = findViewById(R.id.kodGonderButton);
        yenidenKodGonderTextview = findViewById(R.id.yenidenKodGonderTextview);
        ulkeKoduText = "+90";
        final FirebaseAuth.AuthStateListener mAuthListener;

        kodGonderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (!isConnected()) {
                        // Toast.makeText(LoginActivity.this, "Network problem", Toast.LENGTH_SHORT).show();
                        Snackbar.make(view, "Sorry there was a problem with your network", Snackbar.LENGTH_LONG)
                                .setAction("Check Internet", null).show();
                    } else {

                        kodGonderButton(view);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        final EditText ulkeKoduEditText = findViewById(R.id.ulkeKoduText);
        ulkeKoduEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Ülke Seç");  // dialog title

                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        // Implement your code here
                        ulkeKoduEditText.setText(dialCode);
                        ulkeKoduText = dialCode;
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");

            }
        });

    }

    private void goToHome() {
        Intent intent = new Intent(LoginActivity.this, SirketKeyGirisActivity.class);
        intent.putExtra("telefonno", ulkeKoduText + telefonNoText.getText());
        intent.putExtra("userid", auth.getUid());
        intent.putExtra("token", yeniToken);
        //intent.putExtra("sirketKey","");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void kodGonderButton(View view) {
        yenidenKodGonderTextview.setVisibility(View.VISIBLE);
        gonderilenKodText.setVisibility(View.VISIBLE);
        koddogrulaButton.setVisibility(View.VISIBLE);
        String phoneNumber = ulkeKoduText + telefonNoText.getText().toString();
//burada test telefonno yerine normal telefon no yazacaksın unutma!!
        if (phoneNumber.length() > 7) {
            FirebaseAuthSettings firebaseAuthSettings = auth.getFirebaseAuthSettings();
            firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(testtelefonno, testdogrulamakodu);
            setDogrulamaCagrisiAyar();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    testtelefonno,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    verificationCallbacks);
            //auth . setLanguageCode ( "tr" );
            // Açıkça ayarlamak yerine, varsayılan uygulama dilini uygulamak için.
            auth.useAppLanguage();

            //Set processing indicators
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                    .title("Kod Gönderiliyor..")
                    .content("Lütfen Bekleyiniz")
                    .progress(true, 0);

            process_dialog = builder.build();
            process_dialog.show();
        } else {
            Toast.makeText(this, "Lütfen geçerli bir numara giriniz!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDogrulamaCagrisiAyar() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Log.i("smskod", "SMSkod:" + credential.getSmsCode() + "\nTelefon:" + testtelefonno);
                //   signoutButton.setEnabled(true);
                //   statusText.setText("Signed In");

                gonderilenKodText.setText("");
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(LoginActivity.this, "Geçersiz kimlik", Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // SMS quota exceeded
                    Toast.makeText(LoginActivity.this, "limitine ulaştı Bir kaç saat sonra tekrar dene!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String dogrulamaID, PhoneAuthProvider.ForceResendingToken token) {

                telefonDogrulamaId = dogrulamaID;
                yeniToken = token;
                koddogrulaButton.setVisibility(View.VISIBLE);
                gonderilenKodText.setVisibility(View.VISIBLE);
                kodGonderButton.setEnabled(false);
                yenidenKodGonderTextview.setVisibility(View.VISIBLE);

                process_dialog.dismiss();
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    gonderilenKodText.setText("");
                    goToHome();
                    FirebaseUser user = task.getResult().getUser();
                    uid = user.getUid();
                    process_dialog.dismiss();

                } else {
                    if (task.getException() instanceof
                            FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
        });
    }

    public void setYenidenKodGonderTextview(View view) {

        String telefonNo = telefonNoText.getText().toString();

        setDogrulamaCagrisiAyar();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                telefonNo,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                yeniToken);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Kod Yeniden Gönderiliyor")
                .content("Lütfen Bekleyiniz")
                .progress(true, 0);

        process_dialog = builder.build();
        process_dialog.show();
    }

    public boolean isConnected() throws InterruptedException, IOException {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
