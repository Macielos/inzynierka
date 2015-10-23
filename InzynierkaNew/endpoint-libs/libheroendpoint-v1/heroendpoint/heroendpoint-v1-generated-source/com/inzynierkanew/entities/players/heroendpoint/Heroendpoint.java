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
 * on 2015-10-22 at 19:42:24 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.players.heroendpoint;

/**
 * Service definition for Heroendpoint (v1).
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
 * This service uses {@link HeroendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Heroendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the heroendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "heroendpoint/v1/";

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
  public Heroendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Heroendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getHero".
   *
   * This request holds the parameters needed by the heroendpoint server.  After setting any optional
   * parameters, call the {@link GetHero#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetHero getHero(java.lang.Long id) throws java.io.IOException {
    GetHero result = new GetHero(id);
    initialize(result);
    return result;
  }

  public class GetHero extends HeroendpointRequest<com.inzynierkanew.entities.players.heroendpoint.model.Hero> {

    private static final String REST_PATH = "hero/{id}";

    /**
     * Create a request for the method "getHero".
     *
     * This request holds the parameters needed by the the heroendpoint server.  After setting any
     * optional parameters, call the {@link GetHero#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetHero#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetHero(java.lang.Long id) {
      super(Heroendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.players.heroendpoint.model.Hero.class);
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
    public GetHero setAlt(java.lang.String alt) {
      return (GetHero) super.setAlt(alt);
    }

    @Override
    public GetHero setFields(java.lang.String fields) {
      return (GetHero) super.setFields(fields);
    }

    @Override
    public GetHero setKey(java.lang.String key) {
      return (GetHero) super.setKey(key);
    }

    @Override
    public GetHero setOauthToken(java.lang.String oauthToken) {
      return (GetHero) super.setOauthToken(oauthToken);
    }

    @Override
    public GetHero setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetHero) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetHero setQuotaUser(java.lang.String quotaUser) {
      return (GetHero) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetHero setUserIp(java.lang.String userIp) {
      return (GetHero) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetHero setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetHero set(String parameterName, Object value) {
      return (GetHero) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertHero".
   *
   * This request holds the parameters needed by the heroendpoint server.  After setting any optional
   * parameters, call the {@link InsertHero#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.inzynierkanew.entities.players.heroendpoint.model.Hero}
   * @return the request
   */
  public InsertHero insertHero(com.inzynierkanew.entities.players.heroendpoint.model.Hero content) throws java.io.IOException {
    InsertHero result = new InsertHero(content);
    initialize(result);
    return result;
  }

  public class InsertHero extends HeroendpointRequest<com.inzynierkanew.entities.players.heroendpoint.model.Hero> {

    private static final String REST_PATH = "hero";

    /**
     * Create a request for the method "insertHero".
     *
     * This request holds the parameters needed by the the heroendpoint server.  After setting any
     * optional parameters, call the {@link InsertHero#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertHero#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.players.heroendpoint.model.Hero}
     * @since 1.13
     */
    protected InsertHero(com.inzynierkanew.entities.players.heroendpoint.model.Hero content) {
      super(Heroendpoint.this, "POST", REST_PATH, content, com.inzynierkanew.entities.players.heroendpoint.model.Hero.class);
    }

    @Override
    public InsertHero setAlt(java.lang.String alt) {
      return (InsertHero) super.setAlt(alt);
    }

    @Override
    public InsertHero setFields(java.lang.String fields) {
      return (InsertHero) super.setFields(fields);
    }

    @Override
    public InsertHero setKey(java.lang.String key) {
      return (InsertHero) super.setKey(key);
    }

    @Override
    public InsertHero setOauthToken(java.lang.String oauthToken) {
      return (InsertHero) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertHero setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertHero) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertHero setQuotaUser(java.lang.String quotaUser) {
      return (InsertHero) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertHero setUserIp(java.lang.String userIp) {
      return (InsertHero) super.setUserIp(userIp);
    }

    @Override
    public InsertHero set(String parameterName, Object value) {
      return (InsertHero) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listHero".
   *
   * This request holds the parameters needed by the heroendpoint server.  After setting any optional
   * parameters, call the {@link ListHero#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public ListHero listHero() throws java.io.IOException {
    ListHero result = new ListHero();
    initialize(result);
    return result;
  }

  public class ListHero extends HeroendpointRequest<com.inzynierkanew.entities.players.heroendpoint.model.CollectionResponseHero> {

    private static final String REST_PATH = "hero";

    /**
     * Create a request for the method "listHero".
     *
     * This request holds the parameters needed by the the heroendpoint server.  After setting any
     * optional parameters, call the {@link ListHero#execute()} method to invoke the remote operation.
     * <p> {@link
     * ListHero#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListHero() {
      super(Heroendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.players.heroendpoint.model.CollectionResponseHero.class);
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
    public ListHero setAlt(java.lang.String alt) {
      return (ListHero) super.setAlt(alt);
    }

    @Override
    public ListHero setFields(java.lang.String fields) {
      return (ListHero) super.setFields(fields);
    }

    @Override
    public ListHero setKey(java.lang.String key) {
      return (ListHero) super.setKey(key);
    }

    @Override
    public ListHero setOauthToken(java.lang.String oauthToken) {
      return (ListHero) super.setOauthToken(oauthToken);
    }

    @Override
    public ListHero setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListHero) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListHero setQuotaUser(java.lang.String quotaUser) {
      return (ListHero) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListHero setUserIp(java.lang.String userIp) {
      return (ListHero) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListHero setCursor(java.lang.String cursor) {
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

    public ListHero setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListHero set(String parameterName, Object value) {
      return (ListHero) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeHero".
   *
   * This request holds the parameters needed by the heroendpoint server.  After setting any optional
   * parameters, call the {@link RemoveHero#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public RemoveHero removeHero(java.lang.Long id) throws java.io.IOException {
    RemoveHero result = new RemoveHero(id);
    initialize(result);
    return result;
  }

  public class RemoveHero extends HeroendpointRequest<Void> {

    private static final String REST_PATH = "hero/{id}";

    /**
     * Create a request for the method "removeHero".
     *
     * This request holds the parameters needed by the the heroendpoint server.  After setting any
     * optional parameters, call the {@link RemoveHero#execute()} method to invoke the remote
     * operation. <p> {@link
     * RemoveHero#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveHero(java.lang.Long id) {
      super(Heroendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveHero setAlt(java.lang.String alt) {
      return (RemoveHero) super.setAlt(alt);
    }

    @Override
    public RemoveHero setFields(java.lang.String fields) {
      return (RemoveHero) super.setFields(fields);
    }

    @Override
    public RemoveHero setKey(java.lang.String key) {
      return (RemoveHero) super.setKey(key);
    }

    @Override
    public RemoveHero setOauthToken(java.lang.String oauthToken) {
      return (RemoveHero) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveHero setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveHero) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveHero setQuotaUser(java.lang.String quotaUser) {
      return (RemoveHero) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveHero setUserIp(java.lang.String userIp) {
      return (RemoveHero) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveHero setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveHero set(String parameterName, Object value) {
      return (RemoveHero) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateHero".
   *
   * This request holds the parameters needed by the heroendpoint server.  After setting any optional
   * parameters, call the {@link UpdateHero#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.inzynierkanew.entities.players.heroendpoint.model.Hero}
   * @return the request
   */
  public UpdateHero updateHero(com.inzynierkanew.entities.players.heroendpoint.model.Hero content) throws java.io.IOException {
    UpdateHero result = new UpdateHero(content);
    initialize(result);
    return result;
  }

  public class UpdateHero extends HeroendpointRequest<com.inzynierkanew.entities.players.heroendpoint.model.Hero> {

    private static final String REST_PATH = "hero";

    /**
     * Create a request for the method "updateHero".
     *
     * This request holds the parameters needed by the the heroendpoint server.  After setting any
     * optional parameters, call the {@link UpdateHero#execute()} method to invoke the remote
     * operation. <p> {@link
     * UpdateHero#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.players.heroendpoint.model.Hero}
     * @since 1.13
     */
    protected UpdateHero(com.inzynierkanew.entities.players.heroendpoint.model.Hero content) {
      super(Heroendpoint.this, "PUT", REST_PATH, content, com.inzynierkanew.entities.players.heroendpoint.model.Hero.class);
    }

    @Override
    public UpdateHero setAlt(java.lang.String alt) {
      return (UpdateHero) super.setAlt(alt);
    }

    @Override
    public UpdateHero setFields(java.lang.String fields) {
      return (UpdateHero) super.setFields(fields);
    }

    @Override
    public UpdateHero setKey(java.lang.String key) {
      return (UpdateHero) super.setKey(key);
    }

    @Override
    public UpdateHero setOauthToken(java.lang.String oauthToken) {
      return (UpdateHero) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateHero setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateHero) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateHero setQuotaUser(java.lang.String quotaUser) {
      return (UpdateHero) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateHero setUserIp(java.lang.String userIp) {
      return (UpdateHero) super.setUserIp(userIp);
    }

    @Override
    public UpdateHero set(String parameterName, Object value) {
      return (UpdateHero) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Heroendpoint}.
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

    /** Builds a new instance of {@link Heroendpoint}. */
    @Override
    public Heroendpoint build() {
      return new Heroendpoint(this);
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
     * Set the {@link HeroendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setHeroendpointRequestInitializer(
        HeroendpointRequestInitializer heroendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(heroendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
