import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ModelBuilderDocument } from './model-builder-document.model';
import { ModelBuilderDocumentPopupService } from './model-builder-document-popup.service';
import { ModelBuilderDocumentService } from './model-builder-document.service';
import { Model, ModelService } from '../model';
import { Page, PageService } from '../page';
import { Section, SectionService } from '../section';
import { Line, LineService } from '../line';
@Component({
    selector: 'jhi-model-builder-document-dialog',
    templateUrl: './model-builder-document-dialog.component.html'
})
export class ModelBuilderDocumentDialogComponent implements OnInit {

    modelBuilderDocument: ModelBuilderDocument;
    authorities: any[];
    isSaving: boolean;

    models: Model[];

    pages: Page[];

    sections: Section[];

    lines: Line[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private modelBuilderDocumentService: ModelBuilderDocumentService,
        private modelService: ModelService,
        private pageService: PageService,
        private sectionService: SectionService,
        private lineService: LineService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.modelService.query().subscribe(
            (res: Response) => { this.models = res.json(); }, (res: Response) => this.onError(res.json()));
        this.pageService.query().subscribe(
            (res: Response) => { this.pages = res.json(); }, (res: Response) => this.onError(res.json()));
        this.sectionService.query().subscribe(
            (res: Response) => { this.sections = res.json(); }, (res: Response) => this.onError(res.json()));
        this.lineService.query().subscribe(
            (res: Response) => { this.lines = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.modelBuilderDocument.id !== undefined) {
            this.modelBuilderDocumentService.update(this.modelBuilderDocument)
                .subscribe((res: ModelBuilderDocument) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.modelBuilderDocumentService.create(this.modelBuilderDocument)
                .subscribe((res: ModelBuilderDocument) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ModelBuilderDocument) {
        this.eventManager.broadcast({ name: 'modelBuilderDocumentListModification', content: 'OK'});
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

    trackModelById(index: number, item: Model) {
        return item.id;
    }

    trackPageById(index: number, item: Page) {
        return item.id;
    }

    trackSectionById(index: number, item: Section) {
        return item.id;
    }

    trackLineById(index: number, item: Line) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-model-builder-document-popup',
    template: ''
})
export class ModelBuilderDocumentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private modelBuilderDocumentPopupService: ModelBuilderDocumentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.modelBuilderDocumentPopupService
                    .open(ModelBuilderDocumentDialogComponent, params['id']);
            } else {
                this.modalRef = this.modelBuilderDocumentPopupService
                    .open(ModelBuilderDocumentDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
