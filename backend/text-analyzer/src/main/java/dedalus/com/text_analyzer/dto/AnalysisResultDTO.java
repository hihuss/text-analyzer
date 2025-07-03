package dedalus.com.text_analyzer.dto;

import dedalus.com.text_analyzer.utils.AnalysisMode;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class AnalysisResultDTO {
    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private Map<String, Long> results;
    private AnalysisMode analysisMode;
}
