package dimpesh.com.seasonsandseries;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dimpesh.com.seasonsandseries.model.DataObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {


    public static final String TAG = DetailActivityFragment.class.getSimpleName();
    private DataObject mRecieved;
    ImageView collapsing_image;
    ImageView thumbnail;
    TextView vote,rating,popularity,date_air;

    TextView overview_text;
    CollapsingToolbarLayout cl;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        Log.v(TAG,mRecieved.getName());
        cl = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar_layout);
        cl.setTitle(mRecieved.getName());
        cl.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        cl.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        cl.setCollapsedTitleGravity(Gravity.LEFT|Gravity.CENTER);

        collapsing_image=(ImageView)view.findViewById(R.id.details_collapsing_image);
        thumbnail=(ImageView)view.findViewById(R.id.details_thumbnail);

        // Collapsing Image
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500/"+mRecieved.getBackdrop_path()).error(R.drawable.img_placeholder).
                placeholder(R.drawable.img_placeholder).into(collapsing_image);

        // Icon Image...
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+mRecieved.getPoster_path()).error(R.drawable.img_placeholder).
                placeholder(R.drawable.img_placeholder).into(thumbnail);

        // Overview
        overview_text=(TextView)view.findViewById(R.id.details_overview);
        rating=(TextView)view.findViewById(R.id.details_rating);
        vote=(TextView)view.findViewById(R.id.detail_votes);
        popularity=(TextView)view.findViewById(R.id.details_popularity);
        date_air=(TextView)view.findViewById(R.id.details_release_date);

        overview_text.setText(mRecieved.getOverview());
        rating.setText(mRecieved.getVote_average()+"");
        vote.setText(mRecieved.getVote_count()+"");
        popularity.setText(mRecieved.getPopularity()+"");
        date_air.setText(mRecieved.getFirst_air_date());


        // Date set
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //       Log.v(TAG, "onCreate Called");
        if (getArguments() != null) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mRecieved = getArguments().getParcelable("show");
        } else {
            mRecieved = getActivity().getIntent().getParcelableExtra("show");
        }
    }


}
