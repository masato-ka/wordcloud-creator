package ka.masato.wordcloud.wordcloudcreator.infrastructure.imagerepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GcsWordCloudImageRepositoryTest {

    @Autowired
    private GcsWordCloudImageRepository gcsWordCloudImageRepository;

    @Autowired

    @Test
    public void saveImage() {

        try {
            BufferedImage bufferedImage = ImageIO.read(new File("test-image.png"));
            String fileName = gcsWordCloudImageRepository.saveImage(bufferedImage);
        } catch (IOException e) {
            fail();
        }
    }
}