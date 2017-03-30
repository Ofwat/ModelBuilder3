import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FormHeadingCell } from './form-heading-cell.model';
import { FormHeadingCellPopupService } from './form-heading-cell-popup.service';
import { FormHeadingCellService } from './form-heading-cell.service';

@Component({
    selector: 'jhi-form-heading-cell-delete-dialog',
    templateUrl: './form-heading-cell-delete-dialog.component.html'
})
export class FormHeadingCellDeleteDialogComponent {

    formHeadingCell: FormHeadingCell;

    constructor(
        private formHeadingCellService: FormHeadingCellService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.formHeadingCellService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'formHeadingCellListModification',
                content: 'Deleted an formHeadingCell'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-form-heading-cell-delete-popup',
    template: ''
})
export class FormHeadingCellDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private formHeadingCellPopupService: FormHeadingCellPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.formHeadingCellPopupService
                .open(FormHeadingCellDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
