/*
 * Copyright 2020 Key Bridge.
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

import java.util.Locale;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * Simple Locale JSON adapter. Maps a Locals to a language tags.
 *
 * @author Key Bridge
 * @since v2.2.0 created 2020-09-05
 * @since v1.0.1 copy 2021-01-24 from lib-oauth
 */
public class JsonbLocaleAdapter implements JsonbAdapter<Locale, String> {

  /**
   * {@inheritDoc}
   */
  @Override
  public String adaptToJson(Locale orgnl) throws Exception {
    return orgnl.toLanguageTag();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Locale adaptFromJson(String adptd) throws Exception {
    return Locale.forLanguageTag(adptd);
  }

}
