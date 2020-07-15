/*
 * Copyright 2019 Key Bridge.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.keybridge.json.adapter;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.bind.adapter.JsonbAdapter;
import org.locationtech.jts.geom.Envelope;

/**
 * JSON adapter to marshal and unmarshal Envelope class types.
 * <p>
 * Note that JTS {@code Envelope} class marshals as [Xmin, Xmax, Ymin, Ymax].
 * This marshals the format [Xmin, Ymin, Xmax, Ymax], which is used by GML and
 * WFS.
 *
 * @author Key Bridge
 * @since v0.0.1 created 01/02/18
 * @since v1.0.0 copied 2020-07-15 from json-adapter
 */
public class JsonbEnvelopeAdapter implements JsonbAdapter<Envelope, String> {

  private static final DecimalFormat DF = new DecimalFormat("0.000000");
  private static final Logger LOG = Logger.getLogger(JsonbEnvelopeAdapter.class.getName());

  /**
   * {@inheritDoc}
   */
  @Override
  public String adaptToJson(Envelope obj) throws Exception {
    return "ENV(" + DF.format(obj.getMinX())
      + ", " + DF.format(obj.getMinY())
      + ", " + DF.format(obj.getMaxX())
      + ", " + DF.format(obj.getMaxY()) + ")";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Envelope adaptFromJson(String obj) throws Exception {
    Pattern p = Pattern.compile("ENV\\((-?\\d+\\.\\d+)\\s*,\\s*(-?\\d+\\.\\d+)\\s*,\\s*(-?\\d+\\.\\d+)\\s*,\\s*(-?\\d+\\.\\d+)\\)");
    Matcher m = p.matcher(obj);
    if (m.find()) {
      return new Envelope(Double.valueOf(m.group(1)),
                          Double.valueOf(m.group(2)),
                          Double.valueOf(m.group(3)),
                          Double.valueOf(m.group(4)));
    } else {
      LOG.log(Level.WARNING, "WKT envelope parse error. {1}", new Object[]{obj});
      return null;
    }
  }

}
