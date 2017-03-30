import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TransferBlock } from './transfer-block.model';
import { TransferBlockService } from './transfer-block.service';
@Injectable()
export class TransferBlockPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private transferBlockService: TransferBlockService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.transferBlockService.find(id).subscribe(transferBlock => {
                this.transferBlockModalRef(component, transferBlock);
            });
        } else {
            return this.transferBlockModalRef(component, new TransferBlock());
        }
    }

    transferBlockModalRef(component: Component, transferBlock: TransferBlock): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.transferBlock = transferBlock;
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
