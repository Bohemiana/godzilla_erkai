/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package util.http;

import core.ApplicationContext;
import core.shell.ShellEntity;
import core.ui.component.dialog.ShellSuperRequest;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import util.Log;
import util.functions;
import util.http.HttpResponse;

public class Http {
    private static final HostnameVerifier hostnameVerifier = new TrustAnyHostnameVerifier();
    private final Proxy proxy;
    private final ShellEntity shellContext;
    private CookieManager cookieManager;
    private URI uri;
    public String requestMethod = "POST";

    public Http(ShellEntity shellContext) {
        this.shellContext = shellContext;
        this.proxy = ApplicationContext.getProxy(this.shellContext);
    }

    public HttpResponse SendHttpConn(String urlString, String method, Map<String, String> header, byte[] requestData, int connTimeOut, int readTimeOut, Proxy proxy) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection(proxy);
            httpConn.setFixedLengthStreamingMode(requestData.length);
            if (httpConn instanceof HttpsURLConnection) {
                ((HttpsURLConnection)httpConn).setHostnameVerifier(hostnameVerifier);
            }
            httpConn.setDoInput(true);
            httpConn.setDoOutput(!"GET".equalsIgnoreCase(method));
            if (connTimeOut > 0) {
                httpConn.setConnectTimeout(connTimeOut);
            }
            if (readTimeOut > 0) {
                httpConn.setReadTimeout(readTimeOut);
            }
            httpConn.setRequestMethod(method.toUpperCase());
            String ua = ShellSuperRequest.randomUa();
            if (ua != null && ua.trim().length() > 0) {
                httpConn.setRequestProperty("User-Agent", ua.trim());
            }
//            Map<String, String> host = Collections.emptyMap();
//            Http.addHttpHeader((HttpURLConnection) host, host);
            Http.addHttpHeader(httpConn, ApplicationContext.getGloballHttpHeaderX());
            Http.addHttpHeader(httpConn, header);
            Boolean isShowBar = ApplicationContext.isShowHttpProgressBar.get();
            if (isShowBar != null && isShowBar.booleanValue()) {
                if (httpConn.getDoOutput()) {
                    OutputStream outputStream = httpConn.getOutputStream();
                    ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(requestData);
                    byte[] req = new byte[1024];
                    int readOneNum = -1;
                    int readNum = 0;
                    while ((readOneNum = arrayInputStream.read(req)) != -1) {
                        outputStream.write(req, 0, readOneNum);
                        readNum += readOneNum;
                    }
                }
            } else if (httpConn.getDoOutput()) {
                httpConn.getOutputStream().write(requestData);
                httpConn.getOutputStream().flush();
            }
            return new HttpResponse(httpConn, this.shellContext);
        } catch (Exception e) {
            Log.error(e);
            return null;
        }
    }

    public HttpResponse sendHttpResponse(Map<String, String> header, byte[] requestData, int connTimeOut, int readTimeOut) {
        requestData = this.shellContext.getCryptionModule().encode(requestData);
        String left = this.shellContext.getReqLeft();
        String right = this.shellContext.getReqRight();
        if (this.shellContext.isSendLRReqData()) {
            byte[] leftData = left.getBytes();
            byte[] rightData = right.getBytes();
            requestData = (byte[])functions.concatArrays(functions.concatArrays(leftData, 0, (leftData.length > 0 ? leftData.length : 1) - 1, requestData, 0, requestData.length - 1), 0, leftData.length + requestData.length - 1, rightData, 0, (rightData.length > 0 ? rightData.length : 1) - 1);
        }
        return this.SendHttpConn(this.shellContext.getUrl(), this.requestMethod, header, requestData, connTimeOut, readTimeOut, this.proxy);
    }

    public HttpResponse sendHttpResponse(byte[] requestData, int connTimeOut, int readTimeOut) {
        return this.sendHttpResponse(this.shellContext.getHeaders(), requestData, connTimeOut, readTimeOut);
    }

    public HttpResponse sendHttpResponse(byte[] requestData) {
        return this.sendHttpResponse(requestData, this.shellContext.getConnTimeout(), this.shellContext.getReadTimeout());
    }

    public static void addHttpHeader(HttpURLConnection connection, Map<String, String> headerMap) {
        if (headerMap != null) {
            Iterator<String> names = headerMap.keySet().iterator();
            String name = "";
            while (names.hasNext()) {
                name = names.next();
                connection.setRequestProperty(name, headerMap.get(name));
            }
        }
    }

    private static void trustAllHttpsCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[1];
            miTM tm = new miTM();
            trustAllCerts[0] = tm;
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            SSLContext sc2 = SSLContext.getInstance("TLS");
            sc2.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc2.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized URI getUri() {
        if (this.uri == null) {
            try {
                this.uri = URI.create(this.shellContext.getUrl());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.uri;
    }

    public synchronized CookieManager getCookieManager() {
        if (this.cookieManager == null) {
            this.cookieManager = new CookieManager();
            try {
                String cookieStr = this.shellContext.getHeaders().get("Cookie");
                if (cookieStr == null) {
                    cookieStr = this.shellContext.getHeaders().get("cookie");
                }
                if (cookieStr != null) {
                    String[] cookies;
                    for (String cookieStr2 : cookies = cookieStr.split(";")) {
                        String[] cookieAtt = cookieStr2.split("=");
                        if (cookieAtt.length != 2) continue;
                        HttpCookie httpCookie = new HttpCookie(cookieAtt[0], cookieAtt[1]);
                        this.cookieManager.getCookieStore().add(this.getUri(), httpCookie);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.cookieManager;
    }

    static {
        Http.trustAllHttpsCertificates();
    }

    public static class TrustAnyHostnameVerifier
    implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class miTM
    extends X509ExtendedTrustManager
    implements TrustManager,
    X509TrustManager {
        private miTM() {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {
        }
    }
}

