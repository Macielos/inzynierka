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
 * on 2015-11-12 at 22:26:23 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.map.fieldtypeendpoint;

/**
 * Service definition for Fieldtypeendpoint (v1).
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
 * This service uses {@link FieldtypeendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Fieldtypeendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the fieldtypeendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "fieldtypeendpoint/v1/";

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
  public Fieldtypeendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Fieldtypeendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "findByName".
   *
   * This request holds the parameters needed by the fieldtypeendpoint server.  After setting any
   * optional parameters, call the {@link FindByName#execute()} method to invoke the remote operation.
   *
   * @param name
   * @return the request
   */
  public FindByName findByName(java.lang.String name) throws java.io.IOException {
    FindByName result = new FindByName(name);
    initialize(result);
    return result;
  }

  public class FindByName extends FieldtypeendpointRequest<com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType> {

    private static final String REST_PATH = "findByName/{name}";

    /**
     * Create a request for the method "findByName".
     *
     * This request holds the parameters needed by the the fieldtypeendpoint server.  After setting
     * any optional parameters, call the {@link FindByName#execute()} method to invoke the remote
     * operation. <p> {@link
     * FindByName#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param name
     * @since 1.13
     */
    protected FindByName(java.lang.String name) {
      super(Fieldtypeendpoint.this, "POST", REST_PATH, null, com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType.class);
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
    }

    @Override
    public FindByName setAlt(java.lang.String alt) {
      return (FindByName) super.setAlt(alt);
    }

    @Override
    public FindByName setFields(java.lang.String fields) {
      return (FindByName) super.setFields(fields);
    }

    @Override
    public FindByName setKey(java.lang.String key) {
      return (FindByName) super.setKey(key);
    }

    @Override
    public FindByName setOauthToken(java.lang.String oauthToken) {
      return (FindByName) super.setOauthToken(oauthToken);
    }

    @Override
    public FindByName setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (FindByName) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public FindByName setQuotaUser(java.lang.String quotaUser) {
      return (FindByName) super.setQuotaUser(quotaUser);
    }

    @Override
    public FindByName setUserIp(java.lang.String userIp) {
      return (FindByName) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public FindByName setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @Override
    public FindByName set(String parameterName, Object value) {
      return (FindByName) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getFieldType".
   *
   * This request holds the parameters needed by the fieldtypeendpoint server.  After setting any
   * optional parameters, call the {@link GetFieldType#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetFieldType getFieldType(java.lang.Long id) throws java.io.IOException {
    GetFieldType result = new GetFieldType(id);
    initialize(result);
    return result;
  }

  public class GetFieldType extends FieldtypeendpointRequest<com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType> {

    private static final String REST_PATH = "fieldtype/{id}";

    /**
     * Create a request for the method "getFieldType".
     *
     * This request holds the parameters needed by the the fieldtypeendpoint server.  After setting
     * any optional parameters, call the {@link GetFieldType#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetFieldType#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetFieldType(java.lang.Long id) {
      super(Fieldtypeendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType.class);
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
    public GetFieldType setAlt(java.lang.String alt) {
      return (GetFieldType) super.setAlt(alt);
    }

    @Override
    public GetFieldType setFields(java.lang.String fields) {
      return (GetFieldType) super.setFields(fields);
    }

    @Override
    public GetFieldType setKey(java.lang.String key) {
      return (GetFieldType) super.setKey(key);
    }

    @Override
    public GetFieldType setOauthToken(java.lang.String oauthToken) {
      return (GetFieldType) super.setOauthToken(oauthToken);
    }

    @Override
    public GetFieldType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetFieldType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetFieldType setQuotaUser(java.lang.String quotaUser) {
      return (GetFieldType) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetFieldType setUserIp(java.lang.String userIp) {
      return (GetFieldType) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetFieldType setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetFieldType set(String parameterName, Object value) {
      return (GetFieldType) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertFieldType".
   *
   * This request holds the parameters needed by the fieldtypeendpoint server.  After setting any
   * optional parameters, call the {@link InsertFieldType#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType}
   * @return the request
   */
  public InsertFieldType insertFieldType(com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType content) throws java.io.IOException {
    InsertFieldType result = new InsertFieldType(content);
    initialize(result);
    return result;
  }

  public class InsertFieldType extends FieldtypeendpointRequest<com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType> {

    private static final String REST_PATH = "fieldtype";

    /**
     * Create a request for the method "insertFieldType".
     *
     * This request holds the parameters needed by the the fieldtypeendpoint server.  After setting
     * any optional parameters, call the {@link InsertFieldType#execute()} method to invoke the remote
     * operation. <p> {@link InsertFieldType#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType}
     * @since 1.13
     */
    protected InsertFieldType(com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType content) {
      super(Fieldtypeendpoint.this, "POST", REST_PATH, content, com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType.class);
    }

    @Override
    public InsertFieldType setAlt(java.lang.String alt) {
      return (InsertFieldType) super.setAlt(alt);
    }

    @Override
    public InsertFieldType setFields(java.lang.String fields) {
      return (InsertFieldType) super.setFields(fields);
    }

    @Override
    public InsertFieldType setKey(java.lang.String key) {
      return (InsertFieldType) super.setKey(key);
    }

    @Override
    public InsertFieldType setOauthToken(java.lang.String oauthToken) {
      return (InsertFieldType) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertFieldType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertFieldType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertFieldType setQuotaUser(java.lang.String quotaUser) {
      return (InsertFieldType) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertFieldType setUserIp(java.lang.String userIp) {
      return (InsertFieldType) super.setUserIp(userIp);
    }

    @Override
    public InsertFieldType set(String parameterName, Object value) {
      return (InsertFieldType) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listFieldType".
   *
   * This request holds the parameters needed by the fieldtypeendpoint server.  After setting any
   * optional parameters, call the {@link ListFieldType#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListFieldType listFieldType() throws java.io.IOException {
    ListFieldType result = new ListFieldType();
    initialize(result);
    return result;
  }

  public class ListFieldType extends FieldtypeendpointRequest<com.inzynierkanew.entities.map.fieldtypeendpoint.model.CollectionResponseFieldType> {

    private static final String REST_PATH = "fieldtype";

    /**
     * Create a request for the method "listFieldType".
     *
     * This request holds the parameters needed by the the fieldtypeendpoint server.  After setting
     * any optional parameters, call the {@link ListFieldType#execute()} method to invoke the remote
     * operation. <p> {@link ListFieldType#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected ListFieldType() {
      super(Fieldtypeendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.map.fieldtypeendpoint.model.CollectionResponseFieldType.class);
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
    public ListFieldType setAlt(java.lang.String alt) {
      return (ListFieldType) super.setAlt(alt);
    }

    @Override
    public ListFieldType setFields(java.lang.String fields) {
      return (ListFieldType) super.setFields(fields);
    }

    @Override
    public ListFieldType setKey(java.lang.String key) {
      return (ListFieldType) super.setKey(key);
    }

    @Override
    public ListFieldType setOauthToken(java.lang.String oauthToken) {
      return (ListFieldType) super.setOauthToken(oauthToken);
    }

    @Override
    public ListFieldType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListFieldType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListFieldType setQuotaUser(java.lang.String quotaUser) {
      return (ListFieldType) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListFieldType setUserIp(java.lang.String userIp) {
      return (ListFieldType) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListFieldType setCursor(java.lang.String cursor) {
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

    public ListFieldType setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListFieldType set(String parameterName, Object value) {
      return (ListFieldType) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeFieldType".
   *
   * This request holds the parameters needed by the fieldtypeendpoint server.  After setting any
   * optional parameters, call the {@link RemoveFieldType#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveFieldType removeFieldType(java.lang.Long id) throws java.io.IOException {
    RemoveFieldType result = new RemoveFieldType(id);
    initialize(result);
    return result;
  }

  public class RemoveFieldType extends FieldtypeendpointRequest<Void> {

    private static final String REST_PATH = "fieldtype/{id}";

    /**
     * Create a request for the method "removeFieldType".
     *
     * This request holds the parameters needed by the the fieldtypeendpoint server.  After setting
     * any optional parameters, call the {@link RemoveFieldType#execute()} method to invoke the remote
     * operation. <p> {@link RemoveFieldType#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveFieldType(java.lang.Long id) {
      super(Fieldtypeendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveFieldType setAlt(java.lang.String alt) {
      return (RemoveFieldType) super.setAlt(alt);
    }

    @Override
    public RemoveFieldType setFields(java.lang.String fields) {
      return (RemoveFieldType) super.setFields(fields);
    }

    @Override
    public RemoveFieldType setKey(java.lang.String key) {
      return (RemoveFieldType) super.setKey(key);
    }

    @Override
    public RemoveFieldType setOauthToken(java.lang.String oauthToken) {
      return (RemoveFieldType) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveFieldType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveFieldType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveFieldType setQuotaUser(java.lang.String quotaUser) {
      return (RemoveFieldType) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveFieldType setUserIp(java.lang.String userIp) {
      return (RemoveFieldType) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveFieldType setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveFieldType set(String parameterName, Object value) {
      return (RemoveFieldType) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateFieldType".
   *
   * This request holds the parameters needed by the fieldtypeendpoint server.  After setting any
   * optional parameters, call the {@link UpdateFieldType#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType}
   * @return the request
   */
  public UpdateFieldType updateFieldType(com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType content) throws java.io.IOException {
    UpdateFieldType result = new UpdateFieldType(content);
    initialize(result);
    return result;
  }

  public class UpdateFieldType extends FieldtypeendpointRequest<com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType> {

    private static final String REST_PATH = "fieldtype";

    /**
     * Create a request for the method "updateFieldType".
     *
     * This request holds the parameters needed by the the fieldtypeendpoint server.  After setting
     * any optional parameters, call the {@link UpdateFieldType#execute()} method to invoke the remote
     * operation. <p> {@link UpdateFieldType#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType}
     * @since 1.13
     */
    protected UpdateFieldType(com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType content) {
      super(Fieldtypeendpoint.this, "PUT", REST_PATH, content, com.inzynierkanew.entities.map.fieldtypeendpoint.model.FieldType.class);
    }

    @Override
    public UpdateFieldType setAlt(java.lang.String alt) {
      return (UpdateFieldType) super.setAlt(alt);
    }

    @Override
    public UpdateFieldType setFields(java.lang.String fields) {
      return (UpdateFieldType) super.setFields(fields);
    }

    @Override
    public UpdateFieldType setKey(java.lang.String key) {
      return (UpdateFieldType) super.setKey(key);
    }

    @Override
    public UpdateFieldType setOauthToken(java.lang.String oauthToken) {
      return (UpdateFieldType) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateFieldType setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateFieldType) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateFieldType setQuotaUser(java.lang.String quotaUser) {
      return (UpdateFieldType) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateFieldType setUserIp(java.lang.String userIp) {
      return (UpdateFieldType) super.setUserIp(userIp);
    }

    @Override
    public UpdateFieldType set(String parameterName, Object value) {
      return (UpdateFieldType) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Fieldtypeendpoint}.
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

    /** Builds a new instance of {@link Fieldtypeendpoint}. */
    @Override
    public Fieldtypeendpoint build() {
      return new Fieldtypeendpoint(this);
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
     * Set the {@link FieldtypeendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setFieldtypeendpointRequestInitializer(
        FieldtypeendpointRequestInitializer fieldtypeendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(fieldtypeendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
