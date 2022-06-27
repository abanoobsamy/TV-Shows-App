package com.abanoob_samy.tvshowsapp.response;

import com.abanoob_samy.tvshowsapp.models.TVShows;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowsResponse {

    private int page;

    @SerializedName("pages")
    private int total_pages;

    private List<TVShows> tv_shows;

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<TVShows> getTv_shows() {
        return tv_shows;
    }
}
