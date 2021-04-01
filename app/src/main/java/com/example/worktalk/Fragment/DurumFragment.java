package com.example.worktalk.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Adapters.DurumAdapter;
import com.example.worktalk.Models.DurumModel;
import com.example.worktalk.R;
import com.example.worktalk.Utils.GetDate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DurumFragment extends Fragment {
    private String userName, zaman, konuBaslik, konuIcerik, profilFotoUrl, userKey;
    FloatingActionButton addDurumfabBtn;
    private View view;
    private FirebaseDatabase databaseFb;
    private DatabaseReference referenceDb;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DurumModel durumModel;
    private List<DurumModel> listDurumModel;
    private List<String> listUserkey;
    private DurumAdapter durumAdapter;
    private RecyclerView durumListLayoutRecyView;

    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_durum, container, false);
        tanimla();
        addDurumfabBtnFonksiyon();
        durumlariListele();
        return view;
    }

    public void tanimla() {
        databaseFb = FirebaseDatabase.getInstance();
        referenceDb = databaseFb.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        listDurumModel = new ArrayList<DurumModel>();
        listUserkey = new ArrayList<String>();
        addDurumfabBtn = (FloatingActionButton) view.findViewById(R.id.addDurumfabBtn);
        durumListLayoutRecyView = view.findViewById(R.id.durumListLayoutRecyView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        durumListLayoutRecyView.setLayoutManager(layoutManager);
        durumAdapter = new DurumAdapter(listDurumModel, listUserkey, getActivity(), getContext());
        durumListLayoutRecyView.setAdapter(durumAdapter);
    }

    public void durumlariListele() {
        referenceDb.child("Durumlar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String usersKeyler = dataSnapshot.getKey();
                referenceDb.child("Durumlar").child(usersKeyler).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        DurumModel durumModel = dataSnapshot.getValue(DurumModel.class);
                        listUserkey.add(dataSnapshot.getKey());
                        durumAdapter.notifyDataSetChanged();
                        listDurumModel.add(durumModel);
                        durumListLayoutRecyView.scrollToPosition(listDurumModel.size() - 1);
                        //  Log.i("keyler","keyler"+durumModel);

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addDurumfabBtnFonksiyon() {
        FloatingActionButton callAdd_fabButon = (FloatingActionButton) view.findViewById(R.id.callAdd_fabButon);
        addDurumfabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View popupView = getLayoutInflater().inflate(R.layout.durumekle_popup, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                Button durumYayinlaBtn = (Button) popupView.findViewById(R.id.durumYayinlaBtn);
                ImageButton popupKapat = (ImageButton) popupView.findViewById(R.id.popupKapat);
                final EditText konuBaslikDurumText = (EditText) popupView.findViewById(R.id.konuBaslikDurumText);
                final EditText konuIcerikDurumText = (EditText) popupView.findViewById(R.id.konuIcerikDurumText);
                //xml imizdeki button'ımız, popup ı kapatmaya yarayacak
                //----------------------------------------------------------------------------------
                //popup konum ayarlaması
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                int location[] = new int[2];
                v.getLocationOnScreen(location);
                popupWindow.showAtLocation(v, Gravity.CENTER, 10, 0);
                durumYayinlaBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // userNameAl();
                       userName="Test Kullanıcı";
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
                popupKapat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });
    }

    public void durumEkle(String userName, String zaman, String konuBaslik, String konuIcerik, String profilFotoUrl, String userKey) {
        final String durumKey = referenceDb.child("Durumlar").child(userKey).push().getKey().toString();
        referenceDb = databaseFb.getReference().child("Durumlar").child(auth.getUid()).child(durumKey);
        Map map = new HashMap();
        map.put("userName", userName);
        map.put("zaman", zaman);
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

    public String userNameAl() {
        final String[] userNames = new String[1];
        referenceDb.child("UserModel").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (!dataSnapshot.getKey().equals(user.getUid())) {
                    referenceDb.child("UserModel").child(dataSnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           // UserModel userModel = dataSnapshot.getValue(UserModel.class);

                             userNames[0] = dataSnapshot.child("userName").getValue().toString();
                            //userName = userModel.getAdSoyad();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            //Log.i("Kullanıcı:",dataSnapshot.getKey());

            //}

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
return userNames[0];
    }

}

