import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CellRange } from './cell-range.model';
import { CellRangeService } from './cell-range.service';

@Component({
    selector: 'jhi-cell-range-detail',
    templateUrl: './cell-range-detail.component.html'
})
export class CellRangeDetailComponent implements OnInit, OnDestroy {

    cellRange: CellRange;
    private subscription: any;

    constructor(
        private cellRangeService: CellRangeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.cellRangeService.find(id).subscribe(cellRange => {
            this.cellRange = cellRange;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
