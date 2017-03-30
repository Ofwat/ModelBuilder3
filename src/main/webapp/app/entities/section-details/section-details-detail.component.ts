import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SectionDetails } from './section-details.model';
import { SectionDetailsService } from './section-details.service';

@Component({
    selector: 'jhi-section-details-detail',
    templateUrl: './section-details-detail.component.html'
})
export class SectionDetailsDetailComponent implements OnInit, OnDestroy {

    sectionDetails: SectionDetails;
    private subscription: any;

    constructor(
        private sectionDetailsService: SectionDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.sectionDetailsService.find(id).subscribe(sectionDetails => {
            this.sectionDetails = sectionDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
