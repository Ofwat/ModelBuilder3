import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CompanyPage } from './company-page.model';
import { CompanyPageService } from './company-page.service';
@Injectable()
export class CompanyPagePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private companyPageService: CompanyPageService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.companyPageService.find(id).subscribe(companyPage => {
                this.companyPageModalRef(component, companyPage);
            });
        } else {
            return this.companyPageModalRef(component, new CompanyPage());
        }
    }

    companyPageModalRef(component: Component, companyPage: CompanyPage): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.companyPage = companyPage;
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
