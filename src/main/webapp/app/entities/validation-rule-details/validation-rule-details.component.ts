import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { ValidationRuleDetails } from './validation-rule-details.model';
import { ValidationRuleDetailsService } from './validation-rule-details.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-validation-rule-details',
    templateUrl: './validation-rule-details.component.html'
})
export class ValidationRuleDetailsComponent implements OnInit, OnDestroy {
validationRuleDetails: ValidationRuleDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private validationRuleDetailsService: ValidationRuleDetailsService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.validationRuleDetailsService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.validationRuleDetails = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.validationRuleDetailsService.query().subscribe(
            (res: Response) => {
                this.validationRuleDetails = res.json();
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
        this.registerChangeInValidationRuleDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: ValidationRuleDetails) {
        return item.id;
    }



    registerChangeInValidationRuleDetails() {
        this.eventSubscriber = this.eventManager.subscribe('validationRuleDetailsListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
