package com.motschik.route.route;

import java.util.ArrayList;
import java.util.List;
import com.motschik.route.DijkstraBean;
import com.motschik.route.dijkstra.Node;
import com.motschik.route.dijkstra.NodeType;
import com.motschik.route.dijkstra.Station;

public class RouteFromDijkstra {

  public static List<RouteBean> routeFromDijkstra(DijkstraBean input) {

    // 最短経路
    var minRoute = input.getDistTrack().getMinRoute();

    List<RouteBean> routeList = new ArrayList<>();

    boolean first = true;
    Node beforeNode = null;
    var nodeIterator = minRoute.iterator();
    Node node = null;
    RouteBeanLine lineBean = null;
    Integer totalCost = 0;

    while (nodeIterator.hasNext()) {
      beforeNode = node;
      node = nodeIterator.next();
      switch (node.getType()) {
        case START:
          if (first) {
            var stationBean = new RouteBeanStation();
            stationBean.setName(node.getDist().getStation().getName());
            stationBean.setRouteType(RouteType.STATION);
            stationBean.setViaFlag(false);
            stationBean.setDepartureNo(node.getDist().getNo());
            routeList.add(stationBean);
            first = false;
          }
          break;
        case RAIL:
          if (lineBean == null) {
            lineBean = new RouteBeanLine();
            lineBean.setLine(node.getLine());
            lineBean.setName(node.getLine().getName());
            lineBean.setRouteType(RouteType.LINE);
            lineBean.setColor(node.getLine().getColor());
            lineBean.setCost(node.getCost());
            lineBean.setForStation(node.getForStation());
            lineBean.setStationCount(1);
            routeList.add(lineBean);
            totalCost += node.getCost();
          } else {
            if (lineBean.getLine() != node.getLine()) {
              Station station = node.getFrom().getStation();
              var stationBean = new RouteBeanStation();
              stationBean.setName(station.getName());
              stationBean.setRouteType(RouteType.STATION);
              stationBean.setViaFlag(true);
              routeList.add(stationBean);

              lineBean = new RouteBeanLine();
              lineBean.setLine(node.getLine());
              lineBean.setName(node.getLine().getName());
              lineBean.setRouteType(RouteType.LINE);
              lineBean.setColor(node.getLine().getColor());
              lineBean.setCost(node.getCost());
              lineBean.setForStation(node.getForStation());
              lineBean.setStationCount(1);
              routeList.add(lineBean);
              totalCost += node.getCost();
            } else {
              lineBean.addCost(node.getCost() + 1);
              lineBean.incrementStationCount();
              totalCost += node.getCost() + 1;
            }
          }
          break;
        case CHANGE:
          if (lineBean != null) {
            lineBean = null;
          }
          if (node.getDist() instanceof Station) {
            Station station = (Station) node.getDist();
            var stationBean = new RouteBeanStation();
            stationBean.setName(station.getName());
            stationBean.setRouteType(RouteType.STATION);
            stationBean.setViaFlag(false);
            stationBean.setArriveNo(beforeNode.getDist().getNo());
            stationBean.setCost(node.getCost());
            totalCost += node.getCost();
            beforeNode = node;
            node = nodeIterator.next();
            stationBean.setDepartureNo(node.getDist().getNo());
            routeList.add(stationBean);

          }
          break;
        case WALK:
          if (lineBean != null) {
            lineBean = null;
          }
          if (node.getDist() instanceof Station) {
            if (!first) {
              Station station1 = beforeNode.getDist().getStation();
              var stationBean1 = new RouteBeanStation();
              stationBean1.setName(station1.getName());
              stationBean1.setRouteType(RouteType.STATION);
              stationBean1.setViaFlag(false);
              stationBean1.setArriveNo(beforeNode.getDist().getNo());
              routeList.add(stationBean1);
            } else {
              var stationBean = new RouteBeanStation();
              stationBean.setName(node.getFrom().getStation().getName());
              stationBean.setRouteType(RouteType.STATION);
              stationBean.setViaFlag(false);
              stationBean.setDepartureNo(node.getDist().getNo());
              routeList.add(stationBean);
              first = false;
            }

            var walkBean = new RouteBeanWalk();
            walkBean.setCost(node.getCost());
            walkBean.setRouteType(RouteType.WALK);
            routeList.add(walkBean);

            totalCost += node.getCost();

            Station station2 = (Station) node.getDist();
            var stationBean2 = new RouteBeanStation();
            stationBean2.setName(station2.getName());
            stationBean2.setRouteType(RouteType.STATION);
            stationBean2.setViaFlag(false);
            beforeNode = node;
            if (nodeIterator.hasNext()) {
              node = nodeIterator.next();
              stationBean2.setDepartureNo(node.getDist().getNo());
              routeList.add(stationBean2);
            }
          }
      }
    }
    var stationBean = new RouteBeanStation();
    stationBean.setName(node.getDist().getStation().getName());
    stationBean.setRouteType(RouteType.STATION);
    stationBean.setViaFlag(false);
    if (beforeNode.getType() == NodeType.RAIL) {
      stationBean.setArriveNo(node.getDist().getNo());
    }
    routeList.add(stationBean);

    return routeList;

  }
}
