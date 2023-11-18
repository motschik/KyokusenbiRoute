package com.motschik.route.web;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.motschik.route.DijkstraBean;
import com.motschik.route.JsonDijkstraMapper;
import com.motschik.route.dijkstra.Dijkstra;
import com.motschik.route.dijkstra.DijkstraImpl;
import com.motschik.route.json.JsonReader;
import com.motschik.route.route.RouteBeanLine;
import com.motschik.route.route.RouteBeanStation;
import com.motschik.route.route.RouteBeanWalk;
import com.motschik.route.route.RouteFromDijkstra;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RouteController {

  @GetMapping()
  public String index(Model model) {

    var rmo = JsonReader.readLines();
    var stationList = rmo.getStationList();

    model.addAttribute("stationList", stationList);

    return "index";
  }

  @PostMapping("search")
  public String search(Model model, @RequestParam("from") String from,
      @RequestParam("to") String to) {
    var rmo = JsonReader.readLines();
    var stationList = rmo.getStationList();

    model.addAttribute("stationList", stationList);
    log.info(from + "→" + to);

    model.addAttribute("route", calcRoute(from, to));
    model.addAttribute("selectedFrom", from);
    model.addAttribute("selectedTo", to);

    return "search";
  }

  private List<String> calcRoute(String from, String to) {
    var rmo = JsonReader.readLines();

    var dijkstraBean = JsonDijkstraMapper.dijkstraFromJson(rmo);

    Dijkstra dik = new DijkstraImpl();
    var stationMap = dijkstraBean.getStationMap();
    var trackList = dijkstraBean.getTrackList();

    var result = dik.dijkstra(stationMap.get(from), stationMap.get(to), trackList);

    var dijkstraInputBean = new DijkstraBean();
    dijkstraInputBean.setDistTrack(result);
    dijkstraInputBean.setLineMap(dijkstraBean.getLineMap());
    dijkstraInputBean.setStationMap(dijkstraBean.getStationMap());
    dijkstraInputBean.setTrackList(dijkstraBean.getTrackList());

    var routeBeanList = RouteFromDijkstra.routeFromDijkstra(dijkstraInputBean);

    // StringBuilder sb = new StringBuilder();
    List<String> routeList = new ArrayList<>();

    for (var routeBean : routeBeanList) {
      switch (routeBean.getRouteType()) {
        case LINE:
          RouteBeanLine lineBean = (RouteBeanLine) routeBean;

          routeList.add(lineBean.getStationCount() + "駅 | " + lineBean.getName() + " "
              + lineBean.getForStation() + "行き " + lineBean.getCost() + "秒");
          break;
        case STATION:
          RouteBeanStation stationBean = (RouteBeanStation) routeBean;
          StringBuilder sb = new StringBuilder();
          if (stationBean.isViaFlag()) {
            sb.append("<直通> ");
          }
          sb.append(stationBean.getName());
          if (stationBean.getArriveNo() != null) {
            sb.append(" " + (stationBean.getArriveNo() + 1) + "番線着");
          }
          if (stationBean.getDepartureNo() != null) {
            sb.append(" " + (stationBean.getDepartureNo() + 1) + "番線発");
          }
          routeList.add(sb.toString());
          break;
        case WALK:
          RouteBeanWalk walkBean = (RouteBeanWalk) routeBean;
          routeList.add("  ｜ 徒歩 " + walkBean.getCost() + "秒");
      }
      // sb.append("<br>\n");
    }

    return routeList;


  }

}
