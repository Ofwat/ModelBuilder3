import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ModelDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/model-details/model-details-detail.component';
import { ModelDetailsService } from '../../../../../../main/webapp/app/entities/model-details/model-details.service';
import { ModelDetails } from '../../../../../../main/webapp/app/entities/model-details/model-details.model';

describe('Component Tests', () => {

    describe('ModelDetails Management Detail Component', () => {
        let comp: ModelDetailsDetailComponent;
        let fixture: ComponentFixture<ModelDetailsDetailComponent>;
        let service: ModelDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ModelDetailsDetailComponent],
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
                    ModelDetailsService
                ]
            }).overrideComponent(ModelDetailsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModelDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModelDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ModelDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.modelDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
