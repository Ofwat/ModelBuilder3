import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CellDetailComponent } from '../../../../../../main/webapp/app/entities/cell/cell-detail.component';
import { CellService } from '../../../../../../main/webapp/app/entities/cell/cell.service';
import { Cell } from '../../../../../../main/webapp/app/entities/cell/cell.model';

describe('Component Tests', () => {

    describe('Cell Management Detail Component', () => {
        let comp: CellDetailComponent;
        let fixture: ComponentFixture<CellDetailComponent>;
        let service: CellService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CellDetailComponent],
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
                    CellService
                ]
            }).overrideComponent(CellDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CellDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CellService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Cell(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cell).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
