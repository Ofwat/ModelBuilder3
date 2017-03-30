import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TransferBlockItem } from './transfer-block-item.model';
import { TransferBlockItemService } from './transfer-block-item.service';
@Injectable()
export class TransferBlockItemPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private transferBlockItemService: TransferBlockItemService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.transferBlockItemService.find(id).subscribe(transferBlockItem => {
                this.transferBlockItemModalRef(component, transferBlockItem);
            });
        } else {
            return this.transferBlockItemModalRef(component, new TransferBlockItem());
        }
    }

    transferBlockItemModalRef(component: Component, transferBlockItem: TransferBlockItem): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.transferBlockItem = transferBlockItem;
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
