import { TransferBlockDetails } from '../transfer-block-details';
import { TransferBlockItem } from '../transfer-block-item';
import { Transfer } from '../transfer';
export class TransferBlock {
    constructor(
        public id?: number,
        public transferBlockDetails?: TransferBlockDetails,
        public transferBlockItems?: TransferBlockItem,
        public transfer?: Transfer,
    ) { }
}
