import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ValidationRule } from './validation-rule.model';
import { ValidationRuleService } from './validation-rule.service';
@Injectable()
export class ValidationRulePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private validationRuleService: ValidationRuleService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.validationRuleService.find(id).subscribe(validationRule => {
                this.validationRuleModalRef(component, validationRule);
            });
        } else {
            return this.validationRuleModalRef(component, new ValidationRule());
        }
    }

    validationRuleModalRef(component: Component, validationRule: ValidationRule): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.validationRule = validationRule;
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
