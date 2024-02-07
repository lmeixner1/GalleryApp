package edu.fvtc.galleryapp;

public class Pokemon {
    public Pokemon(String name, String entry) {
        this.name = name;
        this.entry = entry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    private String name;
    private String entry;

}
