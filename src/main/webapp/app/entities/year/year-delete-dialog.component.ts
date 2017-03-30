import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Year } from './year.model';
import { YearPopupService } from './year-popup.service';
import { YearService } from './year.service';

@Component({
    selector: 'jhi-year-delete-dialog',
    templateUrl: './year-delete-dialog.component.html'
})
export class YearDeleteDialogComponent {

    year: Year;

    constructor(
        private yearService: YearService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.yearService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'yearListModification',
                content: 'Deleted an year'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-year-delete-popup',
    template: ''
})
export class YearDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private yearPopupService: YearPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.yearPopupService
                .open(YearDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
