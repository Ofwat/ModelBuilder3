import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TextBlockDetailComponent } from '../../../../../../main/webapp/app/entities/text-block/text-block-detail.component';
import { TextBlockService } from '../../../../../../main/webapp/app/entities/text-block/text-block.service';
import { TextBlock } from '../../../../../../main/webapp/app/entities/text-block/text-block.model';

describe('Component Tests', () => {

    describe('TextBlock Management Detail Component', () => {
        let comp: TextBlockDetailComponent;
        let fixture: ComponentFixture<TextBlockDetailComponent>;
        let service: TextBlockService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TextBlockDetailComponent],
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
                    TextBlockService
                ]
            }).overrideComponent(TextBlockDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TextBlockDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TextBlockService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TextBlock(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.textBlock).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
