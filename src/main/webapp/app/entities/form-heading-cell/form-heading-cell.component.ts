import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { FormHeadingCell } from './form-heading-cell.model';
import { FormHeadingCellService } from './form-heading-cell.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-form-heading-cell',
    templateUrl: './form-heading-cell.component.html'
})
export class FormHeadingCellComponent implements OnInit, OnDestroy {
formHeadingCells: FormHeadingCell[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private formHeadingCellService: FormHeadingCellService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.formHeadingCellService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.formHeadingCells = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.formHeadingCellService.query().subscribe(
            (res: Response) => {
                this.formHeadingCells = res.json();
                this.currentSearch = '';
            },
            (res: Response) => this.onError(res.json())
        );
    }

    search (query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFormHeadingCells();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: FormHeadingCell) {
        return item.id;
    }



    registerChangeInFormHeadingCells() {
        this.eventSubscriber = this.eventManager.subscribe('formHeadingCellListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
