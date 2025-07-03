import {Component, Input} from '@angular/core';
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelDescription,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from '@angular/material/expansion';
import {AnalysisResult} from '../../models/analysis-result.model';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-accordion',
  imports: [
    MatAccordion,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatExpansionPanelDescription,
    DatePipe
  ],
  templateUrl: './accordion.html',
  styleUrl: './accordion.scss'
})
export class Accordion {
  @Input() panels: AnalysisResult[] = [];

  getResultEntries(results: Record<string, number> | undefined): [string, number][] {
    if (!results) return [];
    return Object.entries(results);
  }
}
