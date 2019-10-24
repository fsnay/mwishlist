package com.example.moviewishlist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude .Include.NON_NULL)
@JsonPropertyOrder({"id","vote_average","title", "popularity","poster_path","overview","release_date","runtime","status"})
public class Movie implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("vote_average")
    private Number voteAverage;
    @JsonProperty("title")
    private String title;
    @JsonProperty("popularity")
    private Number popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("runtime")
    private int runtime;
    @JsonProperty("status")
    private String status;

//    private boolean watched;

    public Movie(){}

//    public boolean isWatched() {
//        return watched;
//    }
//
//    public void setWatched(boolean watched) {
//        this.watched = watched;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoteAverage() {
        return "Avaliação: "+voteAverage;
    }

    public void setVoteAverage(Number voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Number getPopularity() {
        return popularity;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        if(releaseDate == null || releaseDate.isEmpty()){
            this.releaseDate = "Lançamento: desconhecido";
        }
        else{
            String[] parsing = releaseDate.split("-");
            this.releaseDate = "Lançamento: "+parsing[2]+'/'+parsing[1]+'/'+parsing[0];
        }
    }

    public String getRuntime() {
        return "Runtime: "+runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = "Status: " + status;
    }
}
