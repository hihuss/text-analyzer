import {Component, inject} from '@angular/core';
import {MatToolbar} from '@angular/material/toolbar';
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatButton} from '@angular/material/button';
import {MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {ButtonToggleGroup} from './components/button-toggle-group/button-toggle-group';
import {FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {ANALYSIS_MODE, OPERATING_MODE} from './utils/constants';
import {FormKeys} from './utils/form-keys';

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
  styleUrl: './app.component.scss'
})
export class AppComponent {
  protected readonly ANALYSIS_MODE = ANALYSIS_MODE;
  protected readonly OPERATING_MODE = OPERATING_MODE;
  protected readonly FormKeys = FormKeys;
  protected formGroup: FormGroup;

  private fb = inject(NonNullableFormBuilder);

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
    console.log('analyzing.....')
    // TODO: implement
  }

}
