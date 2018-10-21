package com.example.yhussein.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<Setting> mData;


    public RecyclerViewAdapter(Context mContext, List<Setting> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(mData.get(position).getBookTitle());
        holder.tv_book_author.setText(mData.get(position).getBookAuthor());
        holder.img_book_thumbnail.setImageResource(mData.get(position).getBookId());
        holder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ReadActivity.class);

                // passing data to the book activity
                intent.putExtra("Id", mData.get(position).getPhotoId());
                intent.putExtra("Title", mData.get(position).getBookTitle());
                intent.putExtra("Author", mData.get(position).getBookAuthor());
                intent.putExtra("Description", mData.get(position).getContentUrl());
                intent.putExtra("Thumbnail", mData.get(position).getBookId());
                // start the activity
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        TextView tv_book_author;
        ImageView img_book_thumbnail;
        Button actionButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id);
            tv_book_author = (TextView) itemView.findViewById(R.id.book_author_id);
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            actionButton = itemView.findViewById(R.id.action_button);
        }
    }
}
