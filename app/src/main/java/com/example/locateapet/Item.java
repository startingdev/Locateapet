package com.example.locateapet;

import android.net.Uri;

public class Item {
    private String header;
    private String species;
    private String description;

    private String tags;
    private String image;

    public Item(String header, String species, String description, String image, String tags) {
        this.header = header;
        this.species = species;
        this.description = description;
        this.image = image;
        this.tags = tags;
    }

    public String getHeader() {
        return header;
    }

    public String getSpecies() {
        return species;
    }

    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }

    public String getTags() {
        return tags;
    }

}
