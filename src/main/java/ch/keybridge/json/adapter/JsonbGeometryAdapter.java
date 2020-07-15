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
package ch.keybridge.json.adapter;

import java.lang.reflect.Type;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

/**
 * JSON adapter to marshal and unmarshal Geometry class types.
 *
 * @author Key Bridge
 */
public class JsonbGeometryAdapter {

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@link JsonSerializer}s too) to serialize Objects into JSON.
   */
  public static class Serializer implements JsonbSerializer<Geometry> {

    /**
     * {@inheritDoc} Important. Must specify a 3D WKT Writer to output the
     * Z-component of the geometry. "toString()" and the default WKTWriter are
     * 2-dimensional and do not output the Z-component.
     */
    @Override
    public void serialize(Geometry t, JsonGenerator jg, SerializationContext sc) {
      jg.write(new WKTWriter(3).write(t));
    }
  }

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@link JsonDeserializer}s too) to deserialize Objects of from JSON, using
   * provided {@link JsonParser}.
   */
  public static class Deserializer implements JsonbDeserializer<Geometry> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Geometry deserialize(JsonParser jp, DeserializationContext dc, Type type) {
      try {
        return new WKTReader().read(jp.getString());
      } catch (ParseException ex) {
        return null;
      }
    }

  }

}
