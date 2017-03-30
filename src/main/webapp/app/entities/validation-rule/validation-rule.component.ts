import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { ValidationRule } from './validation-rule.model';
import { ValidationRuleService } from './validation-rule.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-validation-rule',
    templateUrl: './validation-rule.component.html'
})
export class ValidationRuleComponent implements OnInit, OnDestroy {
validationRules: ValidationRule[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private validationRuleService: ValidationRuleService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.validationRuleService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.validationRules = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.validationRuleService.query().subscribe(
            (res: Response) => {
                this.validationRules = res.json();
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
        this.registerChangeInValidationRules();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: ValidationRule) {
        return item.id;
    }



    registerChangeInValidationRules() {
        this.eventSubscriber = this.eventManager.subscribe('validationRuleListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
