package com.agus.submission3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agus.submission3.Model.GetItemUser;
import com.agus.submission3.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ListViewHolder> {
    private List<GetItemUser> listUser;
    private OnItemClickCallback onItemClickCallback;
    Context context;

    public UserAdapter(List <GetItemUser> UserList, Context context) {
        this.listUser = UserList;
        this.context = context;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        GetItemUser getItemUser = listUser.get(position);
        Glide.with(holder.itemView.getContext())
                .load(getItemUser.getAvatarUrl())
                .into(holder.imgUser);
        holder.tvName.setText(getItemUser.getLogin());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listUser.get(holder.getAdapterPosition()));
            }

        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public OnItemClickCallback getOnItemClickCallback() {
        return onItemClickCallback;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUser;
        TextView tvName;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.name_user);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(GetItemUser data);
    }
}
