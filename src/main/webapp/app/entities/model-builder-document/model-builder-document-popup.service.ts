import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ModelBuilderDocument } from './model-builder-document.model';
import { ModelBuilderDocumentService } from './model-builder-document.service';
@Injectable()
export class ModelBuilderDocumentPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private modelBuilderDocumentService: ModelBuilderDocumentService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.modelBuilderDocumentService.find(id).subscribe(modelBuilderDocument => {
                this.modelBuilderDocumentModalRef(component, modelBuilderDocument);
            });
        } else {
            return this.modelBuilderDocumentModalRef(component, new ModelBuilderDocument());
        }
    }

    modelBuilderDocumentModalRef(component: Component, modelBuilderDocument: ModelBuilderDocument): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.modelBuilderDocument = modelBuilderDocument;
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
