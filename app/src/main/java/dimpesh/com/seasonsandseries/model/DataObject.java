package dimpesh.com.seasonsandseries.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DIMPESH : ${month}
 */

public class DataObject implements Parcelable {


    public DataObject() {
    }

    @SerializedName("poster_path")
    public String poster_path;
    @SerializedName("popularity")
    public double popularity;
    @SerializedName("id")
    public int id;
    @SerializedName("backdrop_path")
    public String backdrop_path;
    @SerializedName("vote_average")
    public double vote_average;
    @SerializedName("overview")
    public String overview;
    @SerializedName("first_air_date")
    public String first_air_date;
    @SerializedName("original_language")
    public String original_language;
    @SerializedName("vote_count")
    public int vote_count;
    @SerializedName("name")
    public String name;
    @SerializedName("original_name")
    public String original_name;


    /*
    @SerializedName("origin_country")
    public List<Origin_country> origin_country;
    @SerializedName("genre_ids")
    public List<Genre_ids> genre_ids;
*/

    protected DataObject(Parcel in) {
        poster_path = in.readString();
        popularity = in.readDouble();
        id = in.readInt();
        backdrop_path = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
        first_air_date = in.readString();
        original_language = in.readString();
        vote_count = in.readInt();
        name = in.readString();
        original_name = in.readString();
    }

    public static final Creator<DataObject> CREATOR = new Creator<DataObject>() {
        @Override
        public DataObject createFromParcel(Parcel in) {
            return new DataObject(in);
        }

        @Override
        public DataObject[] newArray(int size) {
            return new DataObject[size];
        }
    };

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getPoster_path() {

        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster_path);
        dest.writeDouble(popularity);
        dest.writeInt(id);
        dest.writeString(backdrop_path);
        dest.writeDouble(vote_average);
        dest.writeString(overview);
        dest.writeString(first_air_date);
        dest.writeString(original_language);
        dest.writeInt(vote_count);
        dest.writeString(name);
        dest.writeString(original_name);
    }
}

