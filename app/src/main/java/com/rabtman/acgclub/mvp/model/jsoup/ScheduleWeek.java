package com.rabtman.acgclub.mvp.model.jsoup;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import me.ghui.fruit.annotations.Pick;

/**
 * @author Rabtman 追番信息
 */
public class ScheduleWeek implements Parcelable{

  @Pick("div.week_item")
  private List<ScheduleItem> scheduleItems;

  public List<ScheduleItem> getScheduleItems() {
    return scheduleItems;
  }

  public void setScheduleItems(List<ScheduleItem> scheduleItems) {
    this.scheduleItems = scheduleItems;
  }


  public static class ScheduleItem implements Parcelable{

    @Pick("li.week_item_left")
    private String name;
    @Pick("li.week_item_right")
    private String episode;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEpisode() {
      return episode;
    }

    public void setEpisode(String episode) {
      this.episode = episode;
    }


    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.name);
      dest.writeString(this.episode);
    }

    public ScheduleItem() {
    }

    protected ScheduleItem(Parcel in) {
      this.name = in.readString();
      this.episode = in.readString();
    }

    public static final Creator<ScheduleItem> CREATOR = new Creator<ScheduleItem>() {
      @Override
      public ScheduleItem createFromParcel(Parcel source) {
        return new ScheduleItem(source);
      }

      @Override
      public ScheduleItem[] newArray(int size) {
        return new ScheduleItem[size];
      }
    };
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeTypedList(this.scheduleItems);
  }

  public ScheduleWeek() {
  }

  protected ScheduleWeek(Parcel in) {
    this.scheduleItems = in.createTypedArrayList(ScheduleItem.CREATOR);
  }

  public static final Creator<ScheduleWeek> CREATOR = new Creator<ScheduleWeek>() {
    @Override
    public ScheduleWeek createFromParcel(Parcel source) {
      return new ScheduleWeek(source);
    }

    @Override
    public ScheduleWeek[] newArray(int size) {
      return new ScheduleWeek[size];
    }
  };
}
