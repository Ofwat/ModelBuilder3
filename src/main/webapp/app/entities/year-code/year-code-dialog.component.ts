import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { YearCode } from './year-code.model';
import { YearCodePopupService } from './year-code-popup.service';
import { YearCodeService } from './year-code.service';
import { TransferBlockItem, TransferBlockItemService } from '../transfer-block-item';
@Component({
    selector: 'jhi-year-code-dialog',
    templateUrl: './year-code-dialog.component.html'
})
export class YearCodeDialogComponent implements OnInit {

    yearCode: YearCode;
    authorities: any[];
    isSaving: boolean;

    transferblockitems: TransferBlockItem[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private yearCodeService: YearCodeService,
        private transferBlockItemService: TransferBlockItemService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.transferBlockItemService.query().subscribe(
            (res: Response) => { this.transferblockitems = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.yearCode.id !== undefined) {
            this.yearCodeService.update(this.yearCode)
                .subscribe((res: YearCode) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.yearCodeService.create(this.yearCode)
                .subscribe((res: YearCode) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: YearCode) {
        this.eventManager.broadcast({ name: 'yearCodeListModification', content: 'OK'});
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

    trackTransferBlockItemById(index: number, item: TransferBlockItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-year-code-popup',
    template: ''
})
export class YearCodePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private yearCodePopupService: YearCodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.yearCodePopupService
                    .open(YearCodeDialogComponent, params['id']);
            } else {
                this.modalRef = this.yearCodePopupService
                    .open(YearCodeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
