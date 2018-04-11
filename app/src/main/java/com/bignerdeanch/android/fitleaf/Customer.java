package com.bignerdeanch.android.fitleaf;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by MFontecchio on 3/30/2018.
 */
public class Customer {

    private UUID mId;
    private String mName;
    private Date mDate;
    private String mPhoto;
    private boolean mCompleted;

    private List<String> mPrevSessions;

    public Customer() {
        this(UUID.randomUUID());
    }

    public Customer(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Date getDate() {
        return mDate;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
