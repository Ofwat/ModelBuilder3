import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FormDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/form-details/form-details-detail.component';
import { FormDetailsService } from '../../../../../../main/webapp/app/entities/form-details/form-details.service';
import { FormDetails } from '../../../../../../main/webapp/app/entities/form-details/form-details.model';

describe('Component Tests', () => {

    describe('FormDetails Management Detail Component', () => {
        let comp: FormDetailsDetailComponent;
        let fixture: ComponentFixture<FormDetailsDetailComponent>;
        let service: FormDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [FormDetailsDetailComponent],
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
                    FormDetailsService
                ]
            }).overrideComponent(FormDetailsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FormDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.formDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
