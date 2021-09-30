import { IEstatus } from '@/shared/model/estatus.model';
import { IProyecto } from '@/shared/model/proyecto.model';

export interface ITarea {
  id?: number;
  nombre?: string | null;
  descripcion?: string | null;
  estatus?: IEstatus | null;
  tarea?: IProyecto | null;
}

export class Tarea implements ITarea {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public descripcion?: string | null,
    public estatus?: IEstatus | null,
    public tarea?: IProyecto | null
  ) {}
}
