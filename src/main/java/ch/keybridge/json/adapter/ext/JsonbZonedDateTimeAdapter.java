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
package ch.keybridge.json.adapter.ext;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * JSON-B adapter to translate between a standard java.time.ZonedDateTime
 * instance and the ISO 8601 Date format. The ISO instant formatter that formats
 * or parses an instant in UTC, such as '2011-12-03T10:15:30Z'.
 * <p>
 * JSON-B has built in java.time transforms that are generally well behaved. Use
 * this when you need to tightly control how a ZonedDateTime is presented.
 *
 * @author Key Bridge
 * @since v0.47.1 created 2020-10-08
 */
public class JsonbZonedDateTimeAdapter implements JsonbAdapter<ZonedDateTime, String> {

  /**
   * The ISO date-time formatter that formats or parses a date-time without an
   * offset, such as '2011-12-03T10:15:30'. This returns an immutable formatter
   * capable of formatting and parsing the ISO-8601 extended offset date-time
   * format.
   */
  private final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

  /**
   * {@inheritDoc}
   */
  @Override
  public String adaptToJson(ZonedDateTime obj) throws Exception {
    return obj != null ? obj.format(DATETIME_FORMATTER) : null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ZonedDateTime adaptFromJson(String obj) throws Exception {
    return obj == null ? null : ZonedDateTime.parse(obj, DATETIME_FORMATTER);
  }

}
