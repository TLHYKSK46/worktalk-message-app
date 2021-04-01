package com.example.worktalk.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Activity.ChatActivity;
import com.example.worktalk.Models.CallModel;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder> {

    Context context;
    List<CallModel> listCallModel;
    FragmentActivity activity;
    String adsoyad, enSonMesaj, telefonNo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceDb;
    FirebaseAuth auth;
    FirebaseUser user;
    List<String> userKeyList;
    UserModel userModel;
    CallModel callModel;


    private String otherKey;


    public CallAdapter(List<CallModel> listCallModel, FragmentActivity activity, Context context, List<String> userKeyList) {
        this.listCallModel = listCallModel;
        this.activity = activity;
        this.context = context;
        this.userKeyList = userKeyList;
        this.adsoyad = adsoyad;

        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceDb = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        listCallModel = new ArrayList<>();
        userKeyList = new ArrayList<>();

    }

    public CallAdapter(List<CallModel> listCallModel, Activity activity, Context context, List<String> listUserkey) {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.user_call_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CallModel callModel=listCallModel.get(position);

        if (!listCallModel.get(position).getOtherName().equals("")) {
            holder.adSoyadTextCall.setText(listCallModel.get(position).getOtherName());
            holder.aramaZamanTextCall.setText((listCallModel.get(position).getZaman()));
            holder.othertelefonNoTextCall.setText(listCallModel.get(position).getOtherTelefonNo());
            holder.otherKey.setText(listCallModel.get(position).getOtherKey());
            holder.userKey=listCallModel.get(position).getUserKey().toString();
            holder.userTelefoNoCall=listCallModel.get(position).getUserTelefonNo().toString();
            holder.fotoUrl=callModel.getProfilFoto();
            if(callModel.getAramaTipi().equals("Gelen")){
                /*Bitmap bitmap=new Bitmap("drawable");
                holder.aramaTypeImgCall.setImageBitmap("");*/
            }
        }
/*        if (!listCallModel.get(position).getProfilFoto().equals("")) {

            Picasso.get().load(listCallModel.get(position).getProfilFoto()).into(holder.userFotoCircleCall);

        }*/

        // referenceDb.child("SesliAramalar").child(userKeyList.get(position).toString()).
        //Log.i("call25", listCallModel.toString());

        referenceDb.child("SesliAramalar").child(userKeyList.get(position).toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                //  holder.adSoyadTextCall.setText(dataSnapshot.child("otherName").getValue().toString());
              /*  holder.adSoyadTextCall.setText(callModel.getUserName().toString());
                holder.aramaZamanTextCall.setText(callModel.getZaman());
                holder.otherKey.setText(callModel.getOtherKey().toString());*/

             /*   mesajlarımModel =dataSnapshot.getValue(MesajlarımModel.class);
                Picasso.get().load(userModel.getProfilFoto()).into(holder.userFotoCircle);
                holder.userNameTextView.setText(userModel.getUserName());*/
              /*  mesajlarımModel = dataSnapshot.getValue(MesajlarımModel.class);
                String userKey=dataSnapshot.child("userKey").getValue().toString();
                if (userKey.equals(user.getUid())) {
                    String adsoyad = dataSnapshot.child("othername").getValue().toString();
                    String enSonMesaj = dataSnapshot.child("messageText").getValue().toString();
                    holder.otherKey.setText(userKeyList.get(position));
                    holder.userNameTextView.setText(adsoyad);
                    holder.enSonMesaj.setText(enSonMesaj);
                    userModel = dataSnapshot.getValue(UserModel.class);

                }
          */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Picasso.get().load(mesajlarımModel.getSeen()).into(holder.seen);
      /*  holder.userCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ChangeFragment changeFragment = new ChangeFragment(context);
                //changeFragment.change(new mesajlarFragment());
                Intent intent = new Intent((FragmentActivity)activity, ChatActivity.class);
                intent.putExtra("username", adsoyad);
                intent.putExtra("othername", adsoyad);
                activity.startActivity(intent);

            }
        });*/
    }


    @Override
    public int getItemCount() {

        return userKeyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView adSoyadTextCall, aramaZamanTextCall, otherKey, key, othertelefonNoTextCall;
        LinearLayout callLayoutTıkla;
        CircleImageView userFotoCircleCall;
        ImageView aramaTypeImgCall;
        ImageButton callImgButon;
        String userKey,userTelefoNoCall,fotoUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            otherKey = itemView.findViewById(R.id.keyCall);
            callImgButon=itemView.findViewById(R.id.callImgButon);
            othertelefonNoTextCall = itemView.findViewById(R.id.telefonNoTextCall);
            adSoyadTextCall = itemView.findViewById(R.id.adSoyadTextCall);
            aramaZamanTextCall = itemView.findViewById(R.id.aramaZamanTextCall);
            callLayoutTıkla = itemView.findViewById(R.id.callLayoutTıkla);
            userFotoCircleCall = itemView.findViewById(R.id.userFotoCircleCall);
            aramaTypeImgCall = itemView.findViewById(R.id.aramaTypeImgCall);
            aramaZamanTextCall = itemView.findViewById(R.id.aramaZamanTextCall);
            callImgButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String aramaTipi = "Giden";
                   String otherName = adSoyadTextCall.getText().toString();
                   String otherCallKey = otherKey.getText().toString();
                  String  zaman = GetDate.getDate();
                    try{

                        Intent myIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+othertelefonNoTextCall.getText()));
                        v.getContext().startActivity(myIntent);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    final String callKey = referenceDb.child("SesliAramalar").child(userKey).child(otherCallKey).push().getKey().toString();
                    final Map callMap = new HashMap();
                    callMap.put("userKey", userKey.toString());
                    callMap.put("otherKey", otherCallKey.toString());
                    // callMap.put("userName", userName.toString());
                    callMap.put("userTelefonNo", userTelefoNoCall.toString());
                    callMap.put("otherName", otherName.toString());
                    callMap.put("otherTelefonNo", othertelefonNoTextCall.getText().toString());
                    callMap.put("zaman", zaman.toString());
                    callMap.put("aramaTipi", aramaTipi.toString());
                   callMap.put("profilFotoUrl", "");
                    referenceDb.child("SesliAramalar").child(userKey).child(otherCallKey).child(callKey).setValue(callMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    intent.putExtra("otherName", adSoyadTextCall.getText().toString());
                    intent.putExtra("otherKey", otherKey.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });

        }


    }
}
