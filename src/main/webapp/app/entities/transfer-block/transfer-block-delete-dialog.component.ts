import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { TransferBlock } from './transfer-block.model';
import { TransferBlockPopupService } from './transfer-block-popup.service';
import { TransferBlockService } from './transfer-block.service';

@Component({
    selector: 'jhi-transfer-block-delete-dialog',
    templateUrl: './transfer-block-delete-dialog.component.html'
})
export class TransferBlockDeleteDialogComponent {

    transferBlock: TransferBlock;

    constructor(
        private transferBlockService: TransferBlockService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.transferBlockService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'transferBlockListModification',
                content: 'Deleted an transferBlock'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transfer-block-delete-popup',
    template: ''
})
export class TransferBlockDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferBlockPopupService: TransferBlockPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.transferBlockPopupService
                .open(TransferBlockDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
