import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { TransferCondition } from './transfer-condition.model';
import { TransferConditionPopupService } from './transfer-condition-popup.service';
import { TransferConditionService } from './transfer-condition.service';
@Component({
    selector: 'jhi-transfer-condition-dialog',
    templateUrl: './transfer-condition-dialog.component.html'
})
export class TransferConditionDialogComponent implements OnInit {

    transferCondition: TransferCondition;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private transferConditionService: TransferConditionService,
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
        if (this.transferCondition.id !== undefined) {
            this.transferConditionService.update(this.transferCondition)
                .subscribe((res: TransferCondition) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.transferConditionService.create(this.transferCondition)
                .subscribe((res: TransferCondition) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: TransferCondition) {
        this.eventManager.broadcast({ name: 'transferConditionListModification', content: 'OK'});
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
    selector: 'jhi-transfer-condition-popup',
    template: ''
})
export class TransferConditionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferConditionPopupService: TransferConditionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.transferConditionPopupService
                    .open(TransferConditionDialogComponent, params['id']);
            } else {
                this.modalRef = this.transferConditionPopupService
                    .open(TransferConditionDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
