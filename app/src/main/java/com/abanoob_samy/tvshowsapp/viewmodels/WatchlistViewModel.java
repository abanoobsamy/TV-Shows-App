package com.abanoob_samy.tvshowsapp.viewmodels;

import android.app.Application;

import com.abanoob_samy.tvshowsapp.database.TVShowDatabase;
import com.abanoob_samy.tvshowsapp.models.TVShows;
import com.abanoob_samy.tvshowsapp.repositories.MostPopularTVShowsRepository;
import com.abanoob_samy.tvshowsapp.repositories.TVShowsDetailsRepository;
import com.abanoob_samy.tvshowsapp.response.TvShowsDetailsResponse;
import com.abanoob_samy.tvshowsapp.response.TvShowsResponse;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {

    private TVShowDatabase tvShowDatabase;

    public WatchlistViewModel(Application application) {
        super(application);
        tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
    }

    public Flowable<List<TVShows>> loadWatchlist() {
        return tvShowDatabase.getTvShowDao().getWatchlist();
    }

    public Completable removeTVShowFromWatchlist(TVShows tvShows) {
        return tvShowDatabase.getTvShowDao().removeFromWatchlist(tvShows);
    }
}
