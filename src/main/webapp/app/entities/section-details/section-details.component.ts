import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { SectionDetails } from './section-details.model';
import { SectionDetailsService } from './section-details.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-section-details',
    templateUrl: './section-details.component.html'
})
export class SectionDetailsComponent implements OnInit, OnDestroy {
sectionDetails: SectionDetails[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private sectionDetailsService: SectionDetailsService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.sectionDetailsService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: Response) => this.sectionDetails = res.json(),
                    (res: Response) => this.onError(res.json())
                );
            return;
       }
        this.sectionDetailsService.query().subscribe(
            (res: Response) => {
                this.sectionDetails = res.json();
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
        this.registerChangeInSectionDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: SectionDetails) {
        return item.id;
    }



    registerChangeInSectionDetails() {
        this.eventSubscriber = this.eventManager.subscribe('sectionDetailsListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
