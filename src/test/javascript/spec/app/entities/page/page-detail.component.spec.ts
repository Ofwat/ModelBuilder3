import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PageDetailComponent } from '../../../../../../main/webapp/app/entities/page/page-detail.component';
import { PageService } from '../../../../../../main/webapp/app/entities/page/page.service';
import { Page } from '../../../../../../main/webapp/app/entities/page/page.model';

describe('Component Tests', () => {

    describe('Page Management Detail Component', () => {
        let comp: PageDetailComponent;
        let fixture: ComponentFixture<PageDetailComponent>;
        let service: PageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [PageDetailComponent],
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
                    PageService
                ]
            }).overrideComponent(PageDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PageService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Page(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.page).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
