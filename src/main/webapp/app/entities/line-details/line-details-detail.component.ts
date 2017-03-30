import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LineDetails } from './line-details.model';
import { LineDetailsService } from './line-details.service';

@Component({
    selector: 'jhi-line-details-detail',
    templateUrl: './line-details-detail.component.html'
})
export class LineDetailsDetailComponent implements OnInit, OnDestroy {

    lineDetails: LineDetails;
    private subscription: any;

    constructor(
        private lineDetailsService: LineDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.lineDetailsService.find(id).subscribe(lineDetails => {
            this.lineDetails = lineDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
