package dimpesh.com.seasonsandseries;

import android.app.Activity;
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
import android.widget.GridView;

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
import dimpesh.com.seasonsandseries.adapter.RecyclerItemClickListener;
import dimpesh.com.seasonsandseries.model.DataObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
{

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private RequestQueue requestQueue;
    private Gson gson;
    ArrayList<DataObject> posts=new ArrayList<DataObject>();
    RecyclerAdapter adapter;

    RecyclerView rv;
    private static final String strUrl = "http://api.themoviedb.org/3/tv/top_rated?api_key=";

    private static final String STATE_ACTIVATED_POSITION="activated_position";

    int position=0;
    int total_pages=1;

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = GridView.INVALID_POSITION;
    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(DataObject m);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(DataObject m) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */


    DataObject movieClicked;





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
        rv.setItemAnimator(new DefaultItemAnimator());// if not written then too this will be default

        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        movieClicked=posts.get(position);
                        mCallbacks.onItemSelected(movieClicked);
                    }
                }));

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
                total_pages=obj1.getInt("total_pages");
                JSONArray arr = obj1.getJSONArray("results");
                Log.v(TAG,String.valueOf(total_pages)+" pages ");
                List<DataObject> postsList = Arrays.asList(gson.fromJson(arr.toString(), DataObject[].class));
                posts.addAll(postsList);
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        if (mActivatedPosition != GridView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }

    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
//        Log.v(TAG, "setActivatedOnItemClick Executed");

        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
/*
        rv.setChoiceMode(activateOnItemClick
                ? 1
                : 0);
*/
    }

    private void setActivatedPosition(int position) {

//        Log.v(TAG, "setActivatedPosition Executed");
/*
        if (position == GridView.INVALID_POSITION) {
            rv.setItemChecked(mActivatedPosition, false);
        } else {
            rv.setItemChecked(position, true);
        }
*/

        mActivatedPosition = position;
    }



}
