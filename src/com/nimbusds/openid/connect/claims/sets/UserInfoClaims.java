package com.nimbusds.openid.connect.claims.sets;


import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.minidev.json.JSONObject;

import com.nimbusds.langtag.LangTag;

import com.nimbusds.openid.connect.ParseException;

import com.nimbusds.openid.connect.claims.ClaimName;
import com.nimbusds.openid.connect.claims.ClaimValueParser;
import com.nimbusds.openid.connect.claims.ClaimWithLangTag;
import com.nimbusds.openid.connect.claims.GenericClaim;
import com.nimbusds.openid.connect.claims.UserID;
import com.nimbusds.openid.connect.claims.UserInfo;

import com.nimbusds.openid.connect.util.JSONObjectUtils;


/**
 * UserInfo claims, serialisable to a JSON object.
 *
 * <p>Example UserInfo claims set:
 *
 * <pre>
 * {
 *   "user_id"     : "248289761001",
 *   "name"        : "Jane Doe",
 *   "given_name"  : "Jane",
 *   "family_name" : "Doe",
 *   "email"       : "janedoe@example.com",
 *   "picture"     : "http://example.com/janedoe/me.jpg"
 * }
 * </pre>
 *
 * <p>Related specifications:
 *
 * <ul>
 *     <li>OpenID Connect Messages 1.0, section 2.4.2.
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @version $version$ (2012-05-23)
 */
public class UserInfoClaims extends JSONObjectClaims {


