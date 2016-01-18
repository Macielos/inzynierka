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

package com.inzynierkanew.entities.map.dungeonvisitendpoint;

/**
 * Service definition for Dungeonvisitendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link DungeonvisitendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Dungeonvisitendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the dungeonvisitendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://1059.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "dungeonvisitendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Dungeonvisitendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Dungeonvisitendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "listDungeonVisitsByIds".
   *
   * This request holds the parameters needed by the dungeonvisitendpoint server.  After setting any
   * optional parameters, call the {@link ListDungeonVisitsByIds#execute()} method to invoke the
   * remote operation.
   *
   * @param heroId
   * @param relatedEntityId
   * @param byLand
   * @return the request
   */
  public ListDungeonVisitsByIds listDungeonVisitsByIds(java.lang.Long heroId, java.lang.Long relatedEntityId, java.lang.Boolean byLand) throws java.io.IOException {
    ListDungeonVisitsByIds result = new ListDungeonVisitsByIds(heroId, relatedEntityId, byLand);
    initialize(result);
    return result;
  }

  public class ListDungeonVisitsByIds extends DungeonvisitendpointRequest<com.inzynierkanew.entities.map.dungeonvisitendpoint.model.CollectionResponseDungeonVisit> {

    private static final String REST_PATH = "dungeonvisit/{heroId}/{relatedEntityId}/{byLand}";

    /**
     * Create a request for the method "listDungeonVisitsByIds".
     *
     * This request holds the parameters needed by the the dungeonvisitendpoint server.  After setting
     * any optional parameters, call the {@link ListDungeonVisitsByIds#execute()} method to invoke the
     * remote operation. <p> {@link ListDungeonVisitsByIds#initialize(com.google.api.client.googleapis
     * .services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param heroId
     * @param relatedEntityId
     * @param byLand
     * @since 1.13
     */
    protected ListDungeonVisitsByIds(java.lang.Long heroId, java.lang.Long relatedEntityId, java.lang.Boolean byLand) {
      super(Dungeonvisitendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.map.dungeonvisitendpoint.model.CollectionResponseDungeonVisit.class);
      this.heroId = com.google.api.client.util.Preconditions.checkNotNull(heroId, "Required parameter heroId must be specified.");
      this.relatedEntityId = com.google.api.client.util.Preconditions.checkNotNull(relatedEntityId, "Required parameter relatedEntityId must be specified.");
      this.byLand = com.google.api.client.util.Preconditions.checkNotNull(byLand, "Required parameter byLand must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListDungeonVisitsByIds setAlt(java.lang.String alt) {
      return (ListDungeonVisitsByIds) super.setAlt(alt);
    }

    @Override
    public ListDungeonVisitsByIds setFields(java.lang.String fields) {
      return (ListDungeonVisitsByIds) super.setFields(fields);
    }

    @Override
    public ListDungeonVisitsByIds setKey(java.lang.String key) {
      return (ListDungeonVisitsByIds) super.setKey(key);
    }

    @Override
    public ListDungeonVisitsByIds setOauthToken(java.lang.String oauthToken) {
      return (ListDungeonVisitsByIds) super.setOauthToken(oauthToken);
    }

    @Override
    public ListDungeonVisitsByIds setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListDungeonVisitsByIds) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListDungeonVisitsByIds setQuotaUser(java.lang.String quotaUser) {
      return (ListDungeonVisitsByIds) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListDungeonVisitsByIds setUserIp(java.lang.String userIp) {
      return (ListDungeonVisitsByIds) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long heroId;

    /**

     */
    public java.lang.Long getHeroId() {
      return heroId;
    }

    public ListDungeonVisitsByIds setHeroId(java.lang.Long heroId) {
      this.heroId = heroId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Long relatedEntityId;

    /**

     */
    public java.lang.Long getRelatedEntityId() {
      return relatedEntityId;
    }

    public ListDungeonVisitsByIds setRelatedEntityId(java.lang.Long relatedEntityId) {
      this.relatedEntityId = relatedEntityId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Boolean byLand;

    /**

     */
    public java.lang.Boolean getByLand() {
      return byLand;
    }

    public ListDungeonVisitsByIds setByLand(java.lang.Boolean byLand) {
      this.byLand = byLand;
      return this;
    }

    @Override
    public ListDungeonVisitsByIds set(String parameterName, Object value) {
      return (ListDungeonVisitsByIds) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeDungeonVisit".
   *
   * This request holds the parameters needed by the dungeonvisitendpoint server.  After setting any
   * optional parameters, call the {@link RemoveDungeonVisit#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveDungeonVisit removeDungeonVisit(java.lang.Long id) throws java.io.IOException {
    RemoveDungeonVisit result = new RemoveDungeonVisit(id);
    initialize(result);
    return result;
  }

  public class RemoveDungeonVisit extends DungeonvisitendpointRequest<Void> {

    private static final String REST_PATH = "dungeonvisit/{id}";

    /**
     * Create a request for the method "removeDungeonVisit".
     *
     * This request holds the parameters needed by the the dungeonvisitendpoint server.  After setting
     * any optional parameters, call the {@link RemoveDungeonVisit#execute()} method to invoke the
     * remote operation. <p> {@link RemoveDungeonVisit#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveDungeonVisit(java.lang.Long id) {
      super(Dungeonvisitendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveDungeonVisit setAlt(java.lang.String alt) {
      return (RemoveDungeonVisit) super.setAlt(alt);
    }

    @Override
    public RemoveDungeonVisit setFields(java.lang.String fields) {
      return (RemoveDungeonVisit) super.setFields(fields);
    }

    @Override
    public RemoveDungeonVisit setKey(java.lang.String key) {
      return (RemoveDungeonVisit) super.setKey(key);
    }

    @Override
    public RemoveDungeonVisit setOauthToken(java.lang.String oauthToken) {
      return (RemoveDungeonVisit) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveDungeonVisit setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveDungeonVisit) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveDungeonVisit setQuotaUser(java.lang.String quotaUser) {
      return (RemoveDungeonVisit) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveDungeonVisit setUserIp(java.lang.String userIp) {
      return (RemoveDungeonVisit) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveDungeonVisit setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveDungeonVisit set(String parameterName, Object value) {
      return (RemoveDungeonVisit) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "saveDungeonVisit".
   *
   * This request holds the parameters needed by the dungeonvisitendpoint server.  After setting any
   * optional parameters, call the {@link SaveDungeonVisit#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.inzynierkanew.entities.map.dungeonvisitendpoint.model.DungeonVisit}
   * @return the request
   */
  public SaveDungeonVisit saveDungeonVisit(com.inzynierkanew.entities.map.dungeonvisitendpoint.model.DungeonVisit content) throws java.io.IOException {
    SaveDungeonVisit result = new SaveDungeonVisit(content);
    initialize(result);
    return result;
  }

  public class SaveDungeonVisit extends DungeonvisitendpointRequest<com.inzynierkanew.entities.map.dungeonvisitendpoint.model.DungeonVisit> {

    private static final String REST_PATH = "saveDungeonVisit";

    /**
     * Create a request for the method "saveDungeonVisit".
     *
     * This request holds the parameters needed by the the dungeonvisitendpoint server.  After setting
     * any optional parameters, call the {@link SaveDungeonVisit#execute()} method to invoke the
     * remote operation. <p> {@link SaveDungeonVisit#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.map.dungeonvisitendpoint.model.DungeonVisit}
     * @since 1.13
     */
    protected SaveDungeonVisit(com.inzynierkanew.entities.map.dungeonvisitendpoint.model.DungeonVisit content) {
      super(Dungeonvisitendpoint.this, "POST", REST_PATH, content, com.inzynierkanew.entities.map.dungeonvisitendpoint.model.DungeonVisit.class);
    }

    @Override
    public SaveDungeonVisit setAlt(java.lang.String alt) {
      return (SaveDungeonVisit) super.setAlt(alt);
    }

    @Override
    public SaveDungeonVisit setFields(java.lang.String fields) {
      return (SaveDungeonVisit) super.setFields(fields);
    }

    @Override
    public SaveDungeonVisit setKey(java.lang.String key) {
      return (SaveDungeonVisit) super.setKey(key);
    }

    @Override
    public SaveDungeonVisit setOauthToken(java.lang.String oauthToken) {
      return (SaveDungeonVisit) super.setOauthToken(oauthToken);
    }

    @Override
    public SaveDungeonVisit setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SaveDungeonVisit) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SaveDungeonVisit setQuotaUser(java.lang.String quotaUser) {
      return (SaveDungeonVisit) super.setQuotaUser(quotaUser);
    }

    @Override
    public SaveDungeonVisit setUserIp(java.lang.String userIp) {
      return (SaveDungeonVisit) super.setUserIp(userIp);
    }

    @Override
    public SaveDungeonVisit set(String parameterName, Object value) {
      return (SaveDungeonVisit) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Dungeonvisitendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Dungeonvisitendpoint}. */
    @Override
    public Dungeonvisitendpoint build() {
      return new Dungeonvisitendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link DungeonvisitendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setDungeonvisitendpointRequestInitializer(
        DungeonvisitendpointRequestInitializer dungeonvisitendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(dungeonvisitendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
