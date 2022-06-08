package com.onix.basicauth;

public class Main {

    public static void main(String[] args) {
        String urlIp = "http://127.0.0.1:8080/manager/html/list";
        String urlWeb = "http://localhost:8080/manager/html/list";
        String login = "admin";
        String password = "admin";
        System.out.println(
                "---------Header---------\n" +
                        ConnectToUrlWithBasicAuth.getInstance(urlIp,login,password)
                                .getHeader()
        );
        System.out.println(
            "---------Body---------\n" +
            ConnectToUrlWithBasicAuth.getInstance(urlWeb,login,password)
            .getBody()
        );

    }
}
