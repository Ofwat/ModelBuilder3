import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FormDetailComponent } from '../../../../../../main/webapp/app/entities/form/form-detail.component';
import { FormService } from '../../../../../../main/webapp/app/entities/form/form.service';
import { Form } from '../../../../../../main/webapp/app/entities/form/form.model';

describe('Component Tests', () => {

    describe('Form Management Detail Component', () => {
        let comp: FormDetailComponent;
        let fixture: ComponentFixture<FormDetailComponent>;
        let service: FormService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [FormDetailComponent],
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
                    FormService
                ]
            }).overrideComponent(FormDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FormDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Form(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.form).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
