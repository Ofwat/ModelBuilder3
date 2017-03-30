import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { SectionDetails } from './section-details.model';
import { SectionDetailsPopupService } from './section-details-popup.service';
import { SectionDetailsService } from './section-details.service';
@Component({
    selector: 'jhi-section-details-dialog',
    templateUrl: './section-details-dialog.component.html'
})
export class SectionDetailsDialogComponent implements OnInit {

    sectionDetails: SectionDetails;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private sectionDetailsService: SectionDetailsService,
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
        if (this.sectionDetails.id !== undefined) {
            this.sectionDetailsService.update(this.sectionDetails)
                .subscribe((res: SectionDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.sectionDetailsService.create(this.sectionDetails)
                .subscribe((res: SectionDetails) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: SectionDetails) {
        this.eventManager.broadcast({ name: 'sectionDetailsListModification', content: 'OK'});
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
    selector: 'jhi-section-details-popup',
    template: ''
})
export class SectionDetailsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private sectionDetailsPopupService: SectionDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.sectionDetailsPopupService
                    .open(SectionDetailsDialogComponent, params['id']);
            } else {
                this.modalRef = this.sectionDetailsPopupService
                    .open(SectionDetailsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
