package dedlaus.com.text_analyzer.service;

import dedlaus.com.text_analyzer.utils.AnalysisMode;

public interface ITextAnalyzerService {

    Integer analyzeText(String text, AnalysisMode analysisMode);
}
