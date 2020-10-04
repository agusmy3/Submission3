package com.agus.submission3.ViewModel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.agus.submission3.Model.GetItemUser;
import com.agus.submission3.Model.GetSearchUser;
import com.agus.submission3.Rest.ApiClient;
import com.agus.submission3.Rest.ApiInterface;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<GetItemUser>> listUser = new MutableLiveData<>();

    public MutableLiveData<List<GetItemUser>> getListUser() {
        return listUser;
    }

    public void setListUser(String username) {
        ApiInterface mApiInterface;
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<GetSearchUser> userCall = mApiInterface.getUser(username);
        userCall.enqueue(new Callback<GetSearchUser>() {
            @Override
            public void onResponse(Call<GetSearchUser> call, Response<GetSearchUser> response) {
                try {
                    if (response.isSuccessful() && response.body()!=null) {
                        listUser.postValue(response.body().getItems());
                    }
                }catch (Exception e) {
                    Log.d("Exception", e.toString());
                }
            }
            @Override
            public void onFailure(Call<GetSearchUser> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
}
