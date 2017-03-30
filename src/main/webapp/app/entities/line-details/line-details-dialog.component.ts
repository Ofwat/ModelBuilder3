import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { LineDetails } from './line-details.model';
import { LineDetailsPopupService } from './line-details-popup.service';
import { LineDetailsService } from './line-details.service';
@Component({
    selector: 'jhi-line-details-dialog',
    templateUrl: './line-details-dialog.component.html'
})
export class LineDetailsDialogComponent implements OnInit {

    lineDetails: LineDetails;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private lineDetailsService: LineDetailsService,
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
        if (this.lineDetails.id !== undefined) {
            this.lineDetailsService.update(this.lineDetails)
                .subscribe((res: LineDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.lineDetailsService.create(this.lineDetails)
                .subscribe((res: LineDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: LineDetails) {
        this.eventManager.broadcast({ name: 'lineDetailsListModification', content: 'OK'});
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
    selector: 'jhi-line-details-popup',
    template: ''
})
export class LineDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private lineDetailsPopupService: LineDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.lineDetailsPopupService
                    .open(LineDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.lineDetailsPopupService
                    .open(LineDetailsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
