import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FormHeadingCellDetailComponent } from '../../../../../../main/webapp/app/entities/form-heading-cell/form-heading-cell-detail.component';
import { FormHeadingCellService } from '../../../../../../main/webapp/app/entities/form-heading-cell/form-heading-cell.service';
import { FormHeadingCell } from '../../../../../../main/webapp/app/entities/form-heading-cell/form-heading-cell.model';

describe('Component Tests', () => {

    describe('FormHeadingCell Management Detail Component', () => {
        let comp: FormHeadingCellDetailComponent;
        let fixture: ComponentFixture<FormHeadingCellDetailComponent>;
        let service: FormHeadingCellService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [FormHeadingCellDetailComponent],
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
                    FormHeadingCellService
                ]
            }).overrideComponent(FormHeadingCellDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormHeadingCellDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormHeadingCellService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FormHeadingCell(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.formHeadingCell).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
