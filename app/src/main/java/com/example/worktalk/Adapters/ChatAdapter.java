package com.example.worktalk.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktalk.Models.ChatMesajModel;
import com.example.worktalk.Models.UserModel;
import com.example.worktalk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    Context context;
    List<ChatMesajModel> listChatMesajModel;
    List<String> userKeyList;
    UserModel userModel;
    Activity activity;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference referenceDb;
    FirebaseAuth auth;
    FirebaseUser user;
    String userKey;
    ChatMesajModel chatMesajModel;
    boolean state;
    int view_send = 1, view_received = 2;

    public ChatAdapter(Context context, List<ChatMesajModel> listChatMesajModel, Activity activity, List<String> userKeyList) {
        this.context = context;
        this.listChatMesajModel = listChatMesajModel;
        this.activity = activity;
        this.userKeyList = userKeyList;
        state = false;
        firebaseDatabase = FirebaseDatabase.getInstance();
        referenceDb = firebaseDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userKey = user.getUid();
        listChatMesajModel = new ArrayList<>();
        userKeyList = new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == view_send) {

            view = LayoutInflater.from(context).inflate(R.layout.send_layout, parent, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.received_layout, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.mesajtextView.setText(listChatMesajModel.get(position).getMessageText().toString());

//        Glide.with(holder.sendFoto.getContext()).load(chatMesajModel.getFotoUrl()).into(holder.sendFoto);
        /*  if(!chatMesajModel.getFotoUrl().equals("null")){
              holder.sendFoto.setVisibility(View.VISIBLE);
              Picasso.get().load(chatMesajModel.getFotoUrl()).into(holder.sendFoto);
          }else {
              Log.i("asd","asd"+chatMesajModel.getFotoUrl());
          }*/

       /* referenceDb = firebaseDatabase.getReference().child("UserModel").child(holder.otherKey.getText().toString());
        referenceDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(userModel.getProfilFoto()).into(holder.chatUserFoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
        referenceDb.child("Mesajlar").child(userKeyList.get(position)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // userModel=dataSnapshot.getValue(UserModel.class);
                ChatMesajModel chatMesajModel = dataSnapshot.getValue(ChatMesajModel.class);

                //holder.dateSend.setText(dataSnapshot.child("time").getValue().toString());
//                Picasso.get().load(userModel.getProfilFoto()).into(holder.chatUserFoto);
                //  holder.dateSend.setText(GetDate.getTime());
                holder.dateSend.setText(listChatMesajModel.get(position).getTime());

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Picasso.get().load(userModel.getProfilFoto()).into(holder.chatUserFoto);

    }

    @Override
    public int getItemCount() {
        return listChatMesajModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mesajtextView;
        TextView dateSend;
        CircleImageView chatUserFoto;
        TextView otherKey;
        ImageView sendFoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chatUserFoto = (CircleImageView) itemView.findViewById(R.id.chatUserImage);
            otherKey = (TextView) itemView.findViewById(R.id.key);

            if (state == true) {
                mesajtextView = itemView.findViewById(R.id.send_textview);
                dateSend = itemView.findViewById(R.id.dateSend);
                sendFoto = itemView.findViewById(R.id.sendFoto);
            } else {
                mesajtextView = itemView.findViewById(R.id.received_textview);
                dateSend = itemView.findViewById(R.id.dateSend);
            }


        }

    }

    @Override
    public int getItemViewType(int position) {
        if (listChatMesajModel.get(position).getFrom().equals(userKey)) {

            state = true;
            return view_send;
        } else {
            state = false;
            return view_received;
        }

    }
}
