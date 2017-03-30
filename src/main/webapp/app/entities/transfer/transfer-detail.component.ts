import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Transfer } from './transfer.model';
import { TransferService } from './transfer.service';

@Component({
    selector: 'jhi-transfer-detail',
    templateUrl: './transfer-detail.component.html'
})
export class TransferDetailComponent implements OnInit, OnDestroy {

    transfer: Transfer;
    private subscription: any;

    constructor(
        private transferService: TransferService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.transferService.find(id).subscribe(transfer => {
            this.transfer = transfer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
