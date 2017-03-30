import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Year } from './year.model';
import { YearService } from './year.service';

@Component({
    selector: 'jhi-year-detail',
    templateUrl: './year-detail.component.html'
})
export class YearDetailComponent implements OnInit, OnDestroy {

    year: Year;
    private subscription: any;

    constructor(
        private yearService: YearService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.yearService.find(id).subscribe(year => {
            this.year = year;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
