import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { TransferBlock } from './transfer-block.model';
import { TransferBlockPopupService } from './transfer-block-popup.service';
import { TransferBlockService } from './transfer-block.service';
import { TransferBlockDetails, TransferBlockDetailsService } from '../transfer-block-details';
import { TransferBlockItem, TransferBlockItemService } from '../transfer-block-item';
import { Transfer, TransferService } from '../transfer';
@Component({
    selector: 'jhi-transfer-block-dialog',
    templateUrl: './transfer-block-dialog.component.html'
})
export class TransferBlockDialogComponent implements OnInit {

    transferBlock: TransferBlock;
    authorities: any[];
    isSaving: boolean;

    transferblockdetails: TransferBlockDetails[];

    transferblockitems: TransferBlockItem[];

    transfers: Transfer[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private transferBlockService: TransferBlockService,
        private transferBlockDetailsService: TransferBlockDetailsService,
        private transferBlockItemService: TransferBlockItemService,
        private transferService: TransferService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.transferBlockDetailsService.query({filter: 'transferblock-is-null'}).subscribe((res: Response) => {
            if (!this.transferBlock.transferBlockDetails || !this.transferBlock.transferBlockDetails.id) {
                this.transferblockdetails = res.json();
            } else {
                this.transferBlockDetailsService.find(this.transferBlock.transferBlockDetails.id).subscribe((subRes: TransferBlockDetails) => {
                    this.transferblockdetails = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.transferBlockItemService.query().subscribe(
            (res: Response) => { this.transferblockitems = res.json(); }, (res: Response) => this.onError(res.json()));
        this.transferService.query().subscribe(
            (res: Response) => { this.transfers = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.transferBlock.id !== undefined) {
            this.transferBlockService.update(this.transferBlock)
                .subscribe((res: TransferBlock) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.transferBlockService.create(this.transferBlock)
                .subscribe((res: TransferBlock) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: TransferBlock) {
        this.eventManager.broadcast({ name: 'transferBlockListModification', content: 'OK'});
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

    trackTransferBlockDetailsById(index: number, item: TransferBlockDetails) {
        return item.id;
    }

    trackTransferBlockItemById(index: number, item: TransferBlockItem) {
        return item.id;
    }

    trackTransferById(index: number, item: Transfer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-transfer-block-popup',
    template: ''
})
export class TransferBlockPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferBlockPopupService: TransferBlockPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.transferBlockPopupService
                    .open(TransferBlockDialogComponent, params['id']);
            } else {
                this.modalRef = this.transferBlockPopupService
                    .open(TransferBlockDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
