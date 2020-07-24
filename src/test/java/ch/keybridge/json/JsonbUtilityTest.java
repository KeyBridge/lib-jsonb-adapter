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

import com.thedeanda.lorem.LoremIpsum;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;
import org.junit.*;

/**
 *
 * @author Key Bridge
 */
public class JsonbUtilityTest {

  private static JsonbUtility jsonb;
  private static LoremIpsum l;
  private static Random r;

  public JsonbUtilityTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    jsonb = new JsonbUtility();
    l = LoremIpsum.getInstance();
    r = new Random();
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testPosition() {

    Position p = new Position();
    p.setName(l.getCity());
    p.setLatitude(r.nextInt(90) * r.nextDouble());
    p.setLongitude(r.nextInt(180) * r.nextDouble());
    p.setElevation(Math.abs(r.nextInt(3000) * r.nextDouble()));
    p.setHeading(r.nextInt(360) * 1.0);
    p.setSpeed(r.nextInt(100) * 1.0);
    p.setDatum(l.getWords(1));
    p.setDomTimeStamp(new Date());
    p.setAccuracyVertical(r.nextInt(50) * r.nextDouble());
    p.setSource(l.getWords(1));
    p.setHaat(r.nextInt(100) * r.nextDouble());
//    p.setRadialHaat(new HashMap<>());

    String json = jsonb.marshal(p);
    Assert.assertNotNull(json);

    Position recovered = jsonb.unmarshal(json, Position.class);
    Assert.assertEquals(p, recovered);
    System.out.println("marshalUnmarshal OK");
  }

  @Test
  public void testEventModel() {

    EventModel e = new EventModel();
    e.setDateStart(ZonedDateTime.now());
    e.setDateEnd(ZonedDateTime.now().plus(new Random().nextInt(48), ChronoUnit.HOURS));
    e.setRecurrence("FREQ=DAILY;INTERVAL=1;COUNT=5;BYDAY=1MO,-2SU");

    System.out.println("e  " + e);
    System.out.println(jsonb.marshal(e));

  }

  @Test
  public void testZoneDateTime() {

//    Error parsing class java.time.ZonedDateTime from value: 2020-07-23T14:43:34.99Z.
    String dt = "2020-07-23T14:43:34.99Z";

    ZonedDateTime zdt = ZonedDateTime.parse(dt);

    System.out.println("zdt: " + zdt.toString());
    System.out.println("  " + zdt.getZone() + " " + zdt.getOffset() + " " + zdt.getZone().getId());
    ZonedDateTime mdt = zdt.withZoneSameInstant(Clock.systemDefaultZone().getZone());
    System.out.println("mdt: " + mdt);

  }

}
