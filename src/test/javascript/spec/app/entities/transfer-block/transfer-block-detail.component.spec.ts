import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TransferBlockDetailComponent } from '../../../../../../main/webapp/app/entities/transfer-block/transfer-block-detail.component';
import { TransferBlockService } from '../../../../../../main/webapp/app/entities/transfer-block/transfer-block.service';
import { TransferBlock } from '../../../../../../main/webapp/app/entities/transfer-block/transfer-block.model';

describe('Component Tests', () => {

    describe('TransferBlock Management Detail Component', () => {
        let comp: TransferBlockDetailComponent;
        let fixture: ComponentFixture<TransferBlockDetailComponent>;
        let service: TransferBlockService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TransferBlockDetailComponent],
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
                    TransferBlockService
                ]
            }).overrideComponent(TransferBlockDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransferBlockDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransferBlockService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TransferBlock(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transferBlock).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
