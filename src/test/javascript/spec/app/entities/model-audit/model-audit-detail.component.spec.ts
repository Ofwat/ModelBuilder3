import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ModelAuditDetailComponent } from '../../../../../../main/webapp/app/entities/model-audit/model-audit-detail.component';
import { ModelAuditService } from '../../../../../../main/webapp/app/entities/model-audit/model-audit.service';
import { ModelAudit } from '../../../../../../main/webapp/app/entities/model-audit/model-audit.model';

describe('Component Tests', () => {

    describe('ModelAudit Management Detail Component', () => {
        let comp: ModelAuditDetailComponent;
        let fixture: ComponentFixture<ModelAuditDetailComponent>;
        let service: ModelAuditService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [ModelAuditDetailComponent],
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
                    ModelAuditService
                ]
            }).overrideComponent(ModelAuditDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModelAuditDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModelAuditService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ModelAudit(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.modelAudit).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
