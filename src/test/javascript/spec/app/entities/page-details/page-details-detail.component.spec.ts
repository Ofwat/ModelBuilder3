import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PageDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/page-details/page-details-detail.component';
import { PageDetailsService } from '../../../../../../main/webapp/app/entities/page-details/page-details.service';
import { PageDetails } from '../../../../../../main/webapp/app/entities/page-details/page-details.model';

describe('Component Tests', () => {

    describe('PageDetails Management Detail Component', () => {
        let comp: PageDetailsDetailComponent;
        let fixture: ComponentFixture<PageDetailsDetailComponent>;
        let service: PageDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PageDetailsDetailComponent],
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
                    PageDetailsService
                ]
            }).overrideComponent(PageDetailsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PageDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PageDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PageDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pageDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
