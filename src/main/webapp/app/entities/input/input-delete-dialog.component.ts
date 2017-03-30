import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Input } from './input.model';
import { InputPopupService } from './input-popup.service';
import { InputService } from './input.service';

@Component({
    selector: 'jhi-input-delete-dialog',
    templateUrl: './input-delete-dialog.component.html'
})
export class InputDeleteDialogComponent {

    input: Input;

    constructor(
        private inputService: InputService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.inputService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'inputListModification',
                content: 'Deleted an input'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-input-delete-popup',
    template: ''
})
export class InputDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private inputPopupService: InputPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.inputPopupService
                .open(InputDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
