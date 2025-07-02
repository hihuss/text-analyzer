import { HttpClient } from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {EMPTY, Observable} from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class AnalysisHttpService {
   http = inject(HttpClient);

  analyzeText(): Observable<number> {
    return EMPTY; // TODO: implement
  }
}
