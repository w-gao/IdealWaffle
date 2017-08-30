package space.wgao.idealwaffle.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * IdealWaffle
 *
 * @author w.gao Copyright (c) 2017
 * @version 1.0
 */
public class HttpsConnection {

    private static final String USER_AGENT = Config.getProperty("UserAgent", "Mozilla/5.0");

    public static Response get(String url) throws IOException {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        switch (responseCode) {
            case 200:
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return new Response(responseCode, response.toString(), con.getHeaderField("Set-Cookie"));
            default:
                return new Response(responseCode);
        }
    }

    public static Response post(String url, String par) throws IOException {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(par);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        switch (responseCode) {
            case 200:
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return new Response(responseCode, response.toString(), con.getHeaderField("Set-Cookie"));
            default:
                return new Response(responseCode);
        }
    }

}
