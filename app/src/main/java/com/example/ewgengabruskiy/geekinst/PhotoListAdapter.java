package com.example.ewgengabruskiy.geekinst;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ToggleButton;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {

    private List<PhotoItem> photoItemList;
    private AdapterView.OnItemClickListener itemClickListener;

    public PhotoListAdapter(List<PhotoItem> photoItemList) {
        this.photoItemList = photoItemList ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ToggleButton favoriteButton;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            favoriteButton = itemView.findViewById(R.id.button_favorite);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.get()
                .load(new File(photoItemList.get(position).getPhotoPath()))
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.favoriteButton.setChecked(photoItemList.get(position).isFavorites());
    }


    @Override
    public int getItemCount() {
        return photoItemList.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    public void addPhotoToList(String path) {

        List<PhotoItem> newPhotoInfoList = new ArrayList<>(photoItemList);
        newPhotoInfoList.add(new PhotoItem(path));
        dispatchUpdates(newPhotoInfoList);

    }

    private void dispatchUpdates(List<PhotoItem> newPhotoInfoList) {
        DiffUtilCallback diffUtilCallback =
                new DiffUtilCallback(photoItemList, newPhotoInfoList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        photoItemList = newPhotoInfoList;
        diffResult.dispatchUpdatesTo(this);
    }

    }
