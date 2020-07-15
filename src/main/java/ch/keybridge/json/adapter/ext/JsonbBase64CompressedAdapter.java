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
package ch.keybridge.json.adapter.ext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * Base64 Text encoder and decoder using GZIP compression.
 * <p>
 * This adapter leverages the Java8 Base64 utility and supports the following
 * types of Base64 as specified in RFC 4648 and RFC 2045.
 * <p>
 * Developer note: This Adapter is NOT a transparent encoder and decoder. This
 * adapter adds a compression step when marshaling and a decompression step when
 * unmarshaling the data.
 * <p>
 * The employed encoding strategy is Basic: Uses "The Base64 Alphabet" as
 * specified in Table 1 of RFC 4648 and RFC 2045 for encoding and decoding
 * operation. The encoder does not add any line feed (line separator) character.
 * The decoder rejects data that contains characters outside the base64
 * alphabet.
 * <p>
 * Use the {@code JsonBaseAdapter} or {@code JsonBase64MimeAdapter} for
 * uncompressed Base64 conversion.
 *
 * @author Key Bridge
 * @since v0.0.1 created 01/02/18
 * @since v1.0.0 copied 2020-07-15 from json-adapter
 */
public class JsonbBase64CompressedAdapter implements JsonbAdapter<byte[], String> {

  private static final Logger LOG = Logger.getLogger(JsonbBase64CompressedAdapter.class.getName());

  /**
   * {@inheritDoc}
   * <p>
   * Encodes the specified byte array into a String using the Base64 encoding
   * scheme. This method first encodes all input bytes into a base64 encoded
   * byte array and then constructs a new String by using the encoded byte array
   * and the ISO-8859-1 charset. In other words, an invocation of this method
   * has exactly the same effect as invoking new String(encode(src),
   * StandardCharsets.ISO_8859_1).
   */
  @Override
  public String adaptToJson(byte[] obj) throws Exception {
    try {
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
      GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream);
      gzipStream.write(obj);
      gzipStream.close();
      byteStream.close();
      return Base64.getMimeEncoder().encodeToString(byteStream.toByteArray());
    } catch (IOException iOException) {
      LOG.log(Level.WARNING, "byte[] serialization error. {1}", new Object[]{iOException.getMessage()});
      return null;
    }
  }

  /**
   * {@inheritDoc}
   * <p>
   * The encoded string sequence MUST represent a GZIP compressed byte array.
   * Decodes a Base64 encoded String into a newly-allocated byte array using the
   * Base64 encoding scheme. An invocation of this method has exactly the same
   * effect as invoking decode(src.getBytes(StandardCharsets.ISO_8859_1))
   */
  @Override
  public byte[] adaptFromJson(String obj) throws Exception {
    try {
      byte[] byteArray = Base64.getMimeDecoder().decode(obj);
      ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
      GZIPInputStream gzipStream = new GZIPInputStream(byteInputStream);
      ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
      int numBytesRead;
      byte[] tempBytes = new byte[6000];
      while ((numBytesRead = gzipStream.read(tempBytes, 0, tempBytes.length)) != -1) {
        byteOutputStream.write(tempBytes, 0, numBytesRead);
      }
      return byteOutputStream.toByteArray();
    } catch (IOException iOException) {
      LOG.log(Level.WARNING, "byte[] parse error. {1}", new Object[]{iOException.getMessage()});
      return null;
    }
  }

}
