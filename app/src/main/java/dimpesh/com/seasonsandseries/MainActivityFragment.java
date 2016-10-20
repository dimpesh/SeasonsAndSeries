package dimpesh.com.seasonsandseries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dimpesh.com.seasonsandseries.adapter.RecyclerAdapter;
import dimpesh.com.seasonsandseries.model.DataObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private RequestQueue requestQueue;
    private Gson gson;
    ArrayList<DataObject> posts=new ArrayList<DataObject>();
    RecyclerAdapter adapter;

    RecyclerView rv;
    private static final String strUrl = "http://api.themoviedb.org/3/tv/top_rated?api_key=";

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());

        //    Building GSON for fetching data
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        fetchData();

        rv = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new RecyclerAdapter(getActivity(), posts);
        rv.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mLinearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());// if not written then too tjis will be default

        return view;
    }

    private void fetchData() {

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

                List<DataObject> postsList = Arrays.asList(gson.fromJson(arr.toString(), DataObject[].class));
                posts.addAll(postsList);
                Log.v(TAG,"AList size :"+posts.size());
                Log.i(TAG, posts + "");
                Log.i(TAG, posts.size() + " posts loaded.");

                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private final Response.ErrorListener onPostError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.v(TAG, error.toString());
        }
    };

}
