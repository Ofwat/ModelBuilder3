import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ModelBuilderDocument } from './model-builder-document.model';
import { ModelBuilderDocumentPopupService } from './model-builder-document-popup.service';
import { ModelBuilderDocumentService } from './model-builder-document.service';

@Component({
    selector: 'jhi-model-builder-document-delete-dialog',
    templateUrl: './model-builder-document-delete-dialog.component.html'
})
export class ModelBuilderDocumentDeleteDialogComponent {

    modelBuilderDocument: ModelBuilderDocument;

    constructor(
        private modelBuilderDocumentService: ModelBuilderDocumentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.modelBuilderDocumentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'modelBuilderDocumentListModification',
                content: 'Deleted an modelBuilderDocument'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-model-builder-document-delete-popup',
    template: ''
})
export class ModelBuilderDocumentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private modelBuilderDocumentPopupService: ModelBuilderDocumentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.modelBuilderDocumentPopupService
                .open(ModelBuilderDocumentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
