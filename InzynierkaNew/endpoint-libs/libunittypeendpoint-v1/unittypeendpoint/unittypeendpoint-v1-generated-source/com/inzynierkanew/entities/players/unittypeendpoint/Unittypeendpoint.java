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
 * on 2015-12-09 at 02:24:35 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.players.unittypeendpoint;

/**
 * Service definition for Unittypeendpoint (v1).
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
 * This service uses {@link UnittypeendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Unittypeendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the unittypeendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "unittypeendpoint/v1/";

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
  public Unittypeendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Unittypeendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getUnitType".
   *
   * This request holds the parameters needed by the unittypeendpoint server.  After setting any
   * optional parameters, call the {@link GetUnitType#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetUnitType getUnitType(java.lang.Long id) throws java.io.IOException {
    GetUnitType result = new GetUnitType(id);
    initialize(result);
    return result;
  }

  public class GetUnitType extends UnittypeendpointRequest<com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType> {

    private static final String REST_PATH = "unittype/{id}";

    /**
     * Create a request for the method "getUnitType".
     *
     * This request holds the parameters needed by the the unittypeendpoint server.  After setting any
     * optional parameters, call the {@link GetUnitType#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetUnitType#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetUnitType(java.lang.Long id) {
      super(Unittypeendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType.class);
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
    public GetUnitType setAlt(java.lang.String alt) {
      return (GetUnitType) super.setAlt(alt);
    }

    @Override
    public GetUnitType setFields(java.lang.String fields) {
      return (GetUnitType) super.setFields(fields);
    }

    @Override
    public GetUnitType setKey(java.lang.String key) {
      return (GetUnitType) super.setKey(key);
    }

    @Override
    public GetUnitType setOauthToken(java.lang.String oauthToken) {
      return (GetUnitType) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUnitType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUnitType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUnitType setQuotaUser(java.lang.String quotaUser) {
      return (GetUnitType) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUnitType setUserIp(java.lang.String userIp) {
      return (GetUnitType) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetUnitType setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetUnitType set(String parameterName, Object value) {
      return (GetUnitType) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertUnitType".
   *
   * This request holds the parameters needed by the unittypeendpoint server.  After setting any
   * optional parameters, call the {@link InsertUnitType#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType}
   * @return the request
   */
  public InsertUnitType insertUnitType(com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType content) throws java.io.IOException {
    InsertUnitType result = new InsertUnitType(content);
    initialize(result);
    return result;
  }

  public class InsertUnitType extends UnittypeendpointRequest<com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType> {

    private static final String REST_PATH = "unittype";

    /**
     * Create a request for the method "insertUnitType".
     *
     * This request holds the parameters needed by the the unittypeendpoint server.  After setting any
     * optional parameters, call the {@link InsertUnitType#execute()} method to invoke the remote
     * operation. <p> {@link InsertUnitType#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType}
     * @since 1.13
     */
    protected InsertUnitType(com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType content) {
      super(Unittypeendpoint.this, "POST", REST_PATH, content, com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType.class);
    }

    @Override
    public InsertUnitType setAlt(java.lang.String alt) {
      return (InsertUnitType) super.setAlt(alt);
    }

    @Override
    public InsertUnitType setFields(java.lang.String fields) {
      return (InsertUnitType) super.setFields(fields);
    }

    @Override
    public InsertUnitType setKey(java.lang.String key) {
      return (InsertUnitType) super.setKey(key);
    }

    @Override
    public InsertUnitType setOauthToken(java.lang.String oauthToken) {
      return (InsertUnitType) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertUnitType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertUnitType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertUnitType setQuotaUser(java.lang.String quotaUser) {
      return (InsertUnitType) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertUnitType setUserIp(java.lang.String userIp) {
      return (InsertUnitType) super.setUserIp(userIp);
    }

    @Override
    public InsertUnitType set(String parameterName, Object value) {
      return (InsertUnitType) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listUnitType".
   *
   * This request holds the parameters needed by the unittypeendpoint server.  After setting any
   * optional parameters, call the {@link ListUnitType#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListUnitType listUnitType() throws java.io.IOException {
    ListUnitType result = new ListUnitType();
    initialize(result);
    return result;
  }

  public class ListUnitType extends UnittypeendpointRequest<com.inzynierkanew.entities.players.unittypeendpoint.model.CollectionResponseUnitType> {

    private static final String REST_PATH = "unittype";

    /**
     * Create a request for the method "listUnitType".
     *
     * This request holds the parameters needed by the the unittypeendpoint server.  After setting any
     * optional parameters, call the {@link ListUnitType#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListUnitType#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListUnitType() {
      super(Unittypeendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.players.unittypeendpoint.model.CollectionResponseUnitType.class);
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
    public ListUnitType setAlt(java.lang.String alt) {
      return (ListUnitType) super.setAlt(alt);
    }

    @Override
    public ListUnitType setFields(java.lang.String fields) {
      return (ListUnitType) super.setFields(fields);
    }

    @Override
    public ListUnitType setKey(java.lang.String key) {
      return (ListUnitType) super.setKey(key);
    }

    @Override
    public ListUnitType setOauthToken(java.lang.String oauthToken) {
      return (ListUnitType) super.setOauthToken(oauthToken);
    }

    @Override
    public ListUnitType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListUnitType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListUnitType setQuotaUser(java.lang.String quotaUser) {
      return (ListUnitType) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListUnitType setUserIp(java.lang.String userIp) {
      return (ListUnitType) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListUnitType setCursor(java.lang.String cursor) {
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

    public ListUnitType setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListUnitType set(String parameterName, Object value) {
      return (ListUnitType) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeUnitType".
   *
   * This request holds the parameters needed by the unittypeendpoint server.  After setting any
   * optional parameters, call the {@link RemoveUnitType#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveUnitType removeUnitType(java.lang.Long id) throws java.io.IOException {
    RemoveUnitType result = new RemoveUnitType(id);
    initialize(result);
    return result;
  }

  public class RemoveUnitType extends UnittypeendpointRequest<Void> {

    private static final String REST_PATH = "unittype/{id}";

    /**
     * Create a request for the method "removeUnitType".
     *
     * This request holds the parameters needed by the the unittypeendpoint server.  After setting any
     * optional parameters, call the {@link RemoveUnitType#execute()} method to invoke the remote
     * operation. <p> {@link RemoveUnitType#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveUnitType(java.lang.Long id) {
      super(Unittypeendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveUnitType setAlt(java.lang.String alt) {
      return (RemoveUnitType) super.setAlt(alt);
    }

    @Override
    public RemoveUnitType setFields(java.lang.String fields) {
      return (RemoveUnitType) super.setFields(fields);
    }

    @Override
    public RemoveUnitType setKey(java.lang.String key) {
      return (RemoveUnitType) super.setKey(key);
    }

    @Override
    public RemoveUnitType setOauthToken(java.lang.String oauthToken) {
      return (RemoveUnitType) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveUnitType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveUnitType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveUnitType setQuotaUser(java.lang.String quotaUser) {
      return (RemoveUnitType) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveUnitType setUserIp(java.lang.String userIp) {
      return (RemoveUnitType) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveUnitType setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveUnitType set(String parameterName, Object value) {
      return (RemoveUnitType) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateUnitType".
   *
   * This request holds the parameters needed by the unittypeendpoint server.  After setting any
   * optional parameters, call the {@link UpdateUnitType#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType}
   * @return the request
   */
  public UpdateUnitType updateUnitType(com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType content) throws java.io.IOException {
    UpdateUnitType result = new UpdateUnitType(content);
    initialize(result);
    return result;
  }

  public class UpdateUnitType extends UnittypeendpointRequest<com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType> {

    private static final String REST_PATH = "unittype";

    /**
     * Create a request for the method "updateUnitType".
     *
     * This request holds the parameters needed by the the unittypeendpoint server.  After setting any
     * optional parameters, call the {@link UpdateUnitType#execute()} method to invoke the remote
     * operation. <p> {@link UpdateUnitType#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType}
     * @since 1.13
     */
    protected UpdateUnitType(com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType content) {
      super(Unittypeendpoint.this, "PUT", REST_PATH, content, com.inzynierkanew.entities.players.unittypeendpoint.model.UnitType.class);
    }

    @Override
    public UpdateUnitType setAlt(java.lang.String alt) {
      return (UpdateUnitType) super.setAlt(alt);
    }

    @Override
    public UpdateUnitType setFields(java.lang.String fields) {
      return (UpdateUnitType) super.setFields(fields);
    }

    @Override
    public UpdateUnitType setKey(java.lang.String key) {
      return (UpdateUnitType) super.setKey(key);
    }

    @Override
    public UpdateUnitType setOauthToken(java.lang.String oauthToken) {
      return (UpdateUnitType) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateUnitType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateUnitType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateUnitType setQuotaUser(java.lang.String quotaUser) {
      return (UpdateUnitType) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateUnitType setUserIp(java.lang.String userIp) {
      return (UpdateUnitType) super.setUserIp(userIp);
    }

    @Override
    public UpdateUnitType set(String parameterName, Object value) {
      return (UpdateUnitType) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Unittypeendpoint}.
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

    /** Builds a new instance of {@link Unittypeendpoint}. */
    @Override
    public Unittypeendpoint build() {
      return new Unittypeendpoint(this);
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
     * Set the {@link UnittypeendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setUnittypeendpointRequestInitializer(
        UnittypeendpointRequestInitializer unittypeendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(unittypeendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
