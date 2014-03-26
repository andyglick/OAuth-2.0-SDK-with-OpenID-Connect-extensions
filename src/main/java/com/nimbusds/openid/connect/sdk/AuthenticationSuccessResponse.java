package com.nimbusds.openid.connect.sdk;


import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import net.jcip.annotations.Immutable;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationSuccessResponse;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.SerializeException;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.util.URIUtils;
import com.nimbusds.oauth2.sdk.util.URLUtils;


/**
 * OpenID Connect authentication success response. Used to return an
 * authorisation code, access token and / or ID Token at the Authorisation
 * endpoint.
 *
 * <p>Example HTTP response with code and ID Token (code flow):
 *
 * <pre>
 * HTTP/1.1 302 Found
 * Location: https://client.example.org/cb#
 * code=Qcb0Orv1zh30vL1MPRsbm-diHiMwcLyZvn1arpZv-Jxf_11jnpEX3Tgfvk
 * &amp;id_token=eyJhbGciOiJSUzI1NiJ9.ew0KICAgICJpc3MiOiAiaHR0cDovL3Nlc
 * nZlci5leGFtcGxlLmNvbSIsDQogICAgInVzZXJfaWQiOiAiMjQ4Mjg5NzYxMDAxI
 * iwNCiAgICAiYXVkIjogInM2QmhkUmtxdDMiLA0KICAgICJub25jZSI6ICJuLTBTN
 * l9XekEyTWoiLA0KICAgICJleHAiOiAxMzExMjgxOTcwLA0KICAgICJpYXQiOiAxM
 * zExMjgwOTcwLA0KICAgICJjX2hhc2giOiAiTERrdEtkb1FhazNQazBjblh4Q2x0Q
 * mdfckNfM1RLVWI5T0xrNWZLTzl1QSINCn0.D6JxCgpOwlyuK7DPRu5hFOIJRSRDT
 * B7TQNRbOw9Vg9WroDi_XNzaqXCFSDH_YqcE-CBhoxD-Iq4eQL4E2jIjil47u7i68
 * Nheev7d8AJk4wfRimgpDhQX5K8YyGDWrTs7bhsMTPAPVa9bLIBndDZ2mEdmPcmR9
 * mXcwJI3IGF9JOaStYXJXMYWUMCmQARZEKG9JxIYPZNhFsqKe4TYQEmrq2s_HHQwk
 * XCGAmLBdptHY-Zx277qtidojQQFXzbD2Ak1ONT5sFjy3yxPnE87pNVtOEST5GJac
 * O1O88gmvmjNayu1-f5mr5Uc70QC6DjlKem3cUN5kudAQ4sLvFkUr8gkIQ
 * </pre>
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>OpenID Connect Core 1.0, section 3.1.2.5, 3.1.2.6, 3.2.2.5, 3.2.2.6,
 *         3.3.2.5 and 3.3.2.6.
 * </ul>
 */
