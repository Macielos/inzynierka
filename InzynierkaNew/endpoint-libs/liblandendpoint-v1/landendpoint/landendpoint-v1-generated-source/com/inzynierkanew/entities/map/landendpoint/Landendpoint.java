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
 * on 2015-12-09 at 02:24:23 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.map.landendpoint;

/**
 * Service definition for Landendpoint (v1).
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
 * This service uses {@link LandendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Landendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the landendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "landendpoint/v1/";

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
  public Landendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Landendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the LandEndpoint collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Landendpoint landendpoint = new Landendpoint(...);}
   *   {@code Landendpoint.LandEndpoint.List request = landendpoint.landEndpoint().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public LandEndpoint landEndpoint() {
    return new LandEndpoint();
  }

  /**
   * The "landEndpoint" collection of methods.
   */
  public class LandEndpoint {

    /**
     * Create a request for the method "landEndpoint.findLandForNewPlayer".
     *
     * This request holds the parameters needed by the landendpoint server.  After setting any optional
     * parameters, call the {@link FindLandForNewPlayer#execute()} method to invoke the remote
     * operation.
     *
     * @return the request
     */
    public FindLandForNewPlayer findLandForNewPlayer() throws java.io.IOException {
      FindLandForNewPlayer result = new FindLandForNewPlayer();
      initialize(result);
      return result;
    }

    public class FindLandForNewPlayer extends LandendpointRequest<com.inzynierkanew.entities.map.landendpoint.model.Land> {

      private static final String REST_PATH = "findLandForNewPlayer";

      /**
       * Create a request for the method "landEndpoint.findLandForNewPlayer".
       *
       * This request holds the parameters needed by the the landendpoint server.  After setting any
       * optional parameters, call the {@link FindLandForNewPlayer#execute()} method to invoke the
       * remote operation. <p> {@link FindLandForNewPlayer#initialize(com.google.api.client.googleapis.s
       * ervices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
       * after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected FindLandForNewPlayer() {
        super(Landendpoint.this, "POST", REST_PATH, null, com.inzynierkanew.entities.map.landendpoint.model.Land.class);
      }

      @Override
      public FindLandForNewPlayer setAlt(java.lang.String alt) {
        return (FindLandForNewPlayer) super.setAlt(alt);
      }

      @Override
      public FindLandForNewPlayer setFields(java.lang.String fields) {
        return (FindLandForNewPlayer) super.setFields(fields);
      }

      @Override
      public FindLandForNewPlayer setKey(java.lang.String key) {
        return (FindLandForNewPlayer) super.setKey(key);
      }

      @Override
      public FindLandForNewPlayer setOauthToken(java.lang.String oauthToken) {
        return (FindLandForNewPlayer) super.setOauthToken(oauthToken);
      }

      @Override
      public FindLandForNewPlayer setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (FindLandForNewPlayer) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public FindLandForNewPlayer setQuotaUser(java.lang.String quotaUser) {
        return (FindLandForNewPlayer) super.setQuotaUser(quotaUser);
      }

      @Override
      public FindLandForNewPlayer setUserIp(java.lang.String userIp) {
        return (FindLandForNewPlayer) super.setUserIp(userIp);
      }

      @Override
      public FindLandForNewPlayer set(String parameterName, Object value) {
        return (FindLandForNewPlayer) super.set(parameterName, value);
      }
    }

  }

  /**
   * Create a request for the method "findLandsInNeighbourhood".
   *
   * This request holds the parameters needed by the landendpoint server.  After setting any optional
   * parameters, call the {@link FindLandsInNeighbourhood#execute()} method to invoke the remote
   * operation.
   *
   * @param mapSegment
   * @return the request
   */
  public FindLandsInNeighbourhood findLandsInNeighbourhood(java.lang.Long mapSegment) throws java.io.IOException {
    FindLandsInNeighbourhood result = new FindLandsInNeighbourhood(mapSegment);
    initialize(result);
    return result;
  }

  public class FindLandsInNeighbourhood extends LandendpointRequest<com.inzynierkanew.entities.map.landendpoint.model.CollectionResponseLand> {

    private static final String REST_PATH = "findLandsInNeighbourhood/{mapSegment}";

    /**
     * Create a request for the method "findLandsInNeighbourhood".
     *
     * This request holds the parameters needed by the the landendpoint server.  After setting any
     * optional parameters, call the {@link FindLandsInNeighbourhood#execute()} method to invoke the
     * remote operation. <p> {@link FindLandsInNeighbourhood#initialize(com.google.api.client.googleap
     * is.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param mapSegment
     * @since 1.13
     */
    protected FindLandsInNeighbourhood(java.lang.Long mapSegment) {
      super(Landendpoint.this, "POST", REST_PATH, null, com.inzynierkanew.entities.map.landendpoint.model.CollectionResponseLand.class);
      this.mapSegment = com.google.api.client.util.Preconditions.checkNotNull(mapSegment, "Required parameter mapSegment must be specified.");
    }

    @Override
    public FindLandsInNeighbourhood setAlt(java.lang.String alt) {
      return (FindLandsInNeighbourhood) super.setAlt(alt);
    }

    @Override
    public FindLandsInNeighbourhood setFields(java.lang.String fields) {
      return (FindLandsInNeighbourhood) super.setFields(fields);
    }

    @Override
    public FindLandsInNeighbourhood setKey(java.lang.String key) {
      return (FindLandsInNeighbourhood) super.setKey(key);
    }

    @Override
    public FindLandsInNeighbourhood setOauthToken(java.lang.String oauthToken) {
      return (FindLandsInNeighbourhood) super.setOauthToken(oauthToken);
    }

    @Override
    public FindLandsInNeighbourhood setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (FindLandsInNeighbourhood) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public FindLandsInNeighbourhood setQuotaUser(java.lang.String quotaUser) {
      return (FindLandsInNeighbourhood) super.setQuotaUser(quotaUser);
    }

    @Override
    public FindLandsInNeighbourhood setUserIp(java.lang.String userIp) {
      return (FindLandsInNeighbourhood) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long mapSegment;

    /**

     */
    public java.lang.Long getMapSegment() {
      return mapSegment;
    }

    public FindLandsInNeighbourhood setMapSegment(java.lang.Long mapSegment) {
      this.mapSegment = mapSegment;
      return this;
    }

    @Override
    public FindLandsInNeighbourhood set(String parameterName, Object value) {
      return (FindLandsInNeighbourhood) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "findLandsWithFreePassages".
   *
   * This request holds the parameters needed by the landendpoint server.  After setting any optional
   * parameters, call the {@link FindLandsWithFreePassages#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public FindLandsWithFreePassages findLandsWithFreePassages() throws java.io.IOException {
    FindLandsWithFreePassages result = new FindLandsWithFreePassages();
    initialize(result);
    return result;
  }

  public class FindLandsWithFreePassages extends LandendpointRequest<com.inzynierkanew.entities.map.landendpoint.model.CollectionResponseLand> {

    private static final String REST_PATH = "findLandsWithFreePassages";

    /**
     * Create a request for the method "findLandsWithFreePassages".
     *
     * This request holds the parameters needed by the the landendpoint server.  After setting any
     * optional parameters, call the {@link FindLandsWithFreePassages#execute()} method to invoke the
     * remote operation. <p> {@link FindLandsWithFreePassages#initialize(com.google.api.client.googlea
     * pis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected FindLandsWithFreePassages() {
      super(Landendpoint.this, "POST", REST_PATH, null, com.inzynierkanew.entities.map.landendpoint.model.CollectionResponseLand.class);
    }

    @Override
    public FindLandsWithFreePassages setAlt(java.lang.String alt) {
      return (FindLandsWithFreePassages) super.setAlt(alt);
    }

    @Override
    public FindLandsWithFreePassages setFields(java.lang.String fields) {
      return (FindLandsWithFreePassages) super.setFields(fields);
    }

    @Override
    public FindLandsWithFreePassages setKey(java.lang.String key) {
      return (FindLandsWithFreePassages) super.setKey(key);
    }

    @Override
    public FindLandsWithFreePassages setOauthToken(java.lang.String oauthToken) {
      return (FindLandsWithFreePassages) super.setOauthToken(oauthToken);
    }

    @Override
    public FindLandsWithFreePassages setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (FindLandsWithFreePassages) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public FindLandsWithFreePassages setQuotaUser(java.lang.String quotaUser) {
      return (FindLandsWithFreePassages) super.setQuotaUser(quotaUser);
    }

    @Override
    public FindLandsWithFreePassages setUserIp(java.lang.String userIp) {
      return (FindLandsWithFreePassages) super.setUserIp(userIp);
    }

    @Override
    public FindLandsWithFreePassages set(String parameterName, Object value) {
      return (FindLandsWithFreePassages) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getLand".
   *
   * This request holds the parameters needed by the landendpoint server.  After setting any optional
   * parameters, call the {@link GetLand#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetLand getLand(java.lang.Long id) throws java.io.IOException {
    GetLand result = new GetLand(id);
    initialize(result);
    return result;
  }

  public class GetLand extends LandendpointRequest<com.inzynierkanew.entities.map.landendpoint.model.Land> {

    private static final String REST_PATH = "land/{id}";

    /**
     * Create a request for the method "getLand".
     *
     * This request holds the parameters needed by the the landendpoint server.  After setting any
     * optional parameters, call the {@link GetLand#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetLand#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetLand(java.lang.Long id) {
      super(Landendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.map.landendpoint.model.Land.class);
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
    public GetLand setAlt(java.lang.String alt) {
      return (GetLand) super.setAlt(alt);
    }

    @Override
    public GetLand setFields(java.lang.String fields) {
      return (GetLand) super.setFields(fields);
    }

    @Override
    public GetLand setKey(java.lang.String key) {
      return (GetLand) super.setKey(key);
    }

    @Override
    public GetLand setOauthToken(java.lang.String oauthToken) {
      return (GetLand) super.setOauthToken(oauthToken);
    }

    @Override
    public GetLand setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetLand) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetLand setQuotaUser(java.lang.String quotaUser) {
      return (GetLand) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetLand setUserIp(java.lang.String userIp) {
      return (GetLand) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetLand setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetLand set(String parameterName, Object value) {
      return (GetLand) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertLand".
   *
   * This request holds the parameters needed by the landendpoint server.  After setting any optional
   * parameters, call the {@link InsertLand#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.inzynierkanew.entities.map.landendpoint.model.Land}
   * @return the request
   */
  public InsertLand insertLand(com.inzynierkanew.entities.map.landendpoint.model.Land content) throws java.io.IOException {
    InsertLand result = new InsertLand(content);
    initialize(result);
    return result;
  }

  public class InsertLand extends LandendpointRequest<com.inzynierkanew.entities.map.landendpoint.model.Land> {

    private static final String REST_PATH = "land";

    /**
     * Create a request for the method "insertLand".
     *
     * This request holds the parameters needed by the the landendpoint server.  After setting any
     * optional parameters, call the {@link InsertLand#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertLand#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.map.landendpoint.model.Land}
     * @since 1.13
     */
    protected InsertLand(com.inzynierkanew.entities.map.landendpoint.model.Land content) {
      super(Landendpoint.this, "POST", REST_PATH, content, com.inzynierkanew.entities.map.landendpoint.model.Land.class);
    }

    @Override
    public InsertLand setAlt(java.lang.String alt) {
      return (InsertLand) super.setAlt(alt);
    }

    @Override
    public InsertLand setFields(java.lang.String fields) {
      return (InsertLand) super.setFields(fields);
    }

    @Override
    public InsertLand setKey(java.lang.String key) {
      return (InsertLand) super.setKey(key);
    }

    @Override
    public InsertLand setOauthToken(java.lang.String oauthToken) {
      return (InsertLand) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertLand setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertLand) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertLand setQuotaUser(java.lang.String quotaUser) {
      return (InsertLand) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertLand setUserIp(java.lang.String userIp) {
      return (InsertLand) super.setUserIp(userIp);
    }

    @Override
    public InsertLand set(String parameterName, Object value) {
      return (InsertLand) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listLand".
   *
   * This request holds the parameters needed by the landendpoint server.  After setting any optional
   * parameters, call the {@link ListLand#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public ListLand listLand() throws java.io.IOException {
    ListLand result = new ListLand();
    initialize(result);
    return result;
  }

  public class ListLand extends LandendpointRequest<com.inzynierkanew.entities.map.landendpoint.model.CollectionResponseLand> {

    private static final String REST_PATH = "land";

    /**
     * Create a request for the method "listLand".
     *
     * This request holds the parameters needed by the the landendpoint server.  After setting any
     * optional parameters, call the {@link ListLand#execute()} method to invoke the remote operation.
     * <p> {@link
     * ListLand#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListLand() {
      super(Landendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.map.landendpoint.model.CollectionResponseLand.class);
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
    public ListLand setAlt(java.lang.String alt) {
      return (ListLand) super.setAlt(alt);
    }

    @Override
    public ListLand setFields(java.lang.String fields) {
      return (ListLand) super.setFields(fields);
    }

    @Override
    public ListLand setKey(java.lang.String key) {
      return (ListLand) super.setKey(key);
    }

    @Override
    public ListLand setOauthToken(java.lang.String oauthToken) {
      return (ListLand) super.setOauthToken(oauthToken);
    }

    @Override
    public ListLand setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListLand) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListLand setQuotaUser(java.lang.String quotaUser) {
      return (ListLand) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListLand setUserIp(java.lang.String userIp) {
      return (ListLand) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListLand setCursor(java.lang.String cursor) {
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

    public ListLand setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListLand set(String parameterName, Object value) {
      return (ListLand) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeLand".
   *
   * This request holds the parameters needed by the landendpoint server.  After setting any optional
   * parameters, call the {@link RemoveLand#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public RemoveLand removeLand(java.lang.Long id) throws java.io.IOException {
    RemoveLand result = new RemoveLand(id);
    initialize(result);
    return result;
  }

  public class RemoveLand extends LandendpointRequest<Void> {

    private static final String REST_PATH = "land/{id}";

    /**
     * Create a request for the method "removeLand".
     *
     * This request holds the parameters needed by the the landendpoint server.  After setting any
     * optional parameters, call the {@link RemoveLand#execute()} method to invoke the remote
     * operation. <p> {@link
     * RemoveLand#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveLand(java.lang.Long id) {
      super(Landendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveLand setAlt(java.lang.String alt) {
      return (RemoveLand) super.setAlt(alt);
    }

    @Override
    public RemoveLand setFields(java.lang.String fields) {
      return (RemoveLand) super.setFields(fields);
    }

    @Override
    public RemoveLand setKey(java.lang.String key) {
      return (RemoveLand) super.setKey(key);
    }

    @Override
    public RemoveLand setOauthToken(java.lang.String oauthToken) {
      return (RemoveLand) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveLand setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveLand) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveLand setQuotaUser(java.lang.String quotaUser) {
      return (RemoveLand) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveLand setUserIp(java.lang.String userIp) {
      return (RemoveLand) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveLand setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveLand set(String parameterName, Object value) {
      return (RemoveLand) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateLand".
   *
   * This request holds the parameters needed by the landendpoint server.  After setting any optional
   * parameters, call the {@link UpdateLand#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.inzynierkanew.entities.map.landendpoint.model.Land}
   * @return the request
   */
  public UpdateLand updateLand(com.inzynierkanew.entities.map.landendpoint.model.Land content) throws java.io.IOException {
    UpdateLand result = new UpdateLand(content);
    initialize(result);
    return result;
  }

  public class UpdateLand extends LandendpointRequest<com.inzynierkanew.entities.map.landendpoint.model.Land> {

    private static final String REST_PATH = "land";

    /**
     * Create a request for the method "updateLand".
     *
     * This request holds the parameters needed by the the landendpoint server.  After setting any
     * optional parameters, call the {@link UpdateLand#execute()} method to invoke the remote
     * operation. <p> {@link
     * UpdateLand#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.map.landendpoint.model.Land}
     * @since 1.13
     */
    protected UpdateLand(com.inzynierkanew.entities.map.landendpoint.model.Land content) {
      super(Landendpoint.this, "PUT", REST_PATH, content, com.inzynierkanew.entities.map.landendpoint.model.Land.class);
    }

    @Override
    public UpdateLand setAlt(java.lang.String alt) {
      return (UpdateLand) super.setAlt(alt);
    }

    @Override
    public UpdateLand setFields(java.lang.String fields) {
      return (UpdateLand) super.setFields(fields);
    }

    @Override
    public UpdateLand setKey(java.lang.String key) {
      return (UpdateLand) super.setKey(key);
    }

    @Override
    public UpdateLand setOauthToken(java.lang.String oauthToken) {
      return (UpdateLand) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateLand setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateLand) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateLand setQuotaUser(java.lang.String quotaUser) {
      return (UpdateLand) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateLand setUserIp(java.lang.String userIp) {
      return (UpdateLand) super.setUserIp(userIp);
    }

    @Override
    public UpdateLand set(String parameterName, Object value) {
      return (UpdateLand) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Landendpoint}.
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

    /** Builds a new instance of {@link Landendpoint}. */
    @Override
    public Landendpoint build() {
      return new Landendpoint(this);
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
     * Set the {@link LandendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setLandendpointRequestInitializer(
        LandendpointRequestInitializer landendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(landendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
