import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Section } from './section.model';
import { SectionPopupService } from './section-popup.service';
import { SectionService } from './section.service';
import { SectionDetails, SectionDetailsService } from '../section-details';
import { ModelBuilderDocument, ModelBuilderDocumentService } from '../model-builder-document';
import { Line, LineService } from '../line';
import { Form, FormService } from '../form';
import { Page, PageService } from '../page';
@Component({
    selector: 'jhi-section-dialog',
    templateUrl: './section-dialog.component.html'
})
export class SectionDialogComponent implements OnInit {

    section: Section;
    authorities: any[];
    isSaving: boolean;

    sectiondetails: SectionDetails[];

    modelbuilderdocuments: ModelBuilderDocument[];

    lines: Line[];

    forms: Form[];

    pages: Page[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private sectionService: SectionService,
        private sectionDetailsService: SectionDetailsService,
        private modelBuilderDocumentService: ModelBuilderDocumentService,
        private lineService: LineService,
        private formService: FormService,
        private pageService: PageService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.sectionDetailsService.query({filter: 'section-is-null'}).subscribe((res: Response) => {
            if (!this.section.sectionDetail || !this.section.sectionDetail.id) {
                this.sectiondetails = res.json();
            } else {
                this.sectionDetailsService.find(this.section.sectionDetail.id).subscribe((subRes: SectionDetails) => {
                    this.sectiondetails = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.modelBuilderDocumentService.query().subscribe(
            (res: Response) => { this.modelbuilderdocuments = res.json(); }, (res: Response) => this.onError(res.json()));
        this.lineService.query().subscribe(
            (res: Response) => { this.lines = res.json(); }, (res: Response) => this.onError(res.json()));
        this.formService.query().subscribe(
            (res: Response) => { this.forms = res.json(); }, (res: Response) => this.onError(res.json()));
        this.pageService.query().subscribe(
            (res: Response) => { this.pages = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.section.id !== undefined) {
            this.sectionService.update(this.section)
                .subscribe((res: Section) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.sectionService.create(this.section)
                .subscribe((res: Section) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Section) {
        this.eventManager.broadcast({ name: 'sectionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackSectionDetailsById(index: number, item: SectionDetails) {
        return item.id;
    }

    trackModelBuilderDocumentById(index: number, item: ModelBuilderDocument) {
        return item.id;
    }

    trackLineById(index: number, item: Line) {
        return item.id;
    }

    trackFormById(index: number, item: Form) {
        return item.id;
    }

    trackPageById(index: number, item: Page) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-section-popup',
    template: ''
})
export class SectionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private sectionPopupService: SectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.sectionPopupService
                    .open(SectionDialogComponent, params['id']);
            } else {
                this.modalRef = this.sectionPopupService
                    .open(SectionDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
