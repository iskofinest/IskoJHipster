import { BaseEntity } from './../../shared';

export class Supplier implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public contactPerson?: string,
        public contactDetails?: string,
        public products?: BaseEntity[],
    ) {
    }
}
