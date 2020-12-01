/*
 * Copyright 2020 Key Bridge. All rights reserved. Use is subject to license
 * terms.
 *
 * This software code is protected by Copyrights and remains the property of
 * Key Bridge and its suppliers, if any. Key Bridge reserves all rights in and to
 * Copyrights and no license is granted under Copyrights in this Software
 * License Agreement.
 *
 * Key Bridge generally licenses Copyrights for commercialization pursuant to
 * the terms of either a Standard Software Source Code License Agreement or a
 * Standard Product License Agreement. A copy of either Agreement can be
 * obtained upon request by sending an email to info@keybridgewireless.com.
 *
 * All information contained herein is the property of Key Bridge and its
 * suppliers, if any. The intellectual and technical concepts contained herein
 * are proprietary.
 */
package ch.keybridge.json.adapter.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.json.bind.adapter.JsonbAdapter;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.WKTReader;

/**
 * JSON adapter implementation to marshal and unmarshal MAP instances of DOUBLE
 * value pairs. This produces a MULTIPOINT encoded String representation of the
 * values. e.g.
 * <pre>
 * DOUBLE ((0.2630339 0.6184835), (0.2564003 0.1303474), (0.1430556 0.227002), (0.5152168 0.0071995))
 * </pre>
 *
 * @author Key Bridge
 * @since v1.0.0 copied 2020-07-15 from json-adapter
 */
public class JsonbMapDoublesAdapter implements JsonbAdapter<Map<Double, Double>, String> {

  /**
   * {@inheritDoc} Marshal a Map of Double pairs into a JTS MULTIPOINT geometry
   * WKT.
   */
  @Override
  public String adaptToJson(Map<Double, Double> orgnl) throws Exception {
    List<Coordinate> coordinates = new ArrayList<>();
    for (Map.Entry<Double, Double> entry : orgnl.entrySet()) {
      coordinates.add(new Coordinate(entry.getKey(), entry.getValue()));
    }
    /**
     * Convert a Map of Double values into a JTS MULTIPOINT geometry. Applying
     * the Precision Model will trim the numbers (key and value) to 4 decimal
     * places. (Scale of 10^3 = 10000, where scale is the amount by which to
     * multiply a coordinate after subtracting the offset, to obtain a precise
     * coordinate).
     */
    return new GeometryFactory(new PrecisionModel(Math.pow(10, 6)))
      .createMultiPoint(coordinates.toArray(new Coordinate[coordinates.size()]))
      .toText().replace("MULTIPOINT", "DOUBLE");
  }

  /**
   * {@inheritDoc} Unmarshal a text-encoded MULTIPOINT geometry into a Map of
   * Double pairs.
   */
  @Override
  public Map<Double, Double> adaptFromJson(String adptd) throws Exception {
    Geometry geometry = new WKTReader().read(adptd.replace("DOUBLE", "MULTIPOINT"));
    Map<Double, Double> treeMap = new TreeMap<>();
    if (geometry instanceof MultiPoint) {
      for (Coordinate coordinate : geometry.getCoordinates()) {
        treeMap.put(coordinate.x, coordinate.y);
      }
    }
    return treeMap;
  }

}
