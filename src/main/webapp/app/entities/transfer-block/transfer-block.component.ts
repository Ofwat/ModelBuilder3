import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { TransferBlock } from './transfer-block.model';
import { TransferBlockService } from './transfer-block.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-transfer-block',
    templateUrl: './transfer-block.component.html'
})
export class TransferBlockComponent implements OnInit, OnDestroy {
transferBlocks: TransferBlock[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private transferBlockService: TransferBlockService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.transferBlockService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.transferBlocks = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.transferBlockService.query().subscribe(
            (res: Response) => {
                this.transferBlocks = res.json();
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
        this.registerChangeInTransferBlocks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: TransferBlock) {
        return item.id;
    }



    registerChangeInTransferBlocks() {
        this.eventSubscriber = this.eventManager.subscribe('transferBlockListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
