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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

/**
 * JSON adapter to translate between a standard java.time.ZonedDateTime instance
 * and the ISO 8601 Date format. The ISO instant formatter that formats or
 * parses an instant in UTC, such as '2011-12-03T10:15:30Z'.
 *
 * @see
 * <a href="http://docs.oracle.com/javase/7/docs/api/java/text/DateFormat.html">DateFormat</a>
 * @see <a href="http://www.w3.org/TR/NOTE-datetime">W3C Date and Time
 * Formats</a>
 * @author Key Bridge
 * @since v0.0.1 created 01/02/18
 * @since v1.0.0 copied 2020-07-15
 */
public class JsonbZonedDateTimeAdapter {

  /**
   * The ISO date-time formatter that formats or parses a date-time without an
   * offset, such as '2011-12-03T10:15:30'. This returns an immutable formatter
   * capable of formatting and parsing the ISO-8601 extended offset date-time
   * format.
   */
  protected static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@link JsonSerializer}s too) to serialize Objects into JSON.
   */
  public static class Serializer implements JsonbSerializer<ZonedDateTime> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(ZonedDateTime v, JsonGenerator jg, SerializationContext sp) {
      jg.write(v.format(DATETIME_FORMATTER));
    }
  }

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@link JsonDeserializer}s too) to deserialize Objects of from JSON, using
   * provided {@link JsonParser}.
   */
  public static class Deserializer implements JsonbDeserializer<ZonedDateTime> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ZonedDateTime deserialize(JsonParser jp, DeserializationContext dc, Type type) {
      return ZonedDateTime.parse(jp.getString(), DATETIME_FORMATTER);
    }

  }
}
