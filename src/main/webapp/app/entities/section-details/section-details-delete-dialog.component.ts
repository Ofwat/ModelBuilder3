import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { SectionDetails } from './section-details.model';
import { SectionDetailsPopupService } from './section-details-popup.service';
import { SectionDetailsService } from './section-details.service';

@Component({
    selector: 'jhi-section-details-delete-dialog',
    templateUrl: './section-details-delete-dialog.component.html'
})
export class SectionDetailsDeleteDialogComponent {

    sectionDetails: SectionDetails;

    constructor(
        private sectionDetailsService: SectionDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.sectionDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sectionDetailsListModification',
                content: 'Deleted an sectionDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-section-details-delete-popup',
    template: ''
})
export class SectionDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private sectionDetailsPopupService: SectionDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.sectionDetailsPopupService
                .open(SectionDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
