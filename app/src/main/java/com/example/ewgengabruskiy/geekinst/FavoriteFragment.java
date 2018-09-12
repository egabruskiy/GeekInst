package com.example.ewgengabruskiy.geekinst;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteFragment extends Fragment{

    private PhotoListAdapter photoListAdapter;


    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_favorite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.gallery_columns)));
        photoListAdapter = new PhotoListAdapter(ImageFileUtils.getFavoritePhotoInfoList(getContext()));
        recyclerView.setAdapter(photoListAdapter);
        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
            @Override
            public void onFavoriteCheckedChanged(boolean isChecked, int position) {
                if (!isChecked) {
                    ImageFileUtils.removeFromFavoriteList(ImageFileUtils.getImageFilePath(photoListAdapter.getPhotoItemList(), position),getContext());

                    dispatchUpdates(ImageFileUtils.getFavoritePhotoInfoList(getContext()));


                }
            }
        });
    }


    private void dispatchUpdates(List<PhotoItem> newPhotoInfoList) {
        DiffUtilCallback diffUtilCallback =
                new DiffUtilCallback(photoListAdapter.getPhotoItemList(), newPhotoInfoList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        photoListAdapter.setPhotoItemList(newPhotoInfoList);
        diffResult.dispatchUpdatesTo(photoListAdapter);
    }

}
