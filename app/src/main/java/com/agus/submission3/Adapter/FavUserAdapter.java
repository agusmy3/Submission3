package com.agus.submission3.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agus.submission3.Entity.FavUser;
import com.agus.submission3.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavUserAdapter extends RecyclerView.Adapter<FavUserAdapter.ListViewHolder> {

    private final ArrayList<FavUser> listFavUser = new ArrayList<>();
    private final Activity activity;
    private OnItemClickCallback onItemClickCallback;

    public FavUserAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<FavUser> getListFavUser() {
        return listFavUser;
    }

    public void setListFavUser(ArrayList<FavUser> listFavUser) {
        this.listFavUser.clear();
        if (listFavUser.size() > 0) {
            this.listFavUser.addAll(listFavUser);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavUserAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        FavUser favUser = listFavUser.get(position);
        holder.tvName.setText(favUser.getName());
        Glide.with(holder.itemView.getContext())
                .load(favUser.getAvatar_url())
                .into(holder.imgUser);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listFavUser.get(holder.getAdapterPosition()));
            }

        });

    }

    @Override
    public int getItemCount() {
        return listFavUser.size();
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

    public void setOnItemClickCallback(FavUserAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public OnItemClickCallback getOnItemClickCallback() {
        return onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(FavUser data);
    }
}
