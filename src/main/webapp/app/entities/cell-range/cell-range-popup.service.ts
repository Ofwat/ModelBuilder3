import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CellRange } from './cell-range.model';
import { CellRangeService } from './cell-range.service';
@Injectable()
export class CellRangePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private cellRangeService: CellRangeService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.cellRangeService.find(id).subscribe(cellRange => {
                this.cellRangeModalRef(component, cellRange);
            });
        } else {
            return this.cellRangeModalRef(component, new CellRange());
        }
    }

    cellRangeModalRef(component: Component, cellRange: CellRange): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cellRange = cellRange;
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
