import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IEstatus } from '@/shared/model/estatus.model';

import EstatusService from './estatus.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Estatus extends Vue {
  @Inject('estatusService') private estatusService: () => EstatusService;
  private removeId: number = null;

  public estatuses: IEstatus[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllEstatuss();
  }

  public clear(): void {
    this.retrieveAllEstatuss();
  }

  public retrieveAllEstatuss(): void {
    this.isFetching = true;
    this.estatusService()
      .retrieve()
      .then(
        res => {
          this.estatuses = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IEstatus): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeEstatus(): void {
    this.estatusService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('hmvJhipsterApp.estatus.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllEstatuss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
