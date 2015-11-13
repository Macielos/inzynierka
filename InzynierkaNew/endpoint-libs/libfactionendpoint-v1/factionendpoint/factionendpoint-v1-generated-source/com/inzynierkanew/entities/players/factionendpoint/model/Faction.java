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
 * on 2015-11-12 at 22:26:48 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.players.factionendpoint.model;

/**
 * Model definition for Faction.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the factionendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Faction extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean appearsInDungeons;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean appearsInTowns;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getAppearsInDungeons() {
    return appearsInDungeons;
  }

  /**
   * @param appearsInDungeons appearsInDungeons or {@code null} for none
   */
  public Faction setAppearsInDungeons(java.lang.Boolean appearsInDungeons) {
    this.appearsInDungeons = appearsInDungeons;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getAppearsInTowns() {
    return appearsInTowns;
  }

  /**
   * @param appearsInTowns appearsInTowns or {@code null} for none
   */
  public Faction setAppearsInTowns(java.lang.Boolean appearsInTowns) {
    this.appearsInTowns = appearsInTowns;
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
  public Faction setId(java.lang.Long id) {
    this.id = id;
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
  public Faction setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  @Override
  public Faction set(String fieldName, Object value) {
    return (Faction) super.set(fieldName, value);
  }

  @Override
  public Faction clone() {
    return (Faction) super.clone();
  }

}
