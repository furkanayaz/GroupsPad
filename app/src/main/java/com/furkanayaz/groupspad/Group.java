package com.furkanayaz.groupspad;

public class Group {
    private String id;
    private String uid;
    private String textViewGroupName;
    private String textViewGroupDescription;
    private String groupuri;
    private String imageViewGroupApp;
    private String textViewGroupDate;
    private String textViewGroupCountry;

    public Group(String id, String uid, String textViewGroupName, String textViewGroupDescription, String groupuri, String imageViewGroupApp, String textViewGroupDate, String textViewGroupCountry) {
        this.id = id;
        this.uid = uid;
        this.textViewGroupName = textViewGroupName;
        this.textViewGroupDescription = textViewGroupDescription;
        this.groupuri = groupuri;
        this.imageViewGroupApp = imageViewGroupApp;
        this.textViewGroupDate = textViewGroupDate;
        this.textViewGroupCountry = textViewGroupCountry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTextViewGroupName() {
        return textViewGroupName;
    }

    public void setTextViewGroupName(String textViewGroupName) {
        this.textViewGroupName = textViewGroupName;
    }

    public String getTextViewGroupDescription() {
        return textViewGroupDescription;
    }

    public void setTextViewGroupDescription(String textViewGroupDescription) {
        this.textViewGroupDescription = textViewGroupDescription;
    }

    public String getGroupuri() {
        return groupuri;
    }

    public void setGroupuri(String groupuri) {
        this.groupuri = groupuri;
    }

    public String getImageViewGroupApp() {
        return imageViewGroupApp;
    }

    public void setImageViewGroupApp(String imageViewGroupApp) {
        this.imageViewGroupApp = imageViewGroupApp;
    }

    public String getTextViewGroupDate() {
        return textViewGroupDate;
    }

    public void setTextViewGroupDate(String textViewGroupDate) {
        this.textViewGroupDate = textViewGroupDate;
    }

    public String getTextViewGroupCountry() {
        return textViewGroupCountry;
    }

    public void setTextViewGroupCountry(String textViewGroupCountry) {
        this.textViewGroupCountry = textViewGroupCountry;
    }
}
