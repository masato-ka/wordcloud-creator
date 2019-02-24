package ka.masato.wordcloud.wordcloudcreator.controller;

import ka.masato.wordcloud.wordcloudcreator.controller.model.ApiErrorMessage;
import ka.masato.wordcloud.wordcloudcreator.controller.model.CreateRequest;
import ka.masato.wordcloud.wordcloudcreator.controller.model.ImageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        , properties = {})
public class WordCloudImageControllerTest {

    //@Mock
    //private WordCloudImageService wordCloudImageService;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void createTestNormal01() {

        String url = "http://masato-ka.com/";
        Integer width = 640;
        Integer height = 480;
        String expectValue = "gcs/kataricloud/test.png";
        String responseBody = "<HTML><BODY>温度センサなどで部屋の温度や外気温を計測し可視化・モニタリングすることは" +
                "IoTのユースケースとして一般的に紹介される例です。" +
                "こう言った場合、部屋の住環境のモニタリングや商品が保管してある倉庫などの環境モニタリング" +
                "として実施されることが大半でしょう。 " +
                "しかし、温度計測が必要であるが、前述の例のような部屋や空間といった面としてのセンシングではなく、" +
                "ピンポイントで温度を計測したい場合が出てきます。例えば機械設備の保守では設備のある特定の部分の" +
                "温度上昇を検知したい場合です。また、ショウケースに入った食品などの品質監視を行う場合、" +
                "ショウケース内の温度を１箇所測っただけでは実際の食品の温度を知ることはできません。 " +
                "こういった場合、測りたい物に直接センサを貼り付けて温度を監視したくなりますが、" +
                "以下の理由から難しいと考えます。</BODY></HTML>";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(url))
                .andRespond(withSuccess(responseBody, MediaType.TEXT_HTML));
        CreateRequest createRequest = getCreateRequest(url, width, height);

        ResponseEntity<ImageResult> result =
                testRestTemplate.exchange("/api/v1/images", HttpMethod.POST, new HttpEntity(createRequest), ImageResult.class);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));

    }


    @Test
    public void createTestNormal02() {

        String url = "http://masato-ka.com/hogehoge?page=10";
        Integer width = 300;
        Integer height = 300;
        String expectValue = "gcs/kataricloud/test.png";
        String responseBody = "1234567890";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(url))
                .andRespond(withSuccess(responseBody, MediaType.TEXT_HTML));

        CreateRequest createRequest = getCreateRequest(url, width, height);

        ResponseEntity<ImageResult> result =
                testRestTemplate.exchange("/api/v1/images", HttpMethod.POST, new HttpEntity(createRequest), ImageResult.class);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void createTestNormal03() {

        String url = "http://masato-ka.com/";
        Integer width = 640;
        Integer height = 480;
        String expectValue = "gcs/kataricloud/test.png";
        String responseBody = "             ";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(url))
                .andRespond(withSuccess(responseBody, MediaType.TEXT_HTML));

        CreateRequest createRequest = getCreateRequest(url, width, height);

        ResponseEntity<ImageResult> result =
                testRestTemplate.exchange("/api/v1/images", HttpMethod.POST, new HttpEntity(createRequest), ImageResult.class);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));

    }

    @Test
    public void createTestAbnormal01() {

        String url = "http://";
        Integer width = 640;
        Integer height = 480;
        String expectValue = "gcs/kataricloud/test.png";
        String responseBody = "             ";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(url))
                .andRespond(withSuccess(responseBody, MediaType.TEXT_HTML));

        CreateRequest createRequest = getCreateRequest(url, width, height);


        ResponseEntity<ApiErrorMessage> result =
                testRestTemplate.exchange("/api/v1/images",
                        HttpMethod.POST, new HttpEntity(createRequest), ApiErrorMessage.class);

        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody().getMessage(), is("Require value is invalid."));

        assertDetailErrorMessage(result.getBody(),
                "targetUrl", "The field must be a valid URL.");


    }

    @Test
    public void createTestAbnormal02() {

        String url = "hogehoge.com";
        Integer width = 640;
        Integer height = 480;
        String expectValue = "gcs/kataricloud/test.png";
        String responseBody = "             ";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(url))
                .andRespond(withSuccess(responseBody, MediaType.TEXT_HTML));

        CreateRequest createRequest = getCreateRequest(url, width, height);
        ResponseEntity<ApiErrorMessage> result =
                testRestTemplate.exchange("/api/v1/images",
                        HttpMethod.POST, new HttpEntity(createRequest), ApiErrorMessage.class);

        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody().getMessage(), is("Require value is invalid."));

        assertDetailErrorMessage(result.getBody(),
                "targetUrl", "The field must be a valid URL.");
    }

    @Test
    public void createTestAbnormal03() {

        String url = "";
        Integer width = 640;
        Integer height = 480;
        String expectValue = "gcs/kataricloud/test.png";
        String responseBody = "             ";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(url))
                .andRespond(withSuccess(responseBody, MediaType.TEXT_HTML));

        CreateRequest createRequest = getCreateRequest(url, width, height);

        ResponseEntity<ApiErrorMessage> result =
                testRestTemplate.exchange("/api/v1/images",
                        HttpMethod.POST, new HttpEntity(createRequest), ApiErrorMessage.class);
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody().getMessage(), is("Require value is invalid."));

        assertDetailErrorMessage(result.getBody(),
                "targetUrl", "The field must be a valid URL.");

    }

    @Test
    public void createTestAbnormal04() {

        String url = "http://masatoka.com";
        Integer width = 640;
        Integer height = 480;
        String expectValue = "gcs/kataricloud/test.png";
        String responseBody = "             ";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(url))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        CreateRequest createRequest = getCreateRequest(url, width, height);

        ResponseEntity<ApiErrorMessage> result =
                testRestTemplate.exchange("/api/v1/images",
                        HttpMethod.POST, new HttpEntity(createRequest), ApiErrorMessage.class);
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_GATEWAY));
        assertThat(result.getBody().getMessage(), is("Can not access target url."));

    }

    @Test
    public void createTestAbnormal05() {

        String url = "http://masato.com/hogehoge";
        Integer width = 1025;
        Integer height = 769;
        String expectValue = "gcs/kataricloud/test.png";
        String responseBody = "テスト文字列";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(url))
                .andRespond(withSuccess(responseBody, MediaType.TEXT_HTML));

        CreateRequest createRequest = getCreateRequest(url, width, height);

        ResponseEntity<ApiErrorMessage> result =
                testRestTemplate.exchange("/api/v1/images", HttpMethod.POST,
                        new HttpEntity(createRequest), ApiErrorMessage.class);
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody().getMessage(),
                is("Require value is invalid."));

        assertDetailErrorMessage(result.getBody(),
                "width", "The field can not be grater than 1024.");
        assertDetailErrorMessage(result.getBody(),
                "height", "The field can not be grater than 768.");

    }

    @Test
    public void createTestAbnormal06() {

        String url = "http://masato.com/hogehoge";
        Integer width = -100;
        Integer height = 99;
        String expectValue = "gcs/kataricloud/test.png";
        String responseBody = "テスト文字列";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(url))
                .andRespond(withSuccess(responseBody, MediaType.TEXT_HTML));

        CreateRequest createRequest = getCreateRequest(url, width, height);

        ResponseEntity<ApiErrorMessage> result =
                testRestTemplate.exchange("/api/v1/images", HttpMethod.POST,
                        new HttpEntity(createRequest), ApiErrorMessage.class);
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody().getMessage(),
                is("Require value is invalid."));

        assertThat(2, is(result.getBody().getDetails().size()));

        assertDetailErrorMessage(result.getBody(), "width", "The field can not be less than 100.");
        assertDetailErrorMessage(result.getBody(), "height", "The field can not be less than 100.");


    }

    @Test
    public void createTestAbnormal07() {

        Map<String, String> body = new HashMap<>();
        body.put("hoge", "fuga");

        ResponseEntity<ApiErrorMessage> result =
                testRestTemplate.exchange("/api/v1/images", HttpMethod.POST,
                        new HttpEntity(body), ApiErrorMessage.class);
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody().getMessage(),
                is("Require value is invalid."));
        assertThat(3, is(result.getBody().getDetails().size()));

        assertDetailErrorMessage(result.getBody(), "targetUrl", "required field.");
        assertDetailErrorMessage(result.getBody(), "width", "required field.");
        assertDetailErrorMessage(result.getBody(), "height", "required field.");

    }

    @Test
    public void createTestAbnormal08() {

        Map<String, String> body = new HashMap<>();
        body.put("hoge", "fuga");

        ResponseEntity<ApiErrorMessage> result =
                testRestTemplate.getForEntity("/api/v1/images", ApiErrorMessage.class);
        assertThat(result.getStatusCode(), is(HttpStatus.METHOD_NOT_ALLOWED));
        assertThat(result.getBody().getMessage(),
                is("Request method 'GET' not supported"));
    }

    @Test
    public void createTestAbnormal09() {

        Map<String, String> body = new HashMap<>();
        body.put("targetUrl", "hoge");
        body.put("width", "hoge");
        body.put("height", "hoge");

        ResponseEntity<ApiErrorMessage> result =
                testRestTemplate.exchange("/api/v1/images", HttpMethod.POST,
                        new HttpEntity(body), ApiErrorMessage.class);

        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(result.getBody().getMessage(), is("JSON message is invalid."));

    }


    private void assertDetailErrorMessage(ApiErrorMessage target, String fieldName, String errorMessage) {

        ApiErrorMessage.Detail result = target.getDetails().stream()
                .filter(detail -> detail.getTarget().equals(fieldName)).findFirst().get();
        assertThat(result.getMessage(), is(errorMessage));

    }


    private CreateRequest getCreateRequest(String url, Integer width, Integer height) {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setTargetUrl(url);
        createRequest.setWidth(width);
        createRequest.setHeight(height);
        return createRequest;
    }

}