import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Text } from './text.model';
import { TextPopupService } from './text-popup.service';
import { TextService } from './text.service';

@Component({
    selector: 'jhi-text-delete-dialog',
    templateUrl: './text-delete-dialog.component.html'
})
export class TextDeleteDialogComponent {

    text: Text;

    constructor(
        private textService: TextService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.textService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'textListModification',
                content: 'Deleted an text'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-text-delete-popup',
    template: ''
})
export class TextDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private textPopupService: TextPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.textPopupService
                .open(TextDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
