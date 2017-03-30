import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TransferBlockDetails } from './transfer-block-details.model';
import { TransferBlockDetailsService } from './transfer-block-details.service';

@Component({
    selector: 'jhi-transfer-block-details-detail',
    templateUrl: './transfer-block-details-detail.component.html'
})
export class TransferBlockDetailsDetailComponent implements OnInit, OnDestroy {

    transferBlockDetails: TransferBlockDetails;
    private subscription: any;

    constructor(
        private transferBlockDetailsService: TransferBlockDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.transferBlockDetailsService.find(id).subscribe(transferBlockDetails => {
            this.transferBlockDetails = transferBlockDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
