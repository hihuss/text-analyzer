import {HttpClient, HttpHeaders} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AnalysisResult} from '../models/analysis-result.model';
import {ANALYSIS_MODE} from '../utils/constants';


@Injectable({
  providedIn: 'root',
})
export class AnalysisHttpService {
  http = inject(HttpClient);

  private readonly api = 'http://localhost:8080';

  analyzeText(textToAnalyze: any, analysisMode: ANALYSIS_MODE): Observable<AnalysisResult> {
    const headers = new HttpHeaders({'Content-Type': 'text/plain'});
    return this.http.post<AnalysisResult>(`${this.api}/analyze-text/${analysisMode}`, textToAnalyze, {headers}
    );
  }

  getHistoryData(): Observable<AnalysisResult[]> {
    return this.http.get<AnalysisResult[]>(`${this.api}/history-data`);
  }
}
