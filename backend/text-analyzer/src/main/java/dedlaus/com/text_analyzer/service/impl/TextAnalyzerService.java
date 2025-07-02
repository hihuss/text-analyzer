package dedlaus.com.text_analyzer.service.impl;

import dedlaus.com.text_analyzer.service.ITextAnalyzerService;
import dedlaus.com.text_analyzer.utils.AnalysisMode;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TextAnalyzerService implements ITextAnalyzerService {

    @Override
    public Map<String, Long> analyzeText(String text, AnalysisMode analysisMode) {
        if (text == null || text.isEmpty()) {
            return Map.of();
        }

        String vowelRegex = "[aeiouAEIOU]";
        String consonantRegex = "[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ]";
        String regexToUse = (AnalysisMode.Vowels == analysisMode) ? vowelRegex : consonantRegex;

        Pattern pattern = Pattern.compile(regexToUse, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        return matcher.results()
                .map(matchResult -> matchResult.group().toLowerCase())
                .collect(Collectors.groupingBy(letter -> letter, Collectors.counting()));
    }
}
