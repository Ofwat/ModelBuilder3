import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Line } from './line.model';
import { LinePopupService } from './line-popup.service';
import { LineService } from './line.service';
import { LineDetails, LineDetailsService } from '../line-details';
import { CellRange, CellRangeService } from '../cell-range';
import { Cell, CellService } from '../cell';
import { ModelBuilderDocument, ModelBuilderDocumentService } from '../model-builder-document';
import { Section, SectionService } from '../section';
@Component({
    selector: 'jhi-line-dialog',
    templateUrl: './line-dialog.component.html'
})
export class LineDialogComponent implements OnInit {

    line: Line;
    authorities: any[];
    isSaving: boolean;

    linedetails: LineDetails[];

    cellranges: CellRange[];

    cells: Cell[];

    modelbuilderdocuments: ModelBuilderDocument[];

    sections: Section[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private lineService: LineService,
        private lineDetailsService: LineDetailsService,
        private cellRangeService: CellRangeService,
        private cellService: CellService,
        private modelBuilderDocumentService: ModelBuilderDocumentService,
        private sectionService: SectionService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.lineDetailsService.query({filter: 'line-is-null'}).subscribe((res: Response) => {
            if (!this.line.lineDetail || !this.line.lineDetail.id) {
                this.linedetails = res.json();
            } else {
                this.lineDetailsService.find(this.line.lineDetail.id).subscribe((subRes: LineDetails) => {
                    this.linedetails = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.cellRangeService.query({filter: 'line-is-null'}).subscribe((res: Response) => {
            if (!this.line.cellRange || !this.line.cellRange.id) {
                this.cellranges = res.json();
            } else {
                this.cellRangeService.find(this.line.cellRange.id).subscribe((subRes: CellRange) => {
                    this.cellranges = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.cellService.query().subscribe(
            (res: Response) => { this.cells = res.json(); }, (res: Response) => this.onError(res.json()));
        this.modelBuilderDocumentService.query().subscribe(
            (res: Response) => { this.modelbuilderdocuments = res.json(); }, (res: Response) => this.onError(res.json()));
        this.sectionService.query().subscribe(
            (res: Response) => { this.sections = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.line.id !== undefined) {
            this.lineService.update(this.line)
                .subscribe((res: Line) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.lineService.create(this.line)
                .subscribe((res: Line) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Line) {
        this.eventManager.broadcast({ name: 'lineListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackLineDetailsById(index: number, item: LineDetails) {
        return item.id;
    }

    trackCellRangeById(index: number, item: CellRange) {
        return item.id;
    }

    trackCellById(index: number, item: Cell) {
        return item.id;
    }

    trackModelBuilderDocumentById(index: number, item: ModelBuilderDocument) {
        return item.id;
    }

    trackSectionById(index: number, item: Section) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-line-popup',
    template: ''
})
export class LinePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private linePopupService: LinePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.linePopupService
                    .open(LineDialogComponent, params['id']);
            } else {
                this.modalRef = this.linePopupService
                    .open(LineDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
