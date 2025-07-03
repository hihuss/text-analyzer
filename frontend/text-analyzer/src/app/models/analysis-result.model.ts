import {ANALYSIS_MODE} from '../utils/constants';

export class AnalysisResult {
  id?: number;
  text?: string;
  createdAt?: string;
  results?: Record<string, number>;
  analysisMode?: ANALYSIS_MODE
}
