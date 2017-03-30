import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Page } from './page.model';
import { PagePopupService } from './page-popup.service';
import { PageService } from './page.service';
import { PageDetails, PageDetailsService } from '../page-details';
import { Section, SectionService } from '../section';
import { ModelBuilderDocument, ModelBuilderDocumentService } from '../model-builder-document';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-page-dialog',
    templateUrl: './page-dialog.component.html'
})
export class PageDialogComponent implements OnInit {

    page: Page;
    authorities: any[];
    isSaving: boolean;

    pagedetails: PageDetails[];

    sections: Section[];

    modelbuilderdocuments: ModelBuilderDocument[];

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pageService: PageService,
        private pageDetailsService: PageDetailsService,
        private sectionService: SectionService,
        private modelBuilderDocumentService: ModelBuilderDocumentService,
        private modelService: ModelService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.pageDetailsService.query({filter: 'page-is-null'}).subscribe((res: Response) => {
            if (!this.page.pageDetail || !this.page.pageDetail.id) {
                this.pagedetails = res.json();
            } else {
                this.pageDetailsService.find(this.page.pageDetail.id).subscribe((subRes: PageDetails) => {
                    this.pagedetails = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.sectionService.query().subscribe(
            (res: Response) => { this.sections = res.json(); }, (res: Response) => this.onError(res.json()));
        this.modelBuilderDocumentService.query().subscribe(
            (res: Response) => { this.modelbuilderdocuments = res.json(); }, (res: Response) => this.onError(res.json()));
        this.modelService.query().subscribe(
            (res: Response) => { this.models = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.page.id !== undefined) {
            this.pageService.update(this.page)
                .subscribe((res: Page) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.pageService.create(this.page)
                .subscribe((res: Page) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Page) {
        this.eventManager.broadcast({ name: 'pageListModification', content: 'OK'});
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

    trackPageDetailsById(index: number, item: PageDetails) {
        return item.id;
    }

    trackSectionById(index: number, item: Section) {
        return item.id;
    }

    trackModelBuilderDocumentById(index: number, item: ModelBuilderDocument) {
        return item.id;
    }

    trackModelById(index: number, item: Model) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-page-popup',
    template: ''
})
export class PagePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private pagePopupService: PagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.pagePopupService
                    .open(PageDialogComponent, params['id']);
            } else {
                this.modalRef = this.pagePopupService
                    .open(PageDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
