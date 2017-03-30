import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FormHeadingCell } from './form-heading-cell.model';
import { FormHeadingCellPopupService } from './form-heading-cell-popup.service';
import { FormHeadingCellService } from './form-heading-cell.service';
import { Form, FormService } from '../form';
@Component({
    selector: 'jhi-form-heading-cell-dialog',
    templateUrl: './form-heading-cell-dialog.component.html'
})
export class FormHeadingCellDialogComponent implements OnInit {

    formHeadingCell: FormHeadingCell;
    authorities: any[];
    isSaving: boolean;

    forms: Form[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private formHeadingCellService: FormHeadingCellService,
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
        if (this.formHeadingCell.id !== undefined) {
            this.formHeadingCellService.update(this.formHeadingCell)
                .subscribe((res: FormHeadingCell) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.formHeadingCellService.create(this.formHeadingCell)
                .subscribe((res: FormHeadingCell) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: FormHeadingCell) {
        this.eventManager.broadcast({ name: 'formHeadingCellListModification', content: 'OK'});
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
    selector: 'jhi-form-heading-cell-popup',
    template: ''
})
export class FormHeadingCellPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private formHeadingCellPopupService: FormHeadingCellPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.formHeadingCellPopupService
                    .open(FormHeadingCellDialogComponent, params['id']);
            } else {
                this.modalRef = this.formHeadingCellPopupService
                    .open(FormHeadingCellDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
