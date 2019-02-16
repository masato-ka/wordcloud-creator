package ka.masato.wordcloud.wordcloudcreator.domain.repository;

import org.springframework.stereotype.Repository;

import java.awt.image.BufferedImage;

@Repository
public interface WordCloudImageRepository {

    String saveImage(BufferedImage image);

    BufferedImage getImage(Integer imangeId);

    void delete(String url);

}
