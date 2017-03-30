import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Text } from './text.model';
import { TextService } from './text.service';

@Component({
    selector: 'jhi-text-detail',
    templateUrl: './text-detail.component.html'
})
export class TextDetailComponent implements OnInit, OnDestroy {

    text: Text;
    private subscription: any;

    constructor(
        private textService: TextService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.textService.find(id).subscribe(text => {
            this.text = text;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
