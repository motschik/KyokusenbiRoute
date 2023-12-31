package com.motschik.route.dijkstra;

import lombok.Data;

@Data
public class Node {

  private Track from;
  private Track dist;
  private Integer cost;

  private Line line;
  private NodeType type;
  private String forStation;

  public Node(Track from, Track dist, Integer cost, Line line, NodeType type, String forStation) {
    this.from = from;
    this.dist = dist;
    this.cost = cost;
    this.line = line;
    this.type = type;
    this.forStation = forStation;
  }

  public Node(Track from, Track dist, Integer cost, NodeType type) {
    this.from = from;
    this.dist = dist;
    this.cost = cost;
    this.type = type;
  }

}
