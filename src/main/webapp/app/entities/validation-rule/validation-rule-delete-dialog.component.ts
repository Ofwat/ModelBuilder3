import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ValidationRule } from './validation-rule.model';
import { ValidationRulePopupService } from './validation-rule-popup.service';
import { ValidationRuleService } from './validation-rule.service';

@Component({
    selector: 'jhi-validation-rule-delete-dialog',
    templateUrl: './validation-rule-delete-dialog.component.html'
})
export class ValidationRuleDeleteDialogComponent {

    validationRule: ValidationRule;

    constructor(
        private validationRuleService: ValidationRuleService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.validationRuleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'validationRuleListModification',
                content: 'Deleted an validationRule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-validation-rule-delete-popup',
    template: ''
})
export class ValidationRuleDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private validationRulePopupService: ValidationRulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.validationRulePopupService
                .open(ValidationRuleDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
