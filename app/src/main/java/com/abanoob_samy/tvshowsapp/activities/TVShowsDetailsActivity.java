package com.abanoob_samy.tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abanoob_samy.tvshowsapp.R;
import com.abanoob_samy.tvshowsapp.adapaters.EpisodeAdapter;
import com.abanoob_samy.tvshowsapp.adapaters.ImageSliderAdapter;
import com.abanoob_samy.tvshowsapp.databinding.ActivityTvshowsDetailsBinding;
import com.abanoob_samy.tvshowsapp.databinding.LayoutEpisodesBottomSheetBinding;
import com.abanoob_samy.tvshowsapp.models.TVShows;
import com.abanoob_samy.tvshowsapp.utilities.TempDataHolder;
import com.abanoob_samy.tvshowsapp.viewmodels.TvShowsDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

public class TVShowsDetailsActivity extends AppCompatActivity {

    private ActivityTvshowsDetailsBinding binding;

    private TvShowsDetailsViewModel mViewModel;
    private TVShows tvShows;

    private BottomSheetDialog dialog;
    private LayoutEpisodesBottomSheetBinding episodesBottomSheetBinding;

    private Boolean isTVShowAvailableInWatchlist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tvshows_details);

        doInitialization();
    }

    private void doInitialization() {

        mViewModel = new ViewModelProvider(this).get(TvShowsDetailsViewModel.class);

        tvShows = (TVShows) getIntent().getSerializableExtra("TVShows");
        binding.imageBack.setOnClickListener(view -> onBackPressed());

        checkTVShowInWatchlist();
        getTVShowDetails();
    }

    private void checkTVShowInWatchlist() {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(mViewModel.getTvShowsFromWatchlist(String.valueOf(tvShows.getId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {

                    //is clicked.
                    isTVShowAvailableInWatchlist = true;
                    binding.imageWatchlist.setImageResource(R.drawable.ic_check);
                    compositeDisposable.dispose();
                }));
    }

    private void getTVShowDetails() {

        if (getIntent().hasExtra("TVShows")) {

            binding.setIsLoading(true);

            String tvShowsId = String.valueOf(tvShows.getId());

            mViewModel.getTvShowsDetailsLiveData(tvShowsId).observe(this, tvShowsDetailsResponse -> {

                binding.setIsLoading(false);

                if (tvShowsDetailsResponse.getTvShow() != null) {

                    if (tvShowsDetailsResponse.getTvShow().getPictures() != null) {

                        loadImageSlider(tvShowsDetailsResponse.getTvShow().getPictures());
                    }

                    binding.setTvShowImageURL(tvShowsDetailsResponse.getTvShow().getImage_path());
                    binding.imageTVShow.setVisibility(View.VISIBLE);

                    binding.setTvShowName(tvShows.getName());
                    binding.textName.setVisibility(View.VISIBLE);

                    binding.setTvShowNetwork(tvShows.getNetwork() + " (" + tvShows.getCountry() + ")");
                    binding.textNetwork.setVisibility(View.VISIBLE);

                    binding.setTvShowStartedDate(tvShows.getStart_date());
                    binding.textStarted.setVisibility(View.VISIBLE);

                    binding.setTvShowStatus(tvShows.getStatus());
                    binding.textStatus.setVisibility(View.VISIBLE);

                    binding.setTvShowDescription(String.valueOf(HtmlCompat.fromHtml(tvShowsDetailsResponse
                            .getTvShow().getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY)));
                    binding.textDescription.setVisibility(View.VISIBLE);

                    //for read more or less
                    binding.textReadMore.setVisibility(View.VISIBLE);
                    binding.textReadMore.setOnClickListener(view -> {

                        if (binding.textReadMore.getText().toString().equals("Read More")) {

                            binding.textDescription.setMaxLines(Integer.MAX_VALUE);
                            binding.textDescription.setEllipsize(null);
                            binding.textReadMore.setText(R.string.read_less);
                        } else {

                            binding.textDescription.setMaxLines(4);
                            binding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                            binding.textReadMore.setText(R.string.read_more);
                        }
                    });

                    binding.setTvShowRating(String.format(Locale.getDefault(),
                            "%.2f",
                            Double.parseDouble(tvShowsDetailsResponse.getTvShow().getRating())));

                    if (tvShowsDetailsResponse.getTvShow().getGenres() != null) {

                        binding.setTvShowGenres(tvShowsDetailsResponse.getTvShow().getGenres()[0]);
                    } else {
                        binding.setTvShowGenres("N/A");
                    }

                    binding.setTvShowRuntime(tvShowsDetailsResponse.getTvShow().getRuntime() + "Min");

                    binding.viewDivider1.setVisibility(View.VISIBLE);
                    binding.layoutMisc.setVisibility(View.VISIBLE);
                    binding.viewDivider2.setVisibility(View.VISIBLE);

                    binding.buttonWebsite.setOnClickListener(view -> {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(tvShowsDetailsResponse.getTvShow().getUrl()));
                        startActivity(intent);
                    });
                    binding.buttonWebsite.setVisibility(View.VISIBLE);

                    binding.buttonEpisode.setOnClickListener(view -> {

                        if (dialog == null) {

                            dialog = new BottomSheetDialog(TVShowsDetailsActivity.this);
                            episodesBottomSheetBinding = DataBindingUtil.
                                    inflate(LayoutInflater.from(TVShowsDetailsActivity.this),
                                            R.layout.layout_episodes_bottom_sheet,
                                            findViewById(R.id.episodesContainer),
                                            false);

                            dialog.setContentView(episodesBottomSheetBinding.getRoot());

                            episodesBottomSheetBinding.recyclerEpisodes.
                                    setAdapter(new EpisodeAdapter(tvShowsDetailsResponse.getTvShow().getEpisodes()));

                            episodesBottomSheetBinding.textTitle.setText(String.format("Episodes | %s", tvShows.getName()));

                            episodesBottomSheetBinding.imageClose.setOnClickListener(view1 -> {

                                dialog.dismiss();
                            });
                        }

                        FrameLayout frameLayout =
                                dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

                        if (frameLayout != null) {

                            BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                            bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                            bottomSheetBehavior.setState(bottomSheetBehavior.STATE_EXPANDED);
                        }

                        dialog.show();
                    });
                    binding.buttonEpisode.setVisibility(View.VISIBLE);

                    binding.imageWatchlist.setOnClickListener(view -> {
                        CompositeDisposable compositeDisposable = new CompositeDisposable();

                        if (isTVShowAvailableInWatchlist) {

                            compositeDisposable.add(mViewModel.removeTVShowFromWatchlist(tvShows)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {

                                        isTVShowAvailableInWatchlist = false;
                                        TempDataHolder.IS_WATCHLIST_UPDATED = true;
                                        binding.imageWatchlist.setImageResource(R.drawable.ic_baseline_watchlist);
                                        Toast.makeText(getApplicationContext(), "Disable from Watchlist.",
                                                Toast.LENGTH_SHORT).show();
                                        compositeDisposable.dispose();
                                    }));
                        }
                        else {
                            compositeDisposable.add(mViewModel.addToWatchlist(tvShows)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {

                                        TempDataHolder.IS_WATCHLIST_UPDATED = true;
                                        binding.imageWatchlist.setImageResource(R.drawable.ic_check);
                                        Toast.makeText(getApplicationContext(), "Added to Watchlist.",
                                                Toast.LENGTH_SHORT).show();
                                        compositeDisposable.dispose();
                                    }));
                        }
                    });
                    binding.imageWatchlist.setVisibility(View.VISIBLE);

                }
            });
        }
    }

    private void loadImageSlider(String[] sliderImage) {

        binding.sliderViewPager.setOffscreenPageLimit(1);
        binding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImage));
        binding.sliderViewPager.setVisibility(View.VISIBLE);
        binding.viewFadingEdge.setVisibility(View.VISIBLE);

        setupSliderIndicators(sliderImage.length);

        binding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicators(int count) {

        ImageView[] indicators = new ImageView[count];

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT
        );

        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {

            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive));

            indicators[i].setLayoutParams(layoutParams);
            binding.layoutSliderIndicator.addView(indicators[i]);
        }

        binding.layoutSliderIndicator.setVisibility(View.VISIBLE);

        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {

        int childCount = binding.layoutSliderIndicator.getChildCount();

        for (int i = 0; i < childCount; i++) {

            ImageView imageView = (ImageView) binding.layoutSliderIndicator.getChildAt(i);

            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.background_slider_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.background_slider_indicator_inactive));
            }
        }
    }
}