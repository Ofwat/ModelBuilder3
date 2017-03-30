import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MacroDetailComponent } from '../../../../../../main/webapp/app/entities/macro/macro-detail.component';
import { MacroService } from '../../../../../../main/webapp/app/entities/macro/macro.service';
import { Macro } from '../../../../../../main/webapp/app/entities/macro/macro.model';

describe('Component Tests', () => {

    describe('Macro Management Detail Component', () => {
        let comp: MacroDetailComponent;
        let fixture: ComponentFixture<MacroDetailComponent>;
        let service: MacroService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [MacroDetailComponent],
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
                    MacroService
                ]
            }).overrideComponent(MacroDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MacroDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MacroService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Macro(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.macro).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
