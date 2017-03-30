import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ValidationRuleItemDetailComponent } from '../../../../../../main/webapp/app/entities/validation-rule-item/validation-rule-item-detail.component';
import { ValidationRuleItemService } from '../../../../../../main/webapp/app/entities/validation-rule-item/validation-rule-item.service';
import { ValidationRuleItem } from '../../../../../../main/webapp/app/entities/validation-rule-item/validation-rule-item.model';

describe('Component Tests', () => {

    describe('ValidationRuleItem Management Detail Component', () => {
        let comp: ValidationRuleItemDetailComponent;
        let fixture: ComponentFixture<ValidationRuleItemDetailComponent>;
        let service: ValidationRuleItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ValidationRuleItemDetailComponent],
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
                    ValidationRuleItemService
                ]
            }).overrideComponent(ValidationRuleItemDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ValidationRuleItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ValidationRuleItemService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ValidationRuleItem(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.validationRuleItem).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
