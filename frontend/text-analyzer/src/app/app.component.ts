import {Component, inject} from '@angular/core';
import {MatToolbar} from '@angular/material/toolbar';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {ButtonToggleGroup} from './components/button-toggle-group/button-toggle-group';
import {FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {ANALYSIS_MODE, OPERATING_MODE} from './utils/constants';
import {FormKeys} from './utils/form-keys';
import {analyzeText} from './utils/helper-functions';
import {AnalysisHttpService} from './services/analysis-http.service';

@Component({
  selector: 'app-root',
  imports: [
    MatSlideToggleModule,
    MatToolbar,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    MatFormField,
    ButtonToggleGroup,
    ReactiveFormsModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  protected readonly ANALYSIS_MODE = Object.values(ANALYSIS_MODE);
  protected readonly OPERATING_MODE = Object.values(OPERATING_MODE);
  protected readonly FormKeys = FormKeys;
  formGroup: FormGroup;

  matchCount = 0; // FIXME: this is temporarily

  private fb = inject(NonNullableFormBuilder);
  private analyseHttpService = inject(AnalysisHttpService);

  constructor() {
   this.formGroup = this.fb.group({
     [FormKeys.Text] : ['', [Validators.required]],
     [FormKeys.AnalysisMode] : ['', [Validators.required]],
     [FormKeys.OperatingMode] : ['', [Validators.required]],
   })
  }

  protected getControl(key: FormKeys): FormControl {
    return this.formGroup.get(key) as FormControl;
  }

  protected resetOptions(): void {
    this.getControl(FormKeys.AnalysisMode).reset();
    this.getControl(FormKeys.OperatingMode).reset();
  }

  analyze() {
    if (this.formGroup.invalid) {
      return;
    }

    const textToAnalyze = this.getControl(FormKeys.Text).value;
    const analysisMode = this.getControl(FormKeys.AnalysisMode).value;
    if (OPERATING_MODE.Offline === this.getControl(FormKeys.OperatingMode).value) {
      this.matchCount = analyzeText(textToAnalyze, analysisMode);
    } else {
      this.analyseHttpService.analyzeText();
    }
  }

}
