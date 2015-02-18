package com.nimbusds.openid.connect.sdk;


import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import net.minidev.json.JSONObject;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;


/**
 * Tests the OpenID Connect access token response.
 */
public class OIDCAccessTokenResponseTest extends TestCase {


	// Example ID token from OIDC Standard
	private static final String ID_TOKEN_STRING = "eyJhbGciOiJSUzI1NiJ9.ew0KICAgICJpc3MiOiAiaHR0cDovL"+
		"3NlcnZlci5leGFtcGxlLmNvbSIsDQogICAgInVzZXJfaWQiOiAiMjQ4Mjg5NzYxM"+
		"DAxIiwNCiAgICAiYXVkIjogInM2QmhkUmtxdDMiLA0KICAgICJub25jZSI6ICJuL"+
		"TBTNl9XekEyTWoiLA0KICAgICJleHAiOiAxMzExMjgxOTcwLA0KICAgICJpYXQiO"+
		"iAxMzExMjgwOTcwDQp9.lsQI_KNHpl58YY24G9tUHXr3Yp7OKYnEaVpRL0KI4szT"+
		"D6GXpZcgxIpkOCcajyDiIv62R9rBWASV191Akk1BM36gUMm8H5s8xyxNdRfBViCa"+
		"xTqHA7X_vV3U-tSWl6McR5qaSJaNQBpg1oGPjZdPG7zWCG-yEJC4-Fbx2FPOS7-h"+
		"5V0k33O5Okd-OoDUKoFPMd6ur5cIwsNyBazcsHdFHqWlCby5nl_HZdW-PHq0gjzy"+
		"JydB5eYIvOfOHYBRVML9fKwdOLM2xVxJsPwvy3BqlVKc593p2WwItIg52ILWrc6A"+
		"tqkqHxKsAXLVyAoVInYkl_NDBkCqYe2KgNJFzfEC8g";


	public static JWT ID_TOKEN;


	static {
		try {
			ID_TOKEN = JWTParser.parse(ID_TOKEN_STRING);
		} catch (Exception e) {
			ID_TOKEN = null;
		}
	}


	public void testMinimalConstructor()
		throws Exception {

		AccessToken accessToken = new BearerAccessToken("abc123");

		OIDCAccessTokenResponse response = new OIDCAccessTokenResponse(accessToken, null);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertNull(response.getRefreshToken());
		assertNull(response.getIDToken());
		assertNull(response.getIDTokenString());
		assertTrue(response.getCustomParams().isEmpty());

		HTTPResponse httpResponse = response.toHTTPResponse();

		response = OIDCAccessTokenResponse.parse(httpResponse);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertNull(response.getRefreshToken());
		assertNull(response.getIDToken());
		assertNull(response.getIDTokenString());
		assertTrue(response.getCustomParams().isEmpty());
	}


	public void testWithIDTokenJWT()
		throws Exception {

		AccessToken accessToken = new BearerAccessToken("abc123");
		RefreshToken refreshToken = new RefreshToken("def456");

		OIDCAccessTokenResponse response = new OIDCAccessTokenResponse(accessToken, refreshToken, ID_TOKEN);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertEquals("def456", response.getRefreshToken().getValue());
		assertEquals(ID_TOKEN_STRING, response.getIDTokenString());
		assertEquals(ID_TOKEN_STRING, response.getIDToken().serialize());
		assertTrue(response.getCustomParams().isEmpty());

		HTTPResponse httpResponse = response.toHTTPResponse();

		response = OIDCAccessTokenResponse.parse(httpResponse);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertEquals("def456", response.getRefreshToken().getValue());
		assertEquals(ID_TOKEN_STRING, response.getIDTokenString());
		assertEquals(ID_TOKEN_STRING, response.getIDToken().serialize());
		assertTrue(response.getCustomParams().isEmpty());
	}


