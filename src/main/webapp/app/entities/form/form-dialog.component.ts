import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Form } from './form.model';
import { FormPopupService } from './form-popup.service';
import { FormService } from './form.service';
import { FormDetails, FormDetailsService } from '../form-details';
import { FormCell, FormCellService } from '../form-cell';
import { FormHeadingCell, FormHeadingCellService } from '../form-heading-cell';
import { Section, SectionService } from '../section';
@Component({
    selector: 'jhi-form-dialog',
    templateUrl: './form-dialog.component.html'
})
export class FormDialogComponent implements OnInit {

    form: Form;
    authorities: any[];
    isSaving: boolean;

    formdetails: FormDetails[];

    formcells: FormCell[];

    formheadingcells: FormHeadingCell[];

    sections: Section[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private formService: FormService,
        private formDetailsService: FormDetailsService,
        private formCellService: FormCellService,
        private formHeadingCellService: FormHeadingCellService,
        private sectionService: SectionService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.formDetailsService.query({filter: 'form-is-null'}).subscribe((res: Response) => {
            if (!this.form.formDetail || !this.form.formDetail.id) {
                this.formdetails = res.json();
            } else {
                this.formDetailsService.find(this.form.formDetail.id).subscribe((subRes: FormDetails) => {
                    this.formdetails = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.formCellService.query().subscribe(
            (res: Response) => { this.formcells = res.json(); }, (res: Response) => this.onError(res.json()));
        this.formHeadingCellService.query().subscribe(
            (res: Response) => { this.formheadingcells = res.json(); }, (res: Response) => this.onError(res.json()));
        this.sectionService.query().subscribe(
            (res: Response) => { this.sections = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.form.id !== undefined) {
            this.formService.update(this.form)
                .subscribe((res: Form) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.formService.create(this.form)
                .subscribe((res: Form) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Form) {
        this.eventManager.broadcast({ name: 'formListModification', content: 'OK'});
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

    trackFormDetailsById(index: number, item: FormDetails) {
        return item.id;
    }

    trackFormCellById(index: number, item: FormCell) {
        return item.id;
    }

    trackFormHeadingCellById(index: number, item: FormHeadingCell) {
        return item.id;
    }

    trackSectionById(index: number, item: Section) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-form-popup',
    template: ''
})
export class FormPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private formPopupService: FormPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.formPopupService
                    .open(FormDialogComponent, params['id']);
            } else {
                this.modalRef = this.formPopupService
                    .open(FormDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
