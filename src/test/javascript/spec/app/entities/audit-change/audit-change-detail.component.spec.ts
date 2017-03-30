import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuditChangeDetailComponent } from '../../../../../../main/webapp/app/entities/audit-change/audit-change-detail.component';
import { AuditChangeService } from '../../../../../../main/webapp/app/entities/audit-change/audit-change.service';
import { AuditChange } from '../../../../../../main/webapp/app/entities/audit-change/audit-change.model';

describe('Component Tests', () => {

    describe('AuditChange Management Detail Component', () => {
        let comp: AuditChangeDetailComponent;
        let fixture: ComponentFixture<AuditChangeDetailComponent>;
        let service: AuditChangeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [AuditChangeDetailComponent],
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
                    AuditChangeService
                ]
            }).overrideComponent(AuditChangeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuditChangeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuditChangeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuditChange(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auditChange).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
