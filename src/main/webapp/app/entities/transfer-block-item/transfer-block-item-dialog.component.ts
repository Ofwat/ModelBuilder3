import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { TransferBlockItem } from './transfer-block-item.model';
import { TransferBlockItemPopupService } from './transfer-block-item-popup.service';
import { TransferBlockItemService } from './transfer-block-item.service';
import { YearCode, YearCodeService } from '../year-code';
import { TransferBlock, TransferBlockService } from '../transfer-block';
@Component({
    selector: 'jhi-transfer-block-item-dialog',
    templateUrl: './transfer-block-item-dialog.component.html'
})
export class TransferBlockItemDialogComponent implements OnInit {

    transferBlockItem: TransferBlockItem;
    authorities: any[];
    isSaving: boolean;

    yearcodes: YearCode[];

    transferblocks: TransferBlock[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private transferBlockItemService: TransferBlockItemService,
        private yearCodeService: YearCodeService,
        private transferBlockService: TransferBlockService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.yearCodeService.query().subscribe(
            (res: Response) => { this.yearcodes = res.json(); }, (res: Response) => this.onError(res.json()));
        this.transferBlockService.query().subscribe(
            (res: Response) => { this.transferblocks = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.transferBlockItem.id !== undefined) {
            this.transferBlockItemService.update(this.transferBlockItem)
                .subscribe((res: TransferBlockItem) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.transferBlockItemService.create(this.transferBlockItem)
                .subscribe((res: TransferBlockItem) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: TransferBlockItem) {
        this.eventManager.broadcast({ name: 'transferBlockItemListModification', content: 'OK'});
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

    trackYearCodeById(index: number, item: YearCode) {
        return item.id;
    }

    trackTransferBlockById(index: number, item: TransferBlock) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-transfer-block-item-popup',
    template: ''
})
export class TransferBlockItemPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferBlockItemPopupService: TransferBlockItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.transferBlockItemPopupService
                    .open(TransferBlockItemDialogComponent, params['id']);
            } else {
                this.modalRef = this.transferBlockItemPopupService
                    .open(TransferBlockItemDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
