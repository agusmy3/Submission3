package com.agus.submission3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.agus.submission3.Adapter.UserAdapter;
import com.agus.submission3.Model.GetItemUser;
import com.agus.submission3.Rest.ApiClient;
import com.agus.submission3.Rest.ApiInterface;
import com.agus.submission3.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;

    ApiInterface mApiInterface;
    List<GetItemUser> listUser = new ArrayList<>();
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.search_activity);

        RecyclerView rvUser = findViewById(R.id.recycleView1);
        progressBar = findViewById(R.id.progresBar1);
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        adapter = new UserAdapter(listUser, MainActivity.this);
        rvUser.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvUser.setItemAnimator(new DefaultItemAnimator());
        rvUser.setHasFixedSize(true);
        rvUser.setAdapter(adapter);
        rvUser.invalidate();
        imgState(true);

        mainViewModel.getListUser().observe(this, new Observer<List<GetItemUser>>() {
            @Override
            public void onChanged(List<GetItemUser> userList) {
                if (userList != null && userList.size()>0) {
                    listUser.addAll(userList);
                    imgState(false);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,getString(R.string.found)+" : "+listUser.size(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,getString(R.string.not_found),Toast.LENGTH_LONG).show();
                }
                showLoading(false);
            }
        });

        adapter.setOnItemClickCallback(new UserAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(GetItemUser data) {
                String username = data.getLogin();
                Intent toDetail = new Intent(MainActivity.this, DetailUserActivity.class);
                toDetail.putExtra(DetailUserActivity.EXTRA_USER, username);
                startActivity(toDetail);
            }

        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView sV = findViewById(R.id.searching);
            sV.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            sV.setQueryHint(getResources().getString(R.string.search_hint));
            sV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    imgState(false);
                    if(checkInternet()){
                        showLoading(true);
                        mainViewModel.setListUser(query);
                    }else{
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle(R.string.warning);
                        alertDialogBuilder
                                .setMessage(R.string.notConect)
                                .setCancelable(false)
                                .setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    listUser.clear();
                    imgState(true);
                    adapter.notifyDataSetChanged();
                    return false;
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            Intent toFavUser = new Intent(MainActivity.this,FavoriteUserActivity.class);
            startActivity(toFavUser);
        }else if(item.getItemId() == R.id.setting){
            Intent toSetting = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(toSetting);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void imgState(Boolean state){
        LinearLayout ll_main = findViewById(R.id.ll_main);
        if (state) {
            ll_main.setVisibility(View.VISIBLE);
        } else {
            ll_main.setVisibility(View.GONE);
        }
    }

    public boolean checkInternet(){
        boolean connectStatus;
        ConnectivityManager ConnectionManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        connectStatus = networkInfo != null && networkInfo.isConnected();
        return connectStatus;
    }

}