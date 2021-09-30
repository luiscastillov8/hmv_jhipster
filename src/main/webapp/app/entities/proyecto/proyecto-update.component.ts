import { Component, Vue, Inject } from 'vue-property-decorator';

import EstatusService from '@/entities/estatus/estatus.service';
import { IEstatus } from '@/shared/model/estatus.model';

import TareaService from '@/entities/tarea/tarea.service';
import { ITarea } from '@/shared/model/tarea.model';

import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from './proyecto.service';

const validations: any = {
  proyecto: {
    nombre: {},
  },
};

@Component({
  validations,
})
export default class ProyectoUpdate extends Vue {
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  public proyecto: IProyecto = new Proyecto();

  @Inject('estatusService') private estatusService: () => EstatusService;

  public estatuses: IEstatus[] = [];

  @Inject('tareaService') private tareaService: () => TareaService;

  public tareas: ITarea[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        vm.retrieveProyecto(to.params.proyectoId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.proyecto.id) {
      this.proyectoService()
        .update(this.proyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hmvJhipsterApp.proyecto.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.proyectoService()
        .create(this.proyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hmvJhipsterApp.proyecto.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveProyecto(proyectoId): void {
    this.proyectoService()
      .find(proyectoId)
      .then(res => {
        this.proyecto = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.estatusService()
      .retrieve()
      .then(res => {
        this.estatuses = res.data;
      });
    this.tareaService()
      .retrieve()
      .then(res => {
        this.tareas = res.data;
      });
  }
}
