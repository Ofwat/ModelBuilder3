import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PageDetails } from './page-details.model';
import { PageDetailsPopupService } from './page-details-popup.service';
import { PageDetailsService } from './page-details.service';
@Component({
    selector: 'jhi-page-details-dialog',
    templateUrl: './page-details-dialog.component.html'
})
export class PageDetailsDialogComponent implements OnInit {

    pageDetails: PageDetails;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private pageDetailsService: PageDetailsService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.pageDetails.id !== undefined) {
            this.pageDetailsService.update(this.pageDetails)
                .subscribe((res: PageDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.pageDetailsService.create(this.pageDetails)
                .subscribe((res: PageDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: PageDetails) {
        this.eventManager.broadcast({ name: 'pageDetailsListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-page-details-popup',
    template: ''
})
export class PageDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private pageDetailsPopupService: PageDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.pageDetailsPopupService
                    .open(PageDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.pageDetailsPopupService
                    .open(PageDetailsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
