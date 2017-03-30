import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TransferBlockItem } from './transfer-block-item.model';
import { TransferBlockItemService } from './transfer-block-item.service';

@Component({
    selector: 'jhi-transfer-block-item-detail',
    templateUrl: './transfer-block-item-detail.component.html'
})
export class TransferBlockItemDetailComponent implements OnInit, OnDestroy {

    transferBlockItem: TransferBlockItem;
    private subscription: any;

    constructor(
        private transferBlockItemService: TransferBlockItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.transferBlockItemService.find(id).subscribe(transferBlockItem => {
            this.transferBlockItem = transferBlockItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
