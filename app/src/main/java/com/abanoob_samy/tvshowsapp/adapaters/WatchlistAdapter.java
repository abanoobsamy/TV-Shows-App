package com.abanoob_samy.tvshowsapp.adapaters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abanoob_samy.tvshowsapp.R;
import com.abanoob_samy.tvshowsapp.databinding.ItemContainerTvShowBinding;
import com.abanoob_samy.tvshowsapp.listeners.WatchlistListener;
import com.abanoob_samy.tvshowsapp.models.TVShows;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistHolder> {

    private List<TVShows> tvShows;
    private LayoutInflater layoutInflater;

    static WatchlistListener watchlistListener;

    public WatchlistAdapter(List<TVShows> tvShows, WatchlistListener watchlistListener) {
        this.tvShows = tvShows;
        this.watchlistListener = watchlistListener;
    }

    @NonNull
    @Override
    public WatchlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {

            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerTvShowBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_container_tv_show, parent, false);

        return new WatchlistHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistHolder holder, int position) {

        holder.bindWatchlist(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class WatchlistHolder extends RecyclerView.ViewHolder {

        ItemContainerTvShowBinding binding;

        public WatchlistHolder(@NonNull ItemContainerTvShowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bindWatchlist(TVShows tvShows) {

            binding.setTvShow(tvShows);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(view -> watchlistListener.onTVShowClicked(tvShows));

            binding.imageDelete.setOnClickListener(view -> {

                watchlistListener.removeTVShowFromWatchlist(tvShows, getAdapterPosition());
            });
            binding.imageDelete.setVisibility(View.VISIBLE);
        }
    }
}
