import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Heading } from './heading.model';
import { HeadingPopupService } from './heading-popup.service';
import { HeadingService } from './heading.service';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-heading-dialog',
    templateUrl: './heading-dialog.component.html'
})
export class HeadingDialogComponent implements OnInit {

    heading: Heading;
    authorities: any[];
    isSaving: boolean;

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private headingService: HeadingService,
        private modelService: ModelService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.modelService.query().subscribe(
            (res: Response) => { this.models = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.heading.id !== undefined) {
            this.headingService.update(this.heading)
                .subscribe((res: Heading) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.headingService.create(this.heading)
                .subscribe((res: Heading) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Heading) {
        this.eventManager.broadcast({ name: 'headingListModification', content: 'OK'});
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

    trackModelById(index: number, item: Model) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-heading-popup',
    template: ''
})
export class HeadingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private headingPopupService: HeadingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.headingPopupService
                    .open(HeadingDialogComponent, params['id']);
            } else {
                this.modalRef = this.headingPopupService
                    .open(HeadingDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
