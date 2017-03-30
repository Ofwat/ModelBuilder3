import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ValidationRuleDetails } from './validation-rule-details.model';
import { ValidationRuleDetailsPopupService } from './validation-rule-details-popup.service';
import { ValidationRuleDetailsService } from './validation-rule-details.service';

@Component({
    selector: 'jhi-validation-rule-details-delete-dialog',
    templateUrl: './validation-rule-details-delete-dialog.component.html'
})
export class ValidationRuleDetailsDeleteDialogComponent {

    validationRuleDetails: ValidationRuleDetails;

    constructor(
        private validationRuleDetailsService: ValidationRuleDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.validationRuleDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'validationRuleDetailsListModification',
                content: 'Deleted an validationRuleDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-validation-rule-details-delete-popup',
    template: ''
})
export class ValidationRuleDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private validationRuleDetailsPopupService: ValidationRuleDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.validationRuleDetailsPopupService
                .open(ValidationRuleDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
