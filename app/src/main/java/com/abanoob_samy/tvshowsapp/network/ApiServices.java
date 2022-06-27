package com.abanoob_samy.tvshowsapp.network;

import com.abanoob_samy.tvshowsapp.response.TvShowsDetailsResponse;
import com.abanoob_samy.tvshowsapp.response.TvShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    //Example: https://www.episodate.com/api/most-popular?page=1

    @GET("most-popular")
    Call<TvShowsResponse> getTvShowsResponseCall(@Query("page") int page);

    //Example: https://www.episodate.com/api/show-details?q=29560

    @GET("show-details")
    Call<TvShowsDetailsResponse> getTvShowsDetailsResponseCall(@Query("q") String tvShowId);

    //Example: https://www.episodate.com/api/search?q=arrow&page=1

    @GET("search")
    Call<TvShowsResponse> getSearchShowsResponseCall(@Query("q") String query, @Query("page") int page);

}
