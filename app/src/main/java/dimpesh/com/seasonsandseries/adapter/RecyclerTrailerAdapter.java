package dimpesh.com.seasonsandseries.adapter;

/**
 * Created by DIMPESH : ${month}
 */

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
import dimpesh.com.seasonsandseries.model.TrailerObject;

/**
 * Created by DIMPESH : ${month}
 */

public class RecyclerTrailerAdapter extends RecyclerView.Adapter<RecyclerTrailerAdapter.MyViewHolder> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    ArrayList<TrailerObject> mData = new ArrayList<TrailerObject>();
    private LayoutInflater mInflater;
    Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,type,engus;
        ImageView thumbnail;
        int position;
        TrailerObject cuTrailer;


        public MyViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.trailer_title);
            type = (TextView) itemView.findViewById(R.id.trailer_type);
            thumbnail = (ImageView) itemView.findViewById(R.id.trailer_image);
            engus=(TextView)itemView.findViewById(R.id.trailer_eng_us);

        }

        public void setData(TrailerObject currentObj, int position) {
            Log.v(TAG, "setData Called");
            String baseImageUrl = "http://img.youtube.com/vi/";
            String endUrl="/0.jpg";
            this.title.setText(currentObj.getName());
            this.type.setText(currentObj.getType());
            this.engus.setText( currentObj.getIso_639_1().toUpperCase()+" | "+currentObj.getIso_3166_1().toUpperCase());
            Picasso.with(mContext).
                    load(baseImageUrl + currentObj.getKey()+endUrl)
                    .resize(250,180)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .into(thumbnail);

            cuTrailer = currentObj;
            this.position = position;

        }
    }

    public RecyclerTrailerAdapter(Context context, ArrayList<TrailerObject> mData) {

        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = mInflater.inflate(R.layout.trailer_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder :" + position);

        TrailerObject currentObj = mData.get(position);
        holder.setData(currentObj, position);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}
