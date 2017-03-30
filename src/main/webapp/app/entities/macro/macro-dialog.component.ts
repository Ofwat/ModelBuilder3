import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Macro } from './macro.model';
import { MacroPopupService } from './macro-popup.service';
import { MacroService } from './macro.service';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-macro-dialog',
    templateUrl: './macro-dialog.component.html'
})
export class MacroDialogComponent implements OnInit {

    macro: Macro;
    authorities: any[];
    isSaving: boolean;

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private macroService: MacroService,
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
        if (this.macro.id !== undefined) {
            this.macroService.update(this.macro)
                .subscribe((res: Macro) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.macroService.create(this.macro)
                .subscribe((res: Macro) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Macro) {
        this.eventManager.broadcast({ name: 'macroListModification', content: 'OK'});
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
    selector: 'jhi-macro-popup',
    template: ''
})
export class MacroPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private macroPopupService: MacroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.macroPopupService
                    .open(MacroDialogComponent, params['id']);
            } else {
                this.modalRef = this.macroPopupService
                    .open(MacroDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
