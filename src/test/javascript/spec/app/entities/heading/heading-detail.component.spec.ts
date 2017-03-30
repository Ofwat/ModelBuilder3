import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HeadingDetailComponent } from '../../../../../../main/webapp/app/entities/heading/heading-detail.component';
import { HeadingService } from '../../../../../../main/webapp/app/entities/heading/heading.service';
import { Heading } from '../../../../../../main/webapp/app/entities/heading/heading.model';

describe('Component Tests', () => {

    describe('Heading Management Detail Component', () => {
        let comp: HeadingDetailComponent;
        let fixture: ComponentFixture<HeadingDetailComponent>;
        let service: HeadingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [HeadingDetailComponent],
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
                    HeadingService
                ]
            }).overrideComponent(HeadingDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HeadingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HeadingService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Heading(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.heading).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
