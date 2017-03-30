import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SectionDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/section-details/section-details-detail.component';
import { SectionDetailsService } from '../../../../../../main/webapp/app/entities/section-details/section-details.service';
import { SectionDetails } from '../../../../../../main/webapp/app/entities/section-details/section-details.model';

describe('Component Tests', () => {

    describe('SectionDetails Management Detail Component', () => {
        let comp: SectionDetailsDetailComponent;
        let fixture: ComponentFixture<SectionDetailsDetailComponent>;
        let service: SectionDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [SectionDetailsDetailComponent],
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
                    SectionDetailsService
                ]
            }).overrideComponent(SectionDetailsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SectionDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SectionDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SectionDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sectionDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
