import { Component, Vue, Inject } from 'vue-property-decorator';

import EstatusService from '@/entities/estatus/estatus.service';
import { IEstatus } from '@/shared/model/estatus.model';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import { ITarea, Tarea } from '@/shared/model/tarea.model';
import TareaService from './tarea.service';

const validations: any = {
  tarea: {
    nombre: {},
    descripcion: {},
  },
};

@Component({
  validations,
})
export default class TareaUpdate extends Vue {
  @Inject('tareaService') private tareaService: () => TareaService;
  public tarea: ITarea = new Tarea();

  @Inject('estatusService') private estatusService: () => EstatusService;

  public estatuses: IEstatus[] = [];

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tareaId) {
        vm.retrieveTarea(to.params.tareaId);
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
    if (this.tarea.id) {
      this.tareaService()
        .update(this.tarea)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hmvJhipsterApp.tarea.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.tareaService()
        .create(this.tarea)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hmvJhipsterApp.tarea.created', { param: param.id });
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

  public retrieveTarea(tareaId): void {
    this.tareaService()
      .find(tareaId)
      .then(res => {
        this.tarea = res;
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
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
