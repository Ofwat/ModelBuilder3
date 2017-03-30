import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { TextBlock } from './text-block.model';
import { TextBlockPopupService } from './text-block-popup.service';
import { TextBlockService } from './text-block.service';

@Component({
    selector: 'jhi-text-block-delete-dialog',
    templateUrl: './text-block-delete-dialog.component.html'
})
export class TextBlockDeleteDialogComponent {

    textBlock: TextBlock;

    constructor(
        private textBlockService: TextBlockService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.textBlockService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'textBlockListModification',
                content: 'Deleted an textBlock'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-text-block-delete-popup',
    template: ''
})
export class TextBlockDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private textBlockPopupService: TextBlockPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.textBlockPopupService
                .open(TextBlockDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
