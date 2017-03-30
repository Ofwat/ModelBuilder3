import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TransferBlockDetails } from './transfer-block-details.model';
import { TransferBlockDetailsService } from './transfer-block-details.service';
@Injectable()
export class TransferBlockDetailsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private transferBlockDetailsService: TransferBlockDetailsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.transferBlockDetailsService.find(id).subscribe(transferBlockDetails => {
                this.transferBlockDetailsModalRef(component, transferBlockDetails);
            });
        } else {
            return this.transferBlockDetailsModalRef(component, new TransferBlockDetails());
        }
    }

    transferBlockDetailsModalRef(component: Component, transferBlockDetails: TransferBlockDetails): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.transferBlockDetails = transferBlockDetails;
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
