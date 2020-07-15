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
import java.time.ZoneId;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

/**
 * JSON adapter to translate between a standard java.time.ZoneId instance.
 *
 * @author Key Bridge
 * @since v0.0.1 created 01/02/18
 * @since v1.0.0 copied 2020-07-15 from json-adapter
 */
public class JsonbZoneIdAdapter {

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@code JsonSerializer}s too) to serialize Objects into JSON.
   */
  public static class Serializer implements JsonbSerializer<ZoneId> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(ZoneId v, JsonGenerator jg, SerializationContext sc) {
      jg.write(v.getId());
    }

  }

  /**
   * Class that defines API used by {@code ObjectMapper} (and other chained
   * {@code JsonDeserializer}s too) to deserialize Objects of from JSON, using
   * provided {@code JsonParser}.
   */
  public static class Deserializer implements JsonbDeserializer<ZoneId> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ZoneId deserialize(JsonParser jp, DeserializationContext dc, Type type) {
      return ZoneId.of(jp.getString());
    }

  }
}
