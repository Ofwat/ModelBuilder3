import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TransferConditionDetailComponent } from '../../../../../../main/webapp/app/entities/transfer-condition/transfer-condition-detail.component';
import { TransferConditionService } from '../../../../../../main/webapp/app/entities/transfer-condition/transfer-condition.service';
import { TransferCondition } from '../../../../../../main/webapp/app/entities/transfer-condition/transfer-condition.model';

describe('Component Tests', () => {

    describe('TransferCondition Management Detail Component', () => {
        let comp: TransferConditionDetailComponent;
        let fixture: ComponentFixture<TransferConditionDetailComponent>;
        let service: TransferConditionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TransferConditionDetailComponent],
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
                    TransferConditionService
                ]
            }).overrideComponent(TransferConditionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransferConditionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransferConditionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TransferCondition(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transferCondition).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
