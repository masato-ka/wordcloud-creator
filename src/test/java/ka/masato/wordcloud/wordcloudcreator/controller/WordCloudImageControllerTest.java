package ka.masato.wordcloud.wordcloudcreator.controller;

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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
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
    public void create() {

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

        CreateRequest createRequest = new CreateRequest();
        createRequest.setTargerUrl(url);
        createRequest.setWidth(width);
        createRequest.setHeight(height);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(createRequest, httpHeaders);
        ResponseEntity<ImageResult> result =
                testRestTemplate.exchange("/api/v1/images", HttpMethod.POST, new HttpEntity(createRequest), ImageResult.class);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));

    }

}