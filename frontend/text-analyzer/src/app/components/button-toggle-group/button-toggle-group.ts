import {Component, Input} from '@angular/core';
import {MatButtonToggle, MatButtonToggleGroup} from "@angular/material/button-toggle";
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {TitleCasePipe} from '@angular/common';

@Component({
  selector: 'app-button-toggle-group',
  imports: [
    MatButtonToggleGroup,
    MatButtonToggle,
    ReactiveFormsModule,
    TitleCasePipe,
  ],
  templateUrl: './button-toggle-group.html',
  styleUrl: './button-toggle-group.scss'
})
export class ButtonToggleGroup {
  @Input() control!: FormControl;
  @Input() options: string[] = [];
}
