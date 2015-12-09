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
 * on 2015-12-09 at 02:24:35 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.players.unittypeendpoint.model;

/**
 * Model definition for UnitType.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the unittypeendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class UnitType extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer cost;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long factionId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer hitpoints;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer maxDamage;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer minDamage;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer missiles;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean ranged;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer speed;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String texture;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getCost() {
    return cost;
  }

  /**
   * @param cost cost or {@code null} for none
   */
  public UnitType setCost(java.lang.Integer cost) {
    this.cost = cost;
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
  public UnitType setFactionId(java.lang.Long factionId) {
    this.factionId = factionId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getHitpoints() {
    return hitpoints;
  }

  /**
   * @param hitpoints hitpoints or {@code null} for none
   */
  public UnitType setHitpoints(java.lang.Integer hitpoints) {
    this.hitpoints = hitpoints;
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
  public UnitType setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMaxDamage() {
    return maxDamage;
  }

  /**
   * @param maxDamage maxDamage or {@code null} for none
   */
  public UnitType setMaxDamage(java.lang.Integer maxDamage) {
    this.maxDamage = maxDamage;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMinDamage() {
    return minDamage;
  }

  /**
   * @param minDamage minDamage or {@code null} for none
   */
  public UnitType setMinDamage(java.lang.Integer minDamage) {
    this.minDamage = minDamage;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMissiles() {
    return missiles;
  }

  /**
   * @param missiles missiles or {@code null} for none
   */
  public UnitType setMissiles(java.lang.Integer missiles) {
    this.missiles = missiles;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public UnitType setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getRanged() {
    return ranged;
  }

  /**
   * @param ranged ranged or {@code null} for none
   */
  public UnitType setRanged(java.lang.Boolean ranged) {
    this.ranged = ranged;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getSpeed() {
    return speed;
  }

  /**
   * @param speed speed or {@code null} for none
   */
  public UnitType setSpeed(java.lang.Integer speed) {
    this.speed = speed;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTexture() {
    return texture;
  }

  /**
   * @param texture texture or {@code null} for none
   */
  public UnitType setTexture(java.lang.String texture) {
    this.texture = texture;
    return this;
  }

  @Override
  public UnitType set(String fieldName, Object value) {
    return (UnitType) super.set(fieldName, value);
  }

  @Override
  public UnitType clone() {
    return (UnitType) super.clone();
  }

}
