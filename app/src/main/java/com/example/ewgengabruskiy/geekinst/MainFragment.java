package com.example.ewgengabruskiy.geekinst;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    private File photoFile;
    private AppCompatImageView imageView;
    public static final int REQUEST_IMAGE = 100;
    private PhotoListAdapter photoListAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main, container, false);
//       imageView = view.findViewById(R.id.photo);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        photoListAdapter = new PhotoListAdapter(ImageFileUtils.getPhotoItemList(getContext()));
        recyclerView.setAdapter(photoListAdapter);
        initListeners();
    }

    private void initListeners() {

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photoFile = new File(ImageFileUtils.getFilesDir(getContext()),
                        ImageFileUtils.getNewFilename());
                Intent cameraIntent = ImageFileUtils.openCameraIntent(photoFile,getContext());
                startActivityForResult(cameraIntent, REQUEST_IMAGE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
//                imageView.setImageURI(Uri.parse(photoFile.getAbsolutePath()));
                photoListAdapter.addPhotoToList(photoFile.getPath());

            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "You cancelled the operation", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
