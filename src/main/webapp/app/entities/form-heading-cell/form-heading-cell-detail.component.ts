import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormHeadingCell } from './form-heading-cell.model';
import { FormHeadingCellService } from './form-heading-cell.service';

@Component({
    selector: 'jhi-form-heading-cell-detail',
    templateUrl: './form-heading-cell-detail.component.html'
})
export class FormHeadingCellDetailComponent implements OnInit, OnDestroy {

    formHeadingCell: FormHeadingCell;
    private subscription: any;

    constructor(
        private formHeadingCellService: FormHeadingCellService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.formHeadingCellService.find(id).subscribe(formHeadingCell => {
            this.formHeadingCell = formHeadingCell;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
