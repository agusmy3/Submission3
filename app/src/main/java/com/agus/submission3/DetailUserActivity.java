package com.agus.submission3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agus.submission3.Adapter.SectionsPagerAdapter;
import com.agus.submission3.DB.FavUserHelper;
import com.agus.submission3.Entity.FavUser;
import com.agus.submission3.Model.GetSingleUser;
import com.agus.submission3.Rest.ApiClient;
import com.agus.submission3.Rest.ApiInterface;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.agus.submission3.DB.FavUserContract.FavUserColumns.AVATAR_URL;
import static com.agus.submission3.DB.FavUserContract.FavUserColumns.COMPANY;
import static com.agus.submission3.DB.FavUserContract.FavUserColumns.FOLLOWERS;
import static com.agus.submission3.DB.FavUserContract.FavUserColumns.FOLLOWING;
import static com.agus.submission3.DB.FavUserContract.FavUserColumns.NAME;
import static com.agus.submission3.DB.FavUserContract.FavUserColumns.USERNAME;

public class DetailUserActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "extra_user";
    TextView tv_name, tv_following, tv_followers, tv_repository, tv_company, tv_location;
    ImageView img_user;
    FloatingActionButton FAB;
    Toolbar toolbar;
    ApiInterface mApiInterface;
    String username, avatar_url, name, company;
    int following, follower;
    FavUserHelper favUserHelper;
    FavUser favUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setTitle(R.string.detail_activity);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        tv_name = findViewById(R.id.name_user);
        tv_followers = findViewById(R.id.int_followers_user);
        tv_following = findViewById(R.id.int_following_user);
        tv_repository = findViewById(R.id.int_repository_user);
        tv_company = findViewById(R.id.company);
        tv_location = findViewById(R.id.location);
        img_user = findViewById(R.id.img_user);
        FAB = findViewById(R.id.fab1);
        username = getIntent().getStringExtra(EXTRA_USER);

        Call<GetSingleUser> userCall = mApiInterface.getDetailUser(username);
        userCall.enqueue(new Callback<GetSingleUser>() {
            @Override
            public void onResponse(Call<GetSingleUser> call, Response<GetSingleUser> response) {
                try {
                    if (response.isSuccessful() && response.body()!=null) {
                        Toast.makeText(DetailUserActivity.this,getString(R.string.selected) +" : "+ response.body().getLogin(),Toast.LENGTH_LONG).show();
                        tv_name.setText(response.body().getName());
                        tv_following.setText(String.valueOf(response.body().getFollowing()));
                        tv_followers.setText(String.valueOf(response.body().getFollowers()));
                        tv_repository.setText(String.valueOf(response.body().getPublicRepos()));
                        tv_company.setText(response.body().getCompany());
                        tv_location.setText(response.body().getLocation());
                        Glide.with(DetailUserActivity.this)
                                .load(response.body().getAvatarUrl())
                                .into(img_user);

                        username = response.body().getLogin();
                        avatar_url = response.body().getAvatarUrl();
                        name = response.body().getName();
                        company = response.body().getCompany();
                        following = response.body().getFollowing();
                        follower = response.body().getFollowers();
                    }else{
                        Toast.makeText(DetailUserActivity.this,getString(R.string.selected_error)+" : "+response.errorBody(),Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Log.d("Exception", e.toString());
                }

            }
            @Override
            public void onFailure(Call<GetSingleUser> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), username);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        favUserHelper = FavUserHelper.getInstance(getApplicationContext());
        favUserHelper.open();
        favUser = new FavUser();
        favUser.setUsername(username);
        favUser.setName(name);
        favUser.setAvatar_url(avatar_url);
        favUser.setCompany(company);
        favUser.setFollowers(follower);
        favUser.setFollowing(following);

        if(cekData(username) > 0){
            setFABState(true);
        }else{
            setFABState(false);
        }

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cekData(username) > 0){
                    long result = favUserHelper.deleteByUsername(username);
                    if (result > 0) {
                        setFABState(false);
                        Toast.makeText(DetailUserActivity.this, R.string.deleteFromFav_succ, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailUserActivity.this, R.string.deleteFromFav_fail, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    ContentValues values = new ContentValues();

                    if(name==null){
                        name="";
                    }

                    if(company==null){
                        company="";
                    }

                    values.put(USERNAME, username);
                    values.put(NAME, name);
                    values.put(AVATAR_URL, avatar_url);
                    values.put(COMPANY, company);
                    values.put(String.valueOf(FOLLOWERS), follower);
                    values.put(String.valueOf(FOLLOWING), following);
                    long result = favUserHelper.insert(values);
                    if (result > 0) {
                        setFABState(true);
                        favUser.setId((int) result);
                        Toast.makeText(DetailUserActivity.this, R.string.saveToFav_succ, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailUserActivity.this, R.string.saveToFav_fail, Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    private void setFABState(boolean state){
        if(state){
            FAB.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else{
            FAB.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }

    private int cekData(String username){
        Cursor cursor = favUserHelper.queryByUsername(username);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favUserHelper.close();
    }

}