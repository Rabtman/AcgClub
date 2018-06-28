package com.rabtman.acgnews.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Rabtman ishuhui post item
 */
public class SHPostItem implements Parcelable {

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
  private int id;
  private String title;
  private int stick;
  private int category;
  private String category_name;
  private String time;
  private String thumb;

  public SHPostItem() {
  }

  protected SHPostItem(Parcel in) {
    this.id = in.readInt();
    this.title = in.readString();
    this.stick = in.readInt();
    this.category = in.readInt();
    this.category_name = in.readString();
    this.time = in.readString();
    this.thumb = in.readString();
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

  public int getStick() {
    return stick;
  }

  public void setStick(int stick) {
    this.stick = stick;
  }

  public int getCategory() {
    return category;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public String getCategory_name() {
    return category_name;
  }

  public void setCategory_name(String category_name) {
    this.category_name = category_name;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.title);
    dest.writeInt(this.stick);
    dest.writeInt(this.category);
    dest.writeString(this.category_name);
    dest.writeString(this.time);
    dest.writeString(this.thumb);
  }

  @Override
  public String toString() {
    return "SHPostItem{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", stick=" + stick +
        ", category=" + category +
        ", category_name='" + category_name + '\'' +
        ", time='" + time + '\'' +
        ", thumb='" + thumb + '\'' +
        '}';
  }
}