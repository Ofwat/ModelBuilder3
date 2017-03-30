import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { CellRange } from './cell-range.model';
import { CellRangePopupService } from './cell-range-popup.service';
import { CellRangeService } from './cell-range.service';
@Component({
    selector: 'jhi-cell-range-dialog',
    templateUrl: './cell-range-dialog.component.html'
})
export class CellRangeDialogComponent implements OnInit {

    cellRange: CellRange;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private cellRangeService: CellRangeService,
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
        if (this.cellRange.id !== undefined) {
            this.cellRangeService.update(this.cellRange)
                .subscribe((res: CellRange) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.cellRangeService.create(this.cellRange)
                .subscribe((res: CellRange) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: CellRange) {
        this.eventManager.broadcast({ name: 'cellRangeListModification', content: 'OK'});
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
    selector: 'jhi-cell-range-popup',
    template: ''
})
export class CellRangePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private cellRangePopupService: CellRangePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.cellRangePopupService
                    .open(CellRangeDialogComponent, params['id']);
            } else {
                this.modalRef = this.cellRangePopupService
                    .open(CellRangeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
