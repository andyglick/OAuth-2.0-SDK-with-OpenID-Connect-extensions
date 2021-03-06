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

package com.nimbusds.oauth2.sdk.util;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import junit.framework.TestCase;
import org.junit.Assert;

import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.util.DateUtils;
import com.nimbusds.oauth2.sdk.http.X509CertificateGenerator;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.oauth2.sdk.id.Subject;


public class X509CertificateUtilsTest extends TestCase {
	
	
	public static final RSAPublicKey RSA_PUBLIC_KEY;
	
	
	public static final RSAPrivateKey RSA_PRIVATE_KEY;
	
	
	static {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			
			RSA_PUBLIC_KEY = (RSAPublicKey)keyPair.getPublic();
			RSA_PRIVATE_KEY = (RSAPrivateKey)keyPair.getPrivate();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void testHasMatchingIssuerAndSubject_true()
		throws Exception {
		
		X509Certificate cert = X509CertificateGenerator.generateCertificate(
			new Issuer("123"),
			new Subject("123"),
			RSA_PUBLIC_KEY,
			RSA_PRIVATE_KEY);
		
		assertTrue(X509CertificateUtils.hasMatchingIssuerAndSubject(cert));
	}
	
	
	public void testHasMatchingIssuerAndSubject_false()
		throws Exception {
		
		X509Certificate cert = X509CertificateGenerator.generateCertificate(
			new Issuer("123"),
			new Subject("456"),
			RSA_PUBLIC_KEY,
			RSA_PRIVATE_KEY);
		
		assertFalse(X509CertificateUtils.hasMatchingIssuerAndSubject(cert));
	}
	
	
	public void testIsSelfIssued_positive()
		throws Exception {
		
		X509Certificate cert = X509CertificateGenerator.generateSelfSignedCertificate(
			new Issuer("123"),
			RSA_PUBLIC_KEY,
			RSA_PRIVATE_KEY
		);
		
		assertTrue(X509CertificateUtils.isSelfIssued(cert));
		assertTrue(X509CertificateUtils.isSelfSigned(cert));
	}
	
	
	public void testIsSelfIssued_negative()
		throws Exception {
		
		X509Certificate cert = X509CertificateGenerator.generateCertificate(
			new Issuer("123"),
			new Subject("456"),
			RSA_PUBLIC_KEY,
			RSA_PRIVATE_KEY
		);
		
		assertFalse(X509CertificateUtils.isSelfIssued(cert));
		assertTrue(X509CertificateUtils.isSelfSigned(cert));
	}
	
	
	public void testPublicKeyMatches()
		throws Exception {
		
		X509Certificate cert = X509CertificateGenerator.generateCertificate(
			new Issuer("123"),
			new Subject("456"),
			RSA_PUBLIC_KEY,
			RSA_PRIVATE_KEY
		);
		
		assertTrue(X509CertificateUtils.publicKeyMatches(cert, RSA_PUBLIC_KEY));
	}
	
	
	public void testPublicKeyMatches_false()
		throws Exception {
		
		X509Certificate cert = X509CertificateGenerator.generateCertificate(
			new Issuer("123"),
			new Subject("456"),
			RSA_PUBLIC_KEY,
			RSA_PRIVATE_KEY
		);
		
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(2048);
		KeyPair keyPair = gen.generateKeyPair();
		PublicKey otherPublicKey = keyPair.getPublic();
		
		assertFalse(X509CertificateUtils.publicKeyMatches(cert, otherPublicKey));
	}
	
	
	public void testPublicKeyMatches_viaJWK()
		throws Exception {
		
		X509Certificate cert = X509CertificateGenerator.generateCertificate(
			new Issuer("123"),
			new Subject("456"),
			RSA_PUBLIC_KEY,
			RSA_PRIVATE_KEY
		);
		
		RSAKey rsaJWK = com.nimbusds.jose.jwk.RSAKey.parse(cert);
		
		assertTrue(X509CertificateUtils.publicKeyMatches(cert, rsaJWK.toPublicKey()));
	}
	
	
	public void testGenerate_signRSA()
		throws Exception {
		
		Date now = new Date();
		Date nbf = new Date(now.getTime() - 1000);
		Date exp = new Date(now.getTime() + 3600_1000);
		
		Issuer issuer = new Issuer("https://c2id.com");
		Subject subject = new Subject("123");
		
		X509Certificate cert = X509CertificateUtils.generate(issuer, subject, nbf, exp, RSA_PUBLIC_KEY, RSA_PRIVATE_KEY);
		
		assertEquals("CN=" + issuer, cert.getIssuerDN().getName());
		assertEquals("CN=" + subject, cert.getSubjectDN().getName());
		
		assertEquals(DateUtils.toSecondsSinceEpoch(nbf), DateUtils.toSecondsSinceEpoch(cert.getNotBefore()));
		assertEquals(DateUtils.toSecondsSinceEpoch(exp), DateUtils.toSecondsSinceEpoch(cert.getNotAfter()));
		
		Assert.assertArrayEquals(RSA_PUBLIC_KEY.getEncoded(), cert.getPublicKey().getEncoded());
		cert.verify(RSA_PUBLIC_KEY);
	}
	
	
	public void testGenerate_signECDSA()
		throws Exception {
		
		Date now = new Date();
		Date nbf = new Date(now.getTime() - 1000);
		Date exp = new Date(now.getTime() + 3600_1000);
		
		Issuer issuer = new Issuer("https://c2id.com");
		Subject subject = new Subject("123");
		
		ECKey ecJWK = new ECKeyGenerator(Curve.P_256).generate();
		
		X509Certificate cert = X509CertificateUtils.generate(issuer, subject, nbf, exp, ecJWK.toPublicKey(), ecJWK.toECPrivateKey());
		
		assertEquals("CN=" + issuer, cert.getIssuerDN().getName());
		assertEquals("CN=" + subject, cert.getSubjectDN().getName());
		
		assertEquals(DateUtils.toSecondsSinceEpoch(nbf), DateUtils.toSecondsSinceEpoch(cert.getNotBefore()));
		assertEquals(DateUtils.toSecondsSinceEpoch(exp), DateUtils.toSecondsSinceEpoch(cert.getNotAfter()));
		
		Assert.assertArrayEquals(ecJWK.toECPublicKey().getEncoded(), cert.getPublicKey().getEncoded());
		cert.verify(ecJWK.toPublicKey());
	}
	
	
	public void testGenerateSelfSigned()
		throws Exception {
		
		Date now = new Date();
		Date nbf = new Date(now.getTime() - 1000);
		Date exp = new Date(now.getTime() + 3600_1000);
		
		Issuer issuer = new Issuer("https://c2id.com");
		
		X509Certificate cert = X509CertificateUtils.generateSelfSigned(issuer, nbf, exp, RSA_PUBLIC_KEY, RSA_PRIVATE_KEY);
		
		assertEquals("CN=" + issuer, cert.getIssuerDN().getName());
		assertEquals("CN=" + issuer, cert.getSubjectDN().getName());
		
		assertEquals(DateUtils.toSecondsSinceEpoch(nbf), DateUtils.toSecondsSinceEpoch(cert.getNotBefore()));
		assertEquals(DateUtils.toSecondsSinceEpoch(exp), DateUtils.toSecondsSinceEpoch(cert.getNotAfter()));
		
		Assert.assertArrayEquals(RSA_PUBLIC_KEY.getEncoded(), cert.getPublicKey().getEncoded());
		cert.verify(RSA_PUBLIC_KEY);
	}
}
