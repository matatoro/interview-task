package org.nv.helpers;

import lombok.Getter;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Getter
public class Helper {
    private final Properties properties;
    private final String baseUrl;
    private final String loginEndpoint;
    private final String searchEndpoint;
    private  final  String user;
    private final String pass;

    public Helper() {
        this.properties = new Properties();
        String filePath = "src/test/resources/test.properties";
        try (FileInputStream fileStream = new FileInputStream(filePath)) {
            properties.load(fileStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file: " + filePath);
        }
        this.baseUrl = this.getProperty("baseUrl");
        this.loginEndpoint = this.getProperty("loginEndpoint");
        this.searchEndpoint = this.getProperty("searchEndpoint");
        this.user = this.getProperty("user");
        this.pass = this.getProperty("pass");
    }

    private String getProperty(String key) {
        return properties.getProperty(key);
    }

    public JSONObject getAuthJsonObject(String user, String pass) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", 9007199254740991L);
        requestBody.put("username", user);
        requestBody.put("password", pass);
        return requestBody;
    }
}
