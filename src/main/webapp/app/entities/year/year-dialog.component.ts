import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Year } from './year.model';
import { YearPopupService } from './year-popup.service';
import { YearService } from './year.service';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-year-dialog',
    templateUrl: './year-dialog.component.html'
})
export class YearDialogComponent implements OnInit {

    year: Year;
    authorities: any[];
    isSaving: boolean;

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private yearService: YearService,
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
        if (this.year.id !== undefined) {
            this.yearService.update(this.year)
                .subscribe((res: Year) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.yearService.create(this.year)
                .subscribe((res: Year) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Year) {
        this.eventManager.broadcast({ name: 'yearListModification', content: 'OK'});
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
    selector: 'jhi-year-popup',
    template: ''
})
export class YearPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private yearPopupService: YearPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.yearPopupService
                    .open(YearDialogComponent, params['id']);
            } else {
                this.modalRef = this.yearPopupService
                    .open(YearDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
