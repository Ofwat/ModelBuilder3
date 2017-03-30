import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { CellRange } from './cell-range.model';
import { CellRangePopupService } from './cell-range-popup.service';
import { CellRangeService } from './cell-range.service';

@Component({
    selector: 'jhi-cell-range-delete-dialog',
    templateUrl: './cell-range-delete-dialog.component.html'
})
export class CellRangeDeleteDialogComponent {

    cellRange: CellRange;

    constructor(
        private cellRangeService: CellRangeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.cellRangeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cellRangeListModification',
                content: 'Deleted an cellRange'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cell-range-delete-popup',
    template: ''
})
export class CellRangeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private cellRangePopupService: CellRangePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.cellRangePopupService
                .open(CellRangeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
