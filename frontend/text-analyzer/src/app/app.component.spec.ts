import {provideZonelessChangeDetection} from '@angular/core';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {FormKeys} from './utils/form-keys';
import {provideHttpClient} from '@angular/common/http';
import {By} from '@angular/platform-browser';
import {ANALYSIS_MODE, OPERATING_MODE} from './utils/constants';
import {AnalysisHttpService} from './services/analysis-http.service';


describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  let component: AppComponent;
  let form: FormGroup;
  let httpServiceSpy: jasmine.SpyObj<AnalysisHttpService>;

  beforeEach(async () => {
    httpServiceSpy = jasmine.createSpyObj('AnalysisHttpService', ['analyzeText']);

    form = new FormGroup({
      [FormKeys.Text]: new FormControl('', [Validators.required]),
      [FormKeys.AnalysisMode]: new FormControl('', [Validators.required]),
      [FormKeys.OperatingMode]: new FormControl('', [Validators.required]),
    });

    await TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [
        provideZonelessChangeDetection(),
        provideHttpClient(),
        { provide: AnalysisHttpService, useValue: httpServiceSpy },
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    component.formGroup = form;
    fixture.detectChanges();
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should render all components', () => {
    const input = fixture.debugElement.query(By.css('textarea'));
    const button = fixture.debugElement.query(By.css('button[type="submit"]'));
    const toggleGroup = fixture.debugElement.queryAll(By.css('mat-button-toggle-group'));

    expect(input).toBeTruthy();
    expect(button).toBeTruthy();
    expect(toggleGroup.length).toBe(2);
  });

  it('submit button disabled if form empty', () => {
    const button = fixture.debugElement.query(By.css('button[type="submit"]'));
    expect(button.nativeElement.disabled).toBeTrue();
  });

  it('submit button disabled if only text provided', () => {
    component.formGroup.get(FormKeys.Text)?.setValue('Lorem Ipsum is simply dummy text of the printing and typesetting industry. ');
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('button[type="submit"]'));
    expect(button.nativeElement.disabled).toBeTrue();
  });

  it('submit button disabled if operating mode missing', () => {
    component.formGroup.get(FormKeys.Text)?.setValue('Lorem Ipsum is simply dummy text of the printing and typesetting industry. ');
    component.formGroup.get(FormKeys.AnalysisMode)?.setValue(ANALYSIS_MODE.Consonants);
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('button[type="submit"]'));
    expect(button.nativeElement.disabled).toBeTrue();
  });

  it('submit button enabled if all values entered', () => {
    setFormValues();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('button[type="submit"]'));
    expect(button.nativeElement.disabled).toBeFalse();
  });


  it('should analyze text in frontend if offline mode selected ', () => {
    spyOn(component, 'analyze');

    setFormValues();
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('button[type="submit"]'));
    button.nativeElement.click();
    fixture.detectChanges();

    expect(component.analyze).toHaveBeenCalled();
  });

  it('should call http method if online mode selected ', () => {
    setFormValues(OPERATING_MODE.Online);
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('button[type="submit"]'));
    button.nativeElement.click();
    fixture.detectChanges();

    expect(httpServiceSpy.analyzeText.calls.count()).toBe(1);
  });


  it('should return correct results when analyzing consonants in text in frontend ', () => {
    const text = 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'
    setFormValues(OPERATING_MODE.Offline, ANALYSIS_MODE.Consonants, text);
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('button[type="submit"]'));
    button.nativeElement.click();
    fixture.detectChanges();

    expect(component.matchCount).toBe(299);
  });

  it('should return correct results when analyzing vowels in text in frontend ', () => {
    const text = 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'
    setFormValues(OPERATING_MODE.Offline, ANALYSIS_MODE.Vowels, text);
    fixture.detectChanges();

    const button = fixture.debugElement.query(By.css('button[type="submit"]'));
    button.nativeElement.click();
    fixture.detectChanges();

    expect(component.matchCount).toBe(168);
  });


  function setFormValues(operatingMode?: OPERATING_MODE, analysisMode?: ANALYSIS_MODE, text?: string) {
    const defaultText = 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.';
    component.formGroup.get(FormKeys.Text)?.setValue(text ?? defaultText);
    component.formGroup.get(FormKeys.AnalysisMode)?.setValue(analysisMode ?? ANALYSIS_MODE.Consonants);
    component.formGroup.get(FormKeys.OperatingMode)?.setValue(operatingMode ?? OPERATING_MODE.Offline);
  }
});
