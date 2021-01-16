/*
 * Copyright 2021 Key Bridge. All rights reserved. Use is subject to license
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * JSON adapter implementation to mirror the @XmlList annotation. Transforms and
 * reverse transforms a simple list of string to a space delimited string.
 *
 * @author Key Bridge
 * @since v1.0.0 created 2021-01-16
 */
public class JsonbListAdapter implements JsonbAdapter<Collection<String>, String> {

  private static final String UTF = "UTF-8";

  /**
   * {@inheritDoc} Convert a collections of strings to a space delimited list of
   * URL-encoded strings. This is the equivalent of the @XmlList annotation.
   */
  @Override
  public String adaptToJson(Collection<String> orgnl) throws Exception {
    StringBuilder sb = new StringBuilder();
    orgnl.stream()
      .map(s -> encode(s))
      .forEach(s -> sb.append(s).append(" "));
    return sb.toString().trim();
  }

  /**
   * {@inheritDoc} Convert a url-encoded collection of space delimited strings
   * to a simple List of decoded string.
   */
  @Override
  public Collection<String> adaptFromJson(String adptd) throws Exception {
    return adptd.contains(" ")
           ? Arrays.asList(adptd.split(" ")).stream().map(s -> decode(s)).collect(Collectors.toList())
           : Collections.singleton(adptd);
  }

  /**
   * Internal method to try to encode the provided string
   *
   * @param s a string
   * @return the string, url-encoded
   */
  private String encode(String s) {
    try {
      return URLEncoder.encode(s, UTF);
    } catch (UnsupportedEncodingException e) {
      return s;
    }
  }

  /**
   * Internal method to try to decode the provided string
   *
   * @param s a string, url-encoded
   * @return the string
   */
  private String decode(String s) {
    try {
      return URLDecoder.decode(s, UTF);
    } catch (UnsupportedEncodingException e) {
      return s;
    }
  }

}
