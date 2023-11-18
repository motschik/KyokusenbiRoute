package com.motschik.route.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TrackObject {
  private Integer no;
  private String line;
  private Integer cost;
  private String dist;
  @JsonProperty("dist_no")
  private Integer distNo;
  @JsonProperty("for")
  private String forStation;
}
