package com.motschik.route.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

  public static void main(String args[]) {

  }

  public static RouteMapObject readLines() {
    String linesJson = readFile("RouteMap.json");
    ObjectMapper mapper = new ObjectMapper();
    try {
      RouteMapObject rmo = mapper.readValue(linesJson, RouteMapObject.class);
      System.out.println(rmo);
      return rmo;
    } catch (JsonProcessingException e) {
      // TODO 自動生成された catch ブロック
      e.printStackTrace();
    }
    return null;
  }

  public static String readFile(String pathStr) {
    try {
      Path path = Paths.get(pathStr);
      return Files.readString(path);
    } catch (IOException e) {
      // TODO 自動生成された catch ブロック
      e.printStackTrace();
    }
    return "";
  }
}
