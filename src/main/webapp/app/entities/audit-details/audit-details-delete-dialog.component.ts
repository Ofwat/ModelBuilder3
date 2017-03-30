import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AuditDetails } from './audit-details.model';
import { AuditDetailsPopupService } from './audit-details-popup.service';
import { AuditDetailsService } from './audit-details.service';

@Component({
    selector: 'jhi-audit-details-delete-dialog',
    templateUrl: './audit-details-delete-dialog.component.html'
})
export class AuditDetailsDeleteDialogComponent {

    auditDetails: AuditDetails;

    constructor(
        private auditDetailsService: AuditDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.auditDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'auditDetailsListModification',
                content: 'Deleted an auditDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-audit-details-delete-popup',
    template: ''
})
export class AuditDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private auditDetailsPopupService: AuditDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.auditDetailsPopupService
                .open(AuditDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
