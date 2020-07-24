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

import java.io.Serializable;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The Event object is designed to enable the recording, persistence and
 * distribution of standardized iCalendar-compatible messages.
 * <p>
 * Event fields comprise the minimum set necessary to record and recreate a
 * fully qualified iCalendar event that maintains Rules compliant scheduling
 * information while also retaining compatibility with most scheduling software
 * packages including iCal&reg; and Outlook&reg;.
 *
 * @author Key Bridge
 * @since v0.26.0 created 2020-07-18 to support LPA/ DEV registrations
 */
public class EventModel implements Comparable<EventModel>, Serializable {

  /**
   * "UTC". Coordinated Universal Time. Coordinated Universal Time, abbreviated
   * to UTC, is the primary time standard by which the world regulates clocks
   * and time. It is within about 1 second of mean solar time at 0° longitude;
   * it does not observe daylight saving time.
   */
  private static final ZoneId ZONE_ID = Clock.systemUTC().getZone();

  /**
   * The "DTSTART" property for a "VEVENT" specifies the inclusive Start of the
   * event. For recurring events, it also specifies the very first instance in
   * the recurrence set.
   * <p>
   * The value is specified in the UTC Time Zone.
   */
  private ZonedDateTime dateStart;
  /**
   * The "DTEND" property for a "VEVENT" component specifies the non-inclusive
   * End of the event.
   * <p>
   * For cases where a "VEVENT" component specifies a "DTSTART" property with a
   * DATE data type but no "DTEND" property, the events non-inclusive End is the
   * end of the date specified by the "DTSTART" property. For cases where a
   * "VEVENT" component specifies a "DTSTART" property with a DATE-TIME data
   * type but no "DTEND" property, the event ends on the same date and time of
   * day specified by the "DTSTART" property.
   * <p>
   * The value is specified in the UTC Time Zone.
   */
  private ZonedDateTime dateEnd;
  /**
   * The Recurrence Rule. This identifies a recurrence rule specification. For
   * example, "FREQ=YEARLY;BYMONTH=1"
   */
  private String recurrence;

  public EventModel() {
  }

  /**
   * Static EventModel instance constructor.
   *
   * @param dateStart  the start date
   * @param dateEnd    the end date
   * @param recurrence (optional) the recurrence rule
   * @return a new EventModel instance
   */
  public static EventModel getInstance(ZonedDateTime dateStart, ZonedDateTime dateEnd, String recurrence) {
    EventModel e = new EventModel();
    e.setDateStart(dateStart);
    e.setDateEnd(dateEnd);
    e.setRecurrence(recurrence);
    return e;
  }

  /**
   * Get the dateStart property. The value is always set to UTC.
   *
   * @return the dateStart property
   */
  public ZonedDateTime getDateStart() {
    return dateStart;
  }

  /**
   * Set the dateStart property. The input value is converted to UTC.
   *
   * @param dateStart the dateStart property
   */
  public void setDateStart(ZonedDateTime dateStart) {
    this.dateStart = dateStart == null ? null : dateStart.withZoneSameInstant(ZONE_ID);
  }

  /**
   * Get the dateEnd property. The value is always set to UTC.
   *
   * @return the dateEnd property
   */
  public ZonedDateTime getDateEnd() {
    return dateEnd;
  }

  /**
   * Set the dateEnd property. The input value is converted to UTC.
   *
   * @param dateEnd the dateEnd property
   */
  public void setDateEnd(ZonedDateTime dateEnd) {
    this.dateEnd = dateEnd == null ? null : dateEnd.withZoneSameInstant(ZONE_ID);
  }

  /**
   * Get the recurrence rule. This identifies a recurrence rule specification.
   *
   * @return the recurrence rule.
   */
  public String getRecurrence() {
    return recurrence;
  }

  /**
   * Set the recurrence rule. This identifies a recurrence rule specification.
   * For example, "FREQ=YEARLY;BYMONTH=1"
   *
   * @param recurrence the recurrence rule.
   */
  public void setRecurrence(String recurrence) {
    this.recurrence = recurrence;
  }

  /**
   * {@inheritDoc} Sort order based upon the date start field. If date start is
   * equal then sort on date end (shortest event first).
   */
  @Override
  public int compareTo(EventModel o) {
    return dateStart.equals(o.getDateStart())
           ? dateEnd.compareTo(o.getDateEnd())
           : dateStart.compareTo(o.getDateStart());
  }

  /**
   * Print the start / end dates and conditionally show the recurrence.
   *
   * @return a pretty-print of this event model
   */
  @Override
  public String toString() {
    return "EventModel{" + "dateStart=" + dateStart + ", dateEnd=" + dateEnd
      + (recurrence == null ? "" : ", recurrence=" + recurrence)
      + '}';
  }

}
