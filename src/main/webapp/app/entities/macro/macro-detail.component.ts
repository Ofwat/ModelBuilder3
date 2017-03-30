import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Macro } from './macro.model';
import { MacroService } from './macro.service';

@Component({
    selector: 'jhi-macro-detail',
    templateUrl: './macro-detail.component.html'
})
export class MacroDetailComponent implements OnInit, OnDestroy {

    macro: Macro;
    private subscription: any;

    constructor(
        private macroService: MacroService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.macroService.find(id).subscribe(macro => {
            this.macro = macro;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
