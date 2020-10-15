package com.irondev25.facultyachivementform.adapters.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.irondev25.facultyachivementform.R;
import com.irondev25.facultyachivementform.pojo.TeacherItem;

import java.util.ArrayList;
import java.util.List;

public class TeacherItemAdapater extends RecyclerView.Adapter<TeacherItemAdapater.CardViewHolder> {
    private List<TeacherItem> teachers = new ArrayList<>();
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_card_item, parent, false);
        return new CardViewHolder(cardItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
//        Volume volume = results.get(position);
//
//        holder.titleTextView.setText(volume.getVolumeInfo().getTitle());
//        holder.publishedDateTextView.setText(volume.getVolumeInfo().getPublishedDate());
//
//        if (volume.getVolumeInfo().getImageLinks() != null) {
//            String imageUrl = volume.getVolumeInfo().getImageLinks().getSmallThumbnail()
//                    .replace("http://", "https://");
//
//            Glide.with(holder.itemView)
//                    .load(imageUrl)
//                    .into(holder.smallThumbnailImageView);
//        }
//
//        if (volume.getVolumeInfo().getAuthors() != null) {
//            Util u = new Util();
//            String authors = u.StringJoin(volume.getVolumeInfo().getAuthors(), ", ");
//            holder.authorsTextView.setText(authors);
//        }

        TeacherItem teacherItem = teachers.get(position);

        Glide.with(holder.itemView).load(teacherItem.getProfilePic()).into(holder.imageView);
        holder.nameTextView.setText(teacherItem.getFullName());
        holder.usernameTextView.setText(teacherItem.getUsername());
    }

    public int getItemCount() {
        return teachers.size();
    }

    public void setResults(List<TeacherItem> teachers){
        this.teachers = teachers;
        notifyDataSetChanged();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView nameTextView;
        public TextView usernameTextView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_image);
            nameTextView = itemView.findViewById(R.id.profile_name);
            usernameTextView = itemView.findViewById(R.id.profile_username);
        }
    }
}
