package com.example.worktalk.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Activity.ChatActivity;
import com.example.worktalk.Models.UserModel;
import com.example.worktalk.R;
import com.example.worktalk.Utils.GetDate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

//KUllanıcıların listeleme işlemleri
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    static List<String> listUserKey;
    Activity activity;
    String userName;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase databaseFb;
    DatabaseReference referenceDR;
    private boolean state = false;
    int view_send = 1, view_received = 2;
    static UserModel userModel;


    public UserAdapter(Context context, List<String> listUserKey, Activity activity, String userName) {
        this.context = context;
        this.listUserKey = listUserKey;
        this.activity = activity;
        this.userName = userName;
        databaseFb = FirebaseDatabase.getInstance();
        referenceDR = databaseFb.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }


    @NonNull
    @Override //layout tanımlanması yapılacak
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false);


        return new ViewHolder(view);
    }

    @Override//viewlere setlemer yapılacak
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        // holder.userNameTextView.setText(listUserKey.get(position).toString());
        referenceDR.child("UserModel").child(listUserKey.get(position).toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                userModel = dataSnapshot.getValue( UserModel.class);
                String sirketKey = dataSnapshot.child("sirketKey").getValue().toString();
                // if (sirketKey.equals())
                Picasso.get().load( userModel.getProfilFoto()).into(holder.userFotoCircle);
                holder.otherNameTextView.setText( userModel.getAdSoyad());
                holder.otherKey.setText(listUserKey.get(position));
                userName = userModel.getAdSoyad();
                holder.fotoUrl = userModel.getProfilFoto();
                // burası call add kısmı-----------------------

                if (user.getUid().equals(listUserKey.get(position))) {

                    // holder.usertelefonNo = userModel.getUserTelefonNo();
                    holder.userName = dataSnapshot.child("adSoyad").getValue().toString();
                }
                holder.usertelefonNo = user.getPhoneNumber().toString();
                holder.otherCallKey = holder.otherKey.getText().toString();
                holder.userKey = user.getUid();
                holder.otherTelefonNo = userModel.getTelefonNo();
                holder.otherName = userModel.getAdSoyad();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*
        holder.userCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity.getApplication(), ChatActivity.class);
                //intent.putExtra("username",userName);
                //   intent.putExtra("othername", listUserKey.get(position).toString());
                activity.startActivity(intent);

            }
        });*/
    }

    @Override//adaptere oluşturulacak listenin boyutu(size)
    public int getItemCount() {
        return listUserKey.size();
    }


    //--------------------------------------------------------------------Class
    //viewleri tanımlanma işlemleri
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView otherNameTextView;
        LinearLayout userCardLayout;
        CircleImageView userFotoCircle;
        TextView otherKey;
        ImageButton callButonKisiler;
        FirebaseDatabase databaseFb;
        DatabaseReference referenceDt;
        FirebaseAuth auth;
        FirebaseUser user;
        String fotoUrl;


        String userName, otherName, otherCallKey, userKey, usertelefonNo, otherTelefonNo, zaman, aramaTipi;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            databaseFb = FirebaseDatabase.getInstance();
            referenceDt = databaseFb.getReference();
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            callButonKisiler = itemView.findViewById(R.id.callButonKisiler);
            otherNameTextView = (TextView) itemView.findViewById(R.id.userNameTextView);
            userFotoCircle = (CircleImageView) itemView.findViewById(R.id.userFotoCircle);
            userCardLayout = (LinearLayout) itemView.findViewById(R.id.userCardLayout);
            otherKey = (TextView) itemView.findViewById(R.id.key);

            itemView.setOnClickListener(this);

            callButonKisiler.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    aramaTipi = "Giden";
                    otherName = otherNameTextView.getText().toString();
                    otherCallKey = otherKey.getText().toString();
                    zaman = GetDate.getDate();
                    try{

                        Intent myIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+otherTelefonNo));
                        v.getContext().startActivity(myIntent);
                    }catch(Exception e){
                        e.printStackTrace();
                    }


                    final String callKey = referenceDt.child("SesliAramalar").child(userKey).child(otherCallKey).push().getKey().toString();
                    final Map callMap = new HashMap();
                    callMap.put("userKey", userKey.toString());
                    callMap.put("otherKey", otherCallKey.toString());
                    // callMap.put("userName", userName.toString());
                    callMap.put("userTelefonNo", usertelefonNo.toString());
                    callMap.put("otherName", otherName.toString());
                    callMap.put("otherTelefonNo", otherTelefonNo.toString());
                    callMap.put("zaman", zaman.toString());
                    callMap.put("aramaTipi", aramaTipi.toString());
                    callMap.put("profilFotoUrl", fotoUrl.toString());
                    referenceDt.child("SesliAramalar").child(userKey).child(otherCallKey).child(callKey).setValue(callMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });


                }
            });

        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            intent.putExtra("otherName", otherNameTextView.getText().toString());
            intent.putExtra("otherKey", otherKey.getText().toString());
            v.getContext().startActivity(intent);
        }
    }


}