	/**
	 * The names of the reserved top-level UserInfo claims.
	 */
	private static final Set<String> reservedClaimNames = new LinkedHashSet<String>();
	
	
	static {
		reservedClaimNames.add("user_id");
		reservedClaimNames.add("name");
		reservedClaimNames.add("given_name");
		reservedClaimNames.add("family_name");
		reservedClaimNames.add("middle_name");
		reservedClaimNames.add("nickname");
		reservedClaimNames.add("profile");
		reservedClaimNames.add("picture");
		reservedClaimNames.add("website");
		reservedClaimNames.add("email");
		reservedClaimNames.add("verified");
		reservedClaimNames.add("gender");
		reservedClaimNames.add("birthday");
		reservedClaimNames.add("zoneinfo");
		reservedClaimNames.add("locale");
		reservedClaimNames.add("phone_number");
		reservedClaimNames.add("address");
		reservedClaimNames.add("updated_time");
	}
	
	
	/**
	 * Gets the names of the reserved top-level UserInfo claims.
	 *
	 * @return The names of the reserved top-level UserInfo claims 
	 *         (read-only set).
	 */
	public static Set<String> getReservedClaimNames() {
	
		return Collections.unmodifiableSet(reservedClaimNames);
	}
	
	
	/**
	 * The user ID (required).
	 */
	private UserID userID;
	
	
	/**
	 * The full name, with language tags (optional).
	 */
	private Map<LangTag, UserInfo.Name> nameEntries = null;
	
	
	/**
	 * The given name or first name, with language tags (optional).
	 */
	private Map<LangTag, UserInfo.GivenName> givenNameEntries = null;
	
	
	/**
	 * The surname or last name, with language tags (optional).
	 */
	private Map<LangTag, UserInfo.FamilyName> familyNameEntries = null;
	
	
	/**
	 * The middle name, with language tags (optional).
	 */
	private Map<LangTag, UserInfo.MiddleName> middleNameEntries = null;
	
	
	/**
	 * The casual name, with language tags (optional).
	 */
	private Map<LangTag, UserInfo.Nickname> nicknameEntries = null;
	
	
	/**
	 * The profile page URL (optional).
	 */
	private UserInfo.Profile profile = null;
	
	
	/**
	 * The picture URL (optional).
	 */
	private UserInfo.Picture picture = null;
	
	
	/**
	 * The web page or blog URL (optional).
	 */
	private UserInfo.Website website = null;
	
	
	/**
	 * The preferred email address (optional).
	 */
	private UserInfo.Email email = null;
	
	
	/**
	 * The email verification status (optional).
	 */
	private UserInfo.Verified verified = null;
	
	
	/**
	 * The gender (optional).
	 */
	private UserInfo.Gender gender = null;
	
	
	/**
	 * The birthday (optional).
	 */
	private UserInfo.Birthday birthday = null;
	
	
	/**
	 * The zoneinfo (optional).
	 */
	private UserInfo.Zoneinfo zoneinfo = null;
	
	
	/**
	 * The locale (optional).
	 */
	private UserInfo.Locale locale = null;
	
	
	/**
	 * The preferred telephone number (optional).
	 */
	private UserInfo.PhoneNumber phoneNumber = null;
	
	
	/**
	 * The preferred address, with language tags (optional).
	 */
	private Map<LangTag, AddressClaims> addressEntries = null;
	
	
	/**
	 * Time the end-user information was last updated (optional).
	 */
	private UserInfo.UpdatedTime updatedTime = null;
	
	
	/**
	 * Creates a new minimal UserInfo claims set. Use the setter methods for 
	 * the optional claims.
	 *
	 * @param userID The user identifier. Must not be {@code null}.
	 */
	public UserInfoClaims(final UserID userID) {
	
		setUserID(userID);
	}
	
	
	/**
	 * Gets the mandatory user identifier. Corresponds to the 
	 * {@code user_id} claim.
	 *
	 * @return The user identifier.
	 */
	public UserID getUserID() {
	
		return userID;
	}
	
	
	/**
	 * Sets the mandatory user identifier. Corresponds to the
	 * {@code user_id} claim.
	 *
	 * @param userID The user identifier. Must not be {@code null}.
	 */
	public void setUserID(final UserID userID) {
	
		if (userID == null)
			throw new NullPointerException("The user ID must not be null");
		
		this.userID = userID;
	}
	
	
	/**
	 * Adds the specified full name, with optional language tag. Corresponds
	 * to the {@code name} claim.
	 *
	 * @param name The full name, with optional language tag. {@code null}
	 *             if not specified.
	 */
	public void addName(final UserInfo.Name name) {
	
		if (name == null)
			return;
			
		if (nameEntries == null)
			nameEntries = new HashMap<LangTag,UserInfo.Name>();
		
		nameEntries.put(name.getLangTag(), name);
	}
	
	
	/**
	 * Gets the full name entry with no language tag. Corresponds to the
	 * {@code name} claim.
	 *
	 * @return The full name with no language tag, {@code null} if not
	 *         specified.
	 */
	public UserInfo.Name getName() {
	
		return getName(null);
	}
	
	
	/**
	 * Gets the full name entry with the specified language tag. Corresponds
	 * to the {@code name} claim.
	 *
	 * @param langTag The language tag of the entry, {@code null} to get the
	 *                untagged entry.
	 *
	 * @return The full name, {@code null} if not specified.
	 */
	public UserInfo.Name getName(final LangTag langTag) {
	
		if (nameEntries == null)
			return null;
		
		return nameEntries.get(langTag);
	}
	
	
	/**
	 * Gets the full name entries. Correspond to the {@code name} claim.
	 *
	 * @return The full name entries, {@code null} or empty map if none.
	 */
	public Map<LangTag,UserInfo.Name> getNameEntries() {
	
		return nameEntries;
	}
	
	
	/**
	 * Adds the specified given or first name, with optional language tag. 
	 * Corresponds to the {@code given_name} claim.
	 *
	 * @param givenName The given or first name, with optional language tag. 
	 *                  {@code null} if not specified.
	 */
	public void addGivenName(final UserInfo.GivenName givenName) {
	
		if (givenName == null)
			return;
			
		if (givenNameEntries == null)
			givenNameEntries = new HashMap<LangTag,UserInfo.GivenName>();
		
		givenNameEntries.put(givenName.getLangTag(), givenName);
	}
	
	
	/**
	 * Gets the given or first name entry with no language tag. Corresponds 
	 * to the {@code given_name} claim.
	 *
	 * @return The given or first name with no language tag, {@code null} if
	 *         not specified.
	 */
	public UserInfo.GivenName getGivenName() {
	
		return getGivenName(null);
	}
	
	
	/**
	 * Gets the given or first name entry with the specified language tag. 
	 * Corresponds to the {@code given_name} claim.
	 *
	 * @param langTag The language tag of the entry, {@code null} to get the
	 *                untagged entry.
	 *
	 * @return The given or first name, {@code null} if not specified.
	 */
	public UserInfo.GivenName getGivenName(final LangTag langTag) {
	
		if (givenNameEntries == null)
			return null;
		
		return givenNameEntries.get(langTag);
	}
	
	
	/**
	 * Gets the given or first name entries. Correspond to the 
	 * {@code given_name} claim.
	 *
	 * @return The given or first name entries, {@code null} or empty map if 
	 *         none.
	 */
	public Map<LangTag,UserInfo.GivenName> getGivenNameEntries() {
	
		return givenNameEntries;
	}
	
	
	/**
	 * Adds the specified surname or last name, with optional language tag. 
	 * Corresponds to the {@code fimily_name} claim.
	 *
	 * @param familyName The surname or last name, with optional language 
	 *                   tag. {@code null} if not specified.
	 */
	public void addFamilyName(final UserInfo.FamilyName familyName) {
	
		if (familyName == null)
			return;
			
		if (familyNameEntries == null)
			familyNameEntries = new HashMap<LangTag,UserInfo.FamilyName>();
		
		familyNameEntries.put(familyName.getLangTag(), familyName);
	}
	
	
	/**
	 * Gets the surname or last name entry with no language tag. Corresponds 
	 * to the {@code family_name} claim.
	 *
	 * @return The surname or last name with no language tag, {@code null} 
	 *         if not specified.
	 */
	public UserInfo.FamilyName getFamilyName() {
	
		return getFamilyName(null);
	}
	
	
	/**
	 * Gets the surname or last name entry with the specified language tag. 
	 * Corresponds to the {@code family_name} claim.
	 *
	 * @param langTag The language tag of the entry, {@code null} to get the
	 *                untagged entry.
	 *
	 * @return The surname or last name, {@code null} if not specified.
	 */
	public UserInfo.FamilyName getFamilyName(final LangTag langTag) {
	
		if (familyNameEntries == null)
			return null;
		
		return familyNameEntries.get(langTag);
	}
	
	
	/**
	 * Gets the surname or last name entries. Correspond to the 
	 * {@code family_name} claim.
	 *
	 * @return The surname or last name entries, {@code null} or empty map 
	 *         if none.
	 */
	public Map<LangTag,UserInfo.FamilyName> getFamilyNameEntries() {
	
		return familyNameEntries;
	}
	
	
	/**
	 * Adds the specified middle name, with optional language tag. 
	 * Corresponds to the {@code middle_name} claim.
	 *
	 * @param middleName The middle name, with optional language tag. 
	 *                   {@code null} if not specified.
	 */
	public void addMiddleName(final UserInfo.MiddleName middleName) {
	
		if (middleName == null)
			return;
			
		if (middleNameEntries == null)
			middleNameEntries = new HashMap<LangTag,UserInfo.MiddleName>();
		
		middleNameEntries.put(middleName.getLangTag(), middleName);
	}
	
	
	/**
	 * Gets the middle name entry with no language tag. Corresponds to the 
	 * {@code middle_name} claim.
	 *
	 * @return The middle name with no language tag, {@code null} if not 
	 *         specified.
	 */
	public UserInfo.MiddleName getMiddleName() {
	
		return getMiddleName(null);
	}
	
	
	/**
	 * Gets the middle name entry with the specified language tag. 
	 * Corresponds to the {@code middle_name} claim.
	 *
	 * @param langTag The language tag of the entry, {@code null} to get the
	 *                untagged entry.
	 *
	 * @return The middle name, {@code null} if not specified.
	 */
	public UserInfo.MiddleName getMiddleName(final LangTag langTag) {
	
		if (middleNameEntries == null)
			return null;
		
		return middleNameEntries.get(langTag);
	}
	
	
	/**
	 * Gets the middle name entries. Correspond to the {@code middle_name} 
	 * claim.
	 *
	 * @return The middle name entries, {@code null} or empty map if none.
	 */
	public Map<LangTag,UserInfo.MiddleName> getMiddleNameEntries() {
	
		return middleNameEntries;
	}
	
	
	/**
	 * Adds the specified casual name, with optional language tag. 
	 * Corresponds to the {@code nickname} claim.
	 *
	 * @param nickname The casual name, with optional language tag. 
	 *                 {@code null} if not specified.
	 */
	public void addNickname(final UserInfo.Nickname nickname) {
	
		if (nickname == null)
			return;
			
		if (nicknameEntries == null)
			nicknameEntries = new HashMap<LangTag,UserInfo.Nickname>();
		
		nicknameEntries.put(nickname.getLangTag(), nickname);
	}
	
	
	/**
	 * Gets the casual name entry with no language tag. Corresponds to the
	 * {@code nickname} claim.
	 *
	 * @return The nick name with no language tag, {@code null} if not
	 *         specified.
	 */
	public UserInfo.Nickname getNickname() {
	
		return getNickname(null);
	}
	
	
	/**
	 * Gets the casual name entry with the specified language tag. 
	 * Corresponds to the {@code nickname} claim.
	 *
	 * @param langTag The language tag of the entry, {@code null} to get the
	 *                untagged entry.
	 *
	 * @return The casual name, {@code null} if not specified.
	 */
	public UserInfo.Nickname getNickname(final LangTag langTag) {
	
		if (nicknameEntries == null)
			return null;
		
		return nicknameEntries.get(langTag);
	}
	
	
	/**
	 * Gets the casual name entries. Correspond to the {@code nickname} 
	 * claim.
	 *
	 * @return The casual name entries, {@code null} or empty map if none.
	 */
	public Map<LangTag,UserInfo.Nickname> getNicknameEntries() {
	
		return nicknameEntries;
	}
	
	
	/**
	 * Gets the profile page. Corresponds to the {@code profile} claim.
	 *
	 * @return The profile page, {@code null} if not specified.
	 */
	public UserInfo.Profile getProfile() {
	
		return profile;
	}
	
	
	/**
	 * Sets the profile page. Corresponds to the {@code profile} claim.
	 *
	 * @param profile The profile page, {@code null} if not specified.
	 */
	public void setProfile(final UserInfo.Profile profile) {
	
		this.profile = profile;
	}
	
	
	/**
	 * Gets the picture. Corresponds to the {@code picture} claim.
	 *
	 * @return The picture, {@code null} if not specified.
	 */
	public UserInfo.Picture getPicture() {
	
		return picture;
	}
	
	
	/**
	 * Sets the picture. Corresponds to the {@code picture} claim.
	 *
	 * @param picture The picture, {@code null} if not specified.
	 */
	public void setPicture(final UserInfo.Picture picture) {
	
		this.picture = picture;
	}
	
	
	/**
	 * Gets the web page or blog. Corresponds to the {@code website} claim.
	 *
	 * @return The web page or blog, {@code null} if not specified.
	 */
	public UserInfo.Website getWebsite() {
	
		return website;
	}
	
	
	/**
	 * Sets the web page or blog. Corresponds to the {@code website} claim.
	 *
	 * @param website The web page or blog, {@code null} if not specified.
	 */
	public void setWebsite(final UserInfo.Website website) {
	
		this.website = website;
	}
	
	
	/**
	 * Gets the preferred email address. Corresponds to the {@code email} 
	 * claim.
	 *
	 * @return The preferred email address, {@code null} if not specified.
	 */
	public UserInfo.Email getEmail() {
	
		return email;
	}
	
	
	/**
	 * Sets the preferred email address. Corresponds to the {@code email}
	 * claim.
	 *
	 * @param email The preferred email address, {@code null} if not
	 *              specified.
	 */
	public void setEmail(final UserInfo.Email email) {
	
		this.email = email;
	}
	
	
	/**
	 * Gets the email verification status. Corresponds to the 
	 * {@code verified} claim.
	 *
	 * @return The email verification status, {@code null} if not specified.
	 */
	public UserInfo.Verified getVerified() {
	
		return verified;
	}
	
	
	/**
	 * Sets the email verification status. Corresponds to the
	 * {@code verified} claim.
	 *
	 * @param verified The email verification status, {@code null} if not
	 *                 specified.
	 */
	public void setVerified(final UserInfo.Verified verified) {
	
		this.verified = verified;
	}
	
	
	/**
	 * Gets the gender. Corresponds to the {@code gender} claim.
	 *
	 * @return The gender, {@code null} if not specified.
	 */
	public UserInfo.Gender getGender() {
	
		return gender;
	}
	
	
	/**
	 * Sets the gender. Corresponds to the {@code gender} claim.
	 *
	 * @param gender The gender, {@code null} if not specified.
	 */
	public void setGender(final UserInfo.Gender gender) {
	
		this.gender = gender;
	}
	
	
	/**
	 * Gets the birthday. Corresponds to the {@code birthday} claim.
	 *
	 * @return The birthday, {@code null} if not specified.
	 */
	public UserInfo.Birthday getBirthday() {
	
		return birthday;
	}
	
	
	/**
	 * Sets the birthday. Corresponds to the {@code birthday} claim.
	 *
	 * @param birthday The birthday, {@code null} if not specified.
	 */
	public void setBirthday(final UserInfo.Birthday birthday) {
	
		this.birthday = birthday;
	}
	
	
	/**
	 * Gets the zoneinfo. Corresponds to the {@code zoneinfo} claim.
	 *
	 * @return The zoneinfo, {@code null} if not specified.
	 */
	public UserInfo.Zoneinfo getZoneinfo() {
	
		return zoneinfo;
	}
	
	
	/**
	 * Sets the zoneinfo. Corresponds to the {@code zoneinfo} claim.
	 *
	 * @param zoneinfo The zoneinfo, {@code null} if not specified.
	 */
	public void setZoneinfo(final UserInfo.Zoneinfo zoneinfo) {
	
		this.zoneinfo = zoneinfo;
	}
	
	
	/**
	 * Gets the locale. Corresponds to the {@code locale} claim.
	 *
	 * @return The locale, {@code null} if not specified.
	 */
	public UserInfo.Locale getLocale() {
	
		return locale;
	}
	
	
	/**
	 * Sets the locale. Corresponds to the {@code locale} claim.
	 *
	 * @param locale The locale, {@code null} if not specified.
	 */
	public void setLocale(final UserInfo.Locale locale) {
	
		this.locale = locale;
	}
	
	
	/**
	 * Gets the preferred telephone number. Corresponds to the 
	 * {@code phone_number} claim.
	 *
	 * @return The preferred telephone number, {@code null} if not 
	 *         specified.
	 */
	public UserInfo.PhoneNumber getPhoneNumber() {
	
		return phoneNumber;
	}
	
	
	/**
	 * Sets the preferred telephone number. Corresponds to the 
	 * {@code phone_number} claim.
	 *
	 * @param phoneNumber The preferred telephone number, {@code null} if
	 *                    not specified.
	 */
	public void setPhoneNumber(final UserInfo.PhoneNumber phoneNumber) {
	
		this.phoneNumber = phoneNumber;
	}
	
	
	/**
	 * Adds the specified preferred address, with optional language tag.
	 * Corresponds to the {@code address} claim.
	 *
	 * @param address The preferred address, with optional language tag.
	 *                {@code null} if not specified.
	 */
	public void addAddress(final AddressClaims address) {
	
		if (address == null)
			return;
		
		if (addressEntries == null)
			addressEntries = new HashMap<LangTag,AddressClaims>();
		
		addressEntries.put(address.getLangTag(), address);
	}
	
	
	/**
	 * Gets the preferred address with no language tag. Corresponds to the 
	 * {@code address} claim.
	 *
	 * @return The preferred address with no language tag, {@code null} if 
	 *         not specified.
	 */
	public AddressClaims getAddress() {
	
		return getAddress(null);
	}
	
	
	/**
	 * Gets the preferred address with the specified language tag. 
	 * Corresponds to the {@code address} claim.
	 *
	 * @param langTag The language tag of the entry, {@code null} to get the
	 *                untagged entry.
	 *
	 * @return The preferred address, {@code null} if not specified.
	 */
	public AddressClaims getAddress(final LangTag langTag) {
	
		if (addressEntries == null)
			return null;
		
		return addressEntries.get(langTag);
	}
	
	
	/**
	 * Gets the time the end-user information was last updated. Corresponds 
	 * to the {@code updated_time} claim.
	 *
	 * @return The time the end-user information was last updated, 
	 *         {@code null} if not specified.
	 */
	public UserInfo.UpdatedTime getUpdatedTime() {
	
		return updatedTime;
	}
	
	
	/**
	 * Sets the time the end-user information was last updated. Corresponds
	 * to the {@code updated_time} claim.
	 *
	 * @param updatedTime The time the end-user information was last 
	 *                    updated, {@code null} if not specified.
	 */
	public void setUpdatedTime(final UserInfo.UpdatedTime updatedTime) {
	
		this.updatedTime = updatedTime;
	}
	
	
	/**
	 * @inheritDoc
	 */
	public void addCustomClaim(final GenericClaim customClaim) {
	
		if (reservedClaimNames.contains(customClaim.getClaimName()))
			throw new IllegalArgumentException("Custom claim name conflicts with reserved claim name: " + customClaim.getClaimName());
	
		customClaims.put(customClaim.getClaimName(), customClaim);
	}
	
	
	/**
	 * @inheritDoc
	 */
	public JSONObject toJSONObject() {
	
		JSONObject o = super.toJSONObject();
		
		o.put("user_id", userID.getClaimValue());
		
		JSONObjectClaims.putIntoJSONObject(o, nameEntries);
		
		JSONObjectClaims.putIntoJSONObject(o, givenNameEntries);
		
		JSONObjectClaims.putIntoJSONObject(o, familyNameEntries);
		
		JSONObjectClaims.putIntoJSONObject(o, middleNameEntries);
		
		JSONObjectClaims.putIntoJSONObject(o, nicknameEntries);
		
		if (profile != null)
			o.put("profile", profile.getClaimValue().toString());
		
		if (picture != null)
			o.put("picture", picture.getClaimValue().toString());
		
		if (website != null)
			o.put("website", website.getClaimValue().toString());
			
		if (email != null)
			o.put("email", email.getClaimValue().toString());
		
		if (verified != null)
			o.put("verified", verified.getClaimValue());
		
		if (gender != null)
			o.put("gender", gender.getClaimValue());
		
		if (birthday != null)
			o.put("birthday", birthday.getClaimValue());
			
		if (zoneinfo != null)
			o.put("zoneinfo", zoneinfo.getClaimValue());
		
		if (locale != null)
			o.put("locale", locale.getClaimValue());
		
		if (phoneNumber != null)
			o.put("phone_number", phoneNumber.getClaimValue());
		
		// Address object include language tag in name
		JSONObjectClaims.putIntoJSONObject(o, addressEntries);
		
		if (updatedTime != null)
			o.put("updated_time", updatedTime.getClaimValue());
		
		return o;
	}
	
	
	/**
	 * Parses a UserInfo claims set from the specified JSON object.
	 *
	 * @param jsonObject The JSON object to parse. Must not be {@code null}.
	 *
	 * @return The UserInfo claims set.
	 *
	 * @throws ParseException If the JSON object couldn't be parsed to a
	 *                        valid UserInfo authentication claims set.
	 */
	public static UserInfoClaims parse(final JSONObject jsonObject)
		throws ParseException {
		
		UserID userID = new UserID();
		ClaimValueParser.parse(jsonObject, userID);
		
		UserInfoClaims uic = new UserInfoClaims(userID);
		
		Iterator<String> it = jsonObject.keySet().iterator();
		
		while (it.hasNext()) {
		
			ClaimName claimName = ClaimName.parse(it.next());
			
			final String base = claimName.getBase();
			final LangTag langTag = claimName.getLangTag();

			if (base.equals("user_id"))
				continue; // ignore
			
					
			// Parse simple language tagged claims

			if (base.equals("name")) {

				UserInfo.Name name = new UserInfo.Name();
				name.setClaimValue(JSONObjectUtils.getString(jsonObject, claimName.getName()));
				name.setLangTag(langTag);
				uic.addName(name);
			}
			
			else if (base.equals("given_name")) {
			
				UserInfo.GivenName givenName = new UserInfo.GivenName();
				givenName.setClaimValue(JSONObjectUtils.getString(jsonObject, claimName.getName()));
				givenName.setLangTag(langTag);
				uic.addGivenName(givenName);
			}

			else if (base.equals("family_name")) {

				UserInfo.FamilyName familyName = new UserInfo.FamilyName();
				familyName.setClaimValue(JSONObjectUtils.getString(jsonObject, claimName.getName()));
				familyName.setLangTag(langTag);
				uic.addFamilyName(familyName);
			}

			else if (base.equals("middle_name")) {

				UserInfo.MiddleName middleName = new UserInfo.MiddleName();
				middleName.setClaimValue(JSONObjectUtils.getString(jsonObject, claimName.getName()));
				middleName.setLangTag(langTag);
				uic.addMiddleName(middleName);
			}

			else if (base.equals("nickname")) {

				UserInfo.Nickname nickname = new UserInfo.Nickname();
				nickname.setClaimValue(JSONObjectUtils.getString(jsonObject, claimName.getName()));
				nickname.setLangTag(langTag);
				uic.addNickname(nickname);
			}
		
			
			// Simple claims with no language tags
		
			else if (claimName.getName().equals("profile")) {

				UserInfo.Profile profile = new UserInfo.Profile();
				profile.setClaimValue(JSONObjectUtils.getURL(jsonObject, "profile"));
				uic.setProfile(profile);
			}

			else if (claimName.getName().equals("picture")) {

				UserInfo.Picture picture = new UserInfo.Picture();
				picture.setClaimValue(JSONObjectUtils.getURL(jsonObject, "picture"));
				uic.setPicture(picture);
			}

			else if (claimName.getName().equals("website")) {

				UserInfo.Website website = new UserInfo.Website();
				website.setClaimValue(JSONObjectUtils.getURL(jsonObject, "website"));
				uic.setWebsite(website);
			}

			else if (claimName.getName().equals("email")) {

				UserInfo.Email email = new UserInfo.Email();
				email.setClaimValue(JSONObjectUtils.getEmail(jsonObject, "email"));
				uic.setEmail(email);
			}

			else if (claimName.getName().equals("verified")) {

				UserInfo.Verified verified = new UserInfo.Verified();
				verified.setClaimValue(JSONObjectUtils.getBoolean(jsonObject, "verified"));
				uic.setVerified(verified);
			}

			else if (claimName.getName().equals("gender")) {

				UserInfo.Gender gender = new UserInfo.Gender();
				gender.setClaimValue(JSONObjectUtils.getString(jsonObject, "gender"));
				uic.setGender(gender);
			}

			else if (claimName.getName().equals("birthday")) {

				UserInfo.Birthday birthday = new UserInfo.Birthday();
				birthday.setClaimValue(JSONObjectUtils.getString(jsonObject, "birthday"));
				uic.setBirthday(birthday);
			}

			else if (claimName.getName().equals("zoneinfo")) {

				UserInfo.Zoneinfo zoneinfo = new UserInfo.Zoneinfo();
				zoneinfo.setClaimValue(JSONObjectUtils.getString(jsonObject, "zoneinfo"));
				uic.setZoneinfo(zoneinfo);
			}

			else if (claimName.getName().equals("locale")) {

				UserInfo.Locale locale = new UserInfo.Locale();
				locale.setClaimValue(JSONObjectUtils.getString(jsonObject, "locale"));
				uic.setLocale(locale);
			}

			else if (claimName.getName().equals("phone_number")) {

				UserInfo.PhoneNumber phoneNumber = new UserInfo.PhoneNumber();
				phoneNumber.setClaimValue(JSONObjectUtils.getString(jsonObject, "phone_number"));
				uic.setPhoneNumber(phoneNumber);
			}

			else if (claimName.getName().equals("updated_time")) {

				UserInfo.UpdatedTime updatedTime = new UserInfo.UpdatedTime();
				updatedTime.setClaimValue(JSONObjectUtils.getString(jsonObject, "updated_time"));
				uic.setUpdatedTime(updatedTime);
			}
		
		
			// Parse composite address with optional top-level language tag
			
			else if (claimName.getBase().equals("address")) {
			
				JSONObject addressJSON = JSONObjectUtils.getJSONObject(jsonObject, claimName.getName());
				AddressClaims address = AddressClaims.parse(addressJSON);
				address.setLangTag(langTag);
				
				uic.addAddress(address);
			}
			
			// We have a custom claim
			
			else {
				GenericClaim gc = new GenericClaim(claimName.getName());
				gc.setClaimValue(jsonObject.get(claimName.getName()));
				uic.addCustomClaim(gc);
			}
		}
		
		return uic;
	}
}
