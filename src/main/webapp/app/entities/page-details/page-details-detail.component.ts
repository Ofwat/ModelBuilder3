import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PageDetails } from './page-details.model';
import { PageDetailsService } from './page-details.service';

@Component({
    selector: 'jhi-page-details-detail',
    templateUrl: './page-details-detail.component.html'
})
export class PageDetailsDetailComponent implements OnInit, OnDestroy {

    pageDetails: PageDetails;
    private subscription: any;

    constructor(
        private pageDetailsService: PageDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.pageDetailsService.find(id).subscribe(pageDetails => {
            this.pageDetails = pageDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
