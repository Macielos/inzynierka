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
 * on 2015-11-03 at 19:29:53 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.map.passageendpoint;

/**
 * Service definition for Passageendpoint (v1).
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
 * This service uses {@link PassageendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Passageendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the passageendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myapp.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "passageendpoint/v1/";

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
  public Passageendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Passageendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getPassage".
   *
   * This request holds the parameters needed by the passageendpoint server.  After setting any
   * optional parameters, call the {@link GetPassage#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetPassage getPassage(java.lang.Long id) throws java.io.IOException {
    GetPassage result = new GetPassage(id);
    initialize(result);
    return result;
  }

  public class GetPassage extends PassageendpointRequest<com.inzynierkanew.entities.map.passageendpoint.model.Passage> {

    private static final String REST_PATH = "passage/{id}";

    /**
     * Create a request for the method "getPassage".
     *
     * This request holds the parameters needed by the the passageendpoint server.  After setting any
     * optional parameters, call the {@link GetPassage#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetPassage#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetPassage(java.lang.Long id) {
      super(Passageendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.map.passageendpoint.model.Passage.class);
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
    public GetPassage setAlt(java.lang.String alt) {
      return (GetPassage) super.setAlt(alt);
    }

    @Override
    public GetPassage setFields(java.lang.String fields) {
      return (GetPassage) super.setFields(fields);
    }

    @Override
    public GetPassage setKey(java.lang.String key) {
      return (GetPassage) super.setKey(key);
    }

    @Override
    public GetPassage setOauthToken(java.lang.String oauthToken) {
      return (GetPassage) super.setOauthToken(oauthToken);
    }

    @Override
    public GetPassage setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetPassage) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetPassage setQuotaUser(java.lang.String quotaUser) {
      return (GetPassage) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetPassage setUserIp(java.lang.String userIp) {
      return (GetPassage) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetPassage setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetPassage set(String parameterName, Object value) {
      return (GetPassage) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertPassage".
   *
   * This request holds the parameters needed by the passageendpoint server.  After setting any
   * optional parameters, call the {@link InsertPassage#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.inzynierkanew.entities.map.passageendpoint.model.Passage}
   * @return the request
   */
  public InsertPassage insertPassage(com.inzynierkanew.entities.map.passageendpoint.model.Passage content) throws java.io.IOException {
    InsertPassage result = new InsertPassage(content);
    initialize(result);
    return result;
  }

  public class InsertPassage extends PassageendpointRequest<com.inzynierkanew.entities.map.passageendpoint.model.Passage> {

    private static final String REST_PATH = "passage";

    /**
     * Create a request for the method "insertPassage".
     *
     * This request holds the parameters needed by the the passageendpoint server.  After setting any
     * optional parameters, call the {@link InsertPassage#execute()} method to invoke the remote
     * operation. <p> {@link InsertPassage#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.map.passageendpoint.model.Passage}
     * @since 1.13
     */
    protected InsertPassage(com.inzynierkanew.entities.map.passageendpoint.model.Passage content) {
      super(Passageendpoint.this, "POST", REST_PATH, content, com.inzynierkanew.entities.map.passageendpoint.model.Passage.class);
    }

    @Override
    public InsertPassage setAlt(java.lang.String alt) {
      return (InsertPassage) super.setAlt(alt);
    }

    @Override
    public InsertPassage setFields(java.lang.String fields) {
      return (InsertPassage) super.setFields(fields);
    }

    @Override
    public InsertPassage setKey(java.lang.String key) {
      return (InsertPassage) super.setKey(key);
    }

    @Override
    public InsertPassage setOauthToken(java.lang.String oauthToken) {
      return (InsertPassage) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertPassage setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertPassage) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertPassage setQuotaUser(java.lang.String quotaUser) {
      return (InsertPassage) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertPassage setUserIp(java.lang.String userIp) {
      return (InsertPassage) super.setUserIp(userIp);
    }

    @Override
    public InsertPassage set(String parameterName, Object value) {
      return (InsertPassage) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listPassage".
   *
   * This request holds the parameters needed by the passageendpoint server.  After setting any
   * optional parameters, call the {@link ListPassage#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListPassage listPassage() throws java.io.IOException {
    ListPassage result = new ListPassage();
    initialize(result);
    return result;
  }

  public class ListPassage extends PassageendpointRequest<com.inzynierkanew.entities.map.passageendpoint.model.CollectionResponsePassage> {

    private static final String REST_PATH = "passage";

    /**
     * Create a request for the method "listPassage".
     *
     * This request holds the parameters needed by the the passageendpoint server.  After setting any
     * optional parameters, call the {@link ListPassage#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListPassage#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListPassage() {
      super(Passageendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.map.passageendpoint.model.CollectionResponsePassage.class);
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
    public ListPassage setAlt(java.lang.String alt) {
      return (ListPassage) super.setAlt(alt);
    }

    @Override
    public ListPassage setFields(java.lang.String fields) {
      return (ListPassage) super.setFields(fields);
    }

    @Override
    public ListPassage setKey(java.lang.String key) {
      return (ListPassage) super.setKey(key);
    }

    @Override
    public ListPassage setOauthToken(java.lang.String oauthToken) {
      return (ListPassage) super.setOauthToken(oauthToken);
    }

    @Override
    public ListPassage setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListPassage) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListPassage setQuotaUser(java.lang.String quotaUser) {
      return (ListPassage) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListPassage setUserIp(java.lang.String userIp) {
      return (ListPassage) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListPassage setCursor(java.lang.String cursor) {
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

    public ListPassage setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListPassage set(String parameterName, Object value) {
      return (ListPassage) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removePassage".
   *
   * This request holds the parameters needed by the passageendpoint server.  After setting any
   * optional parameters, call the {@link RemovePassage#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemovePassage removePassage(java.lang.Long id) throws java.io.IOException {
    RemovePassage result = new RemovePassage(id);
    initialize(result);
    return result;
  }

  public class RemovePassage extends PassageendpointRequest<Void> {

    private static final String REST_PATH = "passage/{id}";

    /**
     * Create a request for the method "removePassage".
     *
     * This request holds the parameters needed by the the passageendpoint server.  After setting any
     * optional parameters, call the {@link RemovePassage#execute()} method to invoke the remote
     * operation. <p> {@link RemovePassage#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemovePassage(java.lang.Long id) {
      super(Passageendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemovePassage setAlt(java.lang.String alt) {
      return (RemovePassage) super.setAlt(alt);
    }

    @Override
    public RemovePassage setFields(java.lang.String fields) {
      return (RemovePassage) super.setFields(fields);
    }

    @Override
    public RemovePassage setKey(java.lang.String key) {
      return (RemovePassage) super.setKey(key);
    }

    @Override
    public RemovePassage setOauthToken(java.lang.String oauthToken) {
      return (RemovePassage) super.setOauthToken(oauthToken);
    }

    @Override
    public RemovePassage setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemovePassage) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemovePassage setQuotaUser(java.lang.String quotaUser) {
      return (RemovePassage) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemovePassage setUserIp(java.lang.String userIp) {
      return (RemovePassage) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemovePassage setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemovePassage set(String parameterName, Object value) {
      return (RemovePassage) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updatePassage".
   *
   * This request holds the parameters needed by the passageendpoint server.  After setting any
   * optional parameters, call the {@link UpdatePassage#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.inzynierkanew.entities.map.passageendpoint.model.Passage}
   * @return the request
   */
  public UpdatePassage updatePassage(com.inzynierkanew.entities.map.passageendpoint.model.Passage content) throws java.io.IOException {
    UpdatePassage result = new UpdatePassage(content);
    initialize(result);
    return result;
  }

  public class UpdatePassage extends PassageendpointRequest<com.inzynierkanew.entities.map.passageendpoint.model.Passage> {

    private static final String REST_PATH = "passage";

    /**
     * Create a request for the method "updatePassage".
     *
     * This request holds the parameters needed by the the passageendpoint server.  After setting any
     * optional parameters, call the {@link UpdatePassage#execute()} method to invoke the remote
     * operation. <p> {@link UpdatePassage#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.map.passageendpoint.model.Passage}
     * @since 1.13
     */
    protected UpdatePassage(com.inzynierkanew.entities.map.passageendpoint.model.Passage content) {
      super(Passageendpoint.this, "PUT", REST_PATH, content, com.inzynierkanew.entities.map.passageendpoint.model.Passage.class);
    }

    @Override
    public UpdatePassage setAlt(java.lang.String alt) {
      return (UpdatePassage) super.setAlt(alt);
    }

    @Override
    public UpdatePassage setFields(java.lang.String fields) {
      return (UpdatePassage) super.setFields(fields);
    }

    @Override
    public UpdatePassage setKey(java.lang.String key) {
      return (UpdatePassage) super.setKey(key);
    }

    @Override
    public UpdatePassage setOauthToken(java.lang.String oauthToken) {
      return (UpdatePassage) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdatePassage setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdatePassage) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdatePassage setQuotaUser(java.lang.String quotaUser) {
      return (UpdatePassage) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdatePassage setUserIp(java.lang.String userIp) {
      return (UpdatePassage) super.setUserIp(userIp);
    }

    @Override
    public UpdatePassage set(String parameterName, Object value) {
      return (UpdatePassage) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Passageendpoint}.
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

    /** Builds a new instance of {@link Passageendpoint}. */
    @Override
    public Passageendpoint build() {
      return new Passageendpoint(this);
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
     * Set the {@link PassageendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setPassageendpointRequestInitializer(
        PassageendpointRequestInitializer passageendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(passageendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
