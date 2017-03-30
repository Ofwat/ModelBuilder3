import { Model } from '../model';
export class Item {
    constructor(
        public id?: number,
        public code?: string,
        public versionNumber?: string,
        public pricebaseCode?: string,
        public purposeCode?: string,
        public textCode?: string,
        public model?: Model,
    ) { }
}
