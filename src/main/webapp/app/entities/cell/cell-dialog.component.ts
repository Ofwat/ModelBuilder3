import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Cell } from './cell.model';
import { CellPopupService } from './cell-popup.service';
import { CellService } from './cell.service';
import { Line, LineService } from '../line';
@Component({
    selector: 'jhi-cell-dialog',
    templateUrl: './cell-dialog.component.html'
})
export class CellDialogComponent implements OnInit {

    cell: Cell;
    authorities: any[];
    isSaving: boolean;

    lines: Line[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private cellService: CellService,
        private lineService: LineService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.lineService.query().subscribe(
            (res: Response) => { this.lines = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.cell.id !== undefined) {
            this.cellService.update(this.cell)
                .subscribe((res: Cell) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.cellService.create(this.cell)
                .subscribe((res: Cell) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Cell) {
        this.eventManager.broadcast({ name: 'cellListModification', content: 'OK'});
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

    trackLineById(index: number, item: Line) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cell-popup',
    template: ''
})
export class CellPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private cellPopupService: CellPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.cellPopupService
                    .open(CellDialogComponent, params['id']);
            } else {
                this.modalRef = this.cellPopupService
                    .open(CellDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
