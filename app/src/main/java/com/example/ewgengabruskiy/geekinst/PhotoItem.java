package com.example.ewgengabruskiy.geekinst;

public class PhotoItem {
    private static final String TAG = "PhotoItem";
    private long id;
    private String photoPath;
    private boolean isFavorites;

    public PhotoItem(String photoPath, boolean isFavorites) {
        this.photoPath = photoPath;
        this.isFavorites = isFavorites;
    }

    public PhotoItem(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public boolean isFavorites() {
        return isFavorites;
    }
}