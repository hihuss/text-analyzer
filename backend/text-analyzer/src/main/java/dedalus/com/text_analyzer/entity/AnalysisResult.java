package dedalus.com.text_analyzer.entity;

import dedalus.com.text_analyzer.utils.AnalysisMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    private LocalDateTime createdAt;

    private String resultJson;

    private AnalysisMode analysisMode;

    public AnalysisResult() {
    }

    public AnalysisResult(String text, AnalysisMode analysisMode, String resultJson) {
        this.text = text;
        this.analysisMode = analysisMode;
        this.resultJson = resultJson;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}