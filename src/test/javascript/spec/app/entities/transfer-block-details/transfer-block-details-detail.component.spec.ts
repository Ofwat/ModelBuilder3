import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TransferBlockDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/transfer-block-details/transfer-block-details-detail.component';
import { TransferBlockDetailsService } from '../../../../../../main/webapp/app/entities/transfer-block-details/transfer-block-details.service';
import { TransferBlockDetails } from '../../../../../../main/webapp/app/entities/transfer-block-details/transfer-block-details.model';

describe('Component Tests', () => {

    describe('TransferBlockDetails Management Detail Component', () => {
        let comp: TransferBlockDetailsDetailComponent;
        let fixture: ComponentFixture<TransferBlockDetailsDetailComponent>;
        let service: TransferBlockDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TransferBlockDetailsDetailComponent],
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
                    TransferBlockDetailsService
                ]
            }).overrideComponent(TransferBlockDetailsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransferBlockDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransferBlockDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TransferBlockDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transferBlockDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
