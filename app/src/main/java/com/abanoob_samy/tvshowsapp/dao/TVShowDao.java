package com.abanoob_samy.tvshowsapp.dao;

import com.abanoob_samy.tvshowsapp.models.TVShows;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TVShowDao {

    @Query("select * from tvshowsapp")
    Flowable<List<TVShows>> getWatchlist();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchlist(TVShows tvShows);

    @Delete
    Completable removeFromWatchlist(TVShows tvShows);

    @Query("SELECT * FROM tvshowsapp WHERE id = :tvShowsId")
    Flowable<TVShows> getTvShowsFromWatchlist(String tvShowsId);
}
