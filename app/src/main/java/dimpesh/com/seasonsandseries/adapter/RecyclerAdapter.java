package dimpesh.com.seasonsandseries.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dimpesh.com.seasonsandseries.R;
import dimpesh.com.seasonsandseries.model.DataObject;

/**
 * Created by DIMPESH : ${month}
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    ArrayList<DataObject> mData = new ArrayList<DataObject>();
    private LayoutInflater mInflater;
    Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle;
        ImageView thumbnail;
        int position;
        DataObject cuLandscape;


        public MyViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.card_title);
            subtitle = (TextView) itemView.findViewById(R.id.card_subtitle);
            thumbnail = (ImageView) itemView.findViewById(R.id.card_thumbnail);

        }

        public void setData(DataObject currentObj, int position) {
            Log.v(TAG, "setData Called");
            String baseImageUrl = "http://image.tmdb.org/t/p/w185/";
            this.title.setText(currentObj.getOriginal_name());
            this.subtitle.setText(String.valueOf(currentObj.getVote_average()));
            Picasso.with(mContext).
                    load(baseImageUrl + currentObj.getPoster_path())
                    .resize(60, 60)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .into(thumbnail);

            cuLandscape = currentObj;
            this.position = position;

        }
    }

    public RecyclerAdapter(Context context, ArrayList<DataObject> mData) {

        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder :" + position);

        DataObject currentObj = mData.get(position);
        holder.setData(currentObj, position);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}
