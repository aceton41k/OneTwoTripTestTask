import io.restassured.RestAssured;


abstract class BaseTest {
    private static String _targetBaseUrl = null;

    static {
        populateTargetBaseUrl();
    }

    protected String getTargetBaseUrl() {
        return _targetBaseUrl;
    }

    private static void populateTargetBaseUrl() {
        if (_targetBaseUrl != null) {
            return;
        }
        _targetBaseUrl = System.getProperty("targetBaseUrl");


        if (_targetBaseUrl == null || _targetBaseUrl.isEmpty()) {
            _targetBaseUrl = "https://www.onetwotrip.com";
//            throw new RuntimeException("Необходимо задать targetBaseUrl в качестве системного свойства.");
        }
        RestAssured.baseURI = _targetBaseUrl;
    }

}

