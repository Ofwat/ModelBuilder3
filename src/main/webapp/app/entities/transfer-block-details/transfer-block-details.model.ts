export class TransferBlockDetails {
    constructor(
        public id?: number,
        public fromModelCode?: string,
        public fromVersionCode?: string,
        public fromPageCode?: string,
        public toModelCode?: string,
        public toVersionCode?: string,
        public toPageCode?: string,
        public toMacroCode?: string,
    ) { }
}
