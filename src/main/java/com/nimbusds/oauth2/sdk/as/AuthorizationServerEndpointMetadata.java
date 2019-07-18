/*
 * oauth2-oidc-sdk
 *
 * Copyright 2012-2016, Connect2id Ltd and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.nimbusds.oauth2.sdk.as;


import java.net.URI;
import java.util.*;

import net.minidev.json.JSONObject;

import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.oauth2.sdk.util.OrderedJSONObject;


/**
 * OAuth 2.0 Authorisation Server (AS) metadata for the endpoints.
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>OAuth 2.0 Authorization Server Metadata (RFC 8414)
 *     <li>OAuth 2.0 Mutual TLS Client Authentication and Certificate Bound
 *         Access Tokens (draft-ietf-oauth-mtls-15)
 *     <li>OAuth 2.0 Device Flow for Browserless and Input Constrained Devices
 *         (draft-ietf-oauth-device-flow-14)
 * </ul>
 */
public class AuthorizationServerEndpointMetadata {
	
	/**
	 * The registered parameter names.
	 */
	private static final Set<String> REGISTERED_PARAMETER_NAMES;
	
	
	static {
		Set<String> p = new HashSet<>();
		p.add("authorization_endpoint");
		p.add("token_endpoint");
		p.add("registration_endpoint");
		p.add("introspection_endpoint");
		p.add("revocation_endpoint");
		p.add("device_authorization_endpoint");
		p.add("request_object_endpoint");
		REGISTERED_PARAMETER_NAMES = Collections.unmodifiableSet(p);
	}
	
	
	/**
	 * Gets the registered provider metadata parameter names for endpoints.
	 *
	 * @return The registered provider metadata parameter names for endpoints,
	 * as an unmodifiable set.
	 */
	public static Set<String> getRegisteredParameterNames() {
		
		return REGISTERED_PARAMETER_NAMES;
	}
	
	
	/**
	 * The authorisation endpoint.
	 */
	private URI authzEndpoint;
	
	
	/**
	 * The token endpoint.
	 */
	private URI tokenEndpoint;
	
	
	/**
	 * The registration endpoint.
	 */
	private URI regEndpoint;
	
	
	/**
	 * The token introspection endpoint.
	 */
	private URI introspectionEndpoint;
	
	
	/**
	 * The token revocation endpoint.
	 */
	private URI revocationEndpoint;
	
	
	/**
	 * The request object endpoint.
	 */
	private URI requestObjectEndpoint;
	
	
	/**
	 * The device authorization endpoint.
	 */
	private URI deviceAuthzEndpoint;
	
	
	/**
	 * Creates a new OAuth 2.0 Authorisation Server (AS) endpoint metadata instance.
	 */
	public AuthorizationServerEndpointMetadata() {
	}
	
	
	/**
	 * Gets the authorisation endpoint URI. Corresponds the
	 * {@code authorization_endpoint} metadata field.
	 *
	 * @return The authorisation endpoint URI, {@code null} if not
	 *         specified.
	 */
	public URI getAuthorizationEndpointURI() {
		
		return authzEndpoint;
	}
	
	
	/**
	 * Sets the authorisation endpoint URI. Corresponds the
	 * {@code authorization_endpoint} metadata field.
	 *
	 * @param authzEndpoint The authorisation endpoint URI, {@code null} if
	 *                      not specified.
	 */
	public void setAuthorizationEndpointURI(final URI authzEndpoint) {
		
		this.authzEndpoint = authzEndpoint;
	}
	
	
	/**
	 * Gets the token endpoint URI. Corresponds the {@code token_endpoint}
	 * metadata field.
	 *
	 * @return The token endpoint URI, {@code null} if not specified.
	 */
	public URI getTokenEndpointURI() {
		
		return tokenEndpoint;
	}
	
	
	/**
	 * Sts the token endpoint URI. Corresponds the {@code token_endpoint}
	 * metadata field.
	 *
	 * @param tokenEndpoint The token endpoint URI, {@code null} if not
	 *                      specified.
	 */
	public void setTokenEndpointURI(final URI tokenEndpoint) {
		
		this.tokenEndpoint = tokenEndpoint;
	}
	
	
	/**
	 * Gets the client registration endpoint URI. Corresponds to the
	 * {@code registration_endpoint} metadata field.
	 *
	 * @return The client registration endpoint URI, {@code null} if not
	 *         specified.
	 */
	public URI getRegistrationEndpointURI() {
		
		return regEndpoint;
	}
	
	
	/**
	 * Sets the client registration endpoint URI. Corresponds to the
	 * {@code registration_endpoint} metadata field.
	 *
	 * @param regEndpoint The client registration endpoint URI,
	 *                    {@code null} if not specified.
	 */
	public void setRegistrationEndpointURI(final URI regEndpoint) {
		
		this.regEndpoint = regEndpoint;
	}
	
	
	/**
	 * Gets the token introspection endpoint URI. Corresponds to the
	 * {@code introspection_endpoint} metadata field.
	 *
	 * @return The token introspection endpoint URI, {@code null} if not
	 *         specified.
	 */
	public URI getIntrospectionEndpointURI() {
		
		return introspectionEndpoint;
	}
	
	
	/**
	 * Sets the token introspection endpoint URI. Corresponds to the
	 * {@code introspection_endpoint} metadata field.
	 *
	 * @param introspectionEndpoint  The token introspection endpoint URI,
	 *                               {@code null} if not specified.
	 */
	public void setIntrospectionEndpointURI(final URI introspectionEndpoint) {
		
		this.introspectionEndpoint = introspectionEndpoint;
	}
	
	
	/**
	 * Gets the token revocation endpoint URI. Corresponds to the
	 * {@code revocation_endpoint} metadata field.
	 *
	 * @return The token revocation endpoint URI, {@code null} if not
	 *         specified.
	 */
	public URI getRevocationEndpointURI() {
		
		return revocationEndpoint;
	}
	
	
	/**
	 * Sets the token revocation endpoint URI. Corresponds to the
	 * {@code revocation_endpoint} metadata field.
	 *
	 * @param revocationEndpoint The token revocation endpoint URI,
	 *                           {@code null} if not specified.
	 */
	public void setRevocationEndpointURI(final URI revocationEndpoint) {
		
		this.revocationEndpoint = revocationEndpoint;
	}
	
	
	/**
	 * Gets the request object endpoint. Corresponds to the
	 * {@code request_object_endpoint} metadata field.
	 *
	 * @return The request object endpoint, {@code null} if not specified.
	 */
	public URI getRequestObjectEndpoint() {
		
		return requestObjectEndpoint;
	}
	
	
	/**
	 * Sets the request object endpoint. Corresponds to the
	 * {@code request_object_endpoint} metadata field.
	 *
	 * @param requestObjectEndpoint The request object endpoint,
	 *                              {@code null} if not specified.
	 */
	public void setRequestObjectEndpoint(final URI requestObjectEndpoint) {
		
		this.requestObjectEndpoint = requestObjectEndpoint;
	}
	
	
	/**
	 * Gets the device authorization endpoint URI. Corresponds the
	 * {@code device_authorization_endpoint} metadata field.
	 *
	 * @return The device authorization endpoint URI, {@code null} if not
	 *         specified.
	 */
	public URI getDeviceAuthorizationEndpointURI() {
		
		return deviceAuthzEndpoint;
	}
	
	
	/**
	 * Sets the device authorization endpoint URI. Corresponds the
	 * {@code device_authorization_endpoint} metadata field.
	 *
	 * @param deviceAuthzEndpoint The device authorization endpoint URI,
	 *                            {@code null} if not specified.
	 */
	public void setDeviceAuthorizationEndpointURI(final URI deviceAuthzEndpoint) {
		
		this.deviceAuthzEndpoint = deviceAuthzEndpoint;
	}
	
	
	/**
	 * Returns the JSON object representation of this OpenID Connect
	 * provider metadata.
	 *
	 * @return The JSON object representation.
	 */
	public JSONObject toJSONObject() {
		
		JSONObject o = new OrderedJSONObject();
		
		if (authzEndpoint != null)
			o.put("authorization_endpoint", authzEndpoint.toString());
		
		if (tokenEndpoint != null)
			o.put("token_endpoint", tokenEndpoint.toString());
		
		if (regEndpoint != null)
			o.put("registration_endpoint", regEndpoint.toString());
		
		if (introspectionEndpoint != null)
			o.put("introspection_endpoint", introspectionEndpoint.toString());
		
		if (revocationEndpoint != null)
			o.put("revocation_endpoint", revocationEndpoint.toString());
		
		if (requestObjectEndpoint != null)
			o.put("request_object_endpoint", requestObjectEndpoint.toString());
		
		if (deviceAuthzEndpoint != null)
			o.put("device_authorization_endpoint", deviceAuthzEndpoint.toString());
		
		return o;
	}
	
	
	@Override
	public String toString() {
		return toJSONObject().toJSONString();
	}
	
	
	/**
	 * Parses an OAuth 2.0 Authorisation Server endpoint metadata from the specified
	 * JSON object.
	 *
	 * @param jsonObject The JSON object to parse. Must not be
	 *                   {@code null}.
	 *
	 * @return The OAuth 2.0 Authorisation Server endpoint metadata.
	 *
	 * @throws ParseException If the JSON object couldn't be parsed to an
	 *                        OAuth 2.0 Authorisation Server endpoint metadata.
	 */
	public static AuthorizationServerEndpointMetadata parse(final JSONObject jsonObject)
		throws ParseException {
		
		// Parse issuer and subject_types_supported first
		
		AuthorizationServerEndpointMetadata as = new AuthorizationServerEndpointMetadata();
		
		as.authzEndpoint = JSONObjectUtils.getURI(jsonObject, "authorization_endpoint", null);
		as.tokenEndpoint = JSONObjectUtils.getURI(jsonObject, "token_endpoint", null);
		as.regEndpoint = JSONObjectUtils.getURI(jsonObject, "registration_endpoint", null);
		as.introspectionEndpoint = JSONObjectUtils.getURI(jsonObject, "introspection_endpoint", null);
		as.revocationEndpoint = JSONObjectUtils.getURI(jsonObject, "revocation_endpoint", null);
		as.deviceAuthzEndpoint = JSONObjectUtils.getURI(jsonObject, "device_authorization_endpoint", null);
		as.requestObjectEndpoint = JSONObjectUtils.getURI(jsonObject, "request_object_endpoint", null);
		
		return as;
	}
}
