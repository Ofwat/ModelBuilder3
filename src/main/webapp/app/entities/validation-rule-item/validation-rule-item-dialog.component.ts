import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ValidationRuleItem } from './validation-rule-item.model';
import { ValidationRuleItemPopupService } from './validation-rule-item-popup.service';
import { ValidationRuleItemService } from './validation-rule-item.service';
import { ValidationRule, ValidationRuleService } from '../validation-rule';
@Component({
    selector: 'jhi-validation-rule-item-dialog',
    templateUrl: './validation-rule-item-dialog.component.html'
})
export class ValidationRuleItemDialogComponent implements OnInit {

    validationRuleItem: ValidationRuleItem;
    authorities: any[];
    isSaving: boolean;

    validationrules: ValidationRule[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private validationRuleItemService: ValidationRuleItemService,
        private validationRuleService: ValidationRuleService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.validationRuleService.query().subscribe(
            (res: Response) => { this.validationrules = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.validationRuleItem.id !== undefined) {
            this.validationRuleItemService.update(this.validationRuleItem)
                .subscribe((res: ValidationRuleItem) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.validationRuleItemService.create(this.validationRuleItem)
                .subscribe((res: ValidationRuleItem) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ValidationRuleItem) {
        this.eventManager.broadcast({ name: 'validationRuleItemListModification', content: 'OK'});
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

    trackValidationRuleById(index: number, item: ValidationRule) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-validation-rule-item-popup',
    template: ''
})
export class ValidationRuleItemPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private validationRuleItemPopupService: ValidationRuleItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.validationRuleItemPopupService
                    .open(ValidationRuleItemDialogComponent, params['id']);
            } else {
                this.modalRef = this.validationRuleItemPopupService
                    .open(ValidationRuleItemDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
