package com.motschik.route.json;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RouteMapObject {
  @JsonProperty("line_list")
  private List<LineObject> lineList;

  @JsonProperty("station_list")
  private List<StationObject> stationList;


}
