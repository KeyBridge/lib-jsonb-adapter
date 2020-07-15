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
package ch.keybridge.json;

import ch.keybridge.json.adapter.*;
import javax.json.bind.JsonbConfig;
import javax.json.bind.JsonbException;
import javax.json.bind.config.BinaryDataStrategy;

/**
 * Common JsonB marshaling and un-marshaling utilities. These methods help to
 * serialize and un-serialize object representations to and from JSON.
 *
 * @author Key Bridge
 * @since v1.0.0 created 2020-07-15
 * @see <a href="http://json-b.net">JSON Binding</a>
 */
public class JsonbUtility {

  /**
   * The JsonB reader instance.
   */
  private JsonbReader reader;
  /**
   * The JsonB writer instance.
   */
  private JsonbWriter writer;

  /**
   * Default no-arg constructor. Sets up the configuration and serializers.
   */
  public JsonbUtility() {
    /**
     * Configure and create the reader instance.
     */
    JsonbConfig readerConfig = new JsonbConfig()
      .withBinaryDataStrategy(BinaryDataStrategy.BASE_64)
      .withDeserializers(new JsonbDateAdapter.Deserializer())
      .withDeserializers(new JsonbDurationAdapter.Deserializer())
      .withDeserializers(new JsonbEnvelopeAdapter.Deserializer())
      .withDeserializers(new JsonbGeometryAdapter.Deserializer())
      .withDeserializers(new JsonbLocalDateAdapter.Deserializer())
      .withDeserializers(new JsonbLocalDateTimeAdapter.Deserializer())
      .withDeserializers(new JsonbZoneIdAdapter.Deserializer())
      .withDeserializers(new JsonbZonedDateTimeAdapter.Deserializer());
    this.reader = new JsonbReader(readerConfig);
    /**
     * Configure and create the writer instance.
     */
    JsonbConfig writerConfig = new JsonbConfig()
      .withFormatting(true)
      .withStrictIJSON(true)
      .withBinaryDataStrategy(BinaryDataStrategy.BASE_64)
      .withPropertyVisibilityStrategy(new JsonbPropertyVisibilityStrategy())
      .withSerializers(new JsonbGeometryAdapter.Serializer())
      .withSerializers(new JsonbEnvelopeAdapter.Serializer())
      .withSerializers(new JsonbDateAdapter.Serializer())
      .withSerializers(new JsonbDurationAdapter.Serializer())
      .withSerializers(new JsonbEnvelopeAdapter.Serializer())
      .withSerializers(new JsonbGeometryAdapter.Serializer())
      .withSerializers(new JsonbLocalDateAdapter.Serializer())
      .withSerializers(new JsonbLocalDateTimeAdapter.Serializer())
      .withSerializers(new JsonbZoneIdAdapter.Serializer())
      .withSerializers(new JsonbZonedDateTimeAdapter.Serializer());
    this.writer = new JsonbWriter(writerConfig);
  }

  /**
   * Marshal an entity class into a JSON String representation.
   *
   * @param <T>   the entity class type
   * @param clazz the entity class to be written
   * @return the entity class serialized into JSON form
   * @throws JsonbException       If any unexpected problem occurs during the
   *                              serialization, such as I/O error.
   * @throws NullPointerException If any of the parameters are null.
   */
  public final <T> String marshal(T clazz) throws JsonbException, NullPointerException {
    return writer.marshal(clazz);
  }

  /**
   * Parse a JSON file into a container class. This method calls the JsonB
   * un-marshaller and returns a class containing all of the content defined in
   * the XML file. Reads in a JSON data from the specified string and return the
   * resulting content tree.
   *
   * @param <T>   the class type that is returned
   * @param json  the JSON source content
   * @param clazz the class type that is parsed - this is the same as the class
   *              type that is returned
   * @return the JSON source parsed into the identified class type
   * @throws JsonbException       If any unexpected error(s) occur(s) during
   *                              deserialization.
   * @throws NullPointerException If any of the parameters are null.
   */
  public final <T> T unmarshal(String json, Class<T> clazz) throws JsonbException, NullPointerException {
    return reader.unmarshal(json, clazz);
  }

}
