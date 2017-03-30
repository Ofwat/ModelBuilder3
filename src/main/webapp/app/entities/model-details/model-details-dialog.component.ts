import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ModelDetails } from './model-details.model';
import { ModelDetailsPopupService } from './model-details-popup.service';
import { ModelDetailsService } from './model-details.service';
@Component({
    selector: 'jhi-model-details-dialog',
    templateUrl: './model-details-dialog.component.html'
})
export class ModelDetailsDialogComponent implements OnInit {

    modelDetails: ModelDetails;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private modelDetailsService: ModelDetailsService,
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
        if (this.modelDetails.id !== undefined) {
            this.modelDetailsService.update(this.modelDetails)
                .subscribe((res: ModelDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.modelDetailsService.create(this.modelDetails)
                .subscribe((res: ModelDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ModelDetails) {
        this.eventManager.broadcast({ name: 'modelDetailsListModification', content: 'OK'});
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
    selector: 'jhi-model-details-popup',
    template: ''
})
export class ModelDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private modelDetailsPopupService: ModelDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.modelDetailsPopupService
                    .open(ModelDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.modelDetailsPopupService
                    .open(ModelDetailsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
