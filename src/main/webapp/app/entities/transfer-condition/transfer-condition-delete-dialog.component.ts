import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { TransferCondition } from './transfer-condition.model';
import { TransferConditionPopupService } from './transfer-condition-popup.service';
import { TransferConditionService } from './transfer-condition.service';

@Component({
    selector: 'jhi-transfer-condition-delete-dialog',
    templateUrl: './transfer-condition-delete-dialog.component.html'
})
export class TransferConditionDeleteDialogComponent {

    transferCondition: TransferCondition;

    constructor(
        private transferConditionService: TransferConditionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.transferConditionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'transferConditionListModification',
                content: 'Deleted an transferCondition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transfer-condition-delete-popup',
    template: ''
})
export class TransferConditionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferConditionPopupService: TransferConditionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.transferConditionPopupService
                .open(TransferConditionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
