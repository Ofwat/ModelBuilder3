import { TextBlock } from '../text-block';
import { Model } from '../model';
export class Text {
    constructor(
        public id?: number,
        public code?: string,
        public textBlocks?: TextBlock,
        public model?: Model,
    ) { }
}
