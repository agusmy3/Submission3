package com.agus.submission3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.agus.submission3.Adapter.FavUserAdapter;
import com.agus.submission3.DB.FavUserHelper;
import com.agus.submission3.DB.MappingFavUserHelper;
import com.agus.submission3.Entity.FavUser;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteUserActivity extends AppCompatActivity implements LoadFavUserCallback {
    ProgressBar progressBar;
    FavUserAdapter adapter;
    RecyclerView rvFavUser;
    FavUserHelper favUserHelper;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_user);
        toolbar = findViewById(R.id.toolbar_favuser);
        toolbar.setTitle(R.string.favorite_activity);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        rvFavUser = findViewById(R.id.recycleView1);
        progressBar = findViewById(R.id.progresBar1);
        rvFavUser.setLayoutManager(new LinearLayoutManager(this));
        rvFavUser.setHasFixedSize(true);
        adapter = new FavUserAdapter(this);
        rvFavUser.setAdapter(adapter);
        favUserHelper = FavUserHelper.getInstance(getApplicationContext());
        favUserHelper.open();
        new LoadFavUserAsync(favUserHelper, this).execute();

        adapter.setOnItemClickCallback(new FavUserAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(FavUser data) {
                String username = data.getUsername();
                Intent toDetail = new Intent(FavoriteUserActivity.this, DetailUserActivity.class);
                toDetail.putExtra(DetailUserActivity.EXTRA_USER, username);
                startActivity(toDetail);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadFavUserAsync(favUserHelper, this).execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favUserHelper.close();
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<FavUser> favuser) {
        progressBar.setVisibility(View.INVISIBLE);
        if (favuser.size() > 0) {
            adapter.setListFavUser(favuser);
        } else {
            adapter.setListFavUser(new ArrayList<FavUser>());
            Toast.makeText(FavoriteUserActivity.this,R.string.not_found,Toast.LENGTH_LONG).show();
        }
    }

    private static class LoadFavUserAsync extends AsyncTask<Void, Void, ArrayList<FavUser>> {
        private final WeakReference<FavUserHelper> weakFavUserHelper;
        private final WeakReference<LoadFavUserCallback> weakCallback;

        private LoadFavUserAsync(FavUserHelper favuserHelper, LoadFavUserCallback callback) {
            weakFavUserHelper = new WeakReference<>(favuserHelper);
            weakCallback = new WeakReference<>(callback);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }
        @Override
        protected ArrayList<FavUser> doInBackground(Void... voids) {
            Cursor dataCursor = weakFavUserHelper.get().queryAll();
            return MappingFavUserHelper.mapCursorToArrayList(dataCursor);
        }
        @Override
        protected void onPostExecute(ArrayList<FavUser> favuser) {
            super.onPostExecute(favuser);
            weakCallback.get().postExecute(favuser);
        }
    }

}

interface LoadFavUserCallback {
    void preExecute();
    void postExecute(ArrayList<FavUser> favuser);
}