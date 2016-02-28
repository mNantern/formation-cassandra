package fr.xebia.training.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ResultPage<T> {

  private List<T> results;

  private  String pagingState;

  public ResultPage() {
    results = new ArrayList<>();
  }

  public ResultPage(List<T> results, String pagingState) {
    this.results = results;
    this.pagingState = pagingState;
  }

  public List<T> getResults() {
    return results;
  }

  public void setResults(List<T> results) {
    this.results = results;
  }

  public String getPagingState() {
    return pagingState;
  }

  public void setPagingState(String pagingState) {
    this.pagingState = pagingState;
  }
}
