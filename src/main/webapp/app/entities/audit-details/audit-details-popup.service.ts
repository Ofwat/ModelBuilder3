import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuditDetails } from './audit-details.model';
import { AuditDetailsService } from './audit-details.service';
@Injectable()
export class AuditDetailsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private auditDetailsService: AuditDetailsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.auditDetailsService.find(id).subscribe(auditDetails => {
                this.auditDetailsModalRef(component, auditDetails);
            });
        } else {
            return this.auditDetailsModalRef(component, new AuditDetails());
        }
    }

    auditDetailsModalRef(component: Component, auditDetails: AuditDetails): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auditDetails = auditDetails;
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
