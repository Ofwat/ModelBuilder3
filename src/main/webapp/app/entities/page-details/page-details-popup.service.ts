import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PageDetails } from './page-details.model';
import { PageDetailsService } from './page-details.service';
@Injectable()
export class PageDetailsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private pageDetailsService: PageDetailsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.pageDetailsService.find(id).subscribe(pageDetails => {
                this.pageDetailsModalRef(component, pageDetails);
            });
        } else {
            return this.pageDetailsModalRef(component, new PageDetails());
        }
    }

    pageDetailsModalRef(component: Component, pageDetails: PageDetails): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pageDetails = pageDetails;
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
