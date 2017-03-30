import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ValidationRuleDetailComponent } from '../../../../../../main/webapp/app/entities/validation-rule/validation-rule-detail.component';
import { ValidationRuleService } from '../../../../../../main/webapp/app/entities/validation-rule/validation-rule.service';
import { ValidationRule } from '../../../../../../main/webapp/app/entities/validation-rule/validation-rule.model';

describe('Component Tests', () => {

    describe('ValidationRule Management Detail Component', () => {
        let comp: ValidationRuleDetailComponent;
        let fixture: ComponentFixture<ValidationRuleDetailComponent>;
        let service: ValidationRuleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ValidationRuleDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    ValidationRuleService
                ]
            }).overrideComponent(ValidationRuleDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ValidationRuleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ValidationRuleService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ValidationRule(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.validationRule).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
