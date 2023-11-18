package com.motschik.route.dijkstra;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Track {
  private List<Node> nodes = new ArrayList<>();

  private boolean confirm = false;
  private Integer totalCost = 1000000;
  private List<Node> minRoute = new ArrayList<>();

  private Station station;
  private Integer no;
  // private Line line;

  public void addNode(Node node) {
    nodes.add(node);
  }

  public void calcNodes() {
    for (Node node : nodes) {
      List<Node> newRoute = new ArrayList<>();
      newRoute.addAll(minRoute);
      newRoute.add(node);
      node.getDist().updateCostIfUpper(totalCost + node.getCost(), newRoute);
    }
  }

  public void updateCostIfUpper(int cost, List<Node> route) {
    if (!confirm && cost < totalCost) {
      totalCost = cost;
      minRoute = route;
    }
  }

  public void setStart() {
    confirm = true;
    totalCost = 0;
  }

  public boolean isNotConfirm() {
    return !confirm;
  }

  public boolean achieveStation(Station dist) {
    if (station == null) {
      return false;
    }
    return station.getId().equals(dist.getId());
  }
}
