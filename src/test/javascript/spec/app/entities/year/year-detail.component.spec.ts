import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { YearDetailComponent } from '../../../../../../main/webapp/app/entities/year/year-detail.component';
import { YearService } from '../../../../../../main/webapp/app/entities/year/year.service';
import { Year } from '../../../../../../main/webapp/app/entities/year/year.model';

describe('Component Tests', () => {

    describe('Year Management Detail Component', () => {
        let comp: YearDetailComponent;
        let fixture: ComponentFixture<YearDetailComponent>;
        let service: YearService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [YearDetailComponent],
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
                    YearService
                ]
            }).overrideComponent(YearDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(YearDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(YearService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Year(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.year).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
