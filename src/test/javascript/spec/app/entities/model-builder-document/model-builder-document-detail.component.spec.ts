import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ModelBuilderDocumentDetailComponent } from '../../../../../../main/webapp/app/entities/model-builder-document/model-builder-document-detail.component';
import { ModelBuilderDocumentService } from '../../../../../../main/webapp/app/entities/model-builder-document/model-builder-document.service';
import { ModelBuilderDocument } from '../../../../../../main/webapp/app/entities/model-builder-document/model-builder-document.model';

describe('Component Tests', () => {

    describe('ModelBuilderDocument Management Detail Component', () => {
        let comp: ModelBuilderDocumentDetailComponent;
        let fixture: ComponentFixture<ModelBuilderDocumentDetailComponent>;
        let service: ModelBuilderDocumentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ModelBuilderDocumentDetailComponent],
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
                    ModelBuilderDocumentService
                ]
            }).overrideComponent(ModelBuilderDocumentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModelBuilderDocumentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModelBuilderDocumentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ModelBuilderDocument(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.modelBuilderDocument).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
