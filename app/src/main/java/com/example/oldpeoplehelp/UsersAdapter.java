package com.example.oldpeoplehelp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class UsersAdapter extends ArrayAdapter implements View.OnClickListener {
    private Activity mContext;
    List<User> userList;

    public UsersAdapter(@NonNull Activity mContext, List<User> userList) {
        super(mContext,R.layout.user_item,userList);
        this.mContext=mContext;
        this.userList=userList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=mContext.getLayoutInflater();
        View listItemView= inflater.inflate(R.layout.user_item,parent,false);

        TextView fullname=listItemView.findViewById(R.id.fullname);
        //TextView email=listItemView.findViewById(R.id.hisEmail);

        fullname.setOnClickListener(this);
        User user=userList.get(position);


        fullname.setText(user.getFullname());
        //email.setText(user.getEmail());
        return listItemView;
    }



    @Override
    public void onClick(View v) {
        Intent intent=new Intent(mContext,MessageActivity.class);
        mContext.startActivity(intent);
    }

}
