import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Page } from './page.model';
import { PageService } from './page.service';

@Component({
    selector: 'jhi-page-detail',
    templateUrl: './page-detail.component.html'
})
export class PageDetailComponent implements OnInit, OnDestroy {

    page: Page;
    private subscription: any;

    constructor(
        private pageService: PageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.pageService.find(id).subscribe(page => {
            this.page = page;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
