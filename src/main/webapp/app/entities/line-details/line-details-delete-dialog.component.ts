import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { LineDetails } from './line-details.model';
import { LineDetailsPopupService } from './line-details-popup.service';
import { LineDetailsService } from './line-details.service';

@Component({
    selector: 'jhi-line-details-delete-dialog',
    templateUrl: './line-details-delete-dialog.component.html'
})
export class LineDetailsDeleteDialogComponent {

    lineDetails: LineDetails;

    constructor(
        private lineDetailsService: LineDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.lineDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lineDetailsListModification',
                content: 'Deleted an lineDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-line-details-delete-popup',
    template: ''
})
export class LineDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private lineDetailsPopupService: LineDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.lineDetailsPopupService
                .open(LineDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
