import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FormDetails } from './form-details.model';
import { FormDetailsPopupService } from './form-details-popup.service';
import { FormDetailsService } from './form-details.service';

@Component({
    selector: 'jhi-form-details-delete-dialog',
    templateUrl: './form-details-delete-dialog.component.html'
})
export class FormDetailsDeleteDialogComponent {

    formDetails: FormDetails;

    constructor(
        private formDetailsService: FormDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.formDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'formDetailsListModification',
                content: 'Deleted an formDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-form-details-delete-popup',
    template: ''
})
export class FormDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private formDetailsPopupService: FormDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.formDetailsPopupService
                .open(FormDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
