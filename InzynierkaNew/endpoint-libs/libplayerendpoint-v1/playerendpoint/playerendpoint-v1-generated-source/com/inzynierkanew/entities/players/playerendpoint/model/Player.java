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
 * on 2015-11-10 at 00:50:56 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.players.playerendpoint.model;

/**
 * Model definition for Player.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the playerendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Player extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String deviceRegistrationID;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer gold;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Hero hero;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime lastLogin;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String password;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime registrationTime;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String sessionId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDeviceRegistrationID() {
    return deviceRegistrationID;
  }

  /**
   * @param deviceRegistrationID deviceRegistrationID or {@code null} for none
   */
  public Player setDeviceRegistrationID(java.lang.String deviceRegistrationID) {
    this.deviceRegistrationID = deviceRegistrationID;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getGold() {
    return gold;
  }

  /**
   * @param gold gold or {@code null} for none
   */
  public Player setGold(java.lang.Integer gold) {
    this.gold = gold;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Hero getHero() {
    return hero;
  }

  /**
   * @param hero hero or {@code null} for none
   */
  public Player setHero(Hero hero) {
    this.hero = hero;
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
  public Player setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getLastLogin() {
    return lastLogin;
  }

  /**
   * @param lastLogin lastLogin or {@code null} for none
   */
  public Player setLastLogin(com.google.api.client.util.DateTime lastLogin) {
    this.lastLogin = lastLogin;
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
  public Player setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPassword() {
    return password;
  }

  /**
   * @param password password or {@code null} for none
   */
  public Player setPassword(java.lang.String password) {
    this.password = password;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getRegistrationTime() {
    return registrationTime;
  }

  /**
   * @param registrationTime registrationTime or {@code null} for none
   */
  public Player setRegistrationTime(com.google.api.client.util.DateTime registrationTime) {
    this.registrationTime = registrationTime;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSessionId() {
    return sessionId;
  }

  /**
   * @param sessionId sessionId or {@code null} for none
   */
  public Player setSessionId(java.lang.String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

  @Override
  public Player set(String fieldName, Object value) {
    return (Player) super.set(fieldName, value);
  }

  @Override
  public Player clone() {
    return (Player) super.clone();
  }

}
