import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { TransferBlockItem } from './transfer-block-item.model';
import { TransferBlockItemService } from './transfer-block-item.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-transfer-block-item',
    templateUrl: './transfer-block-item.component.html'
})
export class TransferBlockItemComponent implements OnInit, OnDestroy {
transferBlockItems: TransferBlockItem[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private transferBlockItemService: TransferBlockItemService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.transferBlockItemService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.transferBlockItems = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.transferBlockItemService.query().subscribe(
            (res: Response) => {
                this.transferBlockItems = res.json();
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
        this.registerChangeInTransferBlockItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: TransferBlockItem) {
        return item.id;
    }



    registerChangeInTransferBlockItems() {
        this.eventSubscriber = this.eventManager.subscribe('transferBlockItemListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
