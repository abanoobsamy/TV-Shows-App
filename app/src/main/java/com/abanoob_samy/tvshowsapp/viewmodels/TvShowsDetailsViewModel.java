package com.abanoob_samy.tvshowsapp.viewmodels;

import android.app.Application;

import com.abanoob_samy.tvshowsapp.dao.TVShowDao;
import com.abanoob_samy.tvshowsapp.database.TVShowDatabase;
import com.abanoob_samy.tvshowsapp.models.TVShows;
import com.abanoob_samy.tvshowsapp.repositories.TVShowsDetailsRepository;
import com.abanoob_samy.tvshowsapp.response.TvShowsDetailsResponse;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TvShowsDetailsViewModel extends AndroidViewModel {

    private TVShowsDetailsRepository repository;

    private TVShowDatabase tvShowDatabase;

    public TvShowsDetailsViewModel(Application application) {
        super(application);
        repository = new TVShowsDetailsRepository();
        tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
    }

    public LiveData<TvShowsDetailsResponse> getTvShowsDetailsLiveData(String tvShowId) {
        return repository.getTvShowsDetailsRepositoryLiveData(tvShowId);
    }

    public Completable addToWatchlist(TVShows tvShows) {
        return tvShowDatabase.getTvShowDao().addToWatchlist(tvShows);
    }

    public Flowable<TVShows> getTvShowsFromWatchlist(String tvShowsId) {
        return tvShowDatabase.getTvShowDao().getTvShowsFromWatchlist(tvShowsId);
    }

    public Completable removeTVShowFromWatchlist(TVShows tvShows) {
        return tvShowDatabase.getTvShowDao().removeFromWatchlist(tvShows);
    }
}
