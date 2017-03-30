import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TextBlock } from './text-block.model';
import { TextBlockService } from './text-block.service';
@Injectable()
export class TextBlockPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private textBlockService: TextBlockService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.textBlockService.find(id).subscribe(textBlock => {
                this.textBlockModalRef(component, textBlock);
            });
        } else {
            return this.textBlockModalRef(component, new TextBlock());
        }
    }

    textBlockModalRef(component: Component, textBlock: TextBlock): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.textBlock = textBlock;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
