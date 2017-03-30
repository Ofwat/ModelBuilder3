import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Cell } from './cell.model';
import { CellPopupService } from './cell-popup.service';
import { CellService } from './cell.service';

@Component({
    selector: 'jhi-cell-delete-dialog',
    templateUrl: './cell-delete-dialog.component.html'
})
export class CellDeleteDialogComponent {

    cell: Cell;

    constructor(
        private cellService: CellService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.cellService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cellListModification',
                content: 'Deleted an cell'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cell-delete-popup',
    template: ''
})
export class CellDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private cellPopupService: CellPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.cellPopupService
                .open(CellDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
