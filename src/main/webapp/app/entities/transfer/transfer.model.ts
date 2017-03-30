import { TransferCondition } from '../transfer-condition';
import { TransferBlock } from '../transfer-block';
import { Model } from '../model';
export class Transfer {
    constructor(
        public id?: number,
        public description?: string,
        public transferCondition?: TransferCondition,
        public transferBlocks?: TransferBlock,
        public model?: Model,
    ) { }
}
