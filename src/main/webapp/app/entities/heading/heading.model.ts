import { Model } from '../model';
export class Heading {
    constructor(
        public id?: number,
        public code?: string,
        public description?: string,
        public notes?: string,
        public parent?: string,
        public annotation?: string,
        public model?: Model,
    ) { }
}
