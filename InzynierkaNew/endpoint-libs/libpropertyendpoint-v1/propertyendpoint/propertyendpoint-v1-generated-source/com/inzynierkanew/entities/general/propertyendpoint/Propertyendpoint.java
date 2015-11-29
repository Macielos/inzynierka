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
 * on 2015-11-29 at 00:46:30 UTC 
 * Modify at your own risk.
 */

package com.inzynierkanew.entities.general.propertyendpoint;

/**
 * Service definition for Propertyendpoint (v1).
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
 * This service uses {@link PropertyendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Propertyendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the propertyendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "propertyendpoint/v1/";

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
  public Propertyendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Propertyendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the PropertyEndpoint collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code Propertyendpoint propertyendpoint = new Propertyendpoint(...);}
   *   {@code Propertyendpoint.PropertyEndpoint.List request = propertyendpoint.propertyEndpoint().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public PropertyEndpoint propertyEndpoint() {
    return new PropertyEndpoint();
  }

  /**
   * The "propertyEndpoint" collection of methods.
   */
  public class PropertyEndpoint {

    /**
     * Create a request for the method "propertyEndpoint.insertProperty".
     *
     * This request holds the parameters needed by the propertyendpoint server.  After setting any
     * optional parameters, call the {@link InsertProperty#execute()} method to invoke the remote
     * operation.
     *
     * @param content the {@link com.inzynierkanew.entities.general.propertyendpoint.model.Property}
     * @return the request
     */
    public InsertProperty insertProperty(com.inzynierkanew.entities.general.propertyendpoint.model.Property content) throws java.io.IOException {
      InsertProperty result = new InsertProperty(content);
      initialize(result);
      return result;
    }

    public class InsertProperty extends PropertyendpointRequest<com.inzynierkanew.entities.general.propertyendpoint.model.Property> {

      private static final String REST_PATH = "property";

      /**
       * Create a request for the method "propertyEndpoint.insertProperty".
       *
       * This request holds the parameters needed by the the propertyendpoint server.  After setting any
       * optional parameters, call the {@link InsertProperty#execute()} method to invoke the remote
       * operation. <p> {@link InsertProperty#initialize(com.google.api.client.googleapis.services.Abstr
       * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
       * the constructor. </p>
       *
       * @param content the {@link com.inzynierkanew.entities.general.propertyendpoint.model.Property}
       * @since 1.13
       */
      protected InsertProperty(com.inzynierkanew.entities.general.propertyendpoint.model.Property content) {
        super(Propertyendpoint.this, "POST", REST_PATH, content, com.inzynierkanew.entities.general.propertyendpoint.model.Property.class);
      }

      @Override
      public InsertProperty setAlt(java.lang.String alt) {
        return (InsertProperty) super.setAlt(alt);
      }

      @Override
      public InsertProperty setFields(java.lang.String fields) {
        return (InsertProperty) super.setFields(fields);
      }

      @Override
      public InsertProperty setKey(java.lang.String key) {
        return (InsertProperty) super.setKey(key);
      }

      @Override
      public InsertProperty setOauthToken(java.lang.String oauthToken) {
        return (InsertProperty) super.setOauthToken(oauthToken);
      }

      @Override
      public InsertProperty setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (InsertProperty) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public InsertProperty setQuotaUser(java.lang.String quotaUser) {
        return (InsertProperty) super.setQuotaUser(quotaUser);
      }

      @Override
      public InsertProperty setUserIp(java.lang.String userIp) {
        return (InsertProperty) super.setUserIp(userIp);
      }

      @Override
      public InsertProperty set(String parameterName, Object value) {
        return (InsertProperty) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "propertyEndpoint.removeProperty".
     *
     * This request holds the parameters needed by the propertyendpoint server.  After setting any
     * optional parameters, call the {@link RemoveProperty#execute()} method to invoke the remote
     * operation.
     *
     * @param id
     * @return the request
     */
    public RemoveProperty removeProperty(java.lang.String id) throws java.io.IOException {
      RemoveProperty result = new RemoveProperty(id);
      initialize(result);
      return result;
    }

    public class RemoveProperty extends PropertyendpointRequest<Void> {

      private static final String REST_PATH = "property/{id}";

      /**
       * Create a request for the method "propertyEndpoint.removeProperty".
       *
       * This request holds the parameters needed by the the propertyendpoint server.  After setting any
       * optional parameters, call the {@link RemoveProperty#execute()} method to invoke the remote
       * operation. <p> {@link RemoveProperty#initialize(com.google.api.client.googleapis.services.Abstr
       * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
       * the constructor. </p>
       *
       * @param id
       * @since 1.13
       */
      protected RemoveProperty(java.lang.String id) {
        super(Propertyendpoint.this, "DELETE", REST_PATH, null, Void.class);
        this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
      }

      @Override
      public RemoveProperty setAlt(java.lang.String alt) {
        return (RemoveProperty) super.setAlt(alt);
      }

      @Override
      public RemoveProperty setFields(java.lang.String fields) {
        return (RemoveProperty) super.setFields(fields);
      }

      @Override
      public RemoveProperty setKey(java.lang.String key) {
        return (RemoveProperty) super.setKey(key);
      }

      @Override
      public RemoveProperty setOauthToken(java.lang.String oauthToken) {
        return (RemoveProperty) super.setOauthToken(oauthToken);
      }

      @Override
      public RemoveProperty setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (RemoveProperty) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public RemoveProperty setQuotaUser(java.lang.String quotaUser) {
        return (RemoveProperty) super.setQuotaUser(quotaUser);
      }

      @Override
      public RemoveProperty setUserIp(java.lang.String userIp) {
        return (RemoveProperty) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.String id;

      /**

       */
      public java.lang.String getId() {
        return id;
      }

      public RemoveProperty setId(java.lang.String id) {
        this.id = id;
        return this;
      }

      @Override
      public RemoveProperty set(String parameterName, Object value) {
        return (RemoveProperty) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "propertyEndpoint.updateProperty".
     *
     * This request holds the parameters needed by the propertyendpoint server.  After setting any
     * optional parameters, call the {@link UpdateProperty#execute()} method to invoke the remote
     * operation.
     *
     * @param content the {@link com.inzynierkanew.entities.general.propertyendpoint.model.Property}
     * @return the request
     */
    public UpdateProperty updateProperty(com.inzynierkanew.entities.general.propertyendpoint.model.Property content) throws java.io.IOException {
      UpdateProperty result = new UpdateProperty(content);
      initialize(result);
      return result;
    }

    public class UpdateProperty extends PropertyendpointRequest<com.inzynierkanew.entities.general.propertyendpoint.model.Property> {

      private static final String REST_PATH = "property";

      /**
       * Create a request for the method "propertyEndpoint.updateProperty".
       *
       * This request holds the parameters needed by the the propertyendpoint server.  After setting any
       * optional parameters, call the {@link UpdateProperty#execute()} method to invoke the remote
       * operation. <p> {@link UpdateProperty#initialize(com.google.api.client.googleapis.services.Abstr
       * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
       * the constructor. </p>
       *
       * @param content the {@link com.inzynierkanew.entities.general.propertyendpoint.model.Property}
       * @since 1.13
       */
      protected UpdateProperty(com.inzynierkanew.entities.general.propertyendpoint.model.Property content) {
        super(Propertyendpoint.this, "PUT", REST_PATH, content, com.inzynierkanew.entities.general.propertyendpoint.model.Property.class);
      }

      @Override
      public UpdateProperty setAlt(java.lang.String alt) {
        return (UpdateProperty) super.setAlt(alt);
      }

      @Override
      public UpdateProperty setFields(java.lang.String fields) {
        return (UpdateProperty) super.setFields(fields);
      }

      @Override
      public UpdateProperty setKey(java.lang.String key) {
        return (UpdateProperty) super.setKey(key);
      }

      @Override
      public UpdateProperty setOauthToken(java.lang.String oauthToken) {
        return (UpdateProperty) super.setOauthToken(oauthToken);
      }

      @Override
      public UpdateProperty setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (UpdateProperty) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public UpdateProperty setQuotaUser(java.lang.String quotaUser) {
        return (UpdateProperty) super.setQuotaUser(quotaUser);
      }

      @Override
      public UpdateProperty setUserIp(java.lang.String userIp) {
        return (UpdateProperty) super.setUserIp(userIp);
      }

      @Override
      public UpdateProperty set(String parameterName, Object value) {
        return (UpdateProperty) super.set(parameterName, value);
      }
    }

  }

  /**
   * Create a request for the method "getProperty".
   *
   * This request holds the parameters needed by the propertyendpoint server.  After setting any
   * optional parameters, call the {@link GetProperty#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetProperty getProperty(java.lang.String id) throws java.io.IOException {
    GetProperty result = new GetProperty(id);
    initialize(result);
    return result;
  }

  public class GetProperty extends PropertyendpointRequest<com.inzynierkanew.entities.general.propertyendpoint.model.Property> {

    private static final String REST_PATH = "property/{id}";

    /**
     * Create a request for the method "getProperty".
     *
     * This request holds the parameters needed by the the propertyendpoint server.  After setting any
     * optional parameters, call the {@link GetProperty#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetProperty#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetProperty(java.lang.String id) {
      super(Propertyendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.general.propertyendpoint.model.Property.class);
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
    public GetProperty setAlt(java.lang.String alt) {
      return (GetProperty) super.setAlt(alt);
    }

    @Override
    public GetProperty setFields(java.lang.String fields) {
      return (GetProperty) super.setFields(fields);
    }

    @Override
    public GetProperty setKey(java.lang.String key) {
      return (GetProperty) super.setKey(key);
    }

    @Override
    public GetProperty setOauthToken(java.lang.String oauthToken) {
      return (GetProperty) super.setOauthToken(oauthToken);
    }

    @Override
    public GetProperty setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetProperty) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetProperty setQuotaUser(java.lang.String quotaUser) {
      return (GetProperty) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetProperty setUserIp(java.lang.String userIp) {
      return (GetProperty) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String id;

    /**

     */
    public java.lang.String getId() {
      return id;
    }

    public GetProperty setId(java.lang.String id) {
      this.id = id;
      return this;
    }

    @Override
    public GetProperty set(String parameterName, Object value) {
      return (GetProperty) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listProperty".
   *
   * This request holds the parameters needed by the propertyendpoint server.  After setting any
   * optional parameters, call the {@link ListProperty#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListProperty listProperty() throws java.io.IOException {
    ListProperty result = new ListProperty();
    initialize(result);
    return result;
  }

  public class ListProperty extends PropertyendpointRequest<com.inzynierkanew.entities.general.propertyendpoint.model.CollectionResponseProperty> {

    private static final String REST_PATH = "property";

    /**
     * Create a request for the method "listProperty".
     *
     * This request holds the parameters needed by the the propertyendpoint server.  After setting any
     * optional parameters, call the {@link ListProperty#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListProperty#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListProperty() {
      super(Propertyendpoint.this, "GET", REST_PATH, null, com.inzynierkanew.entities.general.propertyendpoint.model.CollectionResponseProperty.class);
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
    public ListProperty setAlt(java.lang.String alt) {
      return (ListProperty) super.setAlt(alt);
    }

    @Override
    public ListProperty setFields(java.lang.String fields) {
      return (ListProperty) super.setFields(fields);
    }

    @Override
    public ListProperty setKey(java.lang.String key) {
      return (ListProperty) super.setKey(key);
    }

    @Override
    public ListProperty setOauthToken(java.lang.String oauthToken) {
      return (ListProperty) super.setOauthToken(oauthToken);
    }

    @Override
    public ListProperty setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListProperty) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListProperty setQuotaUser(java.lang.String quotaUser) {
      return (ListProperty) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListProperty setUserIp(java.lang.String userIp) {
      return (ListProperty) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListProperty setCursor(java.lang.String cursor) {
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

    public ListProperty setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListProperty set(String parameterName, Object value) {
      return (ListProperty) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Propertyendpoint}.
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

    /** Builds a new instance of {@link Propertyendpoint}. */
    @Override
    public Propertyendpoint build() {
      return new Propertyendpoint(this);
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
     * Set the {@link PropertyendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setPropertyendpointRequestInitializer(
        PropertyendpointRequestInitializer propertyendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(propertyendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
