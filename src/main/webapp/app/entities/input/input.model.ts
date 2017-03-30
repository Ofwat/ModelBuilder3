import { Model } from '../model';
export class Input {
    constructor(
        public id?: number,
        public code?: string,
        public tag?: string,
        public version?: string,
        public company?: string,
        public defaultInput?: boolean,
        public modelReference?: string,
        public model?: Model,
    ) { }
}
