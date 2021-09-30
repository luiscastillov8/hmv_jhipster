import { IEstatus } from '@/shared/model/estatus.model';
import { ITarea } from '@/shared/model/tarea.model';

export interface IProyecto {
  id?: number;
  nombre?: string | null;
  estatus?: IEstatus | null;
  tareas?: ITarea[] | null;
}

export class Proyecto implements IProyecto {
  constructor(public id?: number, public nombre?: string | null, public estatus?: IEstatus | null, public tareas?: ITarea[] | null) {}
}
