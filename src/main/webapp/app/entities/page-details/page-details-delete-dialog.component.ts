import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PageDetails } from './page-details.model';
import { PageDetailsPopupService } from './page-details-popup.service';
import { PageDetailsService } from './page-details.service';

@Component({
    selector: 'jhi-page-details-delete-dialog',
    templateUrl: './page-details-delete-dialog.component.html'
})
export class PageDetailsDeleteDialogComponent {

    pageDetails: PageDetails;

    constructor(
        private pageDetailsService: PageDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.pageDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pageDetailsListModification',
                content: 'Deleted an pageDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-page-details-delete-popup',
    template: ''
})
export class PageDetailsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private pageDetailsPopupService: PageDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.pageDetailsPopupService
                .open(PageDetailsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
