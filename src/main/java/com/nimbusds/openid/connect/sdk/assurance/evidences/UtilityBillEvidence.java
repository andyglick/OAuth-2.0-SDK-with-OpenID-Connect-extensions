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

package com.nimbusds.openid.connect.sdk.assurance.evidences;


import net.jcip.annotations.Immutable;
import net.minidev.json.JSONObject;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.util.JSONObjectUtils;
import com.nimbusds.oauth2.sdk.util.date.SimpleDate;
import com.nimbusds.openid.connect.sdk.claims.Address;


/**
 * Utility bill used as identity evidence.
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>OpenID Connect for Identity Assurance 1.0, section 4.1.1.
 * </ul>
 */
@Immutable
public final class UtilityBillEvidence extends IdentityEvidence {
	
	
	/**
	 * The utility provider name.
	 */
	private final String providerName;
	
	
	/**
	 * The utility provider address details.
	 */
	private final Address providerAddress;
	
	
	/**
	 * The utility bill date.
	 */
	private final SimpleDate date;
	
	
	/**
	 * Creates a new utility bill used as identity evidence.
	 *
	 * @param providerName    The utility provider name, {@code null} if
	 *                        not specified.
	 * @param providerAddress The utility provider address details,
	 *                        {@code null} if not specified.
	 * @param date            The utility bill date, {@code null} if not
	 *                        specified.
	 */
	public UtilityBillEvidence(final String providerName, final Address providerAddress, final SimpleDate date) {
		
		super(IdentityEvidenceType.UTILITY_BILL);
		this.providerName = providerName;
		this.providerAddress = providerAddress;
		this.date = date;
	}
	
	
	/**
	 * The utility provider name.
	 *
	 * @return The utility provider name, {@code null} if not specified.
	 */
	public String getUtilityProviderName() {
		return providerName;
	}
	
	
	/**
	 * Returns the utility provider address details.
	 *
	 * @return The utility provider address details, {@code null} if not
	 *         specified.
	 */
	public Address getUtilityProviderAddress() {
		return providerAddress;
	}
	
	
	/**
	 * Returns the utility bill date.
	 *
	 * @return The utility bill date, {@code null} if not specified.
	 */
	public SimpleDate getUtilityBillDate() {
		return date;
	}
	
	
	@Override
	public JSONObject toJSONObject() {
		
		JSONObject o = super.toJSONObject();
		
		JSONObject providerDetails = new JSONObject();
		if (getUtilityProviderName() != null) {
			providerDetails.put("name", getUtilityProviderName());
		}
		if (getUtilityProviderAddress() != null) {
			providerDetails.putAll(getUtilityProviderAddress().toJSONObject());
		}
		if (! providerDetails.isEmpty()) {
			o.put("provider", providerDetails);
		}
		
		if (getUtilityBillDate() != null) {
			o.put("date", getUtilityBillDate().toISO8601String());
		}
		
		return o;
	}
	
	
	/**
	 * Parses a utility bill evidence from the specified JSON object.
	 *
	 * @param jsonObject The JSON object. Must not be {@code null}.
	 *
	 * @return The utility bill evidence.
	 *
	 * @throws ParseException If parsing failed.
	 */
	public static UtilityBillEvidence parse(final JSONObject jsonObject)
		throws ParseException {
		
		ensureType(IdentityEvidenceType.UTILITY_BILL, jsonObject);
		
		JSONObject providerDetails = JSONObjectUtils.getJSONObject(jsonObject, "provider", null);
		
		String providerName = null;
		Address providerAddress = null;
		if (providerDetails != null) {
			providerName = JSONObjectUtils.getString(providerDetails, "name", null);
			
			JSONObject providerDetailsCopy = new JSONObject(providerDetails);
			providerDetailsCopy.remove("name");
			
			if (! providerDetailsCopy.isEmpty()) {
				providerAddress = new Address(providerDetailsCopy);
			}
		}
		
		SimpleDate date = null;
		if (jsonObject.get("date") != null) {
			date = SimpleDate.parseISO8601String(JSONObjectUtils.getString(jsonObject, "date"));
		}
		
		return new UtilityBillEvidence(providerName, providerAddress, date);
	}
}
