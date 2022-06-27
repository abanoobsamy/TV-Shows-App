package com.abanoob_samy.tvshowsapp.adapaters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.abanoob_samy.tvshowsapp.R;
import com.abanoob_samy.tvshowsapp.databinding.ItemContainerTvShowBinding;
import com.abanoob_samy.tvshowsapp.listeners.TVShowsListener;
import com.abanoob_samy.tvshowsapp.models.TVShows;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.TvShowsHolder> {

    private List<TVShows> tvShows;
    private LayoutInflater layoutInflater;

    static TVShowsListener tvShowsListener;

    public TvShowsAdapter(List<TVShows> tvShows, TVShowsListener tvShowsListener) {
        this.tvShows = tvShows;
        this.tvShowsListener = tvShowsListener;
    }

    @NonNull
    @Override
    public TvShowsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {

            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerTvShowBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_container_tv_show, parent, false);

        return new TvShowsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowsHolder holder, int position) {

        holder.bindTVShows(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class TvShowsHolder extends RecyclerView.ViewHolder {

        ItemContainerTvShowBinding binding;

        public TvShowsHolder(@NonNull ItemContainerTvShowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bindTVShows(TVShows tvShows) {

            binding.setTvShow(tvShows);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(view -> tvShowsListener.onTVShowListener(tvShows));
        }
    }
}
