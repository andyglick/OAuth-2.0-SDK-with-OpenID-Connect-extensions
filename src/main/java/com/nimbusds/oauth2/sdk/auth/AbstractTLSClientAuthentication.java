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

package com.nimbusds.oauth2.sdk.auth;


import java.security.cert.X509Certificate;
import javax.net.ssl.SSLSocketFactory;

import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.id.ClientID;


/**
 * The base abstract class for TLS / X.509 certificate client authentication at
 * the Token endpoint.
 */
abstract class AbstractTLSClientAuthentication extends ClientAuthentication {
	
	/**
	 * The SSL socket factory for the outgoing HTTPS requests, {@code null}
	 * to use the default one.
	 */
	private final SSLSocketFactory sslSocketFactory;
	
	
	/**
	 * The validated client X.509 certificate from the received HTTPS
	 * request, {@code null} for an outgoing HTTPS request.
	 */
	private final X509Certificate x509Certificate;
	
	
	/**
	 * Creates a new abstract TLS / X.509 certificate client
	 * authentication. This constructor is intended for an outgoing token
	 * request.
	 *
	 * @param method           The client authentication method. Must not
	 *                         be {@code null}.
	 * @param clientID         The client identifier. Must not be
	 *                         {@code null}.
	 * @param sslSocketFactory The SSL socket factory to use for the
	 *                         outgoing HTTPS request and to present the
	 *                         client certificate(s), {@code null} to use
	 *                         the default one.
	 */
	protected AbstractTLSClientAuthentication(final ClientAuthenticationMethod method,
						  final ClientID clientID,
						  final SSLSocketFactory sslSocketFactory) {
		
		super(method, clientID);
		this.sslSocketFactory = sslSocketFactory;
		x509Certificate = null;
	}
	
	
	/**
	 * Creates a new abstract TLS / X.509 certificate client
	 * authentication. This constructor is intended for a received token
	 * request.
	 *
	 * @param method          The client authentication method. Must not
	 *                        be {@code null}.
	 * @param clientID        The client identifier. Must not be
	 *                        {@code null}.
	 * @param x509Certificate The validated client X.509 certificate from
	 *                        the received HTTPS request. Must not be
	 *                        {@code null}.
	 */
	protected AbstractTLSClientAuthentication(final ClientAuthenticationMethod method,
						  final ClientID clientID,
						  final X509Certificate x509Certificate) {
		super(method, clientID);
		sslSocketFactory = null;
		
		if (x509Certificate == null) {
			throw new IllegalArgumentException("The client X.509 certificate must not be null");
		}
		
		this.x509Certificate = x509Certificate;
	}
	
	
	/**
	 * Returns the SSL socket factory to use for the outgoing HTTPS request
	 * and to present the client certificate(s).
	 *
	 * @return The SSL socket factory, {@code null} to use the default one.
	 */
	public SSLSocketFactory getSSLSocketFactory() {
		
		return sslSocketFactory;
	}
	
	
	/**
	 * Returns the validated client X.509 certificate from the received
	 * HTTPS request.
	 *
	 * @return The client X.509 certificate, {@code null} for an outgoing
	 *         HTTPS request.
	 */
	public X509Certificate getClientX509Certificate() {
		
		return x509Certificate;
	}
	
	
	@Override
	public void applyTo(final HTTPRequest httpRequest) {
		
		httpRequest.setSSLSocketFactory(sslSocketFactory);
	}
}