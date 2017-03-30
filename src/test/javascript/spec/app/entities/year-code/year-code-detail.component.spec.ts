import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { YearCodeDetailComponent } from '../../../../../../main/webapp/app/entities/year-code/year-code-detail.component';
import { YearCodeService } from '../../../../../../main/webapp/app/entities/year-code/year-code.service';
import { YearCode } from '../../../../../../main/webapp/app/entities/year-code/year-code.model';

describe('Component Tests', () => {

    describe('YearCode Management Detail Component', () => {
        let comp: YearCodeDetailComponent;
        let fixture: ComponentFixture<YearCodeDetailComponent>;
        let service: YearCodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [YearCodeDetailComponent],
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
                    YearCodeService
                ]
            }).overrideComponent(YearCodeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(YearCodeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(YearCodeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new YearCode(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.yearCode).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
