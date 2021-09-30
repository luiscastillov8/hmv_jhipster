import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEstatus, Estatus } from '@/shared/model/estatus.model';
import EstatusService from './estatus.service';

const validations: any = {
  estatus: {
    nombre: {},
  },
};

@Component({
  validations,
})
export default class EstatusUpdate extends Vue {
  @Inject('estatusService') private estatusService: () => EstatusService;
  public estatus: IEstatus = new Estatus();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estatusId) {
        vm.retrieveEstatus(to.params.estatusId);
      }
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
    if (this.estatus.id) {
      this.estatusService()
        .update(this.estatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hmvJhipsterApp.estatus.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.estatusService()
        .create(this.estatus)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('hmvJhipsterApp.estatus.created', { param: param.id });
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

  public retrieveEstatus(estatusId): void {
    this.estatusService()
      .find(estatusId)
      .then(res => {
        this.estatus = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
