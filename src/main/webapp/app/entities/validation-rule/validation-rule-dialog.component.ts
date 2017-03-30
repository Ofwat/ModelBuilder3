import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ValidationRule } from './validation-rule.model';
import { ValidationRulePopupService } from './validation-rule-popup.service';
import { ValidationRuleService } from './validation-rule.service';
import { ValidationRuleDetails, ValidationRuleDetailsService } from '../validation-rule-details';
import { ValidationRuleItem, ValidationRuleItemService } from '../validation-rule-item';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-validation-rule-dialog',
    templateUrl: './validation-rule-dialog.component.html'
})
export class ValidationRuleDialogComponent implements OnInit {

    validationRule: ValidationRule;
    authorities: any[];
    isSaving: boolean;

    validationruledetails: ValidationRuleDetails[];

    validationruleitems: ValidationRuleItem[];

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private validationRuleService: ValidationRuleService,
        private validationRuleDetailsService: ValidationRuleDetailsService,
        private validationRuleItemService: ValidationRuleItemService,
        private modelService: ModelService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.validationRuleDetailsService.query({filter: 'validationrule-is-null'}).subscribe((res: Response) => {
            if (!this.validationRule.validationRuleDetail || !this.validationRule.validationRuleDetail.id) {
                this.validationruledetails = res.json();
            } else {
                this.validationRuleDetailsService.find(this.validationRule.validationRuleDetail.id).subscribe((subRes: ValidationRuleDetails) => {
                    this.validationruledetails = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.validationRuleItemService.query().subscribe(
            (res: Response) => { this.validationruleitems = res.json(); }, (res: Response) => this.onError(res.json()));
        this.modelService.query().subscribe(
            (res: Response) => { this.models = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.validationRule.id !== undefined) {
            this.validationRuleService.update(this.validationRule)
                .subscribe((res: ValidationRule) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.validationRuleService.create(this.validationRule)
                .subscribe((res: ValidationRule) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ValidationRule) {
        this.eventManager.broadcast({ name: 'validationRuleListModification', content: 'OK'});
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

    trackValidationRuleDetailsById(index: number, item: ValidationRuleDetails) {
        return item.id;
    }

    trackValidationRuleItemById(index: number, item: ValidationRuleItem) {
        return item.id;
    }

    trackModelById(index: number, item: Model) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-validation-rule-popup',
    template: ''
})
export class ValidationRulePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private validationRulePopupService: ValidationRulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.validationRulePopupService
                    .open(ValidationRuleDialogComponent, params['id']);
            } else {
                this.modalRef = this.validationRulePopupService
                    .open(ValidationRuleDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
