package com.example.mfekr.popularmoviesmaster.Model;
public class Trailer {

    private String key;
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public Trailer() {
    }

    /**
     *
     * @param name
     * @param key
     */
    public Trailer(String key, String name) {
        super();
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}