package com.abanoob_samy.tvshowsapp.viewmodels;

import com.abanoob_samy.tvshowsapp.database.TVShowDatabase;
import com.abanoob_samy.tvshowsapp.repositories.SearchTVShowsRepository;
import com.abanoob_samy.tvshowsapp.response.TvShowsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchTvShowsViewModel extends ViewModel {

    private SearchTVShowsRepository repository;

    private TVShowDatabase tvShowDatabase;

    public SearchTvShowsViewModel() {
        repository = new SearchTVShowsRepository();
    }

    public LiveData<TvShowsResponse> getSearchShowsResponseCall(String query, int page) {
        return repository.getSearchShowsResponseCall(query, page);
    }
}
