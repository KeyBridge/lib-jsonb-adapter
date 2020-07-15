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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.bind.adapter.JsonbAdapter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

/**
 * JSON adapter to marshal and unmarshal Geometry class types.
 *
 * @author Key Bridge
 * @since v0.0.1 created 2020-07-15
 * @since v1.0.0 copied 2020-07-15 from json-adapter
 */
public class JsonbGeometryAdapter implements JsonbAdapter<Geometry, String> {

  private static final Logger LOG = Logger.getLogger(JsonbGeometryAdapter.class.getName());

  /**
   * {@inheritDoc} Important. Must specify a 3D WKT Writer to output the
   * Z-component of the geometry. "toString()" and the default WKTWriter are
   * 2-dimensional and do not output the Z-component.
   */
  @Override
  public String adaptToJson(Geometry obj) throws Exception {
    return new WKTWriter(3).write(obj);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Geometry adaptFromJson(String obj) throws Exception {
    try {
      return new WKTReader().read(obj);
    } catch (ParseException ex) {
      LOG.log(Level.WARNING, "WKT geometry parse error {0}. {1}", new Object[]{ex.getMessage(), obj});
      return null;
    }
  }

}
