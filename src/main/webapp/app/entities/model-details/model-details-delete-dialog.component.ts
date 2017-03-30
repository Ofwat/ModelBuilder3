import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { ModelDetails } from './model-details.model';
import { ModelDetailsPopupService } from './model-details-popup.service';
import { ModelDetailsService } from './model-details.service';

@Component({
    selector: 'jhi-model-details-delete-dialog',
    templateUrl: './model-details-delete-dialog.component.html'
})
export class ModelDetailsDeleteDialogComponent {

    modelDetails: ModelDetails;

    constructor(
        private modelDetailsService: ModelDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.modelDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'modelDetailsListModification',
                content: 'Deleted an modelDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-model-details-delete-popup',
    template: ''
})
export class ModelDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private modelDetailsPopupService: ModelDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.modelDetailsPopupService
                .open(ModelDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
