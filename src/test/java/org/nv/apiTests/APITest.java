package org.nv.apiTests;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.nv.helpers.Helper;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;

import static io.restassured.RestAssured.given;

public class APITest {

    private final Helper helper = new Helper();
    private final String baseUrl = helper.getBaseUrl();
    private final String loginEndpoint = helper.getLoginEndpoint();
    private final String searchEndpoint = helper.getSearchEndpoint();

    private String getBearerToken() {
        JSONObject requestBody = helper.getAuthJsonObject(helper.getUser(), helper.getPass());
        String token = given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post(loginEndpoint)
                .then()
                .statusCode(200)
                .extract()
                .asString();
        return "Bearer " + token;
    }

    @DataProvider(name = "searchTerms")
    public Object[][] searchTerms() {
        return new Object[][]{
                {"treasure"},
                {"venice"}
        };
    }

    @Test(dataProvider = "searchTerms")
    public void SearchBookWhileRegistered(String term) {
        String token = getBearerToken();
        searchAndVerifyBook(term, token);
    }

    @Test(dataProvider = "searchTerms")
    public void SearchBookWhileGuest(String term) {
        searchAndVerifyBook(term);
    }

    private RequestSpecification prepareSpecs(String term) {
        return given()
                .baseUri(baseUrl)
                .queryParam("searchTerm", term);
    }

    private void searchAndVerifyBook(String term) {
        searchAndVerifyBook(term, null);
    }

    private void searchAndVerifyBook(String term, String token) {
        RequestSpecification request = given(prepareSpecs(term));

        if (token != null) {
            request.header("Authorization", token); // Add token only for registered user
        }

        List<String> titles = request
                .when()
                .get(searchEndpoint)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getList("data.title");

        Assert.assertTrue(titles.stream().anyMatch(element -> element.toLowerCase().contains(term)));
    }
}


