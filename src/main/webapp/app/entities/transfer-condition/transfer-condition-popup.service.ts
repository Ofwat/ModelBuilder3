import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TransferCondition } from './transfer-condition.model';
import { TransferConditionService } from './transfer-condition.service';
@Injectable()
export class TransferConditionPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private transferConditionService: TransferConditionService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.transferConditionService.find(id).subscribe(transferCondition => {
                this.transferConditionModalRef(component, transferCondition);
            });
        } else {
            return this.transferConditionModalRef(component, new TransferCondition());
        }
    }

    transferConditionModalRef(component: Component, transferCondition: TransferCondition): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.transferCondition = transferCondition;
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
