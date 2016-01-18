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
 * on 2016-01-17 at 21:21:49 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.map.dungeonendpoint;

/**
 * Service definition for Dungeonendpoint (v1).
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
 * This service uses {@link DungeonendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Dungeonendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the dungeonendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "dungeonendpoint/v1/";

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
  public Dungeonendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Dungeonendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the DungeonEndpoint collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Dungeonendpoint dungeonendpoint = new Dungeonendpoint(...);}
   *   {@code Dungeonendpoint.DungeonEndpoint.List request = dungeonendpoint.dungeonEndpoint().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public DungeonEndpoint dungeonEndpoint() {
    return new DungeonEndpoint();
  }

  /**
   * The "dungeonEndpoint" collection of methods.
   */
  public class DungeonEndpoint {

    /**
     * Create a request for the method "dungeonEndpoint.insertDungeon".
     *
     * This request holds the parameters needed by the dungeonendpoint server.  After setting any
     * optional parameters, call the {@link InsertDungeon#execute()} method to invoke the remote
     * operation.
     *
     * @param content the {@link com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon}
     * @return the request
     */
    public InsertDungeon insertDungeon(com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon content) throws java.io.IOException {
      InsertDungeon result = new InsertDungeon(content);
      initialize(result);
      return result;
    }

    public class InsertDungeon extends DungeonendpointRequest<com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon> {

      private static final String REST_PATH = "dungeon";

      /**
       * Create a request for the method "dungeonEndpoint.insertDungeon".
       *
       * This request holds the parameters needed by the the dungeonendpoint server.  After setting any
       * optional parameters, call the {@link InsertDungeon#execute()} method to invoke the remote
       * operation. <p> {@link InsertDungeon#initialize(com.google.api.client.googleapis.services.Abstra
       * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
       * the constructor. </p>
       *
       * @param content the {@link com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon}
       * @since 1.13
       */
      protected InsertDungeon(com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon content) {
        super(Dungeonendpoint.this, "POST", REST_PATH, content, com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon.class);
      }

      @Override
      public InsertDungeon setAlt(java.lang.String alt) {
        return (InsertDungeon) super.setAlt(alt);
      }

      @Override
      public InsertDungeon setFields(java.lang.String fields) {
        return (InsertDungeon) super.setFields(fields);
      }

      @Override
      public InsertDungeon setKey(java.lang.String key) {
        return (InsertDungeon) super.setKey(key);
      }

      @Override
      public InsertDungeon setOauthToken(java.lang.String oauthToken) {
        return (InsertDungeon) super.setOauthToken(oauthToken);
      }

      @Override
      public InsertDungeon setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (InsertDungeon) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public InsertDungeon setQuotaUser(java.lang.String quotaUser) {
        return (InsertDungeon) super.setQuotaUser(quotaUser);
      }

      @Override
      public InsertDungeon setUserIp(java.lang.String userIp) {
        return (InsertDungeon) super.setUserIp(userIp);
      }

      @Override
      public InsertDungeon set(String parameterName, Object value) {
        return (InsertDungeon) super.set(parameterName, value);
      }
    }

  }

  /**
   * Create a request for the method "getDungeon".
   *
   * This request holds the parameters needed by the dungeonendpoint server.  After setting any
   * optional parameters, call the {@link GetDungeon#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetDungeon getDungeon(java.lang.Long id) throws java.io.IOException {
    GetDungeon result = new GetDungeon(id);
    initialize(result);
    return result;
  }

  public class GetDungeon extends DungeonendpointRequest<com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon> {

    private static final String REST_PATH = "getDungeon/{id}";

    /**
     * Create a request for the method "getDungeon".
     *
     * This request holds the parameters needed by the the dungeonendpoint server.  After setting any
     * optional parameters, call the {@link GetDungeon#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetDungeon#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetDungeon(java.lang.Long id) {
      super(Dungeonendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.map.dungeonendpoint.model.Dungeon.class);
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
    public GetDungeon setAlt(java.lang.String alt) {
      return (GetDungeon) super.setAlt(alt);
    }

    @Override
    public GetDungeon setFields(java.lang.String fields) {
      return (GetDungeon) super.setFields(fields);
    }

    @Override
    public GetDungeon setKey(java.lang.String key) {
      return (GetDungeon) super.setKey(key);
    }

    @Override
    public GetDungeon setOauthToken(java.lang.String oauthToken) {
      return (GetDungeon) super.setOauthToken(oauthToken);
    }

    @Override
    public GetDungeon setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetDungeon) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetDungeon setQuotaUser(java.lang.String quotaUser) {
      return (GetDungeon) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetDungeon setUserIp(java.lang.String userIp) {
      return (GetDungeon) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetDungeon setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetDungeon set(String parameterName, Object value) {
      return (GetDungeon) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listDungeon".
   *
   * This request holds the parameters needed by the dungeonendpoint server.  After setting any
   * optional parameters, call the {@link ListDungeon#execute()} method to invoke the remote
   * operation.
   *
   * @param landId
   * @return the request
   */
  public ListDungeon listDungeon(java.lang.Long landId) throws java.io.IOException {
    ListDungeon result = new ListDungeon(landId);
    initialize(result);
    return result;
  }

  public class ListDungeon extends DungeonendpointRequest<com.inzynierkanew.entities.map.dungeonendpoint.model.CollectionResponseDungeon> {

    private static final String REST_PATH = "listDungeon/{landId}";

    /**
     * Create a request for the method "listDungeon".
     *
     * This request holds the parameters needed by the the dungeonendpoint server.  After setting any
     * optional parameters, call the {@link ListDungeon#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListDungeon#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param landId
     * @since 1.13
     */
    protected ListDungeon(java.lang.Long landId) {
      super(Dungeonendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.map.dungeonendpoint.model.CollectionResponseDungeon.class);
      this.landId = com.google.api.client.util.Preconditions.checkNotNull(landId, "Required parameter landId must be specified.");
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
    public ListDungeon setAlt(java.lang.String alt) {
      return (ListDungeon) super.setAlt(alt);
    }

    @Override
    public ListDungeon setFields(java.lang.String fields) {
      return (ListDungeon) super.setFields(fields);
    }

    @Override
    public ListDungeon setKey(java.lang.String key) {
      return (ListDungeon) super.setKey(key);
    }

    @Override
    public ListDungeon setOauthToken(java.lang.String oauthToken) {
      return (ListDungeon) super.setOauthToken(oauthToken);
    }

    @Override
    public ListDungeon setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListDungeon) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListDungeon setQuotaUser(java.lang.String quotaUser) {
      return (ListDungeon) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListDungeon setUserIp(java.lang.String userIp) {
      return (ListDungeon) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long landId;

    /**

     */
    public java.lang.Long getLandId() {
      return landId;
    }

    public ListDungeon setLandId(java.lang.Long landId) {
      this.landId = landId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListDungeon setCursor(java.lang.String cursor) {
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

    public ListDungeon setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListDungeon set(String parameterName, Object value) {
      return (ListDungeon) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeDungeon".
   *
   * This request holds the parameters needed by the dungeonendpoint server.  After setting any
   * optional parameters, call the {@link RemoveDungeon#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveDungeon removeDungeon(java.lang.Long id) throws java.io.IOException {
    RemoveDungeon result = new RemoveDungeon(id);
    initialize(result);
    return result;
  }

  public class RemoveDungeon extends DungeonendpointRequest<Void> {

    private static final String REST_PATH = "dungeon/{id}";

    /**
     * Create a request for the method "removeDungeon".
     *
     * This request holds the parameters needed by the the dungeonendpoint server.  After setting any
     * optional parameters, call the {@link RemoveDungeon#execute()} method to invoke the remote
     * operation. <p> {@link RemoveDungeon#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveDungeon(java.lang.Long id) {
      super(Dungeonendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveDungeon setAlt(java.lang.String alt) {
      return (RemoveDungeon) super.setAlt(alt);
    }

    @Override
    public RemoveDungeon setFields(java.lang.String fields) {
      return (RemoveDungeon) super.setFields(fields);
    }

    @Override
    public RemoveDungeon setKey(java.lang.String key) {
      return (RemoveDungeon) super.setKey(key);
    }

    @Override
    public RemoveDungeon setOauthToken(java.lang.String oauthToken) {
      return (RemoveDungeon) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveDungeon setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveDungeon) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveDungeon setQuotaUser(java.lang.String quotaUser) {
      return (RemoveDungeon) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveDungeon setUserIp(java.lang.String userIp) {
      return (RemoveDungeon) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveDungeon setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveDungeon set(String parameterName, Object value) {
      return (RemoveDungeon) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Dungeonendpoint}.
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

    /** Builds a new instance of {@link Dungeonendpoint}. */
    @Override
    public Dungeonendpoint build() {
      return new Dungeonendpoint(this);
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
     * Set the {@link DungeonendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setDungeonendpointRequestInitializer(
        DungeonendpointRequestInitializer dungeonendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(dungeonendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
