import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { TransferBlockDetails } from './transfer-block-details.model';
import { TransferBlockDetailsService } from './transfer-block-details.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-transfer-block-details',
    templateUrl: './transfer-block-details.component.html'
})
export class TransferBlockDetailsComponent implements OnInit, OnDestroy {
transferBlockDetails: TransferBlockDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private transferBlockDetailsService: TransferBlockDetailsService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.transferBlockDetailsService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.transferBlockDetails = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.transferBlockDetailsService.query().subscribe(
            (res: Response) => {
                this.transferBlockDetails = res.json();
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
        this.registerChangeInTransferBlockDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: TransferBlockDetails) {
        return item.id;
    }



    registerChangeInTransferBlockDetails() {
        this.eventSubscriber = this.eventManager.subscribe('transferBlockDetailsListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
