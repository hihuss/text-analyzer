package dedlaus.com.text_analyzer.service.impl;

import dedlaus.com.text_analyzer.service.ITextAnalyzerService;
import dedlaus.com.text_analyzer.utils.AnalysisMode;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextAnalyzerService implements ITextAnalyzerService {

    @Override
    public Integer analyzeText(String text, AnalysisMode analysisMode) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        String vowelRegex = "[aeiouAEIOU]";
        String consonantRegex = "[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ]";
        String regexToUse = (AnalysisMode.Vowels == analysisMode) ? vowelRegex : consonantRegex;

        Pattern pattern = Pattern.compile(regexToUse);
        Matcher matcher = pattern.matcher(text);

        return Math.toIntExact(matcher.results().count());
    }
}
