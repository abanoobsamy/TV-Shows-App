package com.abanoob_samy.tvshowsapp.repositories;

import com.abanoob_samy.tvshowsapp.network.ApiServices;
import com.abanoob_samy.tvshowsapp.network.Credentials;
import com.abanoob_samy.tvshowsapp.response.TvShowsDetailsResponse;
import com.abanoob_samy.tvshowsapp.response.TvShowsResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTVShowsRepository {

    private ApiServices apiServices;

    public SearchTVShowsRepository() {
        apiServices = Credentials.getRetrofit().create(ApiServices.class);
    }

    public LiveData<TvShowsResponse> getSearchShowsResponseCall(String query, int page) {

        MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();

        apiServices.getSearchShowsResponseCall(query, page).enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowsResponse> call,@NonNull Response<TvShowsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowsResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
