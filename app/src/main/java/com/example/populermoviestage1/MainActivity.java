package com.example.populermoviestage1;

import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.populermoviestage1.Database.AppDatabase;
import com.example.populermoviestage1.Database.MainViewModel;
import com.example.populermoviestage1.Database.Movie;

import java.util.List;

import android.arch.lifecycle.ViewModelProviders;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        private RecyclerView mRecyclerView;
        private MovieAdapter movieAdapter;
        private FavouriteAdapter favouriteAdapter;
        private ProgressBar loadingBar;

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View movieView = inflater.inflate(R.layout.fragment_popular, container, false);
            mRecyclerView = (RecyclerView) movieView.findViewById(R.id.myRecyclerView);
            loadingBar = (ProgressBar) movieView.findViewById(R.id.loadingBar);
            GridLayoutManager gridLayoutManager
                    = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            movieAdapter = new MovieAdapter();
            favouriteAdapter=new FavouriteAdapter();
            MInterface mInterface = ApiClient.getClient().create(MInterface.class);


            if (getArguments() != null) {
                if (getArguments().getInt(ARG_SECTION_NUMBER) == 0) {
                    Call<MovieData> call = mInterface.getPopularMovies();
                    call.enqueue(new Callback<MovieData>() {
                        @Override
                        public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                            movieAdapter.setMovieData(response.body());
                            mRecyclerView.setAdapter(movieAdapter);
                            loadingBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<MovieData> call, Throwable t) {
                            loadingBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Something went wrong, check the logs", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                    Call<MovieData> call = mInterface.getHighestRated();
                    call.enqueue(new Callback<MovieData>() {
                        @Override
                        public void onResponse(Call<MovieData> call, Response<MovieData> response) {
                            movieAdapter.setMovieData(response.body());
                            mRecyclerView.setAdapter(movieAdapter);
                            loadingBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<MovieData> call, Throwable t) {
                            loadingBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Something went wrong, check the logs", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                } else {
                    MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
                    List<Movie> movieList = viewModel.getFavoriteMovies();
                    favouriteAdapter.setMovie(movieList);
                    mRecyclerView.setAdapter(favouriteAdapter);
                    loadingBar.setVisibility(View.GONE);
                }

            }

            return movieView;

        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title;

            if (position == 0) {
                title = "Popular Movies";
            } else if (position == 1) {
                title = "Highest Rated Movies";
            } else {
                title = "Favorite Movies";
            }
            return title;
        }

        @Override
        public int getCount() {

            return 3;
        }
    }
}
