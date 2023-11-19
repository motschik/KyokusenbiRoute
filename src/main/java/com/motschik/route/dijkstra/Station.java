package com.motschik.route.dijkstra;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Station extends Track {
  private String id;
  private String Name;
  private Map<Integer, Track> tracks = new HashMap<>();

  public Track getTrack(int no) {
    return tracks.get(no);
  }

  public void addTrack(Track track, int no) {
    if (!containsNo(no)) {
      addNode(new Node(this, track, 0, NodeType.START));
      tracks.put(no, track);
    }
  }

  public boolean containsNo(int no) {
    return getTrack(no) != null;
  }
}