@Immutable
public class AuthenticationSuccessResponse
	extends AuthorizationSuccessResponse
	implements AuthenticationResponse {


	/**
	 * The ID token, if requested.
	 */
	private final JWT idToken;
	
	
	/**
	 * Creates a new OpenID Connect authentication success response.
	 *
	 * @param redirectURI The requested redirection URI. Must not be
	 *                    {@code null}.
	 * @param code        The authorisation code, {@code null} if not 
	 *                    requested.
	 * @param idToken     The ID token (ready for output), {@code null} if 
	 *                    not requested.
	 * @param accessToken The UserInfo access token, {@code null} if not 
	 *                    requested.
	 * @param state       The state, {@code null} if not requested.
	 */
	public AuthenticationSuccessResponse(final URI redirectURI,
					     final AuthorizationCode code,
					     final JWT idToken,
					     final AccessToken accessToken,
					     final State state) {
		
		super(redirectURI, code, accessToken, state);

		this.idToken = idToken;
	}
	
	
	@Override
	public ResponseType impliedResponseType() {
	
		ResponseType rt = new ResponseType();
		
		if (getAuthorizationCode() != null) {
			rt.add(ResponseType.Value.CODE);
		}

		if (getIDToken() != null) {
			rt.add(OIDCResponseTypeValue.ID_TOKEN);
		}
		
		if (getAccessToken() != null) {
			rt.add(ResponseType.Value.TOKEN);
		}
			
		return rt;
	}
	
	
	/**
	 * Gets the requested ID token.
	 *
	 * @return The ID token (ready for output), {@code null} if not 
	 *         requested.
	 */
	public JWT getIDToken() {
	
		return idToken;
	}
	
	
	@Override
	public Map<String,String> toParameters()
		throws SerializeException {
	
		Map<String,String> params = super.toParameters();

		if (idToken != null) {

			try {
				params.put("id_token", idToken.serialize());		
				
			} catch (IllegalStateException e) {
			
				throw new SerializeException("Couldn't serialize ID token: " + e.getMessage(), e);
			
			}
		}

		return params;
	}


	@Override
	public URI toURI()
		throws SerializeException {

		StringBuilder sb = new StringBuilder(getRedirectionURI().toString());

		// Fragment or query string?
		if (idToken != null || getAccessToken() != null) {
			sb.append('#');
		} else {
			sb.append('?');
		}

		sb.append(URLUtils.serializeParameters(toParameters()));

		try {
			return new URI(sb.toString());

		} catch (URISyntaxException e) {

			throw new SerializeException("Couldn't serialize response: " + e.getMessage(), e);
		}
	}


	/**
	 * Parses an OpenID Connect authentication success response from the
	 * specified redirection URI and parameters.
	 *
	 * @param redirectURI The base redirection URI. Must not be
	 *                    {@code null}.
	 * @param params      The response parameters to parse. Must not be 
	 *                    {@code null}.
	 *
	 * @return The OpenID Connect authentication success response.
	 *
	 * @throws ParseException If the parameters couldn't be parsed to an
	 *                        OpenID Connect authentication success
	 *                        response.
	 */
	public static AuthenticationSuccessResponse parse(final URI redirectURI,
							  final Map<String,String> params)
		throws ParseException {

		AuthorizationSuccessResponse asr = AuthorizationSuccessResponse.parse(redirectURI, params);

		// Parse id_token parameter
		JWT idToken = null;
		
		if (params.get("id_token") != null) {
			
			try {
				idToken = JWTParser.parse(params.get("id_token"));
				
			} catch (java.text.ParseException e) {
			
				throw new ParseException("Invalid ID Token JWT: " + e.getMessage(), e);
			}
		}

		return new AuthenticationSuccessResponse(redirectURI,
			                                    asr.getAuthorizationCode(),
			                                    idToken,
			                                    asr.getAccessToken(),
			                                    asr.getState());
	}
	
	
	/**
	 * Parses an OpenID Connect authentication success response from the
	 * specified URI.
	 *
	 * <p>Example URI:
	 *
	 * <pre>
	 * https://client.example.com/cb?code=SplxlOBeZQQYbYS6WxSbIA&amp;state=xyz
	 * </pre>
	 *
	 * @param uri The URI to parse. Can be absolute or relative, with a
	 *            fragment or query string containing the authentication
	 *            response parameters. Must not be {@code null}.
	 *
	 * @return The OpenID Connect authentication success response.
	 *
	 * @throws ParseException If the redirection URI couldn't be parsed to
	 *                        an OpenID Connect authentication success
	 *                        response.
	 */
	public static AuthenticationSuccessResponse parse(final URI uri)
		throws ParseException {
		
		String paramString;
		
		if (uri.getQuery() != null) {

			paramString = uri.getQuery();
				
		} else if (uri.getFragment() != null) {

			paramString = uri.getFragment();
		
		} else {
			throw new ParseException("Missing authorization response parameters");
		}
		
		Map<String,String> params = URLUtils.parseParameters(paramString);

		if (params == null) {
			throw new ParseException("Missing or invalid authorization response parameters");
		}

		return parse(URIUtils.getBaseURI(uri), params);
	}


	/**
	 * Parses an OpenID Connect authentication success response from the
	 * specified HTTP response.
	 *
	 * <p>Example HTTP response:
	 *
	 * <pre>
	 * HTTP/1.1 302 Found
	 * Location: https://client.example.com/cb?code=SplxlOBeZQQYbYS6WxSbIA&amp;state=xyz
	 * </pre>
	 *
	 * @param httpResponse The HTTP response to parse. Must not be 
	 *                     {@code null}.
	 *
	 * @return The OpenID Connect authentication success response.
	 *
	 * @throws ParseException If the HTTP response couldn't be parsed to an 
	 *                        OpenID Connect authentication success
	 *                        response.
	 */
	public static AuthenticationSuccessResponse parse(final HTTPResponse httpResponse)
		throws ParseException {
		
		if (httpResponse.getStatusCode() != HTTPResponse.SC_FOUND)
			throw new ParseException("Unexpected HTTP status code, must be 302 (Found): " + 
			                         httpResponse.getStatusCode());
		
		URL location = httpResponse.getLocation();
		
		if (location == null)
			throw new ParseException("Missing redirection URI / HTTP Location header");

		try {
			return parse(location.toURI());

		} catch (URISyntaxException e) {

			throw new ParseException(e.getMessage(), e);
		}
	}
}