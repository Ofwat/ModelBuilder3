import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { ValidationRuleItem } from './validation-rule-item.model';
import { ValidationRuleItemService } from './validation-rule-item.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-validation-rule-item',
    templateUrl: './validation-rule-item.component.html'
})
export class ValidationRuleItemComponent implements OnInit, OnDestroy {
validationRuleItems: ValidationRuleItem[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private validationRuleItemService: ValidationRuleItemService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.validationRuleItemService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.validationRuleItems = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.validationRuleItemService.query().subscribe(
            (res: Response) => {
                this.validationRuleItems = res.json();
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
        this.registerChangeInValidationRuleItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: ValidationRuleItem) {
        return item.id;
    }



    registerChangeInValidationRuleItems() {
        this.eventSubscriber = this.eventManager.subscribe('validationRuleItemListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
