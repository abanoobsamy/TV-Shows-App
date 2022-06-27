package com.abanoob_samy.tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abanoob_samy.tvshowsapp.R;
import com.abanoob_samy.tvshowsapp.adapaters.WatchlistAdapter;
import com.abanoob_samy.tvshowsapp.databinding.ActivityWatchlistBinding;
import com.abanoob_samy.tvshowsapp.listeners.WatchlistListener;
import com.abanoob_samy.tvshowsapp.models.TVShows;
import com.abanoob_samy.tvshowsapp.utilities.TempDataHolder;
import com.abanoob_samy.tvshowsapp.viewmodels.WatchlistViewModel;

import java.util.ArrayList;
import java.util.List;

public class WatchlistActivity extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchlistBinding binding;

    private WatchlistViewModel mViewModel;
    private WatchlistAdapter adapter;

    private List<TVShows> watchlistTvShows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist);

        doInitialization();
    }

    private void doInitialization() {

        mViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);

        watchlistTvShows = new ArrayList<>();

        binding.imageBack.setOnClickListener(view -> {

            onBackPressed();
        });

        loadWatchlist();
    }

    private void loadWatchlist() {

        binding.setIsLoading(true);
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(mViewModel.loadWatchlist()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {

                    binding.setIsLoading(false);

                    if (watchlistTvShows.size() > 0) {
                        watchlistTvShows.clear();
                    }

                    watchlistTvShows.addAll(tvShows);

                    adapter = new WatchlistAdapter(watchlistTvShows, this);
                    binding.recyclerWatchlist.setHasFixedSize(true);
                    binding.recyclerWatchlist.setAdapter(adapter);
                    binding.recyclerWatchlist.setVisibility(View.VISIBLE);
                    disposable.dispose();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TempDataHolder.IS_WATCHLIST_UPDATED) {
            loadWatchlist();
            TempDataHolder.IS_WATCHLIST_UPDATED = false;
        }
    }

    @Override
    public void onTVShowClicked(TVShows tvShows) {

        Intent intent = new Intent(WatchlistActivity.this, TVShowsDetailsActivity.class);
        intent.putExtra("TVShows", tvShows);
        startActivity(intent);
    }

    @Override
    public void removeTVShowFromWatchlist(TVShows tvShows, int position) {

        CompositeDisposable disposableDelete = new CompositeDisposable();
        disposableDelete.add(mViewModel.removeTVShowFromWatchlist(tvShows)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {

                    watchlistTvShows.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                    disposableDelete.dispose();

                    Toast.makeText(getApplicationContext(), "Deleted Successfully.", Toast.LENGTH_SHORT).show();
                }));

    }
}