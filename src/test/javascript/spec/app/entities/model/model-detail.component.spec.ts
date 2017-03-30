import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ModelDetailComponent } from '../../../../../../main/webapp/app/entities/model/model-detail.component';
import { ModelService } from '../../../../../../main/webapp/app/entities/model/model.service';
import { Model } from '../../../../../../main/webapp/app/entities/model/model.model';

describe('Component Tests', () => {

    describe('Model Management Detail Component', () => {
        let comp: ModelDetailComponent;
        let fixture: ComponentFixture<ModelDetailComponent>;
        let service: ModelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ModelDetailComponent],
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
                    ModelService
                ]
            }).overrideComponent(ModelDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModelService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Model(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.model).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
