package dedlaus.com.text_analyzer.service;

import dedlaus.com.text_analyzer.utils.AnalysisMode;

import java.util.Map;

public interface ITextAnalyzerService {

    Map<String, Long> analyzeText(String text, AnalysisMode analysisMode);
}
