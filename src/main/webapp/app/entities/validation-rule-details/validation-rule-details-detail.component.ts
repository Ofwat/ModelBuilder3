import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ValidationRuleDetails } from './validation-rule-details.model';
import { ValidationRuleDetailsService } from './validation-rule-details.service';

@Component({
    selector: 'jhi-validation-rule-details-detail',
    templateUrl: './validation-rule-details-detail.component.html'
})
export class ValidationRuleDetailsDetailComponent implements OnInit, OnDestroy {

    validationRuleDetails: ValidationRuleDetails;
    private subscription: any;

    constructor(
        private validationRuleDetailsService: ValidationRuleDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.validationRuleDetailsService.find(id).subscribe(validationRuleDetails => {
            this.validationRuleDetails = validationRuleDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
