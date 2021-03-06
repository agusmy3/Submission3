package com.agus.submission3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agus.submission3.Adapter.UserAdapter;
import com.agus.submission3.Model.GetItemUser;
import com.agus.submission3.Rest.ApiClient;
import com.agus.submission3.Rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFollowing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFollowing extends Fragment {
    RecyclerView rv_following;
    List<GetItemUser> listUser = new ArrayList<>();
    UserAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    public FragmentFollowing() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentFollowing newInstance(String param1) {
        FragmentFollowing fragment = new FragmentFollowing();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<List<GetItemUser>> userCall = mApiInterface.getFollowingUser(mParam1);
            userCall.enqueue(new Callback<List<GetItemUser>>() {
                @Override
                public void onResponse(Call<List<GetItemUser>> call, Response<List<GetItemUser>> response) {
                    try {
                        if (response.isSuccessful() && response.body()!=null) {
                            listUser.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }
                    }catch (Exception e) {
                        Log.d("Exception", e.toString());
                    }
                }
                @Override
                public void onFailure(Call<List<GetItemUser>> call, Throwable t) {
                    Log.d("onFailure", t.toString());
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_following = view.findViewById(R.id.rv_following);
        adapter = new UserAdapter(listUser, this.getContext());
        rv_following.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_following.setItemAnimator(new DefaultItemAnimator());
        rv_following.setHasFixedSize(true);
        rv_following.setAdapter(adapter);
        rv_following.invalidate();
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickCallback(new UserAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(GetItemUser data) {
                String username = data.getLogin();
                Toast.makeText(getContext(),username,Toast.LENGTH_LONG).show();
            }

        });
    }
}