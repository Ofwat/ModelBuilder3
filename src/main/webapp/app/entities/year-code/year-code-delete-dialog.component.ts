import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { YearCode } from './year-code.model';
import { YearCodePopupService } from './year-code-popup.service';
import { YearCodeService } from './year-code.service';

@Component({
    selector: 'jhi-year-code-delete-dialog',
    templateUrl: './year-code-delete-dialog.component.html'
})
export class YearCodeDeleteDialogComponent {

    yearCode: YearCode;

    constructor(
        private yearCodeService: YearCodeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.yearCodeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'yearCodeListModification',
                content: 'Deleted an yearCode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-year-code-delete-popup',
    template: ''
})
export class YearCodeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private yearCodePopupService: YearCodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.yearCodePopupService
                .open(YearCodeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
