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

import java.math.BigDecimal;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * Java XML adapter to translate between a standard java.lang.Double instance
 * and a simple number with two significant digits.
 *
 * @author Key Bridge
 * @since v1.0.0 created 2020-12-01
 */
public class JsonbDouble02PrecisionAdapter implements JsonbAdapter<Double, Double> {

  /**
   * {@inheritDoc} Reduce the precision and convert the double value decimal
   * places.
   */
  @Override
  public Double adaptToJson(Double orgnl) throws Exception {
    /**
     * Intercept invalid double values, which may be produced by the abstract
     * properties parser.
     */
    try {
      if (orgnl == null || orgnl.isNaN() || orgnl.isInfinite()) {
        return null;
      }
      return new BigDecimal(orgnl).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Double adaptFromJson(Double adptd) throws Exception {
    return adptd;
  }

}
