import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ValidationRuleItem } from './validation-rule-item.model';
import { ValidationRuleItemService } from './validation-rule-item.service';

@Component({
    selector: 'jhi-validation-rule-item-detail',
    templateUrl: './validation-rule-item-detail.component.html'
})
export class ValidationRuleItemDetailComponent implements OnInit, OnDestroy {

    validationRuleItem: ValidationRuleItem;
    private subscription: any;

    constructor(
        private validationRuleItemService: ValidationRuleItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.validationRuleItemService.find(id).subscribe(validationRuleItem => {
            this.validationRuleItem = validationRuleItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
