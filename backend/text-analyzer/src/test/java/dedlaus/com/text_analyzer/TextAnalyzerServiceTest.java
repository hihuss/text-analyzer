package dedlaus.com.text_analyzer;

import dedlaus.com.text_analyzer.service.impl.TextAnalyzerService;
import dedlaus.com.text_analyzer.utils.AnalysisMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TextAnalyzerService.class)
public class TextAnalyzerServiceTest {

    @Autowired
    TextAnalyzerService textAnalyzerService;

    @Test
    @DisplayName("Should return 0 when text is empty")
    public void testEmptyText() {
        Integer result = textAnalyzerService.analyzeText("", AnalysisMode.Vowels);
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return 0 when text is null")
    public void testTextNull() {
        Integer result = textAnalyzerService.analyzeText(null, AnalysisMode.Vowels);
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return 0 when text is only whitespaces")
    public void testTextWhitespaces() {
        Integer result = textAnalyzerService.analyzeText("              ", AnalysisMode.Vowels);
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return 0 when counting vowels in all consonants text")
    public void testOnlyConsonants() {
        Integer result = textAnalyzerService.analyzeText("bcdfg", AnalysisMode.Vowels);
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return 0 when counting consonants in all vowels text")
    public void testOnlyVowels() {
        Integer result = textAnalyzerService.analyzeText("aeiou", AnalysisMode.Consonants);
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return 0 when analyzing only characters text")
    public void testOnlyCharacters() {
        String text = "12345!@#$$%^&*()";
        assertThat(textAnalyzerService.analyzeText(text, AnalysisMode.Consonants)).isEqualTo(0);
        assertThat(textAnalyzerService.analyzeText(text, AnalysisMode.Vowels)).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return correct results when analyzing text for consonants")
    public void testConsonants() {
        String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        Integer result = textAnalyzerService.analyzeText(text, AnalysisMode.Consonants);
        assertThat(result).isEqualTo(299);
    }

    @Test
    @DisplayName("Should return correct results when analyzing text for vowels")
    public void testVowels() {
        String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        Integer result = textAnalyzerService.analyzeText(text, AnalysisMode.Vowels);
        assertThat(result).isEqualTo(168);
    }

    @Test
    @DisplayName("Should return correct result when very long string")
    public void testVeryLongString() {
        String text = "a".repeat(1000000);
        Integer result = textAnalyzerService.analyzeText(text, AnalysisMode.Vowels);
        assertThat(result).isEqualTo(1000000);
    }

}
