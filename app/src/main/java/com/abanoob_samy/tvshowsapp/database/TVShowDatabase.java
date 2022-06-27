package com.abanoob_samy.tvshowsapp.database;

import android.content.Context;

import com.abanoob_samy.tvshowsapp.dao.TVShowDao;
import com.abanoob_samy.tvshowsapp.models.TVShows;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TVShows.class, version = 1, exportSchema = false)
public abstract class TVShowDatabase extends RoomDatabase {

    private static TVShowDatabase tvShowDatabase;

    public static synchronized TVShowDatabase getTvShowDatabase(Context context) {

        if (tvShowDatabase == null) {
            tvShowDatabase = Room.databaseBuilder(context, TVShowDatabase.class, "tv_show_db").build();
        }

        return tvShowDatabase;
    }

    public abstract TVShowDao getTvShowDao();
}
