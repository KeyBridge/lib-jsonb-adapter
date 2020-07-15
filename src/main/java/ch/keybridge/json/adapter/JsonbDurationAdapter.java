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
import java.time.Duration;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonParser;

/**
 * JSON adapter to translate between a standard java.time.Duration instance and
 * ISO-8601 seconds based representation, such as PT8H6M12.345S
 *
 * @author Key Bridge
 * @since v0.0.1 created 01/02/18
 * @since v1.0.0 copied 2020-07-15 from json-adapter
 */
public class JsonbDurationAdapter {

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@code JsonSerializer}s too) to serialize Objects into JSON.
   */
  public static class Serializer implements JsonbSerializer<Duration> {

    /**
     * {@inheritDoc}
     * <p>
     * Write a string representation of this duration using ISO-8601 seconds
     * based representation, such as PT8H6M12.345S. The format of the returned
     * string will be PTnHnMnS, where n is the relevant hours, minutes or
     * seconds part of the duration. Any fractional seconds are placed after a
     * decimal point i the seconds section. If a section has a zero value, it is
     * omitted. The hours, minutes and seconds will all have the same sign.
     * <p>
     */
    @Override
    public void serialize(Duration t, javax.json.stream.JsonGenerator jg, SerializationContext sc) {
      jg.write(String.valueOf(t.toString()));
    }

  }

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@code JsonDeserializer}s too) to deserialize Objects of from JSON, using
   * provided {@code JsonParser}.
   */
  public static class Deserializer implements JsonbDeserializer<Duration> {

    /**
     * {@inheritDoc}
     * <p>
     * Obtains a Duration from a text string such as PnDTnHnMn.nS. This will
     * parse a textual representation of a duration, including the string
     * produced by toString(). The formats accepted are based on the ISO-8601
     * duration format PnDTnHnMn.nS with days considered to be exactly 24 hours.
     * <p>
     */
    @Override
    public Duration deserialize(JsonParser jp, javax.json.bind.serializer.DeserializationContext dc, Type type) {
      return Duration.parse(jp.getString());
    }

  }
}
