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

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
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
 */
public class JsonbEnvelopeAdapter {

  private static final DecimalFormat DF = new DecimalFormat("0.000000");

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@link JsonSerializer}s too) to serialize Objects into JSON.
   */
  public static class Serializer implements JsonbSerializer<Envelope> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(Envelope v, JsonGenerator generator, SerializationContext ctx) {
      generator.write("ENV(" + DF.format(v.getMinX())
        + ", " + DF.format(v.getMinY())
        + ", " + DF.format(v.getMaxX())
        + ", " + DF.format(v.getMaxY()) + ")");
    }
  }

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@link JsonDeserializer}s too) to deserialize Objects of from JSON, using
   * provided {@link JsonParser}.
   */
  public static class Deserializer implements JsonbDeserializer<Envelope> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Envelope deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
      Pattern p = Pattern.compile("ENV\\((-?\\d+\\.\\d+)\\s*,\\s*(-?\\d+\\.\\d+)\\s*,\\s*(-?\\d+\\.\\d+)\\s*,\\s*(-?\\d+\\.\\d+)\\)");
      Matcher m = p.matcher(parser.getString());
      if (m.find()) {
        return new Envelope(Double.valueOf(m.group(1)),
                            Double.valueOf(m.group(2)),
                            Double.valueOf(m.group(3)),
                            Double.valueOf(m.group(4)));
      }
      return null;
    }

  }
}
