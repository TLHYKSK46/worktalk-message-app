package com.example.worktalk.Adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Models.DurumModel;
import com.example.worktalk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DurumAdapter extends RecyclerView.Adapter<DurumAdapter.ViewHolder> {
    Context context;
    List<DurumModel> listDurumModel;
    FragmentActivity activity;
    // String userName;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceDb;
    FirebaseAuth auth;
    FirebaseUser user;
    List<String> userKeyList;

    public DurumAdapter(List<DurumModel> listDurumModel, List<String> userKeyList, FragmentActivity activity, Context context) {
        this.listDurumModel = listDurumModel;
        this.activity = activity;
        this.context = context;
        this.userKeyList = userKeyList;
        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceDb = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        listDurumModel = new ArrayList<>();
        userKeyList = new ArrayList<>();

    }

    public DurumAdapter(List<DurumModel> listDurumModel, Activity activity, Context context, List<String> listUserkey) {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.durum_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        DurumModel durumModel = listDurumModel.get(position);
        holder.userKey = durumModel.getUserKey().toString();
        holder.durumKey=userKeyList.get(position);
        holder.userNameDurumText.setText(durumModel.getUserName() + "(" + getItemCount() + ")");
        holder.konuBaslikTextDurum.setText("Konu:" + durumModel.getKonuBaslik());
        holder.icerikTextDurum.setText(durumModel.getKonuIcerik());
        holder.zamanTextDurum.setText(durumModel.getZaman());
        if (!durumModel.getProfilFotoUrl().equals("")) {
            Picasso.get().load(durumModel.getProfilFotoUrl()).into(holder.userFotoCircle);
        }
    }


    @Override
    public int getItemCount() {

        return userKeyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView konuBaslikTextDurum, zamanTextDurum, userNameDurumText, icerikTextDurum;
        String userKey,durumKey;
        CircleImageView userFotoCircle;
        int detayVisibilityKontrol = 0;
        ImageButton detayImgDurum, durumSilImgBtn;
        ImageView arrowtop;

        @SuppressLint("ResourceType")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            konuBaslikTextDurum = itemView.findViewById(R.id.konuBaslikTextDurum);
            zamanTextDurum = itemView.findViewById(R.id.zamanTextDurum);
            userNameDurumText = itemView.findViewById(R.id.userNameDurumText);
            icerikTextDurum = itemView.findViewById(R.id.icerikTextDurum);
            durumSilImgBtn = itemView.findViewById(R.id.durumSilImgBtn);
            detayImgDurum = itemView.findViewById(R.id.detayImgDurum);
            //arrowtop=itemView.findViewById(R.drawable.arrow_back18dp);
            detayImgDurum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (detayVisibilityKontrol == 1) {
                        icerikTextDurum.setVisibility(View.GONE);
                        durumSilImgBtn.setVisibility(View.GONE);
                        detayVisibilityKontrol = 0;
                    } else if (detayVisibilityKontrol == 0) {
                        icerikTextDurum.setVisibility(View.VISIBLE);
                        durumSilImgBtn.setVisibility(View.VISIBLE);
                       // detayImgDurum.setImageDrawable(arrowtop.getDrawable());
                        detayVisibilityKontrol = 1;
                        //Buraya tekrar Bir Bak Düzenle
                        if (user.getUid().equals(userKey)){
                         //   referenceDb.child("Durumlar").child(user.getUid()).child(durumKey).removeValue();
                        }
                    }
                }
            });
            durumSilImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
          /*  itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    intent.putExtra("otherName", userNameDurumText.getText().toString());
                   // intent.putExtra("otherKey", u.toString());
                    v.getContext().startActivity(intent);
                }
            });*/

        }


    }
}
