import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FormCell } from './form-cell.model';
import { FormCellPopupService } from './form-cell-popup.service';
import { FormCellService } from './form-cell.service';
import { Form, FormService } from '../form';
@Component({
    selector: 'jhi-form-cell-dialog',
    templateUrl: './form-cell-dialog.component.html'
})
export class FormCellDialogComponent implements OnInit {

    formCell: FormCell;
    authorities: any[];
    isSaving: boolean;

    forms: Form[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private formCellService: FormCellService,
        private formService: FormService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.formService.query().subscribe(
            (res: Response) => { this.forms = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.formCell.id !== undefined) {
            this.formCellService.update(this.formCell)
                .subscribe((res: FormCell) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.formCellService.create(this.formCell)
                .subscribe((res: FormCell) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: FormCell) {
        this.eventManager.broadcast({ name: 'formCellListModification', content: 'OK'});
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

    trackFormById(index: number, item: Form) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-form-cell-popup',
    template: ''
})
export class FormCellPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private formCellPopupService: FormCellPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.formCellPopupService
                    .open(FormCellDialogComponent, params['id']);
            } else {
                this.modalRef = this.formCellPopupService
                    .open(FormCellDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
