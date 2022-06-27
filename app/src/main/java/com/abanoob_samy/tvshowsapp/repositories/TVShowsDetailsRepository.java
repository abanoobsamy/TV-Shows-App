package com.abanoob_samy.tvshowsapp.repositories;

import com.abanoob_samy.tvshowsapp.network.ApiServices;
import com.abanoob_samy.tvshowsapp.network.Credentials;
import com.abanoob_samy.tvshowsapp.response.TvShowsDetailsResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowsDetailsRepository {

    private ApiServices apiServices;

    public TVShowsDetailsRepository() {
        apiServices = Credentials.getRetrofit().create(ApiServices.class);
    }

    public LiveData<TvShowsDetailsResponse> getTvShowsDetailsRepositoryLiveData(String tvShowId) {

        MutableLiveData<TvShowsDetailsResponse> data = new MutableLiveData<>();

        apiServices.getTvShowsDetailsResponseCall(tvShowId).enqueue(new Callback<TvShowsDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowsDetailsResponse> call,@NonNull Response<TvShowsDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowsDetailsResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
