package com.rabtman.acgnews.mvp.model.entity;

/**
 * @author Rabtman
 */

public class SHPostDetail {
  private int id;
  private String title;
  private String brief;
  private String content;
  private String thumb;
  private long time;
  private String authorID;
  private String sourceID;
  private String authorName;
  private String sourceName;

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

  public String getBrief() {
    return brief;
  }

  public void setBrief(String brief) {
    this.brief = brief;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
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

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }
}
