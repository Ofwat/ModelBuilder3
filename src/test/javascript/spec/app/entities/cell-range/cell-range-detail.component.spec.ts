import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CellRangeDetailComponent } from '../../../../../../main/webapp/app/entities/cell-range/cell-range-detail.component';
import { CellRangeService } from '../../../../../../main/webapp/app/entities/cell-range/cell-range.service';
import { CellRange } from '../../../../../../main/webapp/app/entities/cell-range/cell-range.model';

describe('Component Tests', () => {

    describe('CellRange Management Detail Component', () => {
        let comp: CellRangeDetailComponent;
        let fixture: ComponentFixture<CellRangeDetailComponent>;
        let service: CellRangeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CellRangeDetailComponent],
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
                    CellRangeService
                ]
            }).overrideComponent(CellRangeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CellRangeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CellRangeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CellRange(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cellRange).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
