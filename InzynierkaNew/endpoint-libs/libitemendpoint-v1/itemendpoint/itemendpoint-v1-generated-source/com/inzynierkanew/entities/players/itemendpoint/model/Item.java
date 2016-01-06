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
 * on 2016-01-06 at 13:49:34 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.players.itemendpoint.model;

/**
 * Model definition for Item.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the itemendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Item extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer agilityBonus;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String icon;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer intelligenceBonus;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String itemClass;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer itemLevel;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer strengthBonus;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer value;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getAgilityBonus() {
    return agilityBonus;
  }

  /**
   * @param agilityBonus agilityBonus or {@code null} for none
   */
  public Item setAgilityBonus(java.lang.Integer agilityBonus) {
    this.agilityBonus = agilityBonus;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIcon() {
    return icon;
  }

  /**
   * @param icon icon or {@code null} for none
   */
  public Item setIcon(java.lang.String icon) {
    this.icon = icon;
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
  public Item setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getIntelligenceBonus() {
    return intelligenceBonus;
  }

  /**
   * @param intelligenceBonus intelligenceBonus or {@code null} for none
   */
  public Item setIntelligenceBonus(java.lang.Integer intelligenceBonus) {
    this.intelligenceBonus = intelligenceBonus;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getItemClass() {
    return itemClass;
  }

  /**
   * @param itemClass itemClass or {@code null} for none
   */
  public Item setItemClass(java.lang.String itemClass) {
    this.itemClass = itemClass;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getItemLevel() {
    return itemLevel;
  }

  /**
   * @param itemLevel itemLevel or {@code null} for none
   */
  public Item setItemLevel(java.lang.Integer itemLevel) {
    this.itemLevel = itemLevel;
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
  public Item setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getStrengthBonus() {
    return strengthBonus;
  }

  /**
   * @param strengthBonus strengthBonus or {@code null} for none
   */
  public Item setStrengthBonus(java.lang.Integer strengthBonus) {
    this.strengthBonus = strengthBonus;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getValue() {
    return value;
  }

  /**
   * @param value value or {@code null} for none
   */
  public Item setValue(java.lang.Integer value) {
    this.value = value;
    return this;
  }

  @Override
  public Item set(String fieldName, Object value) {
    return (Item) super.set(fieldName, value);
  }

  @Override
  public Item clone() {
    return (Item) super.clone();
  }

}
