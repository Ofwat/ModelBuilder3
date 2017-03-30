import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ValidationRule } from './validation-rule.model';
import { ValidationRuleService } from './validation-rule.service';

@Component({
    selector: 'jhi-validation-rule-detail',
    templateUrl: './validation-rule-detail.component.html'
})
export class ValidationRuleDetailComponent implements OnInit, OnDestroy {

    validationRule: ValidationRule;
    private subscription: any;

    constructor(
        private validationRuleService: ValidationRuleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.validationRuleService.find(id).subscribe(validationRule => {
            this.validationRule = validationRule;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
