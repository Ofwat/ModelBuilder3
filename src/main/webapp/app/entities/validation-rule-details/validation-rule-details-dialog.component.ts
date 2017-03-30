import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ValidationRuleDetails } from './validation-rule-details.model';
import { ValidationRuleDetailsPopupService } from './validation-rule-details-popup.service';
import { ValidationRuleDetailsService } from './validation-rule-details.service';
@Component({
    selector: 'jhi-validation-rule-details-dialog',
    templateUrl: './validation-rule-details-dialog.component.html'
})
export class ValidationRuleDetailsDialogComponent implements OnInit {

    validationRuleDetails: ValidationRuleDetails;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private validationRuleDetailsService: ValidationRuleDetailsService,
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
        if (this.validationRuleDetails.id !== undefined) {
            this.validationRuleDetailsService.update(this.validationRuleDetails)
                .subscribe((res: ValidationRuleDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.validationRuleDetailsService.create(this.validationRuleDetails)
                .subscribe((res: ValidationRuleDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ValidationRuleDetails) {
        this.eventManager.broadcast({ name: 'validationRuleDetailsListModification', content: 'OK'});
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
    selector: 'jhi-validation-rule-details-popup',
    template: ''
})
export class ValidationRuleDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private validationRuleDetailsPopupService: ValidationRuleDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.validationRuleDetailsPopupService
                    .open(ValidationRuleDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.validationRuleDetailsPopupService
                    .open(ValidationRuleDetailsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
