import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SectionDetails } from './section-details.model';
import { SectionDetailsService } from './section-details.service';
@Injectable()
export class SectionDetailsPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private sectionDetailsService: SectionDetailsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.sectionDetailsService.find(id).subscribe(sectionDetails => {
                this.sectionDetailsModalRef(component, sectionDetails);
            });
        } else {
            return this.sectionDetailsModalRef(component, new SectionDetails());
        }
    }

    sectionDetailsModalRef(component: Component, sectionDetails: SectionDetails): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sectionDetails = sectionDetails;
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
