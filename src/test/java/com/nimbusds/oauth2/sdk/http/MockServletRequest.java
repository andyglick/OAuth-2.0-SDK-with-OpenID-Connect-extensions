package com.nimbusds.oauth2.sdk.http;


import java.io.*;
import java.security.Principal;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * Mock servlet request.
 */
public class MockServletRequest implements HttpServletRequest {


	private String method;


	private Map<String,String> headers = new HashMap<>();


	private String addr;


	private int localPort;


	private String requestURI;


	private String queryString;


	private String entityBody;


	public void setEntityBody(final String entityBody) {

		this.entityBody = entityBody;
	}


	@Override
	public String getAuthType() {
		return null;
	}


	@Override
	public Cookie[] getCookies() {
		return new Cookie[0];
	}


	@Override
	public long getDateHeader(String s) {
		return 0;
	}


	public void setHeader(final String header, final String value) {

		headers.put(header.toLowerCase(), value);
	}


	@Override
	public String getHeader(String s) {

		return headers.get(s.toLowerCase());
	}


	@Override
	public Enumeration<String> getHeaders(String s) {
		return null;
	}


	@Override
	public Enumeration<String> getHeaderNames() {
		return null;
	}


	@Override
	public int getIntHeader(String s) {
		return 0;
	}


	public void setMethod(final String method) {

		this.method = method;
	}


	@Override
	public String getMethod() {

		return method;
	}


	@Override
	public String getPathInfo() {
		return null;
	}


	@Override
	public String getPathTranslated() {
		return null;
	}


	@Override
	public String getContextPath() {
		return null;
	}


	public void setQueryString(final String queryString) {

		this.queryString = queryString;
	}


	@Override
	public String getQueryString() {

		return queryString;
	}


	@Override
	public String getRemoteUser() {
		return null;
	}


	@Override
	public boolean isUserInRole(String s) {
		return false;
	}


	@Override
	public Principal getUserPrincipal() {
		return null;
	}


	@Override
	public String getRequestedSessionId() {
		return null;
	}


	public void setRequestURI(final String requestURI) {

		this.requestURI = requestURI;
	}


	@Override
	public String getRequestURI() {

		return requestURI;
	}


	@Override
	public StringBuffer getRequestURL() {
		return null;
	}


	@Override
	public String getServletPath() {
		return null;
	}


	@Override
	public HttpSession getSession(boolean b) {
		return null;
	}


	@Override
	public HttpSession getSession() {
		return null;
	}


	@Override
	public boolean isRequestedSessionIdValid() {
		return false;
	}


	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}


	@Override
	public boolean isRequestedSessionIdFromURL() {
		return false;
	}


	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return false;
	}


	@Override
	public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
		return false;
	}


	@Override
	public void login(String s, String s1) throws ServletException {

	}


	@Override
	public void logout() throws ServletException {

	}


	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		return null;
	}


	@Override
	public Part getPart(String s) throws IOException, ServletException {
		return null;
	}


	@Override
	public Object getAttribute(String s) {
		return null;
	}


	@Override
	public Enumeration<String> getAttributeNames() {
		return null;
	}


	@Override
	public String getCharacterEncoding() {
		return null;
	}


	@Override
	public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

	}


	@Override
	public int getContentLength() {

		return 0;
	}


	@Override
	public String getContentType() {

		return headers.get("content-type");
	}


	@Override
	public ServletInputStream getInputStream() throws IOException {
		return null;
	}


	@Override
	public String getParameter(String s) {
		return null;
	}


	@Override
	public Enumeration<String> getParameterNames() {
		return null;
	}


	@Override
	public String[] getParameterValues(String s) {
		return new String[0];
	}


	@Override
	public Map<String, String[]> getParameterMap() {
		return null;
	}


	@Override
	public String getProtocol() {
		return null;
	}


	@Override
	public String getScheme() {
		return null;
	}


	@Override
	public String getServerName() {
		return null;
	}


	@Override
	public int getServerPort() {
		return 0;
	}


	@Override
	public BufferedReader getReader() throws IOException {

		return new BufferedReader(new StringReader(entityBody));
	}


	@Override
	public String getRemoteAddr() {
		return null;
	}


	@Override
	public String getRemoteHost() {
		return null;
	}


	@Override
	public void setAttribute(String s, Object o) {

	}


	@Override
	public void removeAttribute(String s) {

	}


	@Override
	public Locale getLocale() {
		return null;
	}


	@Override
	public Enumeration<Locale> getLocales() {
		return null;
	}


	@Override
	public boolean isSecure() {
		return false;
	}


	@Override
	public RequestDispatcher getRequestDispatcher(String s) {
		return null;
	}


	@Override
	public String getRealPath(String s) {
		return null;
	}


	@Override
	public int getRemotePort() {
		return 0;
	}


	@Override
	public String getLocalName() {
		return null;
	}


	public void setLocalAddr(final String addr) {

		this.addr = addr;
	}


	@Override
	public String getLocalAddr() {

		return addr;
	}


	public void setLocalPort(final int localPort) {

		this.localPort = localPort;
	}


	@Override
	public int getLocalPort() {

		return localPort;
	}


	@Override
	public ServletContext getServletContext() {
		return null;
	}


	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		return null;
	}


	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
		return null;
	}


	@Override
	public boolean isAsyncStarted() {
		return false;
	}


	@Override
	public boolean isAsyncSupported() {
		return false;
	}


	@Override
	public AsyncContext getAsyncContext() {
		return null;
	}


	@Override
	public DispatcherType getDispatcherType() {
		return null;
	}
}
