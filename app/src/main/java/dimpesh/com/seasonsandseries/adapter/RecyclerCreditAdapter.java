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
import dimpesh.com.seasonsandseries.model.Credits;


/**
 * Created by DIMPESH : ${month}
 */

public class RecyclerCreditAdapter extends RecyclerView.Adapter<RecyclerCreditAdapter.MyViewHolder> {

    private static final String TAG = RecyclerCreditAdapter.class.getSimpleName();
/*
    ArrayList<Credits.Cast> mData = new ArrayList<Credits.Cast>();
    ArrayList<Credits.Cast> casts = new ArrayList<Credits.Cast>();
*/
    private LayoutInflater mInflater;
    Credits mData=new Credits();
    ArrayList<String>realName=new ArrayList<>();
    ArrayList<String>charName=new ArrayList<>();
    ArrayList<String>img=new ArrayList<>();
    Context mContext;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cast_name, real_name;
        ImageView thumbnail;
        int position;


        public MyViewHolder(View itemView) {

            super(itemView);
            cast_name = (TextView) itemView.findViewById(R.id.cast_char_name);
            real_name = (TextView) itemView.findViewById(R.id.cast_real_name);
            thumbnail = (ImageView) itemView.findViewById(R.id.cast_thumbnail);

        }

        public void setData(ArrayList<String>charName,ArrayList<String>realName,ArrayList<String>image, int position) {
            Log.v(TAG, "setData Called");
            String baseImageUrl = "http://image.tmdb.org/t/p/w185/";
            this.cast_name.setText(charName.get(position));
            this.real_name.setText(realName.get(position));
            Picasso.with(mContext).
                    load(baseImageUrl + image.get(position))
                    .resize(200,250)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .into(thumbnail);

            this.position = position;

        }
    }

    public RecyclerCreditAdapter(Context context, Credits mData) {
        Log.v(TAG,"Constructor called");
        this.mData=mData;
        Log.v(TAG,mData.getCastSize()+"");
        Log.v(TAG,this.mData.getCastSize()+"");

        for (int i=0;i<mData.getCastSize();i++)
        {
            this.realName.add(mData.getCast(i).getName());
            this.charName.add(mData.getCast(i).getCharacter());
            this.img.add(mData.getCast(i).getProfile_path());
        }
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        View view = mInflater.inflate(R.layout.cast_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder :" + position);

        holder.setData(charName,realName,img, position);


    }

    @Override
    public int getItemCount()
    {
        return mData.getCastSize();
    }




}
