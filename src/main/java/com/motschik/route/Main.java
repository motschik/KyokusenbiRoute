package com.motschik.route;

import java.util.Map;
import com.motschik.route.dijkstra.Dijkstra;
import com.motschik.route.dijkstra.DijkstraImpl;
import com.motschik.route.dijkstra.Line;
import com.motschik.route.dijkstra.Node;
import com.motschik.route.dijkstra.NodeType;
import com.motschik.route.dijkstra.Station;
import com.motschik.route.json.JsonReader;
import com.motschik.route.json.RouteMapObject;

public class Main {
  public static void main(String args[]) {
    System.out.println("aaaa");
    var rmo = JsonReader.readLines();

    var dijkstraBean = JsonDijkstraMapper.dijkstraFromJson(rmo);

    outputRouteTest(dijkstraBean);

  }

  private static void outputRouteTest(DijkstraBean dijkstraBean) {
    Dijkstra dik = new DijkstraImpl();
    var stationMap = dijkstraBean.getStationMap();
    var trackList = dijkstraBean.getTrackList();

    var result = dik.dijkstra(stationMap.get("laugh2"), stationMap.get("sap"), trackList);

    Node before = null;
    for (var route : result.getMinRoute()) {

      if (before == null) {
        before = route;
        Station station = route.getDist().getStation();
        if (station != null) {
          System.out.println(station.getName());
        }
        continue;
      }

      Line beforeLine = outputText(before, route);
      before = route;
    }
    outputTextLast(before, result.getMinRoute().get(result.getMinRoute().size() - 1));
  }

  private static Line outputText(Node before, Node route) {
    Line beforeLine = before.getLine();
    switch (route.getType()) {
      case CHANGE:
        if (before.getType() == NodeType.RAIL) {
          System.out
              .println("　｜" + before.getLine().getName() + " " + before.getForStation() + "行き");
          System.out.println(before.getDist().getStation().getName());
        }
        break;
      case WALK:
        if (before.getType() == NodeType.RAIL) {
          System.out
              .println("　｜" + before.getLine().getName() + " " + before.getForStation() + "行き");
          System.out.println(before.getDist().getStation().getName());
        }
        System.out.println("　・徒歩");
        break;
      case RAIL:
        if (before.getType() == NodeType.RAIL && beforeLine != route.getLine()) {
          System.out
              .println("　｜" + before.getLine().getName() + " " + before.getForStation() + "行き");
          System.out.println("＜直通＞ " + before.getDist().getStation().getName());
        }
        break;
      case START:
        if (before.getType() == NodeType.WALK) {
          System.out.println(route.getDist().getStation().getName());
        }
    }
    return beforeLine;
  }

  private static Line outputTextLast(Node before, Node route) {
    Line beforeLine = before.getLine();
    switch (route.getType()) {
      case CHANGE:
        break;
      case WALK:
        System.out.println("　｜徒歩");
        System.out.println(route.getDist().getStation().getName());
        break;
      case RAIL:
        System.out.println("　｜" + before.getLine().getName() + " " + before.getForStation() + "行き");
        System.out.println(route.getDist().getStation().getName());
        break;
      case START:
    }
    return beforeLine;
  }


  private static void outputStations(RouteMapObject routeMapObject, Map<String, Line> lineList,
      Map<String, Station> stationMap) {
    // 駅情報出力用
    for (var stationObject : routeMapObject.getStationList()) {
      Station station = stationMap.get(stationObject.getId());
      System.out.println("【" + station.getName() + "】");
      for (var trackObject : stationObject.getTracks()) {
        System.out.println(
            "  " + trackObject.getNo() + " : " + lineList.get(trackObject.getLine()).getName() + " "
                + trackObject.getForStation() + "行き");
      }
      System.out.println("");
    }
  }
}
