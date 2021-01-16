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

import java.util.ArrayList;
import java.util.Collection;
import org.junit.*;

/**
 *
 * @author Key Bridge
 */
public class JsonbListAdapterTest {

  private static JsonbListAdapter adapter;

  public JsonbListAdapterTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    adapter = new JsonbListAdapter();
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
  public void testAdaptToJson() throws Exception {

    System.out.println("testAdaptToJson");
    Collection<String> collection = new ArrayList<>();
    collection.add("one and one");
    collection.add("two+2");
    collection.add("three*3");
    collection.add("four/4");

    String json = adapter.adaptToJson(collection);

    System.out.println("  json       " + json);
    System.out.println("  collection " + collection);
  }

  @Test
  public void testAdaptFromJson() throws Exception {
    System.out.println("testAdaptFromJson");

    String json = "one+and+one two%2B2 three*3 four%2F4";

    Collection<String> collection = adapter.adaptFromJson(json);
    System.out.println("  json       " + json);
    System.out.println("  collection " + collection);
  }

  @Test
  public void testRoundTrip() throws Exception {
    System.out.println("testRoundTrip");
    Collection<String> collection = new ArrayList<>();
    collection.add("one and one");
    collection.add("two + 2");
    collection.add("three * 3");
    collection.add("four / 4");

    String json = adapter.adaptToJson(collection);

    System.out.println("  json       " + json);
    System.out.println("  collection " + collection);

    Collection<String> recovered = adapter.adaptFromJson(json);
    System.out.println("  json       " + json);
    System.out.println("  collection " + collection);

    Assert.assertTrue(collection.containsAll(recovered));
    Assert.assertTrue(recovered.containsAll(collection));

  }

}
