package io.github.wgao.idealwaffle.utils;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class Response {

    private int responseCode;
    private String response;
    private String cookie;

    public Response(int responseCode) {
        this(responseCode, null, null);
    }

    public Response(int responseCode, String response, String cookie) {
        this.responseCode = responseCode;
        this.response = response;
        this.cookie = cookie;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponse() {
        return response;
    }

    public String[] getCookie() {
        return cookie.split(";");
    }
}
