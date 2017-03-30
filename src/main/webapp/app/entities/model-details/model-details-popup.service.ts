import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ModelDetails } from './model-details.model';
import { ModelDetailsService } from './model-details.service';
@Injectable()
export class ModelDetailsPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private modelDetailsService: ModelDetailsService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.modelDetailsService.find(id).subscribe(modelDetails => {
                modelDetails.lastModified = this.datePipe.transform(modelDetails.lastModified, 'yyyy-MM-ddThh:mm');
                modelDetails.created = this.datePipe.transform(modelDetails.created, 'yyyy-MM-ddThh:mm');
                this.modelDetailsModalRef(component, modelDetails);
            });
        } else {
            return this.modelDetailsModalRef(component, new ModelDetails());
        }
    }

    modelDetailsModalRef(component: Component, modelDetails: ModelDetails): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.modelDetails = modelDetails;
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
