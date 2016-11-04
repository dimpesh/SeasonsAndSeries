package dimpesh.com.seasonsandseries;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dimpesh.com.seasonsandseries.adapter.RecyclerCreditAdapter;
import dimpesh.com.seasonsandseries.adapter.RecyclerItemClickListener;
import dimpesh.com.seasonsandseries.adapter.RecyclerTrailerAdapter;
import dimpesh.com.seasonsandseries.model.Credits;
import dimpesh.com.seasonsandseries.model.DataObject;
import dimpesh.com.seasonsandseries.model.TrailerObject;
import dimpesh.com.seasonsandseries.utils.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {


    public static final String TAG = DetailActivityFragment.class.getSimpleName();
    private RequestQueue requestQueue;
    private Gson gson;
    private RequestQueue requestCreditQueue;
    private Gson gsonCredit;
    Credits credits=new Credits();

    private DataObject mRecieved;
    private AdView mAdView;
    ImageView collapsing_image;
    ImageView thumbnail;
    TextView vote,rating,popularity,date_air;
    ArrayList<TrailerObject> posts = new ArrayList<TrailerObject>();
    RecyclerTrailerAdapter adapter;
    RecyclerView rvTrailer;
    RecyclerCreditAdapter adapterCredit;
   RecyclerView rvCredit;
    TextView overview_text;
    CollapsingToolbarLayout cl;
    ProgressBar pg;
    TrailerObject trailerClicked;
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        Log.v(TAG,mRecieved.getName());
        fetchTrailer(mRecieved.getId());
        fetchCredit(mRecieved.getId());
        cl = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar_layout);

        cl.setTitle(mRecieved.getName());

        cl.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        cl.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

//        cl.setTitleEnabled(false);
        collapsing_image=(ImageView)view.findViewById(R.id.details_collapsing_image);
        thumbnail=(ImageView)view.findViewById(R.id.details_thumbnail);

        // Collapsing Image
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+mRecieved.getBackdrop_path()).error(R.drawable.img_placeholder).
                centerCrop().resize(300,250).placeholder(R.drawable.img_placeholder).into(collapsing_image);

        // Icon Image...
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/"+mRecieved.getPoster_path()).error(R.drawable.img_placeholder).
                placeholder(R.drawable.img_placeholder).into(thumbnail);

        // Overview
        overview_text=(TextView)view.findViewById(R.id.details_overview);
        rating=(TextView)view.findViewById(R.id.details_rating);
        vote=(TextView)view.findViewById(R.id.detail_votes);
        popularity=(TextView)view.findViewById(R.id.details_popularity);
        date_air=(TextView)view.findViewById(R.id.details_release_date);
        pg=(ProgressBar)view.findViewById(R.id.detail_pg);
        overview_text.setText(mRecieved.getOverview());
        rating.setText(mRecieved.getVote_average()+"");
        vote.setText(mRecieved.getVote_count()+"");
        popularity.setText(mRecieved.getPopularity()+"");
        date_air.setText(mRecieved.getFirst_air_date());

        /**
         * Recycler view for Trailers
         */

        rvTrailer = (RecyclerView) view.findViewById(R.id.details_trailer_recyclerview);
        adapter = new RecyclerTrailerAdapter(getActivity(), posts);
        rvTrailer.setAdapter(adapter);
        GridLayoutManager gvManager=new GridLayoutManager(getActivity(),1);
        gvManager.setOrientation(GridLayoutManager.HORIZONTAL);

//        rvTrailer.setLayoutManager(mLinearLayoutManager);
        rvTrailer.setLayoutManager(gvManager);
        rvTrailer.setItemAnimator(new DefaultItemAnimator());// if not written then too this will be default
        rvTrailer.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        trailerClicked = posts.get(position);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" +trailerClicked.getKey())));
                    }
                }));



        rvCredit = (RecyclerView) view.findViewById(R.id.details_cast_recyclerview);

        GridLayoutManager gvManagerCredit=new GridLayoutManager(getActivity(),1);
        gvManagerCredit.setOrientation(GridLayoutManager.HORIZONTAL);

/*
        adapterCredit = new RecyclerCreditAdapter(getActivity(),credits);
        rvCredit.setAdapter(adapterCredit);
*/

//        rvTrailer.setLayoutManager(mLinearLayoutManager);
        rvCredit.setLayoutManager(gvManagerCredit);
        rvCredit.setItemAnimator(new DefaultItemAnimator());// if not written then too this will be default




        // TODO : handle empty view of trailers, Credits and List of tv shows...
        // Date set

        // TODO : Add Admob ID Correctly before publishing...
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        pg.setVisibility(View.VISIBLE);

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

        requestQueue = Volley.newRequestQueue(getActivity());
        requestCreditQueue = Volley.newRequestQueue(getActivity());

        //    Building GSON for fetching data
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        gsonCredit = gsonBuilder.create();


    }

    public void fetchTrailer(int id)
    {
        String strUrl="https://api.themoviedb.org/3/tv/"+id+"/videos?api_key="+ Constants.APIKEY_KEY;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, strUrl
                , onPostLoaded, onPostError);
        requestQueue.add(stringRequest);

    }
    private final Response.Listener<String> onPostLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                JSONObject obj1 = new JSONObject(response);
                JSONArray arr = obj1.getJSONArray("results");
                List<TrailerObject> postsList = Arrays.asList(gson.fromJson(arr.toString(), TrailerObject[].class));
                posts.addAll(postsList);
                Log.i(TAG, posts.size() + " posts loaded.");
                for(int i=0;i<posts.size();i++)
                {
                    Log.v(TAG,"Name :"+i+" "+posts.get(i).getName());
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            pg.setVisibility(View.INVISIBLE);
        }
    };

    private final Response.ErrorListener onPostError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.v(TAG, error.toString());
            pg.setVisibility(View.INVISIBLE);

        }
    };



    public void fetchCredit(int id)
    {
        String trailerUrl="https://api.themoviedb.org/3/tv/"+id+"?api_key="+Constants.APIKEY_KEY+"&append_to_response=credits";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, trailerUrl
                , onPostTrailerLoaded, onPostTrailerError);
        requestQueue.add(stringRequest);

    }
    private final Response.Listener<String> onPostTrailerLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                Log.i(TAG,"response :"+response);
                JSONObject obj1 = new JSONObject(response);

                JSONObject obj2= obj1.getJSONObject("credits");

//                CreditObject cr =new CreditObject();

                  credits= gson.fromJson(obj2+"", Credits.class);

/*
                int n=credits.getCastSize();
                Log.v(TAG,"Total "+n+" Cast");
                for(int i=0;i<n;i++)
                {
                    Log.v(TAG,(i+1)+". "+credits.getCast(i).getName());
                }

*/
/*
                adapterCredit=new RecyclerCreditAdapter(getActivity(),credits);
                rvCredit.setAdapter(adapterCredit);
*/
/*
                adapterCredit.notifyDataSetChanged();
*/


        adapterCredit = new RecyclerCreditAdapter(getActivity(),credits);
        rvCredit.setAdapter(adapterCredit);



            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener onPostTrailerError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.v(TAG, error.toString());
        }
    };


}
