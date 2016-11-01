package dimpesh.com.seasonsandseries;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import dimpesh.com.seasonsandseries.model.DataObject;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callbacks{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (findViewById(R.id.show_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((MainActivityFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_show))
                    .setActivateOnItemClick(true);
        }
    }
        /**
         * Callback method from {@link MainActivityFragment.Callbacks}
         * indicating that the item with the given ID was selected.
         */
        @Override
        public void onItemSelected(DataObject m) {
            if (mTwoPane) {
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a
                // fragment transaction.
                Bundle arguments = new Bundle();
                arguments.putParcelable("show",m);
                DetailActivityFragment fragment = new DetailActivityFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.show_detail_container, fragment)
                        .commit();

            } else {
                Intent detailIntent = new Intent(this, DetailActivity.class);
                detailIntent.putExtra("show", m);
                startActivity(detailIntent);
            }
        }




}
