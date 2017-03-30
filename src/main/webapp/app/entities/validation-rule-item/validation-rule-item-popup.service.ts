import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ValidationRuleItem } from './validation-rule-item.model';
import { ValidationRuleItemService } from './validation-rule-item.service';
@Injectable()
export class ValidationRuleItemPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private validationRuleItemService: ValidationRuleItemService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.validationRuleItemService.find(id).subscribe(validationRuleItem => {
                this.validationRuleItemModalRef(component, validationRuleItem);
            });
        } else {
            return this.validationRuleItemModalRef(component, new ValidationRuleItem());
        }
    }

    validationRuleItemModalRef(component: Component, validationRuleItem: ValidationRuleItem): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.validationRuleItem = validationRuleItem;
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
