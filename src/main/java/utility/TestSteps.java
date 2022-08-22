package utility;

import io.restassured.response.Response;

public class TestSteps {

    public void buildRequest() {

    }

    public void sendRequest() {

    }

    public boolean checkResponseIsValid(Response response) {
        return(response.getStatusCode() == 200);
    }

    public int prepareActualResponse(Response response) {
        return(response.getStatusCode());
    }

    public int prepareExpectedResponse(int statusCode) {
        return(statusCode);
    }

    public boolean checkActualVsExpectedResponses(Response response, int statusCode) {
        return(prepareActualResponse(response) == prepareExpectedResponse(statusCode));
    }
}
