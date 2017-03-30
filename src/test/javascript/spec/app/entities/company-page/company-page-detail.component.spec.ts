import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CompanyPageDetailComponent } from '../../../../../../main/webapp/app/entities/company-page/company-page-detail.component';
import { CompanyPageService } from '../../../../../../main/webapp/app/entities/company-page/company-page.service';
import { CompanyPage } from '../../../../../../main/webapp/app/entities/company-page/company-page.model';

describe('Component Tests', () => {

    describe('CompanyPage Management Detail Component', () => {
        let comp: CompanyPageDetailComponent;
        let fixture: ComponentFixture<CompanyPageDetailComponent>;
        let service: CompanyPageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CompanyPageDetailComponent],
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
                    CompanyPageService
                ]
            }).overrideComponent(CompanyPageDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyPageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyPageService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CompanyPage(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.companyPage).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
