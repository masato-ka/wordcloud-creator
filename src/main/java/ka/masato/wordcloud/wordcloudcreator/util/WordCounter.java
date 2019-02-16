package ka.masato.wordcloud.wordcloudcreator.util;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WordCounter {

    private Tokenizer tokenizer;


    public WordCounter(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public Map<String, Long> countToken(String rawText) {

        String stripText = Jsoup.parse(rawText).text();
        List<Token> tokens = tokenizer.tokenize(stripText);
        Map<String, Long> dataSet = tokens.stream()
                .filter(t -> t.getPartOfSpeechLevel2().equals("固有名詞"))
                .map(t -> t.getSurface())
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return dataSet;
    }

}
