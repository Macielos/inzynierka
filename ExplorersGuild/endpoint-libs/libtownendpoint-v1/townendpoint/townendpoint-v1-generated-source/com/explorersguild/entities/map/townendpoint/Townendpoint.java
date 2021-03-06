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
 * on 2016-02-01 at 02:48:56 UTC 
 * Modify at your own risk.
 */

package com.explorersguild.entities.map.townendpoint;

/**
 * Service definition for Townendpoint (v1).
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
 * This service uses {@link TownendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Townendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the townendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "townendpoint/v1/";

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
  public Townendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Townendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getTown".
   *
   * This request holds the parameters needed by the townendpoint server.  After setting any optional
   * parameters, call the {@link GetTown#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetTown getTown(java.lang.Long id) throws java.io.IOException {
    GetTown result = new GetTown(id);
    initialize(result);
    return result;
  }

  public class GetTown extends TownendpointRequest<com.explorersguild.entities.map.townendpoint.model.Town> {

    private static final String REST_PATH = "town/{id}";

    /**
     * Create a request for the method "getTown".
     *
     * This request holds the parameters needed by the the townendpoint server.  After setting any
     * optional parameters, call the {@link GetTown#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetTown#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetTown(java.lang.Long id) {
      super(Townendpoint.this, "GET", REST_PATH, null, com.explorersguild.entities.map.townendpoint.model.Town.class);
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
    public GetTown setAlt(java.lang.String alt) {
      return (GetTown) super.setAlt(alt);
    }

    @Override
    public GetTown setFields(java.lang.String fields) {
      return (GetTown) super.setFields(fields);
    }

    @Override
    public GetTown setKey(java.lang.String key) {
      return (GetTown) super.setKey(key);
    }

    @Override
    public GetTown setOauthToken(java.lang.String oauthToken) {
      return (GetTown) super.setOauthToken(oauthToken);
    }

    @Override
    public GetTown setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetTown) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetTown setQuotaUser(java.lang.String quotaUser) {
      return (GetTown) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetTown setUserIp(java.lang.String userIp) {
      return (GetTown) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetTown setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetTown set(String parameterName, Object value) {
      return (GetTown) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertTown".
   *
   * This request holds the parameters needed by the townendpoint server.  After setting any optional
   * parameters, call the {@link InsertTown#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.explorersguild.entities.map.townendpoint.model.Town}
   * @return the request
   */
  public InsertTown insertTown(com.explorersguild.entities.map.townendpoint.model.Town content) throws java.io.IOException {
    InsertTown result = new InsertTown(content);
    initialize(result);
    return result;
  }

  public class InsertTown extends TownendpointRequest<com.explorersguild.entities.map.townendpoint.model.Town> {

    private static final String REST_PATH = "town";

    /**
     * Create a request for the method "insertTown".
     *
     * This request holds the parameters needed by the the townendpoint server.  After setting any
     * optional parameters, call the {@link InsertTown#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertTown#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.explorersguild.entities.map.townendpoint.model.Town}
     * @since 1.13
     */
    protected InsertTown(com.explorersguild.entities.map.townendpoint.model.Town content) {
      super(Townendpoint.this, "POST", REST_PATH, content, com.explorersguild.entities.map.townendpoint.model.Town.class);
    }

    @Override
    public InsertTown setAlt(java.lang.String alt) {
      return (InsertTown) super.setAlt(alt);
    }

    @Override
    public InsertTown setFields(java.lang.String fields) {
      return (InsertTown) super.setFields(fields);
    }

    @Override
    public InsertTown setKey(java.lang.String key) {
      return (InsertTown) super.setKey(key);
    }

    @Override
    public InsertTown setOauthToken(java.lang.String oauthToken) {
      return (InsertTown) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertTown setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertTown) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertTown setQuotaUser(java.lang.String quotaUser) {
      return (InsertTown) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertTown setUserIp(java.lang.String userIp) {
      return (InsertTown) super.setUserIp(userIp);
    }

    @Override
    public InsertTown set(String parameterName, Object value) {
      return (InsertTown) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listTown".
   *
   * This request holds the parameters needed by the townendpoint server.  After setting any optional
   * parameters, call the {@link ListTown#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public ListTown listTown() throws java.io.IOException {
    ListTown result = new ListTown();
    initialize(result);
    return result;
  }

  public class ListTown extends TownendpointRequest<com.explorersguild.entities.map.townendpoint.model.CollectionResponseTown> {

    private static final String REST_PATH = "town";

    /**
     * Create a request for the method "listTown".
     *
     * This request holds the parameters needed by the the townendpoint server.  After setting any
     * optional parameters, call the {@link ListTown#execute()} method to invoke the remote operation.
     * <p> {@link
     * ListTown#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListTown() {
      super(Townendpoint.this, "GET", REST_PATH, null, com.explorersguild.entities.map.townendpoint.model.CollectionResponseTown.class);
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
    public ListTown setAlt(java.lang.String alt) {
      return (ListTown) super.setAlt(alt);
    }

    @Override
    public ListTown setFields(java.lang.String fields) {
      return (ListTown) super.setFields(fields);
    }

    @Override
    public ListTown setKey(java.lang.String key) {
      return (ListTown) super.setKey(key);
    }

    @Override
    public ListTown setOauthToken(java.lang.String oauthToken) {
      return (ListTown) super.setOauthToken(oauthToken);
    }

    @Override
    public ListTown setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListTown) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListTown setQuotaUser(java.lang.String quotaUser) {
      return (ListTown) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListTown setUserIp(java.lang.String userIp) {
      return (ListTown) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListTown setCursor(java.lang.String cursor) {
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

    public ListTown setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListTown set(String parameterName, Object value) {
      return (ListTown) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeTown".
   *
   * This request holds the parameters needed by the townendpoint server.  After setting any optional
   * parameters, call the {@link RemoveTown#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public RemoveTown removeTown(java.lang.Long id) throws java.io.IOException {
    RemoveTown result = new RemoveTown(id);
    initialize(result);
    return result;
  }

  public class RemoveTown extends TownendpointRequest<Void> {

    private static final String REST_PATH = "town/{id}";

    /**
     * Create a request for the method "removeTown".
     *
     * This request holds the parameters needed by the the townendpoint server.  After setting any
     * optional parameters, call the {@link RemoveTown#execute()} method to invoke the remote
     * operation. <p> {@link
     * RemoveTown#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveTown(java.lang.Long id) {
      super(Townendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveTown setAlt(java.lang.String alt) {
      return (RemoveTown) super.setAlt(alt);
    }

    @Override
    public RemoveTown setFields(java.lang.String fields) {
      return (RemoveTown) super.setFields(fields);
    }

    @Override
    public RemoveTown setKey(java.lang.String key) {
      return (RemoveTown) super.setKey(key);
    }

    @Override
    public RemoveTown setOauthToken(java.lang.String oauthToken) {
      return (RemoveTown) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveTown setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveTown) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveTown setQuotaUser(java.lang.String quotaUser) {
      return (RemoveTown) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveTown setUserIp(java.lang.String userIp) {
      return (RemoveTown) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveTown setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveTown set(String parameterName, Object value) {
      return (RemoveTown) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateTown".
   *
   * This request holds the parameters needed by the townendpoint server.  After setting any optional
   * parameters, call the {@link UpdateTown#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.explorersguild.entities.map.townendpoint.model.Town}
   * @return the request
   */
  public UpdateTown updateTown(com.explorersguild.entities.map.townendpoint.model.Town content) throws java.io.IOException {
    UpdateTown result = new UpdateTown(content);
    initialize(result);
    return result;
  }

  public class UpdateTown extends TownendpointRequest<com.explorersguild.entities.map.townendpoint.model.Town> {

    private static final String REST_PATH = "town";

    /**
     * Create a request for the method "updateTown".
     *
     * This request holds the parameters needed by the the townendpoint server.  After setting any
     * optional parameters, call the {@link UpdateTown#execute()} method to invoke the remote
     * operation. <p> {@link
     * UpdateTown#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.explorersguild.entities.map.townendpoint.model.Town}
     * @since 1.13
     */
    protected UpdateTown(com.explorersguild.entities.map.townendpoint.model.Town content) {
      super(Townendpoint.this, "PUT", REST_PATH, content, com.explorersguild.entities.map.townendpoint.model.Town.class);
    }

    @Override
    public UpdateTown setAlt(java.lang.String alt) {
      return (UpdateTown) super.setAlt(alt);
    }

    @Override
    public UpdateTown setFields(java.lang.String fields) {
      return (UpdateTown) super.setFields(fields);
    }

    @Override
    public UpdateTown setKey(java.lang.String key) {
      return (UpdateTown) super.setKey(key);
    }

    @Override
    public UpdateTown setOauthToken(java.lang.String oauthToken) {
      return (UpdateTown) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateTown setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateTown) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateTown setQuotaUser(java.lang.String quotaUser) {
      return (UpdateTown) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateTown setUserIp(java.lang.String userIp) {
      return (UpdateTown) super.setUserIp(userIp);
    }

    @Override
    public UpdateTown set(String parameterName, Object value) {
      return (UpdateTown) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Townendpoint}.
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

    /** Builds a new instance of {@link Townendpoint}. */
    @Override
    public Townendpoint build() {
      return new Townendpoint(this);
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
     * Set the {@link TownendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setTownendpointRequestInitializer(
        TownendpointRequestInitializer townendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(townendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
