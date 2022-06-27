package com.abanoob_samy.tvshowsapp.response;

import com.abanoob_samy.tvshowsapp.models.TVShowsDetails;
import com.google.gson.annotations.SerializedName;

public class TvShowsDetailsResponse {

    @SerializedName("tvShow")
    private TVShowsDetails tvShowsDetails;

    public TVShowsDetails getTvShow() {
        return tvShowsDetails;
    }
}
