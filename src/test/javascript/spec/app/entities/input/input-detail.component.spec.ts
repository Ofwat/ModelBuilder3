import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { InputDetailComponent } from '../../../../../../main/webapp/app/entities/input/input-detail.component';
import { InputService } from '../../../../../../main/webapp/app/entities/input/input.service';
import { Input } from '../../../../../../main/webapp/app/entities/input/input.model';

describe('Component Tests', () => {

    describe('Input Management Detail Component', () => {
        let comp: InputDetailComponent;
        let fixture: ComponentFixture<InputDetailComponent>;
        let service: InputService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [InputDetailComponent],
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
                    InputService
                ]
            }).overrideComponent(InputDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InputDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InputService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Input(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.input).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
