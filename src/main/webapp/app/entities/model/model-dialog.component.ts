import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Model } from './model.model';
import { ModelPopupService } from './model-popup.service';
import { ModelService } from './model.service';
import { ModelDetails, ModelDetailsService } from '../model-details';
import { ModelAudit, ModelAuditService } from '../model-audit';
import { Item, ItemService } from '../item';
import { Year, YearService } from '../year';
import { Heading, HeadingService } from '../heading';
import { ValidationRule, ValidationRuleService } from '../validation-rule';
import { CompanyPage, CompanyPageService } from '../company-page';
import { ModelBuilderDocument, ModelBuilderDocumentService } from '../model-builder-document';
import { Page, PageService } from '../page';
import { Transfer, TransferService } from '../transfer';
import { Macro, MacroService } from '../macro';
import { Text, TextService } from '../text';
import { Input, InputService } from '../input';
@Component({
    selector: 'jhi-model-dialog',
    templateUrl: './model-dialog.component.html'
})
export class ModelDialogComponent implements OnInit {

    model: Model;
    authorities: any[];
    isSaving: boolean;

    modeldetails: ModelDetails[];

    modelaudits: ModelAudit[];

    items: Item[];

    years: Year[];

    headings: Heading[];

    validationrules: ValidationRule[];

    companypages: CompanyPage[];

    modelbuilderdocuments: ModelBuilderDocument[];

    pages: Page[];

    transfers: Transfer[];

    macros: Macro[];

    texts: Text[];

    inputs: Input[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private modelService: ModelService,
        private modelDetailsService: ModelDetailsService,
        private modelAuditService: ModelAuditService,
        private itemService: ItemService,
        private yearService: YearService,
        private headingService: HeadingService,
        private validationRuleService: ValidationRuleService,
        private companyPageService: CompanyPageService,
        private modelBuilderDocumentService: ModelBuilderDocumentService,
        private pageService: PageService,
        private transferService: TransferService,
        private macroService: MacroService,
        private textService: TextService,
        private inputService: InputService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.modelDetailsService.query({filter: 'model-is-null'}).subscribe((res: Response) => {
            if (!this.model.modelDetails || !this.model.modelDetails.id) {
                this.modeldetails = res.json();
            } else {
                this.modelDetailsService.find(this.model.modelDetails.id).subscribe((subRes: ModelDetails) => {
                    this.modeldetails = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.modelAuditService.query().subscribe(
            (res: Response) => { this.modelaudits = res.json(); }, (res: Response) => this.onError(res.json()));
        this.itemService.query().subscribe(
            (res: Response) => { this.items = res.json(); }, (res: Response) => this.onError(res.json()));
        this.yearService.query().subscribe(
            (res: Response) => { this.years = res.json(); }, (res: Response) => this.onError(res.json()));
        this.headingService.query().subscribe(
            (res: Response) => { this.headings = res.json(); }, (res: Response) => this.onError(res.json()));
        this.validationRuleService.query().subscribe(
            (res: Response) => { this.validationrules = res.json(); }, (res: Response) => this.onError(res.json()));
        this.companyPageService.query().subscribe(
            (res: Response) => { this.companypages = res.json(); }, (res: Response) => this.onError(res.json()));
        this.modelBuilderDocumentService.query().subscribe(
            (res: Response) => { this.modelbuilderdocuments = res.json(); }, (res: Response) => this.onError(res.json()));
        this.pageService.query().subscribe(
            (res: Response) => { this.pages = res.json(); }, (res: Response) => this.onError(res.json()));
        this.transferService.query().subscribe(
            (res: Response) => { this.transfers = res.json(); }, (res: Response) => this.onError(res.json()));
        this.macroService.query().subscribe(
            (res: Response) => { this.macros = res.json(); }, (res: Response) => this.onError(res.json()));
        this.textService.query().subscribe(
            (res: Response) => { this.texts = res.json(); }, (res: Response) => this.onError(res.json()));
        this.inputService.query().subscribe(
            (res: Response) => { this.inputs = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.model.id !== undefined) {
            this.modelService.update(this.model)
                .subscribe((res: Model) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.modelService.create(this.model)
                .subscribe((res: Model) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Model) {
        this.eventManager.broadcast({ name: 'modelListModification', content: 'OK'});
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

    trackModelDetailsById(index: number, item: ModelDetails) {
        return item.id;
    }

    trackModelAuditById(index: number, item: ModelAudit) {
        return item.id;
    }

    trackItemById(index: number, item: Item) {
        return item.id;
    }

    trackYearById(index: number, item: Year) {
        return item.id;
    }

    trackHeadingById(index: number, item: Heading) {
        return item.id;
    }

    trackValidationRuleById(index: number, item: ValidationRule) {
        return item.id;
    }

    trackCompanyPageById(index: number, item: CompanyPage) {
        return item.id;
    }

    trackModelBuilderDocumentById(index: number, item: ModelBuilderDocument) {
        return item.id;
    }

    trackPageById(index: number, item: Page) {
        return item.id;
    }

    trackTransferById(index: number, item: Transfer) {
        return item.id;
    }

    trackMacroById(index: number, item: Macro) {
        return item.id;
    }

    trackTextById(index: number, item: Text) {
        return item.id;
    }

    trackInputById(index: number, item: Input) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-model-popup',
    template: ''
})
export class ModelPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private modelPopupService: ModelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.modelPopupService
                    .open(ModelDialogComponent, params['id']);
            } else {
                this.modalRef = this.modelPopupService
                    .open(ModelDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
