import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Form } from './form.model';
import { FormService } from './form.service';

@Component({
    selector: 'jhi-form-detail',
    templateUrl: './form-detail.component.html'
})
export class FormDetailComponent implements OnInit, OnDestroy {

    form: Form;
    private subscription: any;

    constructor(
        private formService: FormService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.formService.find(id).subscribe(form => {
            this.form = form;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
