import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AuditChange } from './audit-change.model';
import { AuditChangeService } from './audit-change.service';
@Injectable()
export class AuditChangePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private auditChangeService: AuditChangeService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.auditChangeService.find(id).subscribe(auditChange => {
                this.auditChangeModalRef(component, auditChange);
            });
        } else {
            return this.auditChangeModalRef(component, new AuditChange());
        }
    }

    auditChangeModalRef(component: Component, auditChange: AuditChange): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auditChange = auditChange;
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
