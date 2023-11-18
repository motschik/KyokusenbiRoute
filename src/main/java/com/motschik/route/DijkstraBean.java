package com.motschik.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.motschik.route.dijkstra.Line;
import com.motschik.route.dijkstra.Station;
import com.motschik.route.dijkstra.Track;
import lombok.Data;

@Data
public class DijkstraBean {
  private List<Track> trackList = new ArrayList<>();
  private Map<String, Line> lineMap = new HashMap<>();
  private Map<String, Station> StationMap = new HashMap<>();
  private Track distTrack;
}
