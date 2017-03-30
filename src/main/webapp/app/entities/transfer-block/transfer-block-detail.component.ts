import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TransferBlock } from './transfer-block.model';
import { TransferBlockService } from './transfer-block.service';

@Component({
    selector: 'jhi-transfer-block-detail',
    templateUrl: './transfer-block-detail.component.html'
})
export class TransferBlockDetailComponent implements OnInit, OnDestroy {

    transferBlock: TransferBlock;
    private subscription: any;

    constructor(
        private transferBlockService: TransferBlockService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.transferBlockService.find(id).subscribe(transferBlock => {
            this.transferBlock = transferBlock;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
