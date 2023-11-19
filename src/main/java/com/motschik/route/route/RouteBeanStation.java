package com.motschik.route.route;

import lombok.Data;

@Data
public class RouteBeanStation extends RouteBean {
  private String name;
  private boolean viaFlag = false;
  private Integer arriveNo;
  private Integer departureNo;
  private Integer cost;
}
