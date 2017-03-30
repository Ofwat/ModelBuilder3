import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AuditDetails } from './audit-details.model';
import { AuditDetailsPopupService } from './audit-details-popup.service';
import { AuditDetailsService } from './audit-details.service';
@Component({
    selector: 'jhi-audit-details-dialog',
    templateUrl: './audit-details-dialog.component.html'
})
export class AuditDetailsDialogComponent implements OnInit {

    auditDetails: AuditDetails;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private auditDetailsService: AuditDetailsService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.auditDetails.id !== undefined) {
            this.auditDetailsService.update(this.auditDetails)
                .subscribe((res: AuditDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.auditDetailsService.create(this.auditDetails)
                .subscribe((res: AuditDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: AuditDetails) {
        this.eventManager.broadcast({ name: 'auditDetailsListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-audit-details-popup',
    template: ''
})
export class AuditDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private auditDetailsPopupService: AuditDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.auditDetailsPopupService
                    .open(AuditDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.auditDetailsPopupService
                    .open(AuditDetailsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
