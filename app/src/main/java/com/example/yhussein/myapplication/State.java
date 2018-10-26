package com.example.yhussein.myapplication;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "state")
public class State {
    @PrimaryKey
    @NonNull
    private int stateId;

    @ColumnInfo(name = "book_mark")
    private int bookMark;

    @ColumnInfo(name = "reader_ip")
    private String readerIp;

    @ColumnInfo(name = "reader_language")
    private String readerLanguage;

    @ColumnInfo(name = "sound_status")
    private String soundStatus;

    @NonNull
    public int getStateId() {
        return stateId;
    }

    public void setStateId(@NonNull int stateId) {
        this.stateId = stateId;
    }

    public int getBookMark() {
        return bookMark;
    }

    public void setBookMark(int bookMark) {
        this.bookMark = bookMark;
    }

    public String getReaderIp() {
        return readerIp;
    }

    public void setReaderIp(String readerIp) {
        this.readerIp = readerIp;
    }

    public String getReaderLanguage() {
        return readerLanguage;
    }

    public void setReaderLanguage(String readerLanguage) {
        this.readerLanguage = readerLanguage;
    }

    public String getSoundStatus() {
        return soundStatus;
    }

    public void setSoundStatus(String soundStatus) {
        this.soundStatus = soundStatus;
    }
}
