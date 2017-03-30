import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Text } from './text.model';
import { TextPopupService } from './text-popup.service';
import { TextService } from './text.service';
import { TextBlock, TextBlockService } from '../text-block';
import { Model, ModelService } from '../model';
@Component({
    selector: 'jhi-text-dialog',
    templateUrl: './text-dialog.component.html'
})
export class TextDialogComponent implements OnInit {

    text: Text;
    authorities: any[];
    isSaving: boolean;

    textblocks: TextBlock[];

    models: Model[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private textService: TextService,
        private textBlockService: TextBlockService,
        private modelService: ModelService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.textBlockService.query().subscribe(
            (res: Response) => { this.textblocks = res.json(); }, (res: Response) => this.onError(res.json()));
        this.modelService.query().subscribe(
            (res: Response) => { this.models = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.text.id !== undefined) {
            this.textService.update(this.text)
                .subscribe((res: Text) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.textService.create(this.text)
                .subscribe((res: Text) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Text) {
        this.eventManager.broadcast({ name: 'textListModification', content: 'OK'});
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

    trackTextBlockById(index: number, item: TextBlock) {
        return item.id;
    }

    trackModelById(index: number, item: Model) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-text-popup',
    template: ''
})
export class TextPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private textPopupService: TextPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.textPopupService
                    .open(TextDialogComponent, params['id']);
            } else {
                this.modalRef = this.textPopupService
                    .open(TextDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
