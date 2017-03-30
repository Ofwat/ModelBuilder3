import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TransferCondition } from './transfer-condition.model';
import { TransferConditionService } from './transfer-condition.service';

@Component({
    selector: 'jhi-transfer-condition-detail',
    templateUrl: './transfer-condition-detail.component.html'
})
export class TransferConditionDetailComponent implements OnInit, OnDestroy {

    transferCondition: TransferCondition;
    private subscription: any;

    constructor(
        private transferConditionService: TransferConditionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.transferConditionService.find(id).subscribe(transferCondition => {
            this.transferCondition = transferCondition;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
