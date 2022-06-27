package com.abanoob_samy.tvshowsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import com.abanoob_samy.tvshowsapp.R;
import com.abanoob_samy.tvshowsapp.adapaters.TvShowsAdapter;
import com.abanoob_samy.tvshowsapp.databinding.ActivitySearchBinding;
import com.abanoob_samy.tvshowsapp.listeners.TVShowsListener;
import com.abanoob_samy.tvshowsapp.models.TVShows;
import com.abanoob_samy.tvshowsapp.viewmodels.SearchTvShowsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowsListener {

    private ActivitySearchBinding binding;
    private SearchTvShowsViewModel mViewModel;

    private TvShowsAdapter adapter;
    private List<TVShows> tvShows = new ArrayList<>();

    private int currentPage = 1;
    private int totalAvailablePage = 1;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        doInitialization();
    }

    private void doInitialization() {

        mViewModel = new ViewModelProvider(this).get(SearchTvShowsViewModel.class);

        binding.imageBack.setOnClickListener(view -> {

            onBackPressed();
        });

        binding.recyclerSearch.setHasFixedSize(true);
        adapter = new TvShowsAdapter(tvShows, this);
        binding.recyclerSearch.setAdapter(adapter);

        binding.editInputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().trim().isEmpty()) {

                    timer = new Timer();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            new Handler(Looper.getMainLooper()).post(() -> {

                                currentPage = 1;
                                totalAvailablePage = 1;
                                getSearchTVShows(editable.toString());
                            });
                        }
                    }, 800);
                }
                else {
                    tvShows.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        binding.recyclerSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!binding.recyclerSearch.canScrollVertically(1)) {
                    if (!binding.editInputSearch.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePage) {
                            currentPage += 1;
                            getSearchTVShows(binding.editInputSearch.getText().toString());
                        }
                    }
                }
            }
        });

        binding.editInputSearch.requestFocus();
    }

    private void getSearchTVShows(String query) {

        toggleLoading();
        mViewModel.getSearchShowsResponseCall(query, currentPage).observe(this, tvShowsResponse -> {

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

        Intent intent = new Intent(SearchActivity.this, TVShowsDetailsActivity.class);
        intent.putExtra("TVShows", tvShows);
        startActivity(intent);
    }
}