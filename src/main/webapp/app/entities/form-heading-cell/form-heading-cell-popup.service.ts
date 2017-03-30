import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FormHeadingCell } from './form-heading-cell.model';
import { FormHeadingCellService } from './form-heading-cell.service';
@Injectable()
export class FormHeadingCellPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private formHeadingCellService: FormHeadingCellService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.formHeadingCellService.find(id).subscribe(formHeadingCell => {
                this.formHeadingCellModalRef(component, formHeadingCell);
            });
        } else {
            return this.formHeadingCellModalRef(component, new FormHeadingCell());
        }
    }

    formHeadingCellModalRef(component: Component, formHeadingCell: FormHeadingCell): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.formHeadingCell = formHeadingCell;
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
