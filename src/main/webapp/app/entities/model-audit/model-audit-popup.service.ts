import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ModelAudit } from './model-audit.model';
import { ModelAuditService } from './model-audit.service';
@Injectable()
export class ModelAuditPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private modelAuditService: ModelAuditService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.modelAuditService.find(id).subscribe(modelAudit => {
                this.modelAuditModalRef(component, modelAudit);
            });
        } else {
            return this.modelAuditModalRef(component, new ModelAudit());
        }
    }

    modelAuditModalRef(component: Component, modelAudit: ModelAudit): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.modelAudit = modelAudit;
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
