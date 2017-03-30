import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuditDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/audit-details/audit-details-detail.component';
import { AuditDetailsService } from '../../../../../../main/webapp/app/entities/audit-details/audit-details.service';
import { AuditDetails } from '../../../../../../main/webapp/app/entities/audit-details/audit-details.model';

describe('Component Tests', () => {

    describe('AuditDetails Management Detail Component', () => {
        let comp: AuditDetailsDetailComponent;
        let fixture: ComponentFixture<AuditDetailsDetailComponent>;
        let service: AuditDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [AuditDetailsDetailComponent],
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
                    AuditDetailsService
                ]
            }).overrideComponent(AuditDetailsDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuditDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuditDetailsService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AuditDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auditDetails).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
