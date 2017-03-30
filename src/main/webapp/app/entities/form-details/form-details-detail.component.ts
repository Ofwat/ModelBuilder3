import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormDetails } from './form-details.model';
import { FormDetailsService } from './form-details.service';

@Component({
    selector: 'jhi-form-details-detail',
    templateUrl: './form-details-detail.component.html'
})
export class FormDetailsDetailComponent implements OnInit, OnDestroy {

    formDetails: FormDetails;
    private subscription: any;

    constructor(
        private formDetailsService: FormDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.formDetailsService.find(id).subscribe(formDetails => {
            this.formDetails = formDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
