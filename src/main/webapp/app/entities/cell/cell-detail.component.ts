import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Cell } from './cell.model';
import { CellService } from './cell.service';

@Component({
    selector: 'jhi-cell-detail',
    templateUrl: './cell-detail.component.html'
})
export class CellDetailComponent implements OnInit, OnDestroy {

    cell: Cell;
    private subscription: any;

    constructor(
        private cellService: CellService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.cellService.find(id).subscribe(cell => {
            this.cell = cell;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
