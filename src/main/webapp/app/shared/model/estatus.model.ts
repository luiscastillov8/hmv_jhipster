export interface IEstatus {
  id?: number;
  nombre?: string | null;
}

export class Estatus implements IEstatus {
  constructor(public id?: number, public nombre?: string | null) {}
}
