package dimpesh.com.seasonsandseries.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DIMPESH : ${month}
 */

public class Credits
{


    @SerializedName("cast")
    public List<Cast> cast;
    @SerializedName("crew")
    public List<Crew> crew;

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

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }
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

        public String getCredit_id() {
            return credit_id;
        }

        public void setCredit_id(String credit_id) {
            this.credit_id = credit_id;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getProfile_path() {
            return profile_path;
        }

        public void setProfile_path(String profile_path) {
            this.profile_path = profile_path;
        }
    }

    public Cast getCast(int pos) {
        return cast.get(pos);
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public Crew getCrew(int position) {
        return crew.get(position);
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
