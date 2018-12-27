package com.rabtman.acgnews.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author Rabtman ishuhui post page
 */
public class SHPage {

  private int count;
  private int totalPages;
  @SerializedName("pageSize")
  private int numsPerPage;
  private int currentPage;
  @SerializedName("data")
  private List<SHPostItem> postItems;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getNumsPerPage() {
    return numsPerPage;
  }

  public void setNumsPerPage(int numsPerPage) {
    this.numsPerPage = numsPerPage;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public List<SHPostItem> getPostItems() {
    return postItems;
  }

  public void setPostItems(List<SHPostItem> postItems) {
    this.postItems = postItems;
  }

  @Override
  public String toString() {
    return "SHPage{" +
        "count=" + count +
        ", totalPages=" + totalPages +
        ", numsPerPage=" + numsPerPage +
        ", currentPage=" + currentPage +
        ", postItems=" + postItems +
        '}';
  }
}