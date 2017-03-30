import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FormCell } from './form-cell.model';
import { FormCellPopupService } from './form-cell-popup.service';
import { FormCellService } from './form-cell.service';

@Component({
    selector: 'jhi-form-cell-delete-dialog',
    templateUrl: './form-cell-delete-dialog.component.html'
})
export class FormCellDeleteDialogComponent {

    formCell: FormCell;

    constructor(
        private formCellService: FormCellService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.formCellService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'formCellListModification',
                content: 'Deleted an formCell'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-form-cell-delete-popup',
    template: ''
})
export class FormCellDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private formCellPopupService: FormCellPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.formCellPopupService
                .open(FormCellDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
