package com.onix.basicauth;

//import org.apache.commons.codec.binary.Base64;
import java.util.Base64;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class ConnectToUrlWithBasicAuth {
    private static ConnectToUrlWithBasicAuth connectToUrlWithBasicAuth = null;
    private static String urlWeb;
    private static String userName;
    private static String passWord;
    private static String encryptedValidationInfo;
    private static HttpURLConnection urlConnection;

    private ConnectToUrlWithBasicAuth() {

    }

    public static ConnectToUrlWithBasicAuth getInstance(String url, String username, String password) {
        urlWeb = url;
        userName = username;
        passWord = password;
        String plainValidationInfo = username+":"+password;
        byte[] encryptedValidationBytes = Base64.getEncoder().encode(plainValidationInfo.getBytes());
        encryptedValidationInfo = new String(encryptedValidationBytes);
        initHttpURLConnection();
        if (connectToUrlWithBasicAuth == null) {
            connectToUrlWithBasicAuth = new ConnectToUrlWithBasicAuth();
        }
        return connectToUrlWithBasicAuth;
    }

    private static void initHttpURLConnection() {
        try {
            URL url = new URL(urlWeb);
            //URLConnection urlConnection = url.openConnection();
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + encryptedValidationInfo);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("GET");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getHeader(){
        String result = null;
        StringBuilder sb = new StringBuilder();
        urlConnection.getHeaderFields().forEach((key, value) -> {
            sb.append((key == null ? "" : key + " : ") + Arrays.toString(value.toArray()) + "\n");
        });
        result = sb.toString();
        return result;
    }

    public String getBody(){
        String result = null;
        try {

            try (InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream())){
                int readChars;
                char[] chars = new char[2^10];
                StringBuilder sb = new StringBuilder();
                while ((readChars = inputStreamReader.read(chars)) > 0) {
                    sb.append(chars, 0, readChars);
                }
                result  = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



}
