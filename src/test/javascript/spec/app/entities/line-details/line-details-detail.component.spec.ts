import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LineDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/line-details/line-details-detail.component';
import { LineDetailsService } from '../../../../../../main/webapp/app/entities/line-details/line-details.service';
import { LineDetails } from '../../../../../../main/webapp/app/entities/line-details/line-details.model';

describe('Component Tests', () => {

    describe('LineDetails Management Detail Component', () => {
        let comp: LineDetailsDetailComponent;
        let fixture: ComponentFixture<LineDetailsDetailComponent>;
        let service: LineDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [LineDetailsDetailComponent],
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
                    LineDetailsService
                ]
            }).overrideComponent(LineDetailsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LineDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LineDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LineDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lineDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
