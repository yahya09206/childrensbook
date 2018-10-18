package com.example.yhussein.myapplication;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Gerald Shields on 10/17/2018.
 */

@Entity
public class Book implements Parcelable {

    @PrimaryKey
    @NonNull
    private String Title;
    @ColumnInfo
    private String Category ;
    @ColumnInfo
    private String Description ;
    @ColumnInfo
    private int Thumbnail ;

    public Book() {
    }

    @Ignore
    public Book(String title, String category, String description, int thumbnail) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
    }


    protected Book(Parcel in) {
        Title = in.readString();
        Category = in.readString();
        Description = in.readString();
        Thumbnail = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public int getThumbnail() {
        return Thumbnail;
    }


    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Category);
        dest.writeString(Description);
        dest.writeInt(Thumbnail);
    }
}
