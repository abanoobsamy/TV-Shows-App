package com.abanoob_samy.tvshowsapp.listeners;

import com.abanoob_samy.tvshowsapp.models.TVShows;

public interface WatchlistListener {

    void onTVShowClicked(TVShows tvShows);

    void removeTVShowFromWatchlist(TVShows tvShows, int position);

}
