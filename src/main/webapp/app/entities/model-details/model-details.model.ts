export class ModelDetails {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public version?: string,
        public modelType?: string,
        public textCode?: string,
        public baseYearCode?: string,
        public reportYearCode?: string,
        public allowDataChanges?: boolean,
        public modelFamilyCode?: string,
        public modelFamilyParent?: boolean,
        public displayOrder?: number,
        public branchTag?: string,
        public runCode?: string,
        public lastModified?: any,
        public created?: any,
        public createdBy?: string,
        public lastModifiedBy?: string,
        public fountainModelId?: number,
    ) { }
}
