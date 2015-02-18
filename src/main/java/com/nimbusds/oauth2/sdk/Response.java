package com.nimbusds.oauth2.sdk;


import com.nimbusds.oauth2.sdk.http.HTTPResponse;


/**
 * Response message, serialises to an HTTP response.
 */
public interface Response extends Message {


	/**
	 * Checks if the response indicates success.
	 *
	 * @return {@code true} if the response indicates success, else
	 *         {@code false}.
	 */
	public boolean indicatesSuccess();

	
	/**
	 * Returns the matching HTTP response.
	 *
	 * @return The HTTP response.
	 *
	 * @throws SerializeException If the response message couldn't be 
	 *                            serialised to an HTTP response.
	 */
	public HTTPResponse toHTTPResponse() 
		throws SerializeException;
}
