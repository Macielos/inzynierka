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
 * (build: 2015-08-03 17:34:38 UTC)
 * on 2015-11-12 at 22:26:31 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.map.landendpoint.model;

/**
 * Model definition for Land.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the landendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Land extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Dungeon> dungeons;

  static {
    // hack to force ProGuard to consider Dungeon used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Dungeon.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.Integer> fields;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean hasFreePassage;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer height;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long mapSegment;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer maxX;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer maxY;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer minX;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer minY;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Passage> passages;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long townId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer width;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Dungeon> getDungeons() {
    return dungeons;
  }

  /**
   * @param dungeons dungeons or {@code null} for none
   */
  public Land setDungeons(java.util.List<Dungeon> dungeons) {
    this.dungeons = dungeons;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Integer> getFields() {
    return fields;
  }

  /**
   * @param fields fields or {@code null} for none
   */
  public Land setFields(java.util.List<java.lang.Integer> fields) {
    this.fields = fields;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getHasFreePassage() {
    return hasFreePassage;
  }

  /**
   * @param hasFreePassage hasFreePassage or {@code null} for none
   */
  public Land setHasFreePassage(java.lang.Boolean hasFreePassage) {
    this.hasFreePassage = hasFreePassage;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getHeight() {
    return height;
  }

  /**
   * @param height height or {@code null} for none
   */
  public Land setHeight(java.lang.Integer height) {
    this.height = height;
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
  public Land setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMapSegment() {
    return mapSegment;
  }

  /**
   * @param mapSegment mapSegment or {@code null} for none
   */
  public Land setMapSegment(java.lang.Long mapSegment) {
    this.mapSegment = mapSegment;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMaxX() {
    return maxX;
  }

  /**
   * @param maxX maxX or {@code null} for none
   */
  public Land setMaxX(java.lang.Integer maxX) {
    this.maxX = maxX;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMaxY() {
    return maxY;
  }

  /**
   * @param maxY maxY or {@code null} for none
   */
  public Land setMaxY(java.lang.Integer maxY) {
    this.maxY = maxY;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMinX() {
    return minX;
  }

  /**
   * @param minX minX or {@code null} for none
   */
  public Land setMinX(java.lang.Integer minX) {
    this.minX = minX;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMinY() {
    return minY;
  }

  /**
   * @param minY minY or {@code null} for none
   */
  public Land setMinY(java.lang.Integer minY) {
    this.minY = minY;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Passage> getPassages() {
    return passages;
  }

  /**
   * @param passages passages or {@code null} for none
   */
  public Land setPassages(java.util.List<Passage> passages) {
    this.passages = passages;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getTownId() {
    return townId;
  }

  /**
   * @param townId townId or {@code null} for none
   */
  public Land setTownId(java.lang.Long townId) {
    this.townId = townId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getWidth() {
    return width;
  }

  /**
   * @param width width or {@code null} for none
   */
  public Land setWidth(java.lang.Integer width) {
    this.width = width;
    return this;
  }

  @Override
  public Land set(String fieldName, Object value) {
    return (Land) super.set(fieldName, value);
  }

  @Override
  public Land clone() {
    return (Land) super.clone();
  }

}
