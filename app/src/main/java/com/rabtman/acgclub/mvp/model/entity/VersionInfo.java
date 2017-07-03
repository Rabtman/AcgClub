package com.rabtman.acgclub.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Rabtman
 */

public class VersionInfo implements Parcelable {

  public static final Creator<VersionInfo> CREATOR = new Creator<VersionInfo>() {
    @Override
    public VersionInfo createFromParcel(Parcel source) {
      return new VersionInfo(source);
    }

    @Override
    public VersionInfo[] newArray(int size) {
      return new VersionInfo[size];
    }
  };
  private String versionName;
  private int versionCode;
  private String desc;
  private String appLink;

  public VersionInfo() {
  }

  protected VersionInfo(Parcel in) {
    this.versionName = in.readString();
    this.versionCode = in.readInt();
    this.desc = in.readString();
    this.appLink = in.readString();
  }

  public String getVersionName() {
    return versionName;
  }

  public void setVersionName(String versionName) {
    this.versionName = versionName;
  }

  public int getVersionCode() {
    return versionCode;
  }

  public void setVersionCode(int versionCode) {
    this.versionCode = versionCode;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getAppLink() {
    return appLink;
  }

  public void setAppLink(String appLink) {
    this.appLink = appLink;
  }

  @Override
  public String toString() {
    return "VersionInfo{" +
        "versionName='" + versionName + '\'' +
        ", versionCode=" + versionCode +
        ", desc='" + desc + '\'' +
        ", appLink='" + appLink + '\'' +
        '}';
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.versionName);
    dest.writeInt(this.versionCode);
    dest.writeString(this.desc);
    dest.writeString(this.appLink);
  }
}
