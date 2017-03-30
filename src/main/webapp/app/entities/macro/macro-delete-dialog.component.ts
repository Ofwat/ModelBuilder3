import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Macro } from './macro.model';
import { MacroPopupService } from './macro-popup.service';
import { MacroService } from './macro.service';

@Component({
    selector: 'jhi-macro-delete-dialog',
    templateUrl: './macro-delete-dialog.component.html'
})
export class MacroDeleteDialogComponent {

    macro: Macro;

    constructor(
        private macroService: MacroService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.macroService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'macroListModification',
                content: 'Deleted an macro'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-macro-delete-popup',
    template: ''
})
export class MacroDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private macroPopupService: MacroPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.macroPopupService
                .open(MacroDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
