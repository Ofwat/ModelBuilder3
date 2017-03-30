import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ValidationRuleItem } from './validation-rule-item.model';
import { ValidationRuleItemPopupService } from './validation-rule-item-popup.service';
import { ValidationRuleItemService } from './validation-rule-item.service';

@Component({
    selector: 'jhi-validation-rule-item-delete-dialog',
    templateUrl: './validation-rule-item-delete-dialog.component.html'
})
export class ValidationRuleItemDeleteDialogComponent {

    validationRuleItem: ValidationRuleItem;

    constructor(
        private validationRuleItemService: ValidationRuleItemService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.validationRuleItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'validationRuleItemListModification',
                content: 'Deleted an validationRuleItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-validation-rule-item-delete-popup',
    template: ''
})
export class ValidationRuleItemDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private validationRuleItemPopupService: ValidationRuleItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.validationRuleItemPopupService
                .open(ValidationRuleItemDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
