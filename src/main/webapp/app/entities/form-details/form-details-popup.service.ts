import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FormDetails } from './form-details.model';
import { FormDetailsService } from './form-details.service';
@Injectable()
export class FormDetailsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private formDetailsService: FormDetailsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.formDetailsService.find(id).subscribe(formDetails => {
                this.formDetailsModalRef(component, formDetails);
            });
        } else {
            return this.formDetailsModalRef(component, new FormDetails());
        }
    }

    formDetailsModalRef(component: Component, formDetails: FormDetails): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.formDetails = formDetails;
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
