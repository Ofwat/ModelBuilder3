export class TransferCondition {
    constructor(
        public id?: number,
        public itemCode?: string,
        public yearCode?: string,
        public value?: string,
        public failureMessage?: string,
    ) { }
}
