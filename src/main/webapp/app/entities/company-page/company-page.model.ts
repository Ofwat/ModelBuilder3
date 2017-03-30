import { Model } from '../model';
export class CompanyPage {
    constructor(
        public id?: number,
        public companyCode?: string,
        public pageCode?: string,
        public model?: Model,
    ) { }
}
