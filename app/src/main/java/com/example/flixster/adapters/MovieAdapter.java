package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.flixster.DetailActivity;
import com.example.flixster.MainActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XM; and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(movieView);
    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d("MovieAdapter","OnBindViewHolder" + position);
        //Get the movie  at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie dat into VH
        holder.bind(movie);
    }

    //Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //if phone is in landscape
            if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                //then imageurl = back drop image
                imageUrl = movie.getBackdropPath();
            }
            else{

                //else imageurl = poster image
                imageUrl = movie.getPosterPath();
            }


            int radius = 20; // corner radius, higher value = more rounded
            int margin = 0; // crop margin, set to 0 for corners with no crop
            Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);
            //1. register click listner on the whole row

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //2. navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    Pair<View, String> p1 = Pair.create((View)tvOverview, "tvOverview");
                    Pair<View, String> p2 = Pair.create((View)tvTitle, "tvTitle");
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,(View)tvTitle,"transitionTitle");
                    context.startActivity(i,options.toBundle());

                }
            });
        }
    }
}
