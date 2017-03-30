import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Heading } from './heading.model';
import { HeadingService } from './heading.service';

@Component({
    selector: 'jhi-heading-detail',
    templateUrl: './heading-detail.component.html'
})
export class HeadingDetailComponent implements OnInit, OnDestroy {

    heading: Heading;
    private subscription: any;

    constructor(
        private headingService: HeadingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.headingService.find(id).subscribe(heading => {
            this.heading = heading;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
