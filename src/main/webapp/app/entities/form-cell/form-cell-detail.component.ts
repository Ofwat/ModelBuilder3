import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormCell } from './form-cell.model';
import { FormCellService } from './form-cell.service';

@Component({
    selector: 'jhi-form-cell-detail',
    templateUrl: './form-cell-detail.component.html'
})
export class FormCellDetailComponent implements OnInit, OnDestroy {

    formCell: FormCell;
    private subscription: any;

    constructor(
        private formCellService: FormCellService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.formCellService.find(id).subscribe(formCell => {
            this.formCell = formCell;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
