package com.motschik.route.dijkstra;

import java.util.Collection;

public interface Dijkstra {

  public void dijkstra(Track startTrack, Collection<Track> tracks);

  public Track dijkstra(Track startTrack, Station dist, Collection<Track> tracks);

}
