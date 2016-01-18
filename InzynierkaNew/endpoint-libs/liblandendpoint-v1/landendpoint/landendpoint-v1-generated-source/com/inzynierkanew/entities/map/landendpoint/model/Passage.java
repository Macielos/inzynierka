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
 * (build: 2016-01-08 17:48:37 UTC)
 * on 2016-01-17 at 21:21:53 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.map.landendpoint.model;

/**
 * Model definition for Passage.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the landendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Passage extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer direction;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long nextLandId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer nextX;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer nextY;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean portal;

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
  public java.lang.Integer getDirection() {
    return direction;
  }

  /**
   * @param direction direction or {@code null} for none
   */
  public Passage setDirection(java.lang.Integer direction) {
    this.direction = direction;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public Passage setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getNextLandId() {
    return nextLandId;
  }

  /**
   * @param nextLandId nextLandId or {@code null} for none
   */
  public Passage setNextLandId(java.lang.Long nextLandId) {
    this.nextLandId = nextLandId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getNextX() {
    return nextX;
  }

  /**
   * @param nextX nextX or {@code null} for none
   */
  public Passage setNextX(java.lang.Integer nextX) {
    this.nextX = nextX;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getNextY() {
    return nextY;
  }

  /**
   * @param nextY nextY or {@code null} for none
   */
  public Passage setNextY(java.lang.Integer nextY) {
    this.nextY = nextY;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getPortal() {
    return portal;
  }

  /**
   * @param portal portal or {@code null} for none
   */
  public Passage setPortal(java.lang.Boolean portal) {
    this.portal = portal;
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
  public Passage setType(java.lang.Long type) {
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
  public Passage setX(java.lang.Integer x) {
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
  public Passage setY(java.lang.Integer y) {
    this.y = y;
    return this;
  }

  @Override
  public Passage set(String fieldName, Object value) {
    return (Passage) super.set(fieldName, value);
  }

  @Override
  public Passage clone() {
    return (Passage) super.clone();
  }

}
