import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ModelDetails } from './model-details.model';
import { ModelDetailsService } from './model-details.service';

@Component({
    selector: 'jhi-model-details-detail',
    templateUrl: './model-details-detail.component.html'
})
export class ModelDetailsDetailComponent implements OnInit, OnDestroy {

    modelDetails: ModelDetails;
    private subscription: any;

    constructor(
        private modelDetailsService: ModelDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.modelDetailsService.find(id).subscribe(modelDetails => {
            this.modelDetails = modelDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
