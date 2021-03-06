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
 * on 2016-02-01 at 02:49:18 UTC 
 * Modify at your own risk.
 */

package com.explorersguild.entities.map.townvisitendpoint;

/**
 * Service definition for Townvisitendpoint (v1).
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
 * This service uses {@link TownvisitendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Townvisitendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the townvisitendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://explorersguild-1199.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "townvisitendpoint/v1/";

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
  public Townvisitendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Townvisitendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getTownVisit".
   *
   * This request holds the parameters needed by the townvisitendpoint server.  After setting any
   * optional parameters, call the {@link GetTownVisit#execute()} method to invoke the remote
   * operation.
   *
   * @param townId
   * @param heroId
   * @return the request
   */
  public GetTownVisit getTownVisit(java.lang.Long townId, java.lang.Long heroId) throws java.io.IOException {
    GetTownVisit result = new GetTownVisit(townId, heroId);
    initialize(result);
    return result;
  }

  public class GetTownVisit extends TownvisitendpointRequest<com.explorersguild.entities.map.townvisitendpoint.model.TownVisit> {

    private static final String REST_PATH = "townvisit/{townId}/{heroId}";

    /**
     * Create a request for the method "getTownVisit".
     *
     * This request holds the parameters needed by the the townvisitendpoint server.  After setting
     * any optional parameters, call the {@link GetTownVisit#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetTownVisit#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param townId
     * @param heroId
     * @since 1.13
     */
    protected GetTownVisit(java.lang.Long townId, java.lang.Long heroId) {
      super(Townvisitendpoint.this, "GET", REST_PATH, null, com.explorersguild.entities.map.townvisitendpoint.model.TownVisit.class);
      this.townId = com.google.api.client.util.Preconditions.checkNotNull(townId, "Required parameter townId must be specified.");
      this.heroId = com.google.api.client.util.Preconditions.checkNotNull(heroId, "Required parameter heroId must be specified.");
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
    public GetTownVisit setAlt(java.lang.String alt) {
      return (GetTownVisit) super.setAlt(alt);
    }

    @Override
    public GetTownVisit setFields(java.lang.String fields) {
      return (GetTownVisit) super.setFields(fields);
    }

    @Override
    public GetTownVisit setKey(java.lang.String key) {
      return (GetTownVisit) super.setKey(key);
    }

    @Override
    public GetTownVisit setOauthToken(java.lang.String oauthToken) {
      return (GetTownVisit) super.setOauthToken(oauthToken);
    }

    @Override
    public GetTownVisit setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetTownVisit) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetTownVisit setQuotaUser(java.lang.String quotaUser) {
      return (GetTownVisit) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetTownVisit setUserIp(java.lang.String userIp) {
      return (GetTownVisit) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long townId;

    /**

     */
    public java.lang.Long getTownId() {
      return townId;
    }

    public GetTownVisit setTownId(java.lang.Long townId) {
      this.townId = townId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Long heroId;

    /**

     */
    public java.lang.Long getHeroId() {
      return heroId;
    }

    public GetTownVisit setHeroId(java.lang.Long heroId) {
      this.heroId = heroId;
      return this;
    }

    @Override
    public GetTownVisit set(String parameterName, Object value) {
      return (GetTownVisit) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listTownVisit".
   *
   * This request holds the parameters needed by the townvisitendpoint server.  After setting any
   * optional parameters, call the {@link ListTownVisit#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListTownVisit listTownVisit() throws java.io.IOException {
    ListTownVisit result = new ListTownVisit();
    initialize(result);
    return result;
  }

  public class ListTownVisit extends TownvisitendpointRequest<com.explorersguild.entities.map.townvisitendpoint.model.CollectionResponseTownVisit> {

    private static final String REST_PATH = "townvisit";

    /**
     * Create a request for the method "listTownVisit".
     *
     * This request holds the parameters needed by the the townvisitendpoint server.  After setting
     * any optional parameters, call the {@link ListTownVisit#execute()} method to invoke the remote
     * operation. <p> {@link ListTownVisit#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected ListTownVisit() {
      super(Townvisitendpoint.this, "GET", REST_PATH, null, com.explorersguild.entities.map.townvisitendpoint.model.CollectionResponseTownVisit.class);
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
    public ListTownVisit setAlt(java.lang.String alt) {
      return (ListTownVisit) super.setAlt(alt);
    }

    @Override
    public ListTownVisit setFields(java.lang.String fields) {
      return (ListTownVisit) super.setFields(fields);
    }

    @Override
    public ListTownVisit setKey(java.lang.String key) {
      return (ListTownVisit) super.setKey(key);
    }

    @Override
    public ListTownVisit setOauthToken(java.lang.String oauthToken) {
      return (ListTownVisit) super.setOauthToken(oauthToken);
    }

    @Override
    public ListTownVisit setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListTownVisit) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListTownVisit setQuotaUser(java.lang.String quotaUser) {
      return (ListTownVisit) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListTownVisit setUserIp(java.lang.String userIp) {
      return (ListTownVisit) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListTownVisit setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListTownVisit setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListTownVisit set(String parameterName, Object value) {
      return (ListTownVisit) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeTownVisit".
   *
   * This request holds the parameters needed by the townvisitendpoint server.  After setting any
   * optional parameters, call the {@link RemoveTownVisit#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveTownVisit removeTownVisit(java.lang.Long id) throws java.io.IOException {
    RemoveTownVisit result = new RemoveTownVisit(id);
    initialize(result);
    return result;
  }

  public class RemoveTownVisit extends TownvisitendpointRequest<Void> {

    private static final String REST_PATH = "townvisit/{id}";

    /**
     * Create a request for the method "removeTownVisit".
     *
     * This request holds the parameters needed by the the townvisitendpoint server.  After setting
     * any optional parameters, call the {@link RemoveTownVisit#execute()} method to invoke the remote
     * operation. <p> {@link RemoveTownVisit#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveTownVisit(java.lang.Long id) {
      super(Townvisitendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveTownVisit setAlt(java.lang.String alt) {
      return (RemoveTownVisit) super.setAlt(alt);
    }

    @Override
    public RemoveTownVisit setFields(java.lang.String fields) {
      return (RemoveTownVisit) super.setFields(fields);
    }

    @Override
    public RemoveTownVisit setKey(java.lang.String key) {
      return (RemoveTownVisit) super.setKey(key);
    }

    @Override
    public RemoveTownVisit setOauthToken(java.lang.String oauthToken) {
      return (RemoveTownVisit) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveTownVisit setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveTownVisit) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveTownVisit setQuotaUser(java.lang.String quotaUser) {
      return (RemoveTownVisit) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveTownVisit setUserIp(java.lang.String userIp) {
      return (RemoveTownVisit) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveTownVisit setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveTownVisit set(String parameterName, Object value) {
      return (RemoveTownVisit) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "saveTownVisit".
   *
   * This request holds the parameters needed by the townvisitendpoint server.  After setting any
   * optional parameters, call the {@link SaveTownVisit#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.explorersguild.entities.map.townvisitendpoint.model.TownVisit}
   * @return the request
   */
  public SaveTownVisit saveTownVisit(com.explorersguild.entities.map.townvisitendpoint.model.TownVisit content) throws java.io.IOException {
    SaveTownVisit result = new SaveTownVisit(content);
    initialize(result);
    return result;
  }

  public class SaveTownVisit extends TownvisitendpointRequest<com.explorersguild.entities.map.townvisitendpoint.model.TownVisit> {

    private static final String REST_PATH = "saveTownVisit";

    /**
     * Create a request for the method "saveTownVisit".
     *
     * This request holds the parameters needed by the the townvisitendpoint server.  After setting
     * any optional parameters, call the {@link SaveTownVisit#execute()} method to invoke the remote
     * operation. <p> {@link SaveTownVisit#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.explorersguild.entities.map.townvisitendpoint.model.TownVisit}
     * @since 1.13
     */
    protected SaveTownVisit(com.explorersguild.entities.map.townvisitendpoint.model.TownVisit content) {
      super(Townvisitendpoint.this, "POST", REST_PATH, content, com.explorersguild.entities.map.townvisitendpoint.model.TownVisit.class);
    }

    @Override
    public SaveTownVisit setAlt(java.lang.String alt) {
      return (SaveTownVisit) super.setAlt(alt);
    }

    @Override
    public SaveTownVisit setFields(java.lang.String fields) {
      return (SaveTownVisit) super.setFields(fields);
    }

    @Override
    public SaveTownVisit setKey(java.lang.String key) {
      return (SaveTownVisit) super.setKey(key);
    }

    @Override
    public SaveTownVisit setOauthToken(java.lang.String oauthToken) {
      return (SaveTownVisit) super.setOauthToken(oauthToken);
    }

    @Override
    public SaveTownVisit setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SaveTownVisit) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SaveTownVisit setQuotaUser(java.lang.String quotaUser) {
      return (SaveTownVisit) super.setQuotaUser(quotaUser);
    }

    @Override
    public SaveTownVisit setUserIp(java.lang.String userIp) {
      return (SaveTownVisit) super.setUserIp(userIp);
    }

    @Override
    public SaveTownVisit set(String parameterName, Object value) {
      return (SaveTownVisit) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Townvisitendpoint}.
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

    /** Builds a new instance of {@link Townvisitendpoint}. */
    @Override
    public Townvisitendpoint build() {
      return new Townvisitendpoint(this);
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
     * Set the {@link TownvisitendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setTownvisitendpointRequestInitializer(
        TownvisitendpointRequestInitializer townvisitendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(townvisitendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
