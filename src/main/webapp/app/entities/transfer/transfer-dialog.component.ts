import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Transfer } from './transfer.model';
import { TransferPopupService } from './transfer-popup.service';
import { TransferService } from './transfer.service';
import { TransferCondition, TransferConditionService } from '../transfer-condition';
import { TransferBlock, TransferBlockService } from '../transfer-block';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-transfer-dialog',
    templateUrl: './transfer-dialog.component.html'
})
export class TransferDialogComponent implements OnInit {

    transfer: Transfer;
    authorities: any[];
    isSaving: boolean;

    transferconditions: TransferCondition[];

    transferblocks: TransferBlock[];

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private transferService: TransferService,
        private transferConditionService: TransferConditionService,
        private transferBlockService: TransferBlockService,
        private modelService: ModelService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.transferConditionService.query({filter: 'transfer-is-null'}).subscribe((res: Response) => {
            if (!this.transfer.transferCondition || !this.transfer.transferCondition.id) {
                this.transferconditions = res.json();
            } else {
                this.transferConditionService.find(this.transfer.transferCondition.id).subscribe((subRes: TransferCondition) => {
                    this.transferconditions = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.transferBlockService.query().subscribe(
            (res: Response) => { this.transferblocks = res.json(); }, (res: Response) => this.onError(res.json()));
        this.modelService.query().subscribe(
            (res: Response) => { this.models = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.transfer.id !== undefined) {
            this.transferService.update(this.transfer)
                .subscribe((res: Transfer) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.transferService.create(this.transfer)
                .subscribe((res: Transfer) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Transfer) {
        this.eventManager.broadcast({ name: 'transferListModification', content: 'OK'});
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

    trackTransferConditionById(index: number, item: TransferCondition) {
        return item.id;
    }

    trackTransferBlockById(index: number, item: TransferBlock) {
        return item.id;
    }

    trackModelById(index: number, item: Model) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-transfer-popup',
    template: ''
})
export class TransferPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferPopupService: TransferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.transferPopupService
                    .open(TransferDialogComponent, params['id']);
            } else {
                this.modalRef = this.transferPopupService
                    .open(TransferDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
