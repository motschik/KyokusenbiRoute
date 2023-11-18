package com.motschik.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.motschik.route.dijkstra.Line;
import com.motschik.route.dijkstra.Node;
import com.motschik.route.dijkstra.NodeType;
import com.motschik.route.dijkstra.Station;
import com.motschik.route.dijkstra.Track;
import com.motschik.route.json.RouteMapObject;

public class JsonDijkstraMapper {

  public static DijkstraBean dijkstraFromJson(RouteMapObject routeMapObject) {


    Map<String, Line> lineList = new HashMap<>();
    for (var lineObject : routeMapObject.getLineList()) {
      Line line = new Line();
      line.setId(lineObject.getId());
      line.setName(lineObject.getName());
      line.setColor(lineObject.getColor());
      lineList.put(lineObject.getId(), line);
      // System.out.println(line.getName());
    }

    Map<String, Station> stationMap = new HashMap<>();
    List<Track> trackList = new ArrayList<>();

    for (var stationObject : routeMapObject.getStationList()) {
      Station station = new Station();
      station.setId(stationObject.getId());
      station.setName(stationObject.getName());
      stationMap.put(stationObject.getId(), station);
      trackList.add(station);
      for (var trackObject : stationObject.getTracks()) {
        Track track = new Track();
        track.setStation(station);
        track.setNo(trackObject.getNo());
        station.addTrack(track, trackObject.getNo());
        trackList.add(track);
      }

    }
    for (var stationObject : routeMapObject.getStationList()) {
      Station station = stationMap.get(stationObject.getId());
      for (var trackObject : stationObject.getTracks()) {
        if (trackObject.getDistNo() != null) {
          Track track = station.getTrack(trackObject.getNo());
          Station distStation = stationMap.get(trackObject.getDist());
          Track distTrack = distStation.getTrack(trackObject.getDistNo());
          Line nodeLine = lineList.get(trackObject.getLine());
          if (distTrack == null) {
            System.out.println("aaa");
          }
          track.addNode(new Node(distTrack, trackObject.getCost(), nodeLine, NodeType.RAIL,
              trackObject.getForStation()));
        }
      }

      for (var trackEntry : station.getTracks().entrySet()) {
        var track = trackEntry.getValue();
        track.addNode(new Node(station, 10, NodeType.CHANGE));

        for (var transfer : stationObject.getWalkTransfers()) {
          Station transferStation = stationMap.get(transfer.getId());
          track.addNode(new Node(transferStation, transfer.getCost(), NodeType.WALK));
        }
      }
    }

    var ret = new DijkstraBean();
    ret.setLineMap(lineList);
    ret.setStationMap(stationMap);
    ret.setTrackList(trackList);

    return ret;
    // 以下テストコード
  }

}
