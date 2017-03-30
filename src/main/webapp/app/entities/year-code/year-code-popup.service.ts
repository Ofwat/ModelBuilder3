import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { YearCode } from './year-code.model';
import { YearCodeService } from './year-code.service';
@Injectable()
export class YearCodePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private yearCodeService: YearCodeService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.yearCodeService.find(id).subscribe(yearCode => {
                this.yearCodeModalRef(component, yearCode);
            });
        } else {
            return this.yearCodeModalRef(component, new YearCode());
        }
    }

    yearCodeModalRef(component: Component, yearCode: YearCode): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.yearCode = yearCode;
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
