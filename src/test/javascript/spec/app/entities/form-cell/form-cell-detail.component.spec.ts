import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FormCellDetailComponent } from '../../../../../../main/webapp/app/entities/form-cell/form-cell-detail.component';
import { FormCellService } from '../../../../../../main/webapp/app/entities/form-cell/form-cell.service';
import { FormCell } from '../../../../../../main/webapp/app/entities/form-cell/form-cell.model';

describe('Component Tests', () => {

    describe('FormCell Management Detail Component', () => {
        let comp: FormCellDetailComponent;
        let fixture: ComponentFixture<FormCellDetailComponent>;
        let service: FormCellService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [FormCellDetailComponent],
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
                    FormCellService
                ]
            }).overrideComponent(FormCellDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormCellDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormCellService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FormCell(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.formCell).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
