import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { CompanyPage } from './company-page.model';
import { CompanyPagePopupService } from './company-page-popup.service';
import { CompanyPageService } from './company-page.service';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-company-page-dialog',
    templateUrl: './company-page-dialog.component.html'
})
export class CompanyPageDialogComponent implements OnInit {

    companyPage: CompanyPage;
    authorities: any[];
    isSaving: boolean;

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private companyPageService: CompanyPageService,
        private modelService: ModelService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.modelService.query().subscribe(
            (res: Response) => { this.models = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.companyPage.id !== undefined) {
            this.companyPageService.update(this.companyPage)
                .subscribe((res: CompanyPage) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.companyPageService.create(this.companyPage)
                .subscribe((res: CompanyPage) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: CompanyPage) {
        this.eventManager.broadcast({ name: 'companyPageListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-company-page-popup',
    template: ''
})
export class CompanyPagePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private companyPagePopupService: CompanyPagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.companyPagePopupService
                    .open(CompanyPageDialogComponent, params['id']);
            } else {
                this.modalRef = this.companyPagePopupService
                    .open(CompanyPageDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
