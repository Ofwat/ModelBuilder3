import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { TransferBlockDetails } from './transfer-block-details.model';
import { TransferBlockDetailsPopupService } from './transfer-block-details-popup.service';
import { TransferBlockDetailsService } from './transfer-block-details.service';
@Component({
    selector: 'jhi-transfer-block-details-dialog',
    templateUrl: './transfer-block-details-dialog.component.html'
})
export class TransferBlockDetailsDialogComponent implements OnInit {

    transferBlockDetails: TransferBlockDetails;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private transferBlockDetailsService: TransferBlockDetailsService,
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
        if (this.transferBlockDetails.id !== undefined) {
            this.transferBlockDetailsService.update(this.transferBlockDetails)
                .subscribe((res: TransferBlockDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.transferBlockDetailsService.create(this.transferBlockDetails)
                .subscribe((res: TransferBlockDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: TransferBlockDetails) {
        this.eventManager.broadcast({ name: 'transferBlockDetailsListModification', content: 'OK'});
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
    selector: 'jhi-transfer-block-details-popup',
    template: ''
})
export class TransferBlockDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferBlockDetailsPopupService: TransferBlockDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.transferBlockDetailsPopupService
                    .open(TransferBlockDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.transferBlockDetailsPopupService
                    .open(TransferBlockDetailsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
