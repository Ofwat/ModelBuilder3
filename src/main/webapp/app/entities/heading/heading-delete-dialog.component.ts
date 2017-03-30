import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Heading } from './heading.model';
import { HeadingPopupService } from './heading-popup.service';
import { HeadingService } from './heading.service';

@Component({
    selector: 'jhi-heading-delete-dialog',
    templateUrl: './heading-delete-dialog.component.html'
})
export class HeadingDeleteDialogComponent {

    heading: Heading;

    constructor(
        private headingService: HeadingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.headingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'headingListModification',
                content: 'Deleted an heading'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-heading-delete-popup',
    template: ''
})
export class HeadingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private headingPopupService: HeadingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.headingPopupService
                .open(HeadingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
