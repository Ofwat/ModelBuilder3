import { Line } from '../line';
export class Cell {
    constructor(
        public id?: number,
        public code?: string,
        public year?: string,
        public equation?: string,
        public type?: string,
        public cgType?: string,
        public line?: Line,
    ) { }
}