	public void testWithIDTokenJWTAndCustomParams()
		throws Exception {

		AccessToken accessToken = new BearerAccessToken("abc123");
		RefreshToken refreshToken = new RefreshToken("def456");
		Map<String,Object> customParams = new HashMap<>();
		customParams.put("sub_sid", "abc");
		customParams.put("priority", 10);

		OIDCAccessTokenResponse response = new OIDCAccessTokenResponse(accessToken, refreshToken, ID_TOKEN, customParams);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertEquals("def456", response.getRefreshToken().getValue());
		assertEquals(ID_TOKEN_STRING, response.getIDTokenString());
		assertEquals(ID_TOKEN_STRING, response.getIDToken().serialize());
		assertEquals("abc", (String)response.getCustomParams().get("sub_sid"));
		assertEquals(10, ((Number)response.getCustomParams().get("priority")).intValue());
		assertEquals(2, response.getCustomParams().size());

		HTTPResponse httpResponse = response.toHTTPResponse();

		response = OIDCAccessTokenResponse.parse(httpResponse);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertEquals("def456", response.getRefreshToken().getValue());
		assertEquals(ID_TOKEN_STRING, response.getIDTokenString());
		assertEquals(ID_TOKEN_STRING, response.getIDToken().serialize());
		assertEquals("abc", (String)response.getCustomParams().get("sub_sid"));
		assertEquals(10, ((Number)response.getCustomParams().get("priority")).intValue());
		assertEquals(2, response.getCustomParams().size());
	}


	public void testWithIDTokenString()
		throws Exception {

		AccessToken accessToken = new BearerAccessToken("abc123");
		RefreshToken refreshToken = new RefreshToken("def456");

		OIDCAccessTokenResponse response = new OIDCAccessTokenResponse(accessToken, refreshToken, ID_TOKEN_STRING);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertEquals("def456", response.getRefreshToken().getValue());
		assertEquals(ID_TOKEN_STRING, response.getIDTokenString());
		assertEquals(ID_TOKEN_STRING, response.getIDToken().serialize());
		assertTrue(response.getCustomParams().isEmpty());

		HTTPResponse httpResponse = response.toHTTPResponse();

		response = OIDCAccessTokenResponse.parse(httpResponse);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertEquals("def456", response.getRefreshToken().getValue());
		assertEquals(ID_TOKEN_STRING, response.getIDTokenString());
		assertEquals(ID_TOKEN_STRING, response.getIDToken().serialize());
		assertTrue(response.getCustomParams().isEmpty());
	}


	public void testWithIDTokenStringAndCustomParams()
		throws Exception {

		AccessToken accessToken = new BearerAccessToken("abc123");
		RefreshToken refreshToken = new RefreshToken("def456");
		Map<String,Object> customParams = new HashMap<>();
		customParams.put("sub_sid", "abc");
		customParams.put("priority", 10);

		OIDCAccessTokenResponse response = new OIDCAccessTokenResponse(accessToken, refreshToken, ID_TOKEN_STRING, customParams);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertEquals("def456", response.getRefreshToken().getValue());
		assertEquals(ID_TOKEN_STRING, response.getIDTokenString());
		assertEquals(ID_TOKEN_STRING, response.getIDToken().serialize());
		assertEquals("abc", (String)response.getCustomParams().get("sub_sid"));
		assertEquals(10, ((Number)response.getCustomParams().get("priority")).intValue());
		assertEquals(2, response.getCustomParams().size());

		HTTPResponse httpResponse = response.toHTTPResponse();

		response = OIDCAccessTokenResponse.parse(httpResponse);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertEquals("def456", response.getRefreshToken().getValue());
		assertEquals(ID_TOKEN_STRING, response.getIDTokenString());
		assertEquals(ID_TOKEN_STRING, response.getIDToken().serialize());
		assertEquals("abc", (String)response.getCustomParams().get("sub_sid"));
		assertEquals(10, ((Number)response.getCustomParams().get("priority")).intValue());
		assertEquals(2, response.getCustomParams().size());
	}


	public void testWithInvalidIDTokenString()
		throws Exception {

		AccessToken accessToken = new BearerAccessToken("abc123");
		RefreshToken refreshToken = new RefreshToken("def456");
		String invalidIDTokenString = "ey...";

		OIDCAccessTokenResponse response = new OIDCAccessTokenResponse(accessToken, refreshToken, invalidIDTokenString);

		assertTrue(response.indicatesSuccess());
		assertEquals("abc123", response.getAccessToken().getValue());
		assertEquals("def456", response.getRefreshToken().getValue());
		assertNull(response.getIDToken());
		assertEquals(invalidIDTokenString, response.getIDTokenString());

		JSONObject jsonObject = response.toJSONObject();

		try {
			OIDCAccessTokenResponse.parse(jsonObject);
			fail("Failed to raise exception");

		} catch (ParseException e) {
			// ok
		}
	}
}
