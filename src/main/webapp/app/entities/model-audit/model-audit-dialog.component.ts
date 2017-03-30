import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ModelAudit } from './model-audit.model';
import { ModelAuditPopupService } from './model-audit-popup.service';
import { ModelAuditService } from './model-audit.service';
import { AuditDetails, AuditDetailsService } from '../audit-details';
import { AuditChange, AuditChangeService } from '../audit-change';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-model-audit-dialog',
    templateUrl: './model-audit-dialog.component.html'
})
export class ModelAuditDialogComponent implements OnInit {

    modelAudit: ModelAudit;
    authorities: any[];
    isSaving: boolean;

    modelauditdetails: AuditDetails[];

    auditchanges: AuditChange[];

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private modelAuditService: ModelAuditService,
        private auditDetailsService: AuditDetailsService,
        private auditChangeService: AuditChangeService,
        private modelService: ModelService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.auditDetailsService.query({filter: 'modelaudit-is-null'}).subscribe((res: Response) => {
            if (!this.modelAudit.modelAuditDetail || !this.modelAudit.modelAuditDetail.id) {
                this.modelauditdetails = res.json();
            } else {
                this.auditDetailsService.find(this.modelAudit.modelAuditDetail.id).subscribe((subRes: AuditDetails) => {
                    this.modelauditdetails = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.auditChangeService.query().subscribe(
            (res: Response) => { this.auditchanges = res.json(); }, (res: Response) => this.onError(res.json()));
        this.modelService.query().subscribe(
            (res: Response) => { this.models = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.modelAudit.id !== undefined) {
            this.modelAuditService.update(this.modelAudit)
                .subscribe((res: ModelAudit) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.modelAuditService.create(this.modelAudit)
                .subscribe((res: ModelAudit) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ModelAudit) {
        this.eventManager.broadcast({ name: 'modelAuditListModification', content: 'OK'});
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

    trackAuditDetailsById(index: number, item: AuditDetails) {
        return item.id;
    }

    trackAuditChangeById(index: number, item: AuditChange) {
        return item.id;
    }

    trackModelById(index: number, item: Model) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-model-audit-popup',
    template: ''
})
export class ModelAuditPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private modelAuditPopupService: ModelAuditPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.modelAuditPopupService
                    .open(ModelAuditDialogComponent, params['id']);
            } else {
                this.modalRef = this.modelAuditPopupService
                    .open(ModelAuditDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
