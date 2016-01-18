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
 * on 2016-01-17 at 21:21:55 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.map.dungeonvisitendpoint.model;

/**
 * Model definition for DungeonVisit.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the dungeonvisitendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class DungeonVisit extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.Integer> army;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long dungeonId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long heroId;

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
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Integer> getArmy() {
    return army;
  }

  /**
   * @param army army or {@code null} for none
   */
  public DungeonVisit setArmy(java.util.List<java.lang.Integer> army) {
    this.army = army;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getDungeonId() {
    return dungeonId;
  }

  /**
   * @param dungeonId dungeonId or {@code null} for none
   */
  public DungeonVisit setDungeonId(java.lang.Long dungeonId) {
    this.dungeonId = dungeonId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getHeroId() {
    return heroId;
  }

  /**
   * @param heroId heroId or {@code null} for none
   */
  public DungeonVisit setHeroId(java.lang.Long heroId) {
    this.heroId = heroId;
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
  public DungeonVisit setId(java.lang.Long id) {
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
  public DungeonVisit setLandId(java.lang.Long landId) {
    this.landId = landId;
    return this;
  }

  @Override
  public DungeonVisit set(String fieldName, Object value) {
    return (DungeonVisit) super.set(fieldName, value);
  }

  @Override
  public DungeonVisit clone() {
    return (DungeonVisit) super.clone();
  }

}
