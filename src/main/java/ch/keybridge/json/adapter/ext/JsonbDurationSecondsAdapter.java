/*
 * Copyright 2018 Key Bridge.
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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * Java JSON adapter to translate between a standard java.time.Duration instance
 * to seconds.
 *
 * @since v0.0.1
 * @author Jesse Caulfield 10/06/17
 * @author Key Bridge LLC
 * @since v2.0.0 rewrite 2020-08-20
 * @since v1.0.1 copy 2021-01-24 from lib-oauth
 */
public class JsonbDurationSecondsAdapter implements JsonbAdapter<Duration, String> {

  /**
   * {@inheritDoc}
   */
  @Override
  public String adaptToJson(Duration obj) throws Exception {
    return obj == null ? null : String.valueOf(obj.getSeconds());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration adaptFromJson(String obj) throws Exception {
    return obj == null ? null : Duration.of(Long.parseLong(obj), ChronoUnit.SECONDS);
  }

}
