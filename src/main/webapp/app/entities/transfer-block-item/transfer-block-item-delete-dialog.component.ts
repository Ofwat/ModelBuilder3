import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { TransferBlockItem } from './transfer-block-item.model';
import { TransferBlockItemPopupService } from './transfer-block-item-popup.service';
import { TransferBlockItemService } from './transfer-block-item.service';

@Component({
    selector: 'jhi-transfer-block-item-delete-dialog',
    templateUrl: './transfer-block-item-delete-dialog.component.html'
})
export class TransferBlockItemDeleteDialogComponent {

    transferBlockItem: TransferBlockItem;

    constructor(
        private transferBlockItemService: TransferBlockItemService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.transferBlockItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'transferBlockItemListModification',
                content: 'Deleted an transferBlockItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transfer-block-item-delete-popup',
    template: ''
})
export class TransferBlockItemDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private transferBlockItemPopupService: TransferBlockItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.transferBlockItemPopupService
                .open(TransferBlockItemDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
