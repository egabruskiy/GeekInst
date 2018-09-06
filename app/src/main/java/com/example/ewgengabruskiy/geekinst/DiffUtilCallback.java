package com.example.ewgengabruskiy.geekinst;


import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class DiffUtilCallback extends DiffUtil.Callback {
    private List<PhotoItem> oldList;
    private List<PhotoItem> newList;

    public DiffUtilCallback(List<PhotoItem> oldList, List<PhotoItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getPhotoPath().equals(newList.get(newItemPosition).getPhotoPath());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getPhotoPath().equals(newList.get(newItemPosition).getPhotoPath());
    }
}
