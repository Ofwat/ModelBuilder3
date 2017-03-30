import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CompanyPage } from './company-page.model';
import { CompanyPageService } from './company-page.service';

@Component({
    selector: 'jhi-company-page-detail',
    templateUrl: './company-page-detail.component.html'
})
export class CompanyPageDetailComponent implements OnInit, OnDestroy {

    companyPage: CompanyPage;
    private subscription: any;

    constructor(
        private companyPageService: CompanyPageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.companyPageService.find(id).subscribe(companyPage => {
            this.companyPage = companyPage;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
