package com.motschik.route.dijkstra;

import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DijkstraImpl implements Dijkstra {

  @Override
  public void dijkstra(Track startTrack, Collection<Track> tracks) {
    // スタート地点の設定
    startTrack.setStart();
    // 未確定のTrackが無くなるまで繰り返し
    while (tracks.stream().anyMatch(Track::isNotConfirm)) {
      // 各TrackからNodeで繋がる先のコストを計算
      tracks.stream().filter(Track::isConfirm).forEach(Track::calcNodes);
      // 未確定のTrackのうち最小のものを確定
      Optional<Track> minTrack = tracks.stream().filter(Track::isNotConfirm)
          .min((t1, t2) -> t1.getTotalCost() - t2.getTotalCost());
      minTrack.get().setConfirm(true);
    }
  }

  @Override
  public Track dijkstra(Track startTrack, Station dist, Collection<Track> tracks) {
    // スタート地点の設定
    startTrack.setStart();
    // 未確定のTrackが無くなるまで繰り返し
    while (tracks.stream().anyMatch(Track::isNotConfirm)) {
      // 各TrackからNodeで繋がる先のコストを計算
      tracks.stream().filter(Track::isConfirm).forEach(Track::calcNodes);
      // 未確定のTrackのうち最小のものを確定
      Optional<Track> minTrack = tracks.stream().filter(Track::isNotConfirm)
          .min((t1, t2) -> t1.getTotalCost() - t2.getTotalCost());
      minTrack.get().setConfirm(true);

      if (minTrack.get().achieveStation(dist)) {
        return minTrack.get();
      }
    }
    return null;
  }
}
