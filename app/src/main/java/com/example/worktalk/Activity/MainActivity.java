package com.example.worktalk.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Fragment.AramalarFragment;
import com.example.worktalk.Fragment.AyarlarFragment;
import com.example.worktalk.Fragment.DurumFragment;
import com.example.worktalk.Fragment.mesajlarFragment;
import com.example.worktalk.R;
import com.example.worktalk.Utils.ChangeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ChangeFragment changeFragment;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<String> list;
    String userName;
    RecyclerView mesajlarRecyView;

    FirebaseAuth auth;
    FirebaseUser user;
    String telefonNo;
    String userId,token;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_Mesajlar:
                 //  changeFragment = new ChangeFragment( MainActivity.this );
                    changeFragment.change( new mesajlarFragment() );
                    return true;
                case R.id.action_Aramalar:
                    changeFragment.change( new AramalarFragment() );
                    return true;
                case R.id.action_Durum:
                    changeFragment.change( new DurumFragment() );
                    return true;
                case R.id.action_Ayarlar:
                    changeFragment.change( new AyarlarFragment() );
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        try {
            intentVeriAl();
           tanımla();
           // kontrolEt();
        }catch(Exception e){

            Toast.makeText(this,"Hata!:"+auth.getCurrentUser(),Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Hata!:"+e.toString(),Toast.LENGTH_SHORT).show();
            Log.i("hata","mainhatası"+e.toString());

        }
        BottomNavigationView navView = findViewById( R.id.nav_view );
        changeFragment = new ChangeFragment( MainActivity.this );
        changeFragment.change( new mesajlarFragment() );
        navView.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener );
    }
    public void tanımla() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }
/*
    public void kontrolEt() {
        if (user != null) {
            Intent ıntent = new Intent( MainActivity.this, LoginActivity.class );
            startActivity( ıntent );
            finish();
        }
    }*/

    public  void intentVeriAl(){
        Bundle intent=getIntent().getExtras();
        //userId=intent.getString("userid");
        //userName=intent.getString("userid");
       // telefonNo=intent.getString("telefonno");
        //token=intent.getString("token");
    }



}
