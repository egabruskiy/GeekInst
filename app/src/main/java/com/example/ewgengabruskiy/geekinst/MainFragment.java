package com.example.ewgengabruskiy.geekinst;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MainFragment extends Fragment {

    private File photoFile;
    public static final int REQUEST_IMAGE = 100;
    private PhotoListAdapter photoListAdapter;


    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        photoListAdapter = new PhotoListAdapter(ImageFileUtils.getPhotoItemList(getContext()));
        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {


            @Override
            public void onFavoriteCheckedChanged(boolean isChecked, int position) {
                if (isChecked) {
                    ImageFileUtils.addToFavoriteList(ImageFileUtils.getImageFilePath(photoListAdapter.getPhotoItemList(), position),getContext());
                } else {
                    ImageFileUtils.removeFromFavoriteList(ImageFileUtils.getImageFilePath(photoListAdapter.getPhotoItemList(), position),getContext());
                }
            }
        });
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
                photoListAdapter.addPhotoToList(photoFile.getPath());

                dispatchUpdates(ImageFileUtils.getPhotoItemList(getContext()));

            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "You cancelled the operation", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }



    private void dispatchUpdates(List<PhotoItem> newPhotoInfoList) {
        DiffUtilCallback diffUtilCallback =
                new DiffUtilCallback(photoListAdapter.getPhotoItemList(), newPhotoInfoList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        photoListAdapter.setPhotoItemList(newPhotoInfoList);
        diffResult.dispatchUpdatesTo(photoListAdapter);
    }
}
