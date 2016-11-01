package dimpesh.com.seasonsandseries.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DIMPESH : ${month}
 */

public class CreditObject
{

    @SerializedName("backdrop_path")
    public String backdrop_path;
    @SerializedName("created_by")

    public List<Created_by> created_by;
    @SerializedName("episode_run_time")
/*    public List<Episode_run_time> episode_run_time;
    @SerializedName("first_air_date")
*/
    public String first_air_date;
    @SerializedName("genres")
    public List<Genres> genres;
    @SerializedName("homepage")
    public String homepage;
    @SerializedName("id")
    public int id;
    @SerializedName("in_production")
    public boolean in_production;
    @SerializedName("languages")
/*
    public List<Languages> languages;
    @SerializedName("last_air_date")
*/
    public String last_air_date;
    @SerializedName("name")
    public String name;
    @SerializedName("networks")
    public List<Networks> networks;
    @SerializedName("number_of_episodes")
    public int number_of_episodes;
    @SerializedName("number_of_seasons")
    public int number_of_seasons;
    @SerializedName("origin_country")
/*
    public List<Origin_country> origin_country;
    @SerializedName("original_language")
*/
    public String original_language;
    @SerializedName("original_name")
    public String original_name;
    @SerializedName("overview")
    public String overview;
    @SerializedName("popularity")
    public double popularity;
    @SerializedName("poster_path")
    public String poster_path;
    @SerializedName("production_companies")
    public List<Production_companies> production_companies;
    @SerializedName("seasons")
    public List<Seasons> seasons;
    @SerializedName("status")
    public String status;
    @SerializedName("type")
    public String type;
    @SerializedName("vote_average")
    public double vote_average;
    @SerializedName("vote_count")
    public int vote_count;
    @SerializedName("credits")
    public Credits credits;

    public static class Created_by {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("profile_path")
        public String profile_path;
    }

    public static class Genres {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
    }

    public static class Networks {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
    }

    public static class Production_companies {
        @SerializedName("name")
        public String name;
        @SerializedName("id")
        public int id;
    }

    public static class Seasons {
        @SerializedName("air_date")
        public String air_date;
        @SerializedName("episode_count")
        public int episode_count;
        @SerializedName("id")
        public int id;
        @SerializedName("poster_path")
        public String poster_path;
        @SerializedName("season_number")
        public int season_number;
    }

    public static class Cast {
        @SerializedName("character")
        public String character;
        @SerializedName("credit_id")
        public String credit_id;
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("profile_path")
        public String profile_path;
        @SerializedName("order")
        public int order;
    }

    public static class Crew {
        @SerializedName("credit_id")
        public String credit_id;
        @SerializedName("department")
        public String department;
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("job")
        public String job;
        @SerializedName("profile_path")
        public String profile_path;
    }

    public static class Credits {
        @SerializedName("cast")
        public List<Cast> cast;
        @SerializedName("crew")
        public List<Crew> crew;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public void setIn_production(boolean in_production) {
        this.in_production = in_production;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public void setLast_air_date(String last_air_date) {
        this.last_air_date = last_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Networks> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Networks> networks) {
        this.networks = networks;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public void setNumber_of_episodes(int number_of_episodes) {
        this.number_of_episodes = number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public void setNumber_of_seasons(int number_of_seasons) {
        this.number_of_seasons = number_of_seasons;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<Production_companies> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<Production_companies> production_companies) {
        this.production_companies = production_companies;
    }

    public List<Seasons> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Seasons> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }
}
