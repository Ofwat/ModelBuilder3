import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { YearCode } from './year-code.model';
import { YearCodeService } from './year-code.service';

@Component({
    selector: 'jhi-year-code-detail',
    templateUrl: './year-code-detail.component.html'
})
export class YearCodeDetailComponent implements OnInit, OnDestroy {

    yearCode: YearCode;
    private subscription: any;

    constructor(
        private yearCodeService: YearCodeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.yearCodeService.find(id).subscribe(yearCode => {
            this.yearCode = yearCode;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
