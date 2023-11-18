package com.motschik.route.route;

import com.motschik.route.dijkstra.Line;
import lombok.Data;

@Data
public class RouteBeanLine extends RouteBean {
  private String name;
  private String color;
  private Integer cost = 0;
  private Integer stationCount = 0;
  private Line line;
  private String forStation;

  public void addCost(int i) {
    cost += i;
  }

  public void incrementStationCount() {
    stationCount++;
  }
}
