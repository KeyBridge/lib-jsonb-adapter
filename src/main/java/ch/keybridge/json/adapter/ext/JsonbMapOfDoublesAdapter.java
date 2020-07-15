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

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

/**
 * JSON adapter implementation to marshal and unmarshal MAP instances of DOUBLE
 * value pairs.
 * <p>
 * This produces a MULTIPOINT encoded String representation of the values. e.g.
 * <pre>
 * DOUBLE ((0.2630339 0.6184835), (0.2564003 0.1303474), (0.1430556 0.227002), (0.5152168 0.0071995))
 * </pre>
 *
 * @author Key Bridge
 * @since v0.0.1 created 01/02/18
 */
public class JsonbMapOfDoublesAdapter {

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@code JsonSerializer}s too) to serialize Objects into JSON.
   */
  public static class Serializer implements JsonbSerializer<Map<Double, Double>> {

    /**
     * {@inheritDoc}
     * <p>
     * Convert the Map of Double values into a JTS MULTIPOINT geometry. Applying
     * the Precision Model will trim the numbers (key and value) to 4 decimal
     * places. (Scale of 10^3 = 10000, where scale is the amount by which to
     * multiply a coordinate after subtracting the offset, to obtain a precise
     * coordinate).
     */
    @Override
    public void serialize(Map<Double, Double> v, JsonGenerator jg, SerializationContext sc) {
      if (v.isEmpty()) {
        jg.writeNull();
        return;
      }
      List<Coordinate> coordinates = v.entrySet().stream().map(e -> new Coordinate(e.getKey(), e.getValue())).collect(Collectors.toList());
      jg.write(new GeometryFactory(new PrecisionModel(Math.pow(10, 6)))
        .createMultiPoint(coordinates.toArray(new Coordinate[coordinates.size()]))
        .toText().replace("MULTIPOINT", "DOUBLE"));
    }

  }

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@code JsonDeserializer}s too) to deserialize Objects of from JSON, using
   * provided {@code JsonParser}.
   */
  public static class Deserializer implements JsonbDeserializer<Map<Double, Double>> {

    /**
     * {@inheritDoc}
     * <p>
     * Convert a Map of Double values into a JTS MULTIPOINT geometry. Applying
     * the Precision Model will trim the numbers (key and value) to 4 decimal
     * places. (Scale of 10^3 = 10000, where scale is the amount by which to
     * multiply a coordinate after subtracting the offset, to obtain a precise
     * coordinate).
     */
    @Override
    public Map<Double, Double> deserialize(JsonParser jp, DeserializationContext dc, Type type) {
      try {
        Map<Double, Double> treeMap = new TreeMap<>();
        Geometry geometry = new WKTReader().read(jp.getString().replace("DOUBLE", "MULTIPOINT"));
        if (geometry instanceof MultiPoint) {
          for (Coordinate coordinate : geometry.getCoordinates()) {
            treeMap.put(coordinate.x, coordinate.y);
          }
        }
        return treeMap;
      } catch (ParseException exception) {
        return null;
      }
    }

  }
}
