import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { TextBlock } from './text-block.model';
import { TextBlockPopupService } from './text-block-popup.service';
import { TextBlockService } from './text-block.service';
import { Text, TextService } from '../text';
@Component({
    selector: 'jhi-text-block-dialog',
    templateUrl: './text-block-dialog.component.html'
})
export class TextBlockDialogComponent implements OnInit {

    textBlock: TextBlock;
    authorities: any[];
    isSaving: boolean;

    texts: Text[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private textBlockService: TextBlockService,
        private textService: TextService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.textService.query().subscribe(
            (res: Response) => { this.texts = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.textBlock.id !== undefined) {
            this.textBlockService.update(this.textBlock)
                .subscribe((res: TextBlock) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.textBlockService.create(this.textBlock)
                .subscribe((res: TextBlock) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: TextBlock) {
        this.eventManager.broadcast({ name: 'textBlockListModification', content: 'OK'});
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

    trackTextById(index: number, item: Text) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-text-block-popup',
    template: ''
})
export class TextBlockPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private textBlockPopupService: TextBlockPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.textBlockPopupService
                    .open(TextBlockDialogComponent, params['id']);
            } else {
                this.modalRef = this.textBlockPopupService
                    .open(TextBlockDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
