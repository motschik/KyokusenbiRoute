package com.motschik.route.json;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StationObject {

  private String id;
  private String name;
  private String parent;
  private List<TrackObject> tracks;
  @JsonProperty("walk_transfers")
  private List<WalkTransferObject> walkTransfers = new ArrayList<>();
}
