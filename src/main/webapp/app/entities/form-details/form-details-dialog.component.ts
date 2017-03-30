import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FormDetails } from './form-details.model';
import { FormDetailsPopupService } from './form-details-popup.service';
import { FormDetailsService } from './form-details.service';
@Component({
    selector: 'jhi-form-details-dialog',
    templateUrl: './form-details-dialog.component.html'
})
export class FormDetailsDialogComponent implements OnInit {

    formDetails: FormDetails;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private formDetailsService: FormDetailsService,
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
        if (this.formDetails.id !== undefined) {
            this.formDetailsService.update(this.formDetails)
                .subscribe((res: FormDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.formDetailsService.create(this.formDetails)
                .subscribe((res: FormDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: FormDetails) {
        this.eventManager.broadcast({ name: 'formDetailsListModification', content: 'OK'});
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
    selector: 'jhi-form-details-popup',
    template: ''
})
export class FormDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private formDetailsPopupService: FormDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.formDetailsPopupService
                    .open(FormDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.formDetailsPopupService
                    .open(FormDetailsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
