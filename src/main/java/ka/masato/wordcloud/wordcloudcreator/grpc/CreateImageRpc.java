package ka.masato.wordcloud.wordcloudcreator.grpc;


import io.grpc.stub.StreamObserver;
import ka.masato.wordcloud.wordcloudcreator.CreateImageRequest;
import ka.masato.wordcloud.wordcloudcreator.ImageResponse;
import ka.masato.wordcloud.wordcloudcreator.WordCloudImageCreatorGrpc;
import ka.masato.wordcloud.wordcloudcreator.domain.service.WordCloudImageService;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class CreateImageRpc extends WordCloudImageCreatorGrpc.WordCloudImageCreatorImplBase {

    private WordCloudImageService wordCloudImageService;

    public CreateImageRpc(WordCloudImageService wordCloudImageService) {
        this.wordCloudImageService = wordCloudImageService;
    }

    @Override
    public void create(CreateImageRequest request, StreamObserver<ImageResponse> responseObserver) {
        String url = wordCloudImageService.createImage(request.getUrl(), request.getWidth(), request.getHeight());
        ImageResponse imageResponse = ImageResponse.newBuilder().setFilePath(url).build();
        responseObserver.onNext(imageResponse);
        responseObserver.onCompleted();
    }
}
