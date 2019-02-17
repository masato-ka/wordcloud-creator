package ka.masato.wordcloud.wordcloudcreator.grpc;

import io.grpc.testing.GrpcServerRule;
import ka.masato.wordcloud.wordcloudcreator.domain.service.WordCloudImageService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreateImageRpcTest {

    @Mock
    WordCloudImageService wordCloudImageService;

    @Rule
    public GrpcServerRule grpcServerRule = new GrpcServerRule().directExecutor();


    //Mock Test;
    @Test
    public void createMockTest() {
        String testUrl = "http://masato-ka.com/example";
        int width = 640;
        int height = 420;
        String expectRest = "gcs/katariclloud/kjdfklajfdk.png";
        when(wordCloudImageService.createImage(testUrl, width, height)).thenReturn(expectRest);

        grpcServerRule.getServiceRegistry().addService(new CreateImageRpc(wordCloudImageService));
        WordCloudImageCreatorGrpc.WordCloudImageCreatorBlockingStub blockingStub =
                WordCloudImageCreatorGrpc.newBlockingStub(grpcServerRule.getChannel());

        ImageResponse result = blockingStub.create(
                CreateImageRequest.newBuilder()
                        .setUrl(testUrl).setWidth(width).setHeight(height)
                        .build()
        );

        assertThat(result.getFilePath(), is(expectRest));

    }


}