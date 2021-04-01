package com.example.worktalk.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Adapters.ChatAdapter;
import com.example.worktalk.Models.ChatMesajModel;
import com.example.worktalk.Models.UserModel;
import com.example.worktalk.R;
import com.example.worktalk.Utils.GetDate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    String userName, otherName;
    String adsoyad, enSonMesaj;
    TextView chatUserName;
    ImageView backImage, sendImage;
    CircleImageView chatUserImage;
    EditText chatEditText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceDb;
    private StorageReference storageRef;
    private FirebaseStorage StorageFb;
    FirebaseAuth auth;
    FirebaseUser user;
    RecyclerView chatRcycView;
    ChatAdapter chatAdapter;
    List<ChatMesajModel> listChatMesaj;
    List<String> listKey;
    FloatingActionButton sendFloatActionButon, addFileButon;
    private static final int PICK_IMAGE_REQUEST = 1;
    UserModel userModel;
    Uri dowloandUrl;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            final Uri secilenResim = data.getData();
           // StorageReference resimRef = storageRef.child(secilenResim.getLastPathSegment());
            StorageReference resimRef = storageRef.child("ChatFotolar/").child(secilenResim.getLastPathSegment());
            resimRef.putFile(secilenResim).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dowloandUrl = taskSnapshot.getUploadSessionUri();

                    /*
        mesajGonder(user.getUid(), getOtherKey(), "text", GetDate.getTime(), false, "null",dowloandUrl.toString());
*/
String type="image";
                    referenceDb.push().setValue(new ChatMesajModel(user.getUid(),getOtherKey(),type,
                            GetDate.getDate(),"",dowloandUrl.toString(),false,user.getUid()));
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tanimla();
        loadMesaj();

    }

    public void tanimla() {

        chatUserName = (TextView) findViewById(R.id.chatUserName);
        backImage = (ImageView) findViewById(R.id.backImage);
        chatEditText = (EditText) findViewById(R.id.chatEditText);
        chatUserImage = (CircleImageView) findViewById(R.id.chatUserImage);
        sendFloatActionButon = (FloatingActionButton) findViewById(R.id.sendFloatActionButon);
        addFileButon = (FloatingActionButton) findViewById(R.id.addFileButon);
        chatUserName.setText(getUserName());
        // chatEditText.setText(getOtherKey());
        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceDb = firebaseDatabase.getReference();
        StorageFb = FirebaseStorage.getInstance();
        storageRef = StorageFb.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        //-------------------------------------------------------------------
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                //intent.putExtra( "kadi", userName );
                startActivity(intent);
            }
        });

        //mesaj gönderme işlemi
        chatUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, OtherProfileActivity.class);
                intent.putExtra("userName", getUserName());
                intent.putExtra("otherKey", getOtherKey());
                startActivity(intent);
            }
        });
        sendFloatActionButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //burası Mesaj gönderme alaanı
                final String mesaj = chatEditText.getText().toString();
                chatEditText.setText("");
                if (!mesaj.equals("")) {
                    mesajGonder(user.getUid(), getOtherKey(), "text", GetDate.getDate(), false, mesaj, "null");
                    sohbetListem(user.getUid(), getOtherKey(), GetDate.getDate(), false, mesaj);
                    enSonMesaj = mesaj.toString();
                }
            }

        });
        addFileButon.setOnClickListener(new View.OnClickListener() {
            //Burası File Gonder butona tıklanınca çıkan Popup 'un oluşturulduğu yer..
            @Override
            public void onClick(View v) {
                final View popupView = getLayoutInflater().inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                FloatingActionButton fotoGonderButon = (FloatingActionButton) popupView.findViewById(R.id.fotoGonderButon);
                //xml imizdeki button'ımız, popup ı kapatmaya yarayacak
                //----------------
                //popup konum ayarlaması
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                int location[] = new int[2];
                v.getLocationOnScreen(location);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                fotoGonderButon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GaleridenfotoSec();
                        /*firebaseDatabase = FirebaseDatabase.getInstance();
                        referenceDb = firebaseDatabase.getReference("Mesajlar/" );
                        StorageFb = FirebaseStorage.getInstance();
                        storageRef = StorageFb.getReference("chatFoto/");*/
//                        mesajGonder(user.getUid(), getOtherKey(), "text", GetDate.getTime(), false, "null",dowloandUrl.toString());

                    }
                });
            }
        });
