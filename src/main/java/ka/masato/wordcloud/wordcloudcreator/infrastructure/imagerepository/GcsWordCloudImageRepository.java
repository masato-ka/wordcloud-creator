package ka.masato.wordcloud.wordcloudcreator.infrastructure.imagerepository;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import ka.masato.wordcloud.wordcloudcreator.domain.repository.WordCloudImageRepository;
import ka.masato.wordcloud.wordcloudcreator.exception.KatariCloudFailedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Repository
public class GcsWordCloudImageRepository implements WordCloudImageRepository {

    private final String BUCKET_NAME;
    private Storage storage;
    private String FILE_EXTENTION = "png";

    public GcsWordCloudImageRepository(@Value("${storage.gcs.backet:kataricloud}") String bucket_name) {
        BUCKET_NAME = bucket_name;
        storage = StorageOptions.getDefaultInstance().getService();
    }


    @Override
    public String saveImage(BufferedImage image) {
        BlobInfo blobInfo = BlobInfo
                .newBuilder(BUCKET_NAME, RandomStringUtils.randomAlphanumeric(20) + "." + FILE_EXTENTION)
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                .build();
        InputStream is = getInputStream(image).get();
        blobInfo = storage.create(blobInfo, is);
        return null;
    }

    @Override
    public BufferedImage getImage(Integer imangeId) {
        return null;
    }

    @Override
    public void delete(String url) {

    }

    private Optional<InputStream> getInputStream(BufferedImage image) {
        Optional<InputStream> is = Optional.empty();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
            is = Optional.ofNullable(new ByteArrayInputStream(baos.toByteArray()));
        } catch (IOException e) {
            throw new KatariCloudFailedException("Failed get image input stream: " + e.getMessage());
        }
        return is;
    }

}
