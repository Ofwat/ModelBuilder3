import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { TransferBlockDetails } from './transfer-block-details.model';
import { TransferBlockDetailsPopupService } from './transfer-block-details-popup.service';
import { TransferBlockDetailsService } from './transfer-block-details.service';

@Component({
    selector: 'jhi-transfer-block-details-delete-dialog',
    templateUrl: './transfer-block-details-delete-dialog.component.html'
})
export class TransferBlockDetailsDeleteDialogComponent {

    transferBlockDetails: TransferBlockDetails;

    constructor(
        private transferBlockDetailsService: TransferBlockDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.transferBlockDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'transferBlockDetailsListModification',
                content: 'Deleted an transferBlockDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transfer-block-details-delete-popup',
    template: ''
})
export class TransferBlockDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferBlockDetailsPopupService: TransferBlockDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.transferBlockDetailsPopupService
                .open(TransferBlockDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
