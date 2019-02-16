package ka.masato.wordcloud.wordcloudcreator.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordCounterTest {

    @Autowired
    WordCounter wordCounter;

    @Test
    public void countToken() {
        wordCounter.countToken("今日のテストは100点です。");
    }
}