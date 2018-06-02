package com.rabtman.acgnews.mvp.model.entity;

/**
 * @author Rabtman ishuhui post item
 */
public class SHPostItem {

  private int id;
  private String title;
  private int stick;
  private int category;
  private String category_name;
  private String time;
  private String thumb;

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
}