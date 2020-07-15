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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonParser;

/**
 * JSON adapter to translate between a standard java.util.Date instance and the
 * ISO 8601 Date and time format.
 * <p>
 * The International Standard for the representation of dates and times is ISO
 * 8601. ISO 8601 describes an internationally accepted way to represent dates
 * and times using numbers.
 * <p>
 * For example, September 27, 2012 is represented as 2012-09-27 according to the
 * pattern <code>YYYY-MM-DD</code>.
 * <p>
 * ISO 8601 describes a large number of date/time formats. For example it
 * defines Basic Format, without punctuation, and Extended Format, with
 * punctuation, and it allows elements to be omitted. Different standards may
 * need different levels of granularity in the date and time.
 * <p>
 * This profile defines a complete date plus time in UTC (Coordinated Universal
 * Time).
 *
 * @see
 * <a href="http://docs.oracle.com/javase/7/docs/api/java/text/DateFormat.html">DateFormat</a>
 * @see <a href="http://www.w3.org/TR/NOTE-datetime">W3C Date and Time
 * Formats</a>
 * @author Key Bridge
 * @since v1.0.0 created 01/02/18
 * @since v1.0.0 copied 2020-07-15 from json-adapter
 */
public class JsonbDateAdapter {

  /**
   * A complete ISO 8601 Date Time configuration.
   * <p>
   * Different standards may need different levels of granularity in the date
   * and time. ISO 8601 defines six levels. Standards that reference this
   * profile should specify one or more of these granularities. If a given
   * standard allows more than one granularity, it should specify the meaning of
   * the dates and times with reduced precision, for example, the result of
   * comparing two dates with different precisions.
   * <p>
   * This implementation supports version 5: Complete date plus hours, minutes
   * and seconds. {@code YYYY-MM-DDThh:mm:ssTZD} (e.g.
   * 1997-07-16T19:20:30+01:00).
   * <p>
   * Times are expressed in UTC (Coordinated Universal Time), with a special UTC
   * designator ("Z").
   */
  private static final String PATTERN_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  /**
   * UTC (Coordinated Universal Time)
   */
  private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
  /**
   * A date time formatter for the above declared pattern.
   */
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN_DATE_TIME);

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@link JsonSerializer}s too) to serialize Objects into JSON.
   */
  public static class Serializer implements JsonbSerializer<Date> {

    /**
     * {@inheritDoc}
     * <p>
     * Unmarshal a DATE-TIME string into a Date instance. An example input value
     * would be, for example: "2001-10-26T19:32Z"
     */
    @Override
    public void serialize(Date v, javax.json.stream.JsonGenerator jg, SerializationContext sc) {
      DATE_FORMAT.setTimeZone(UTC);
      jg.write(DATE_FORMAT.format(v));
    }
  }

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@link JsonDeserializer}s too) to deserialize Objects of from JSON, using
   * provided {@link JsonParser}.
   */
  public static class Deserializer implements JsonbDeserializer<Date> {

    /**
     * {@inheritDoc}
     * <p>
     * Write an Date to the ISO 8601 DATE-TIME format. An example output value
     * would be, for example: "2001-10-26T19:32Z"
     */
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext dc, Type type) {
      try {
        DATE_FORMAT.setTimeZone(UTC);
        return DATE_FORMAT.parse(jp.getString());
      } catch (ParseException ex) {
        return null;
      }
    }

  }
}
