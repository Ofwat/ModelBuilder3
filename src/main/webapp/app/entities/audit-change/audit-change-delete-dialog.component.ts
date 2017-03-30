import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AuditChange } from './audit-change.model';
import { AuditChangePopupService } from './audit-change-popup.service';
import { AuditChangeService } from './audit-change.service';

@Component({
    selector: 'jhi-audit-change-delete-dialog',
    templateUrl: './audit-change-delete-dialog.component.html'
})
export class AuditChangeDeleteDialogComponent {

    auditChange: AuditChange;

    constructor(
        private auditChangeService: AuditChangeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.auditChangeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'auditChangeListModification',
                content: 'Deleted an auditChange'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-audit-change-delete-popup',
    template: ''
})
export class AuditChangeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private auditChangePopupService: AuditChangePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.auditChangePopupService
                .open(AuditChangeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
