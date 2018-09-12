package com.example.ewgengabruskiy.geekinst;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import androidx.core.content.FileProvider;

import static android.content.Context.MODE_PRIVATE;

public class ImageFileUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.ROOT);
    private static final String FILE_PROVIDER_AUTHORITY  = "com.example.myapp.fileprovider";
    private static String favListKey = "FavList";



    public static String getNewFilename() {
        return String.format("%s%s%s", "IMG_", simpleDateFormat.format(new Date()), ".jpg");
    }


    public static File getFilesDir(Context context) {
        File filesDir = context.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (filesDir == null) {
            filesDir = context.getApplicationContext().getFilesDir();
        }

        return filesDir;
    }



    public static Intent  openCameraIntent(File photoFile, Context context) {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(context.getPackageManager()) != null) {

            Uri photoUri = FileProvider.getUriForFile(context, FILE_PROVIDER_AUTHORITY, photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        }
        return pictureIntent;

    }


    public static List<PhotoItem> getPhotoItemList(Context context) {
        File[] files = getFilesDir(context).listFiles();
        List<PhotoItem> photoItemList = new ArrayList<>();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                photoItemList.add(new PhotoItem(files[i].getPath()));
            }
        }

        return photoItemList;
    }

    public static void addToFavoriteList(String imageFilePath, Context context) {
        Set<String> stringSet = context.getSharedPreferences(favListKey,MODE_PRIVATE).getStringSet(favListKey, new HashSet<String>());
        stringSet.add(imageFilePath);
        SharedPreferences.Editor editor = context.getSharedPreferences(favListKey,MODE_PRIVATE).edit();
        editor.clear();
        editor.putStringSet(favListKey, stringSet);
        editor.apply();
    }
    public static void removeFromFavoriteList(String imageFilePath,Context context) {
        Set<String> stringSet = context.getSharedPreferences(favListKey,MODE_PRIVATE).getStringSet(favListKey, new HashSet<String>());
        stringSet.remove(imageFilePath);
        SharedPreferences.Editor editor =context.getSharedPreferences(favListKey,MODE_PRIVATE).edit();
        editor.clear();
        editor.putStringSet(favListKey, stringSet);
        editor.apply();
    }

    public static List<PhotoItem> getFavoritePhotoInfoList(Context context) {
        List<PhotoItem> photoInfoList = new ArrayList<>();
        Set<String> stringSet = context.getSharedPreferences(favListKey,MODE_PRIVATE).getStringSet(favListKey, new HashSet<String>());

        for (String s : stringSet) {
            photoInfoList.add(new PhotoItem(s, true));
        }

        return photoInfoList;
    }


    public static String getImageFilePath(List<PhotoItem> filesList, int position) {
        if (position >= filesList.size()) {
            return "";
        }

        return filesList.get(position).getPhotoPath();
    }
}
