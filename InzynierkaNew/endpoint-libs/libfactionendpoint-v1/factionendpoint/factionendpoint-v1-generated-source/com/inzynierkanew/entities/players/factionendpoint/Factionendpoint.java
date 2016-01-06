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
 * on 2016-01-06 at 13:49:40 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.players.factionendpoint;

/**
 * Service definition for Factionendpoint (v1).
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
 * This service uses {@link FactionendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Factionendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the factionendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "factionendpoint/v1/";

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
  public Factionendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Factionendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getFaction".
   *
   * This request holds the parameters needed by the factionendpoint server.  After setting any
   * optional parameters, call the {@link GetFaction#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetFaction getFaction(java.lang.Long id) throws java.io.IOException {
    GetFaction result = new GetFaction(id);
    initialize(result);
    return result;
  }

  public class GetFaction extends FactionendpointRequest<com.inzynierkanew.entities.players.factionendpoint.model.Faction> {

    private static final String REST_PATH = "faction/{id}";

    /**
     * Create a request for the method "getFaction".
     *
     * This request holds the parameters needed by the the factionendpoint server.  After setting any
     * optional parameters, call the {@link GetFaction#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetFaction#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetFaction(java.lang.Long id) {
      super(Factionendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.players.factionendpoint.model.Faction.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
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
    public GetFaction setAlt(java.lang.String alt) {
      return (GetFaction) super.setAlt(alt);
    }

    @Override
    public GetFaction setFields(java.lang.String fields) {
      return (GetFaction) super.setFields(fields);
    }

    @Override
    public GetFaction setKey(java.lang.String key) {
      return (GetFaction) super.setKey(key);
    }

    @Override
    public GetFaction setOauthToken(java.lang.String oauthToken) {
      return (GetFaction) super.setOauthToken(oauthToken);
    }

    @Override
    public GetFaction setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetFaction) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetFaction setQuotaUser(java.lang.String quotaUser) {
      return (GetFaction) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetFaction setUserIp(java.lang.String userIp) {
      return (GetFaction) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetFaction setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetFaction set(String parameterName, Object value) {
      return (GetFaction) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getFactionsForDungeons".
   *
   * This request holds the parameters needed by the factionendpoint server.  After setting any
   * optional parameters, call the {@link GetFactionsForDungeons#execute()} method to invoke the
   * remote operation.
   *
   * @return the request
   */
  public GetFactionsForDungeons getFactionsForDungeons() throws java.io.IOException {
    GetFactionsForDungeons result = new GetFactionsForDungeons();
    initialize(result);
    return result;
  }

  public class GetFactionsForDungeons extends FactionendpointRequest<com.inzynierkanew.entities.players.factionendpoint.model.CollectionResponseFaction> {

    private static final String REST_PATH = "collectionresponse_faction_dungeons";

    /**
     * Create a request for the method "getFactionsForDungeons".
     *
     * This request holds the parameters needed by the the factionendpoint server.  After setting any
     * optional parameters, call the {@link GetFactionsForDungeons#execute()} method to invoke the
     * remote operation. <p> {@link GetFactionsForDungeons#initialize(com.google.api.client.googleapis
     * .services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected GetFactionsForDungeons() {
      super(Factionendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.players.factionendpoint.model.CollectionResponseFaction.class);
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
    public GetFactionsForDungeons setAlt(java.lang.String alt) {
      return (GetFactionsForDungeons) super.setAlt(alt);
    }

    @Override
    public GetFactionsForDungeons setFields(java.lang.String fields) {
      return (GetFactionsForDungeons) super.setFields(fields);
    }

    @Override
    public GetFactionsForDungeons setKey(java.lang.String key) {
      return (GetFactionsForDungeons) super.setKey(key);
    }

    @Override
    public GetFactionsForDungeons setOauthToken(java.lang.String oauthToken) {
      return (GetFactionsForDungeons) super.setOauthToken(oauthToken);
    }

    @Override
    public GetFactionsForDungeons setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetFactionsForDungeons) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetFactionsForDungeons setQuotaUser(java.lang.String quotaUser) {
      return (GetFactionsForDungeons) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetFactionsForDungeons setUserIp(java.lang.String userIp) {
      return (GetFactionsForDungeons) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public GetFactionsForDungeons setCursor(java.lang.String cursor) {
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

    public GetFactionsForDungeons setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public GetFactionsForDungeons set(String parameterName, Object value) {
      return (GetFactionsForDungeons) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getFactionsForTowns".
   *
   * This request holds the parameters needed by the factionendpoint server.  After setting any
   * optional parameters, call the {@link GetFactionsForTowns#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public GetFactionsForTowns getFactionsForTowns() throws java.io.IOException {
    GetFactionsForTowns result = new GetFactionsForTowns();
    initialize(result);
    return result;
  }

  public class GetFactionsForTowns extends FactionendpointRequest<com.inzynierkanew.entities.players.factionendpoint.model.CollectionResponseFaction> {

    private static final String REST_PATH = "collectionresponse_faction_towns";

    /**
     * Create a request for the method "getFactionsForTowns".
     *
     * This request holds the parameters needed by the the factionendpoint server.  After setting any
     * optional parameters, call the {@link GetFactionsForTowns#execute()} method to invoke the remote
     * operation. <p> {@link GetFactionsForTowns#initialize(com.google.api.client.googleapis.services.
     * AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected GetFactionsForTowns() {
      super(Factionendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.players.factionendpoint.model.CollectionResponseFaction.class);
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
    public GetFactionsForTowns setAlt(java.lang.String alt) {
      return (GetFactionsForTowns) super.setAlt(alt);
    }

    @Override
    public GetFactionsForTowns setFields(java.lang.String fields) {
      return (GetFactionsForTowns) super.setFields(fields);
    }

    @Override
    public GetFactionsForTowns setKey(java.lang.String key) {
      return (GetFactionsForTowns) super.setKey(key);
    }

    @Override
    public GetFactionsForTowns setOauthToken(java.lang.String oauthToken) {
      return (GetFactionsForTowns) super.setOauthToken(oauthToken);
    }

    @Override
    public GetFactionsForTowns setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetFactionsForTowns) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetFactionsForTowns setQuotaUser(java.lang.String quotaUser) {
      return (GetFactionsForTowns) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetFactionsForTowns setUserIp(java.lang.String userIp) {
      return (GetFactionsForTowns) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public GetFactionsForTowns setCursor(java.lang.String cursor) {
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

    public GetFactionsForTowns setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public GetFactionsForTowns set(String parameterName, Object value) {
      return (GetFactionsForTowns) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertFaction".
   *
   * This request holds the parameters needed by the factionendpoint server.  After setting any
   * optional parameters, call the {@link InsertFaction#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.inzynierkanew.entities.players.factionendpoint.model.Faction}
   * @return the request
   */
  public InsertFaction insertFaction(com.inzynierkanew.entities.players.factionendpoint.model.Faction content) throws java.io.IOException {
    InsertFaction result = new InsertFaction(content);
    initialize(result);
    return result;
  }

  public class InsertFaction extends FactionendpointRequest<com.inzynierkanew.entities.players.factionendpoint.model.Faction> {

    private static final String REST_PATH = "faction";

    /**
     * Create a request for the method "insertFaction".
     *
     * This request holds the parameters needed by the the factionendpoint server.  After setting any
     * optional parameters, call the {@link InsertFaction#execute()} method to invoke the remote
     * operation. <p> {@link InsertFaction#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.players.factionendpoint.model.Faction}
     * @since 1.13
     */
    protected InsertFaction(com.inzynierkanew.entities.players.factionendpoint.model.Faction content) {
      super(Factionendpoint.this, "POST", REST_PATH, content, com.inzynierkanew.entities.players.factionendpoint.model.Faction.class);
    }

    @Override
    public InsertFaction setAlt(java.lang.String alt) {
      return (InsertFaction) super.setAlt(alt);
    }

    @Override
    public InsertFaction setFields(java.lang.String fields) {
      return (InsertFaction) super.setFields(fields);
    }

    @Override
    public InsertFaction setKey(java.lang.String key) {
      return (InsertFaction) super.setKey(key);
    }

    @Override
    public InsertFaction setOauthToken(java.lang.String oauthToken) {
      return (InsertFaction) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertFaction setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertFaction) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertFaction setQuotaUser(java.lang.String quotaUser) {
      return (InsertFaction) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertFaction setUserIp(java.lang.String userIp) {
      return (InsertFaction) super.setUserIp(userIp);
    }

    @Override
    public InsertFaction set(String parameterName, Object value) {
      return (InsertFaction) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listFaction".
   *
   * This request holds the parameters needed by the factionendpoint server.  After setting any
   * optional parameters, call the {@link ListFaction#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListFaction listFaction() throws java.io.IOException {
    ListFaction result = new ListFaction();
    initialize(result);
    return result;
  }

  public class ListFaction extends FactionendpointRequest<com.inzynierkanew.entities.players.factionendpoint.model.CollectionResponseFaction> {

    private static final String REST_PATH = "faction";

    /**
     * Create a request for the method "listFaction".
     *
     * This request holds the parameters needed by the the factionendpoint server.  After setting any
     * optional parameters, call the {@link ListFaction#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListFaction#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListFaction() {
      super(Factionendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.players.factionendpoint.model.CollectionResponseFaction.class);
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
    public ListFaction setAlt(java.lang.String alt) {
      return (ListFaction) super.setAlt(alt);
    }

    @Override
    public ListFaction setFields(java.lang.String fields) {
      return (ListFaction) super.setFields(fields);
    }

    @Override
    public ListFaction setKey(java.lang.String key) {
      return (ListFaction) super.setKey(key);
    }

    @Override
    public ListFaction setOauthToken(java.lang.String oauthToken) {
      return (ListFaction) super.setOauthToken(oauthToken);
    }

    @Override
    public ListFaction setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListFaction) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListFaction setQuotaUser(java.lang.String quotaUser) {
      return (ListFaction) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListFaction setUserIp(java.lang.String userIp) {
      return (ListFaction) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListFaction setCursor(java.lang.String cursor) {
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

    public ListFaction setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListFaction set(String parameterName, Object value) {
      return (ListFaction) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Factionendpoint}.
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

    /** Builds a new instance of {@link Factionendpoint}. */
    @Override
    public Factionendpoint build() {
      return new Factionendpoint(this);
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
     * Set the {@link FactionendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setFactionendpointRequestInitializer(
        FactionendpointRequestInitializer factionendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(factionendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
