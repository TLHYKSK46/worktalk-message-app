package com.example.worktalk.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.R;
import com.example.worktalk.Utils.GetDate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateDurumActivity extends AppCompatActivity {
      private String userName,zaman,konuBaslik,konuIcerik,profilFotoUrl,userKey;
    private View view;
    private FirebaseDatabase databaseFb;
    private DatabaseReference referenceDb;
    private FirebaseAuth auth;
    private FirebaseUser user;
    // private CallModel callModel;
    //  private List<CallModel> listCallModel;
    private List<String> listUserkey;

    private RecyclerView durumListLayoutRecyView;
    // private CallAdapter callAdapter;
    private Activity activity;
    EditText konuBaslikDurumText, konuIcerikDurumText;
    Button durumYayinlaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_durum);
        try {
            tanimla();

        } catch (Exception e) {
            Log.i("hata", "Hata:" + e );
        }

    }

    public void tanimla() {
        databaseFb = FirebaseDatabase.getInstance();
        referenceDb = databaseFb.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //listCallModel = new ArrayList<CallModel>();
        listUserkey = new ArrayList<String>();
        konuBaslikDurumText = (EditText) findViewById(R.id.konuBaslikDurumText);
        konuIcerikDurumText = (EditText) findViewById(R.id.konuIcerikDurumText);
        durumYayinlaBtn = (Button) findViewById(R.id.durumYayinlaBtn);
        yayinEkle();
    }
    public void yayinEkle() {
        durumYayinlaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = "Test Kullanıcı";
                zaman = GetDate.getDate();
                konuBaslik = konuBaslikDurumText.getText().toString();
                konuIcerik = konuIcerikDurumText.getText().toString();
                profilFotoUrl = "";
                userKey = user.getUid();
                durumEkle(userName, zaman, konuBaslik, konuIcerik, profilFotoUrl, userKey);
                konuIcerikDurumText.setText("");
                konuBaslikDurumText.setText("");
            }
        });
    }
    //    private String userName,zaman,konuBaslik,konuIcerik,profilFotoUrl,userKey;
    public void durumEkle(String userName, String zaman, String konuBaslik, String konuIcerik, String profilFotoUrl, String userKey) {
        final String durumKey = referenceDb.child("Durumlar").child(userKey).push().getKey().toString();
        referenceDb = databaseFb.getReference().child("Durumlar").child(auth.getUid()).child(durumKey);
        Map map = new HashMap();
        map.put("userName", userName);
        map.put("zaman",zaman);
        map.put("konuBaslik", konuBaslik);
        map.put("konuIcerik", konuIcerik);
        map.put("userKey", userKey);
        map.put("profilFotoUrl", profilFotoUrl);
        referenceDb.setValue(map);
         /*   MaterialDialog.Builder builder = new MaterialDialog.Builder(getApplicationContext())
                .title("Yayınlanıyor..")
                .content("Lütfen Bekleyiniz")
                .progress(true, 0);
        MaterialDialog process_dialog = builder.build();
        process_dialog.show();*/

        //Intent intent=new Intent(CreateDurumActivity.this,);
       // startActivity(intent);

    }

}



