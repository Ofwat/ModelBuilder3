import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TransferBlockItemDetailComponent } from '../../../../../../main/webapp/app/entities/transfer-block-item/transfer-block-item-detail.component';
import { TransferBlockItemService } from '../../../../../../main/webapp/app/entities/transfer-block-item/transfer-block-item.service';
import { TransferBlockItem } from '../../../../../../main/webapp/app/entities/transfer-block-item/transfer-block-item.model';

describe('Component Tests', () => {

    describe('TransferBlockItem Management Detail Component', () => {
        let comp: TransferBlockItemDetailComponent;
        let fixture: ComponentFixture<TransferBlockItemDetailComponent>;
        let service: TransferBlockItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TransferBlockItemDetailComponent],
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
                    TransferBlockItemService
                ]
            }).overrideComponent(TransferBlockItemDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TransferBlockItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransferBlockItemService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TransferBlockItem(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.transferBlockItem).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
