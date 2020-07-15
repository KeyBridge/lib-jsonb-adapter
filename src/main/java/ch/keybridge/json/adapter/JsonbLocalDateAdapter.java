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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonParser;

/**
 * JSON adapter to translate between a standard java.time.LocalDate instance and
 * the ISO 8601 Date format. The ISO instant formatter that formats or parses an
 * instant in UTC, such as '2011-12-03T10:15:30Z'.
 *
 * @see
 * <a href="http://docs.oracle.com/javase/7/docs/api/java/text/DateFormat.html">DateFormat</a>
 * @see <a href="http://www.w3.org/TR/NOTE-datetime">W3C Date and Time
 * Formats</a>
 * @author Key Bridge
 * @since v0.0.1 created 01/02/18
 * @since v1.0.0 copied 2020-07-15 from json-adapter
 */
public class JsonbLocalDateAdapter {

  /**
   * The ISO date formatter that formats or parses a date without an offset,
   * such as '2011-12-03'. This returns an immutable formatter capable of
   * formatting and parsing the ISO-8601 extended local date format. The format
   * consists of:
   * <ul>
   * <li>Four digits or more for the year. Years in the range 0000 to 9999 will
   * be pre-padded by zero to ensure four digits. Years outside that range will
   * have a prefixed positive or negative symbol.</li>
   * <li>A dash</li>
   * <li>Two digits for the month-of-year. This is pre-padded by zero to ensure
   * two digits.</li>
   * <li>A dash</li>
   * <li>Two digits for the day-of-month. This is pre-padded by zero to ensure
   * two digits.</li></ul>
   */
  protected static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@code JsonSerializer}s too) to serialize Objects into JSON.
   */
  public static class Serializer implements JsonbSerializer<LocalDate> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(LocalDate v, javax.json.stream.JsonGenerator jg, SerializationContext sc) {
      jg.write(v.format(DATETIME_FORMATTER));
    }
  }

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@code JsonDeserializer}s too) to deserialize Objects of from JSON, using
   * provided {@code JsonParser}.
   */
  public static class Deserializer implements JsonbDeserializer<LocalDate> {

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDate deserialize(JsonParser jp, javax.json.bind.serializer.DeserializationContext dc, Type type) {
      return LocalDate.parse(jp.getString(), DATETIME_FORMATTER);
    }

  }
}
