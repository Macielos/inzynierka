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
 * on 2015-09-21 at 20:37:36 UTC 
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
  private java.util.List<EmptyField> fields;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.util.List<java.lang.Long> fieldsNew;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Passage> passages;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Town town;

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
  public java.util.List<EmptyField> getFields() {
    return fields;
  }

  /**
   * @param fields fields or {@code null} for none
   */
  public Land setFields(java.util.List<EmptyField> fields) {
    this.fields = fields;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Long> getFieldsNew() {
    return fieldsNew;
  }

  /**
   * @param fieldsNew fieldsNew or {@code null} for none
   */
  public Land setFieldsNew(java.util.List<java.lang.Long> fieldsNew) {
    this.fieldsNew = fieldsNew;
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
  public Town getTown() {
    return town;
  }

  /**
   * @param town town or {@code null} for none
   */
  public Land setTown(Town town) {
    this.town = town;
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
