import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LineDetails } from './line-details.model';
import { LineDetailsService } from './line-details.service';
@Injectable()
export class LineDetailsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private lineDetailsService: LineDetailsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.lineDetailsService.find(id).subscribe(lineDetails => {
                this.lineDetailsModalRef(component, lineDetails);
            });
        } else {
            return this.lineDetailsModalRef(component, new LineDetails());
        }
    }

    lineDetailsModalRef(component: Component, lineDetails: LineDetails): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lineDetails = lineDetails;
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
