import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Input } from './input.model';
import { InputPopupService } from './input-popup.service';
import { InputService } from './input.service';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-input-dialog',
    templateUrl: './input-dialog.component.html'
})
export class InputDialogComponent implements OnInit {

    input: Input;
    authorities: any[];
    isSaving: boolean;

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private inputService: InputService,
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
        if (this.input.id !== undefined) {
            this.inputService.update(this.input)
                .subscribe((res: Input) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.inputService.create(this.input)
                .subscribe((res: Input) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Input) {
        this.eventManager.broadcast({ name: 'inputListModification', content: 'OK'});
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
    selector: 'jhi-input-popup',
    template: ''
})
export class InputPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private inputPopupService: InputPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.inputPopupService
                    .open(InputDialogComponent, params['id']);
            } else {
                this.modalRef = this.inputPopupService
                    .open(InputDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
