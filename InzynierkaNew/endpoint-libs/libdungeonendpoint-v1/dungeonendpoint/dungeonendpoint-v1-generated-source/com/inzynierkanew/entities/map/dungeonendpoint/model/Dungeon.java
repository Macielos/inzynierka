/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-11-16 19:10:01 UTC)
 * on 2016-01-06 at 13:48:53 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.map.dungeonendpoint.model;

/**
 * Model definition for Dungeon.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the dungeonendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Dungeon extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.Integer> army;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long factionId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long landId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long type;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer x;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer y;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Integer> getArmy() {
    return army;
  }

  /**
   * @param army army or {@code null} for none
   */
  public Dungeon setArmy(java.util.List<java.lang.Integer> army) {
    this.army = army;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getFactionId() {
    return factionId;
  }

  /**
   * @param factionId factionId or {@code null} for none
   */
  public Dungeon setFactionId(java.lang.Long factionId) {
    this.factionId = factionId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Dungeon setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getLandId() {
    return landId;
  }

  /**
   * @param landId landId or {@code null} for none
   */
  public Dungeon setLandId(java.lang.Long landId) {
    this.landId = landId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getType() {
    return type;
  }

  /**
   * @param type type or {@code null} for none
   */
  public Dungeon setType(java.lang.Long type) {
    this.type = type;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getX() {
    return x;
  }

  /**
   * @param x x or {@code null} for none
   */
  public Dungeon setX(java.lang.Integer x) {
    this.x = x;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getY() {
    return y;
  }

  /**
   * @param y y or {@code null} for none
   */
  public Dungeon setY(java.lang.Integer y) {
    this.y = y;
    return this;
  }

  @Override
  public Dungeon set(String fieldName, Object value) {
    return (Dungeon) super.set(fieldName, value);
  }

  @Override
  public Dungeon clone() {
    return (Dungeon) super.clone();
  }

}