//-----------------------------------------------------------------------
        listKey = new ArrayList<String>();
        listChatMesaj = new ArrayList<ChatMesajModel>();
        //Burası Mesajlaşma Ekranında Mesaşların Görüntülenme Alanı (gelen/giden)-----------------------------
        chatRcycView = (RecyclerView) findViewById(R.id.chatRcycView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChatActivity.this, 1);
        chatRcycView.setLayoutManager(layoutManager);
        chatAdapter = new ChatAdapter(ChatActivity.this, listChatMesaj, ChatActivity.this, listKey);
        chatRcycView.setAdapter(chatAdapter);
        //-------------------------------------------------------------------------------------------------------

    }


    public void GaleridenfotoSec() {
        Intent resimSec = new Intent(Intent.ACTION_PICK);
        resimSec.setType("image/*");
        startActivityForResult(resimSec, PICK_IMAGE_REQUEST);

    }

    public void sohbetListem(final String userKey, final String otherKey, String date, Boolean seen, String endmessageText) {
        //final String sohbetListemKey = referenceDb.child("SohbetListem").child(otherKey).push().getKey();
        //referenceDb = firebaseDatabase.getReference().child("SohbetListem").child(otherKey);
        final Map messageMap = new HashMap();
        messageMap.put("userKey", userKey);
        messageMap.put("otherKey", otherKey);
        messageMap.put("othername", getUserName());
        messageMap.put("seen", seen);
        messageMap.put("time", date);
        messageMap.put("messageText", endmessageText);
        messageMap.put("profilFoto", "null");
        // referenceDb.setValue(messageMap);
        referenceDb.child("SohbetListem").child(otherKey).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


            }
        });

    }

    public void mesajGonder(final String userKey, final String otherKey, String textType, String date, Boolean seen, String messageText, String fotoUrl) {
        //her Mesaj için key üretiyoruz bu kodla

        final String mesajKey = referenceDb.child("Mesajlar").child(userKey).child(otherKey).push().getKey().toString();
        final Map messageMap = new HashMap();
        messageMap.put("textType", textType);
        messageMap.put("seen", seen);
        messageMap.put("time", date);
        messageMap.put("messageText", messageText);
        messageMap.put("from", userKey);
        messageMap.put("othername", getUserName());
        messageMap.put("fotoUrl", fotoUrl);
        referenceDb.child("Mesajlar").child(userKey).child(otherKey).child(mesajKey).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    referenceDb.child("Mesajlar").child(otherKey).child(userKey).child(mesajKey).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            }

                        }
                    });
                }
            }
        });

    }

    public void loadMesaj() {
        referenceDb.child("Mesajlar").child(user.getUid()).child(getOtherKey()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatMesajModel chatMesajModel = dataSnapshot.getValue(ChatMesajModel.class);

                listChatMesaj.add(chatMesajModel);
                chatAdapter.notifyDataSetChanged();
                listKey.add(dataSnapshot.getKey());
                chatRcycView.scrollToPosition(listChatMesaj.size() - 1);
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


    public String getUserName() {
        //böylede veri alınır ıntentlerle
        Bundle bundle = getIntent().getExtras();
        userName = bundle.getString("otherName").toString();
        return userName;
    }

    public String getOtherKey() {
        //ıntentlerle böylede veri alabiliriz
        String otherKey = getIntent().getExtras().getString("otherKey").toString();
        return otherKey;
    }

}
