import { YearCode } from '../year-code';
import { TransferBlock } from '../transfer-block';
export class TransferBlockItem {
    constructor(
        public id?: number,
        public itemCode?: string,
        public companyType?: string,
        public yearCodes?: YearCode,
        public transferBLock?: TransferBlock,
    ) { }
}
