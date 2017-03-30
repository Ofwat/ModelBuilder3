import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ValidationRuleDetails } from './validation-rule-details.model';
import { ValidationRuleDetailsService } from './validation-rule-details.service';
@Injectable()
export class ValidationRuleDetailsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private validationRuleDetailsService: ValidationRuleDetailsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.validationRuleDetailsService.find(id).subscribe(validationRuleDetails => {
                this.validationRuleDetailsModalRef(component, validationRuleDetails);
            });
        } else {
            return this.validationRuleDetailsModalRef(component, new ValidationRuleDetails());
        }
    }

    validationRuleDetailsModalRef(component: Component, validationRuleDetails: ValidationRuleDetails): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.validationRuleDetails = validationRuleDetails;
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
