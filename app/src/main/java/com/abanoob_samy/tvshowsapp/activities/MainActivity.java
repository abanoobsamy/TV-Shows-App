package com.abanoob_samy.tvshowsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.abanoob_samy.tvshowsapp.R;
import com.abanoob_samy.tvshowsapp.adapaters.TvShowsAdapter;
import com.abanoob_samy.tvshowsapp.databinding.ActivityMainBinding;
import com.abanoob_samy.tvshowsapp.listeners.TVShowsListener;
import com.abanoob_samy.tvshowsapp.models.TVShows;
import com.abanoob_samy.tvshowsapp.viewmodels.MostPopularTvShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TVShowsListener {

    private ActivityMainBinding binding;

    private MostPopularTvShowsViewModel mViewModel;

    private TvShowsAdapter adapter;
    private List<TVShows> tvShows = new ArrayList<>();

    private int currentPage = 1;
    private int totalAvailablePage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInitialization();
    }

    private void doInitialization() {

        mViewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);

        binding.recyclerMain.setHasFixedSize(true);
        adapter = new TvShowsAdapter(tvShows, this);
        binding.recyclerMain.setAdapter(adapter);

        binding.recyclerMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!binding.recyclerMain.canScrollVertically(1)) {

                    if (currentPage <= totalAvailablePage) {

                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });

        binding.watchlist.setOnClickListener(view -> {

            startActivity(new Intent(getApplicationContext(), WatchlistActivity.class));
        });

        binding.imageSearch.setOnClickListener(view -> {

            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        });

        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {

//        mViewModel.getTvShowsResponseLiveData(0).observe(this, new Observer<TvShowsResponse>() {
//            @Override
//            public void onChanged(TvShowsResponse tvShowsResponse) {
//
//            }
//        });

        // that or that is true.

//        binding.setIsLoading(true);
        toggleLoading();

        mViewModel.getTvShowsResponseLiveData(currentPage).observe(this, tvShowsResponse -> {

//            binding.setIsLoading(false);
            toggleLoading();

            if (tvShowsResponse != null) {
                totalAvailablePage = tvShowsResponse.getTotal_pages();
                if (tvShowsResponse.getTv_shows() != null) {
                    int oldCount = tvShows.size();
                    tvShows.addAll(tvShowsResponse.getTv_shows());
                    adapter.notifyItemRangeInserted(oldCount, tvShows.size());
                }
            }
        });
    }

    private void toggleLoading() {

        if (currentPage == 1) {

            if (binding.getIsLoading() != null && binding.getIsLoading()) {
                binding.setIsLoading(false);
            }
            else {
                binding.setIsLoading(true);
            }
        }
        else {

            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore()) {
                binding.setIsLoadingMore(false);
            }
            else {
                binding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowListener(TVShows tvShows) {

        Intent intent = new Intent(MainActivity.this, TVShowsDetailsActivity.class);
        intent.putExtra("TVShows", tvShows);
        startActivity(intent);
    }
}