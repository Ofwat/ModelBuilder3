import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ModelAudit } from './model-audit.model';
import { ModelAuditPopupService } from './model-audit-popup.service';
import { ModelAuditService } from './model-audit.service';

@Component({
    selector: 'jhi-model-audit-delete-dialog',
    templateUrl: './model-audit-delete-dialog.component.html'
})
export class ModelAuditDeleteDialogComponent {

    modelAudit: ModelAudit;

    constructor(
        private modelAuditService: ModelAuditService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.modelAuditService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'modelAuditListModification',
                content: 'Deleted an modelAudit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-model-audit-delete-popup',
    template: ''
})
export class ModelAuditDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private modelAuditPopupService: ModelAuditPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.modelAuditPopupService
                .open(ModelAuditDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
