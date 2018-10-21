package com.example.yhussein.myapplication;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "setting")
public class Setting {
    @PrimaryKey
    @NonNull
    private int bookId;

    @ColumnInfo(name = "book_title")
    private String bookTitle;

    @ColumnInfo(name = "book_author")
    private String bookAuthor;

    @ColumnInfo(name = "reader_name")
    private String readerName;

    @ColumnInfo(name = "book_mark")
    private String bookMark;

    @ColumnInfo(name = "reading_language")
    private String readingLanguage;

    @ColumnInfo(name = "sound_status")
    private String soundStatus;

    @ColumnInfo(name = "photo_id")
    private String photoId;

    @ColumnInfo(name = "audio_url")
    private String audioUrl;

    @ColumnInfo(name = "content_url")
    private String contentUrl;

    @ColumnInfo(name = "like_count")
    private int likeCount;

    // Constructor that is used to create an instance of the Movie object
    public Setting(int bookId, String bookTitle, String bookAuthor, String readerName,
                   String bookMark, String readingLanguage, String soundStatus,
                   String photoId, String audioUrl, String contentUrl, int likeCount) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.readerName = readerName;
        this.bookMark = bookMark;
        this.readingLanguage = readingLanguage;
        this.soundStatus = soundStatus;
        this.photoId = photoId;
        this.audioUrl = audioUrl;
        this.contentUrl = contentUrl;
        this.likeCount = likeCount;
    }

    @NonNull
    public int getBookId() {
        return bookId;
    }

    public void setBookId(@NonNull int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getBookMark() {
        return bookMark;
    }

    public void setBookMark(String bookMark) {
        this.bookMark = bookMark;
    }

    public String getReadingLanguage() {
        return readingLanguage;
    }

    public void setReadingLanguage(String readingLanguage) {
        this.readingLanguage = readingLanguage;
    }

    public String getSoundStatus() {
        return soundStatus;
    }

    public void setSoundStatus(String soundStatus) {
        this.soundStatus = soundStatus;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
