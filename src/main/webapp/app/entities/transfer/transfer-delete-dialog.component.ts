import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Transfer } from './transfer.model';
import { TransferPopupService } from './transfer-popup.service';
import { TransferService } from './transfer.service';

@Component({
    selector: 'jhi-transfer-delete-dialog',
    templateUrl: './transfer-delete-dialog.component.html'
})
export class TransferDeleteDialogComponent {

    transfer: Transfer;

    constructor(
        private transferService: TransferService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.transferService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'transferListModification',
                content: 'Deleted an transfer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transfer-delete-popup',
    template: ''
})
export class TransferDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferPopupService: TransferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.transferPopupService
                .open(TransferDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
