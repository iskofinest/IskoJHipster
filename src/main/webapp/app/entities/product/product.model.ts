import { BaseEntity } from './../../shared';

export class Product implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public reference?: string,
        public category?: string,
        public description?: string,
        public brand?: string,
        public model?: string,
        public quantity?: number,
        public unit?: string,
        public product_date?: any,
        public originalPrice?: number,
        public sellingPrice?: number,
        public agent?: string,
        public suppliers?: BaseEntity[],
    ) {
    }
}
