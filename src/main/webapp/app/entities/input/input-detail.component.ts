import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Input } from './input.model';
import { InputService } from './input.service';

@Component({
    selector: 'jhi-input-detail',
    templateUrl: './input-detail.component.html'
})
export class InputDetailComponent implements OnInit, OnDestroy {

    input: Input;
    private subscription: any;

    constructor(
        private inputService: InputService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.inputService.find(id).subscribe(input => {
            this.input = input;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
