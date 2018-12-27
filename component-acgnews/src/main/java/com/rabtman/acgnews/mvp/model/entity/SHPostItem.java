package com.rabtman.acgnews.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Rabtman ishuhui post item
 */
public class SHPostItem implements Parcelable {

  private int id;
  private String title;
  public static final Creator<SHPostItem> CREATOR = new Creator<SHPostItem>() {
    @Override
    public SHPostItem createFromParcel(Parcel source) {
      return new SHPostItem(source);
    }

    @Override
    public SHPostItem[] newArray(int size) {
      return new SHPostItem[size];
    }
  };
  private String brief;
  private String thumb;
  private int sticky;
  private long time;
  private String authorID;
  private String sourceID;
  private String authorName;
  private String categoryIDs;


  public SHPostItem() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String sourceName;

  protected SHPostItem(Parcel in) {
    this.id = in.readInt();
    this.title = in.readString();
    this.brief = in.readString();
    this.sticky = in.readInt();
    this.thumb = in.readString();
    this.time = in.readLong();
    this.authorID = in.readString();
    this.sourceID = in.readString();
    this.authorName = in.readString();
    this.categoryIDs = in.readString();
    this.sourceName = in.readString();
  }

  public String getBrief() {
    return brief;
  }

  public void setBrief(String brief) {
    this.brief = brief;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public int getSticky() {
    return sticky;
  }

  public void setSticky(int sticky) {
    this.sticky = sticky;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getAuthorID() {
    return authorID;
  }

  public void setAuthorID(String authorID) {
    this.authorID = authorID;
  }

  public String getSourceID() {
    return sourceID;
  }

  public void setSourceID(String sourceID) {
    this.sourceID = sourceID;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getCategoryIDs() {
    return categoryIDs;
  }

  public void setCategoryIDs(String categoryIDs) {
    this.categoryIDs = categoryIDs;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.title);
    dest.writeString(this.brief);
    dest.writeInt(this.sticky);
    dest.writeString(this.thumb);
    dest.writeLong(this.time);
    dest.writeString(this.authorID);
    dest.writeString(this.sourceID);
    dest.writeString(this.authorName);
    dest.writeString(this.categoryIDs);
    dest.writeString(this.sourceName);
  }

  @Override
  public String toString() {
    return "SHPostItem{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", brief='" + brief + '\'' +
        ", sticky=" + sticky +
        ", thumb='" + thumb + '\'' +
        ", time=" + time +
        ", authorID='" + authorID + '\'' +
        ", sourceID='" + sourceID + '\'' +
        ", authorName='" + authorName + '\'' +
        ", categoryIDs='" + categoryIDs + '\'' +
        ", sourceName='" + sourceName + '\'' +
        '}';
  }
}