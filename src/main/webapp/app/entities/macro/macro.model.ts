import { Model } from '../model';
export class Macro {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public block?: string,
        public conditionalItemCode?: string,
        public pageCode?: string,
        public model?: Model,
    ) { }
}
