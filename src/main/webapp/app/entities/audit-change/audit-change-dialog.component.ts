import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AuditChange } from './audit-change.model';
import { AuditChangePopupService } from './audit-change-popup.service';
import { AuditChangeService } from './audit-change.service';
import { ModelAudit, ModelAuditService } from '../model-audit';
@Component({
    selector: 'jhi-audit-change-dialog',
    templateUrl: './audit-change-dialog.component.html'
})
export class AuditChangeDialogComponent implements OnInit {

    auditChange: AuditChange;
    authorities: any[];
    isSaving: boolean;

    modelaudits: ModelAudit[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private auditChangeService: AuditChangeService,
        private modelAuditService: ModelAuditService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.modelAuditService.query().subscribe(
            (res: Response) => { this.modelaudits = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.auditChange.id !== undefined) {
            this.auditChangeService.update(this.auditChange)
                .subscribe((res: AuditChange) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.auditChangeService.create(this.auditChange)
                .subscribe((res: AuditChange) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: AuditChange) {
        this.eventManager.broadcast({ name: 'auditChangeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackModelAuditById(index: number, item: ModelAudit) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-audit-change-popup',
    template: ''
})
export class AuditChangePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private auditChangePopupService: AuditChangePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.auditChangePopupService
                    .open(AuditChangeDialogComponent, params['id']);
            } else {
                this.modalRef = this.auditChangePopupService
                    .open(AuditChangeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
