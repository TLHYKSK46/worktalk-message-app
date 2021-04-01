package com.example.worktalk.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Activity.ChatActivity;
import com.example.worktalk.Models.ChatMesajModel;
import com.example.worktalk.Models.UserModel;
import com.example.worktalk.Models.MesajlarımModel;
import com.example.worktalk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MesajlarimAdapter extends RecyclerView.Adapter<MesajlarimAdapter.ViewHolder> {

    Context context;
    List<MesajlarımModel> listMesajlarim;
    FragmentActivity activity;
    String adsoyad, enSonMesaj;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceDb;
    FirebaseAuth auth;
    FirebaseUser user;
    List<String> userKeyList;
    UserModel userModel;
    ChatMesajModel chatMesajModel;
    MesajlarımModel mesajlarımModel;
    private String otherKey;


    public MesajlarimAdapter(List<MesajlarımModel> listMesajlarim, FragmentActivity activity, Context context, List<String> userKeyList) {
        this.listMesajlarim = listMesajlarim;
        this.activity = activity;
        this.context = context;
        this.userKeyList = userKeyList;
        this.adsoyad = adsoyad;

        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceDb = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        listMesajlarim = new ArrayList<>();
        userKeyList = new ArrayList<>();

    }

    public MesajlarimAdapter(List<MesajlarımModel> listMesajlarim, Activity activity, Context context, List<MesajlarımModel> listUserkey) {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.mesaj_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

      /*  holder.userNameTextView.setText(listMesajlarim.get(position).getUserName());
        holder.enSonMesaj.setText(listMesajlarim.get(position).getEnSonMesaj());
        Picasso.get().load(listMesajlarim.get(position).getProfilFoto()).into(holder.userFotoCircle);
        holder.time.setText(listMesajlarim.get(position).getTime());*/
        // holder.userNameTextView.setText(userModel.getUserName().toString());
        // holder.userNameTextView.setText(userKeyList.get(position).toString());

        referenceDb.child("SohbetListem").child(userKeyList.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


             /*   mesajlarımModel =dataSnapshot.getValue(MesajlarımModel.class);
                Picasso.get().load(userModel.getProfilFoto()).into(holder.userFotoCircle);
                holder.userNameTextView.setText(userModel.getUserName());*/
                mesajlarımModel = dataSnapshot.getValue(MesajlarımModel.class);
                String userKey=dataSnapshot.child("userKey").getValue().toString();
                if (userKey.equals(user.getUid())) {
                    String adsoyad = dataSnapshot.child("othername").getValue().toString();
                    String enSonMesaj = dataSnapshot.child("messageText").getValue().toString();
                    if(enSonMesaj.length()>35){
                        holder.enSonMesaj.setText(enSonMesaj+"...");
                    }
                    holder.otherKey.setText(userKeyList.get(position));
                    holder.userNameTextView.setText(adsoyad);
                    holder.enSonMesaj.setText(enSonMesaj);
                    userModel = dataSnapshot.getValue( UserModel.class);

                }
                if (!dataSnapshot.child("profilFoto").getValue().toString().equals("null")) {

                    Picasso.get().load(dataSnapshot.child("profilFoto").getValue().toString()).into(holder.userFotoCircle);

                } else {

                }
                holder.time.setText(listMesajlarim.get(position).getTime());
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

        return listMesajlarim.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView, time, enSonMesaj,otherKey;
        LinearLayout userCardLayout;
        CircleImageView userFotoCircle;
        CircleImageView seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            otherKey=itemView.findViewById(R.id.otherKey);
            userNameTextView = (TextView) itemView.findViewById(R.id.userNameTextView);
            userCardLayout = (LinearLayout) itemView.findViewById(R.id.userCardLayout);
            userFotoCircle = (CircleImageView) itemView.findViewById(R.id.userFotoCircle);
            seen = (CircleImageView) itemView.findViewById(R.id.seen);
            time = (TextView) itemView.findViewById(R.id.time);
            enSonMesaj = (TextView) itemView.findViewById(R.id.enSonMesaj);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    intent.putExtra("otherName", userNameTextView.getText().toString());
                    intent.putExtra("otherKey", otherKey.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });

        }




    }
}
