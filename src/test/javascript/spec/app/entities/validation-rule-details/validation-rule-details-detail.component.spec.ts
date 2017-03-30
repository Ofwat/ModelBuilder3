import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ValidationRuleDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/validation-rule-details/validation-rule-details-detail.component';
import { ValidationRuleDetailsService } from '../../../../../../main/webapp/app/entities/validation-rule-details/validation-rule-details.service';
import { ValidationRuleDetails } from '../../../../../../main/webapp/app/entities/validation-rule-details/validation-rule-details.model';

describe('Component Tests', () => {

    describe('ValidationRuleDetails Management Detail Component', () => {
        let comp: ValidationRuleDetailsDetailComponent;
        let fixture: ComponentFixture<ValidationRuleDetailsDetailComponent>;
        let service: ValidationRuleDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ValidationRuleDetailsDetailComponent],
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
                    ValidationRuleDetailsService
                ]
            }).overrideComponent(ValidationRuleDetailsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ValidationRuleDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ValidationRuleDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ValidationRuleDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.validationRuleDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
