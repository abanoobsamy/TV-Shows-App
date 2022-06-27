package com.abanoob_samy.tvshowsapp.adapaters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.abanoob_samy.tvshowsapp.R;
import com.abanoob_samy.tvshowsapp.databinding.ItemContainerEpisodeBinding;
import com.abanoob_samy.tvshowsapp.models.Episode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder> {

    private List<Episode> episodes;
    private LayoutInflater layoutInflater;

    public EpisodeAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {

            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerEpisodeBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_container_episode, parent, false);

        return new EpisodeHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeHolder holder, int position) {
        holder.setBindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public class EpisodeHolder extends RecyclerView.ViewHolder {

        ItemContainerEpisodeBinding binding;

        public EpisodeHolder(@NonNull ItemContainerEpisodeBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void setBindEpisode(Episode episode) {

            String title = "S";

            String season = episode.getSeason();
            if (season.length() == 1) {
                season = "0".concat(season);
            }

            String episodeNumber = episode.getEpisode();
            if (episodeNumber.length() == 1) {
                episodeNumber = "0".concat(episodeNumber);
            }

            episodeNumber = "E".concat(episodeNumber);

            title = title.concat(season).concat(episodeNumber);

            binding.setTitle(title);
            binding.setName(episode.getName());
            binding.setAirDate(episode.getAir_date());
        }
    }
}
