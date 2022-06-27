package com.abanoob_samy.tvshowsapp.repositories;

import com.abanoob_samy.tvshowsapp.network.ApiServices;
import com.abanoob_samy.tvshowsapp.network.Credentials;
import com.abanoob_samy.tvshowsapp.response.TvShowsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsRepository {

    private ApiServices apiServices;

    public MostPopularTVShowsRepository() {
        apiServices = Credentials.getRetrofit().create(ApiServices.class);
    }

    public LiveData<TvShowsResponse> getTvShowsResponseLiveData(int page) {

        MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();

        apiServices.getTvShowsResponseCall(page).enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(Call<TvShowsResponse> call, Response<TvShowsResponse> response) {

                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TvShowsResponse> call, Throwable t) {

                data.setValue(null);
            }
        });

        return data;
    }
}
