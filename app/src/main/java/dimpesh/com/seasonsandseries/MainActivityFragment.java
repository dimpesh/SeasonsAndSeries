package dimpesh.com.seasonsandseries;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import dimpesh.com.seasonsandseries.adapter.EndlessScrollListener;
import dimpesh.com.seasonsandseries.adapter.RecyclerAdapter;
import dimpesh.com.seasonsandseries.adapter.RecyclerItemClickListener;
import dimpesh.com.seasonsandseries.model.DataObject;
import dimpesh.com.seasonsandseries.utils.Constants;

import static android.content.Context.MODE_WORLD_WRITEABLE;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    private RequestQueue requestQueue;
    private Gson gson;
    ArrayList<DataObject> posts = new ArrayList<DataObject>();
    RecyclerAdapter adapter;
    ProgressBar pg;
    SharedPreferences pref;
    SharedPreferences.Editor editorPref;
    Boolean isChanged=false;
    RecyclerView rv;
    String category;
    int vote_count;
    String API_KEY="?api_key=";
    private String strUrl = "http://api.themoviedb.org/3/tv/";
    TextView emp_view;

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    // Store a member variable for the listener
    private EndlessScrollListener scrollListener;
    int position = 0;
    int current_page = 1;
    int total_pages = 2;
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

//      to enable options Menu.
//        setHasOptionsMenu(true);

        requestQueue = Volley.newRequestQueue(getActivity());

        //    Building GSON for fetching data
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        pg = (ProgressBar) view.findViewById(R.id.pg);
        pref = getActivity().getSharedPreferences("settings", MODE_WORLD_WRITEABLE);
        category=pref.getString(getString(R.string.pref_key_sort),"popular");
        vote_count=pref.getInt(getString(R.string.pref_key_vote_count),50);
        fetchData(current_page);
        rv = (RecyclerView) view.findViewById(R.id.recycler_view);
        emp_view=(TextView)view.findViewById(R.id.tv_no_data);
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
                        movieClicked = posts.get(position);
                        mCallbacks.onItemSelected(movieClicked);
                    }
                }));


        /**
         * Endless ScrollListener
         */
        scrollListener = new EndlessScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi();
            }
        };

        // Adds the scroll listener to RecyclerView
        rv.addOnScrollListener(scrollListener);

        if(posts.isEmpty())
        {
            emp_view.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);

        }
        else {
            emp_view.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void loadNextDataFromApi() {
        current_page++;
        fetchData(current_page);
    }

    private void fetchData(int page) {
        pg.setVisibility(View.VISIBLE);
        Log.v(TAG,"fetch Data Called");
        String fetchUrl = strUrl+category+API_KEY+ Constants.APIKEY_KEY + "&page=" + page;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, fetchUrl
                , onPostLoaded, onPostError);
        requestQueue.add(stringRequest);

    }

    private final Response.Listener<String> onPostLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            rv.setVisibility(View.VISIBLE);
            emp_view.setVisibility(View.GONE);
            try {
                JSONObject obj1 = new JSONObject(response);
                total_pages = obj1.getInt("total_pages");
                JSONArray arr = obj1.getJSONArray("results");
                Log.v(TAG, String.valueOf(total_pages) + " pages ");
                List<DataObject> postsList = Arrays.asList(gson.fromJson(arr.toString(), DataObject[].class));
                posts.addAll(postsList);
                Log.i(TAG, posts.size() + " posts loaded.");
                for(int i=0;i<posts.size();i++)
                {
                    Log.v(TAG,"Name :"+i+" "+posts.get(i).getName());
                }
                pg.setVisibility(View.INVISIBLE);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_sortby_popularity)
        {
            Log.v(TAG,"menu clicked");
            if(category.equals("top_rated"))
            {
                editorPref = pref.edit();
                editorPref.putString(getString(R.string.pref_key_sort),"popular");
                category=pref.getString(getString(R.string.pref_key_sort),"popular");
                editorPref.commit();
            }
            return true;
        }
        if (id==R.id.action_sortby_rating)
        {
            Log.v(TAG,"menu clicked");
            if(category.equals("popular"))
            {
                editorPref = pref.edit();
                editorPref.putString(getString(R.string.pref_key_sort),getString(R.string.pref_key_sort_top_rating));
                editorPref.commit();
                category=pref.getString(getString(R.string.pref_key_sort),"top_rated");
            }
            return true;

        }

        if (id == R.id.action_vote_avg) {
            Log.v(TAG,"menu clicked");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("Change Vote Count");
            final EditText passEdt = new EditText(getActivity());
            String num=String.valueOf(pref.getInt(getString(R.string.pref_key_vote_count),150));
            passEdt.setText(num);
            passEdt.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            LinearLayout ll = new LinearLayout(getActivity());
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.addView(passEdt);
            alertDialog.setView(ll);
            alertDialog.setCancelable(true);
            alertDialog.setPositiveButton("CHANGE", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (passEdt.getText().toString().length() == 0) {
                        Toast.makeText(getActivity(), getString(R.string.vote_cant_be_empty), Toast.LENGTH_SHORT).show();
                    } else {
                        editorPref = pref.edit();
                        editorPref.putInt(getString(R.string.pref_key_vote_count), Integer.parseInt(passEdt.getText().toString()));
                        editorPref.commit();
                        Toast.makeText(getActivity(), getString(R.string.vote_changed_success), Toast.LENGTH_SHORT).show();

                    }
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
