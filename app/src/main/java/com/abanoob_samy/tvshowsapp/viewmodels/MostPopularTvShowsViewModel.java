package com.abanoob_samy.tvshowsapp.viewmodels;

import com.abanoob_samy.tvshowsapp.repositories.MostPopularTVShowsRepository;
import com.abanoob_samy.tvshowsapp.response.TvShowsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MostPopularTvShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepository repository;

    public MostPopularTvShowsViewModel() {
        repository = new MostPopularTVShowsRepository();
    }

    public LiveData<TvShowsResponse> getTvShowsResponseLiveData(int page) {
        return repository.getTvShowsResponseLiveData(page);
    }
}
