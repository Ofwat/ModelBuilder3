import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { CompanyPage } from './company-page.model';
import { CompanyPagePopupService } from './company-page-popup.service';
import { CompanyPageService } from './company-page.service';

@Component({
    selector: 'jhi-company-page-delete-dialog',
    templateUrl: './company-page-delete-dialog.component.html'
})
export class CompanyPageDeleteDialogComponent {

    companyPage: CompanyPage;

    constructor(
        private companyPageService: CompanyPageService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.companyPageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'companyPageListModification',
                content: 'Deleted an companyPage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-company-page-delete-popup',
    template: ''
})
export class CompanyPageDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private companyPagePopupService: CompanyPagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.companyPagePopupService
                .open(CompanyPageDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
