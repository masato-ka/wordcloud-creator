package ka.masato.wordcloud.wordcloudcreator.controller;

import ka.masato.wordcloud.wordcloudcreator.controller.model.CreateRequest;
import ka.masato.wordcloud.wordcloudcreator.controller.model.ImageResult;
import ka.masato.wordcloud.wordcloudcreator.domain.service.WordCloudImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        , properties = {})
public class WordCloudImageControllerTest {

    @Mock
    private WordCloudImageService wordCloudImageService;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void create() {

        String url = "";
        Integer width = 640;
        Integer height = 480;
        String expectValue = "gcs/kataricloud/test.png";
        when(wordCloudImageService.createImage(url, width, height)).thenReturn(expectValue);

        CreateRequest createRequest = new CreateRequest();
        createRequest.setTargerUrl(url);
        createRequest.setWidth(width);
        createRequest.setHeight(height);

        HttpEntity<CreateRequest> httpEntity = new HttpEntity<CreateRequest>(createRequest);
        ResponseEntity<ImageResult> result =
                restTemplate.exchange("/api/v1/images", HttpMethod.POST, httpEntity, ImageResult.class);


        assertThat(result.getBody().getImagePath(), is(expectValue));

    }

}