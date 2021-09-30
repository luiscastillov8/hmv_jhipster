import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEstatus } from '@/shared/model/estatus.model';
import EstatusService from './estatus.service';

@Component
export default class EstatusDetails extends Vue {
  @Inject('estatusService') private estatusService: () => EstatusService;
  public estatus: IEstatus = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estatusId) {
        vm.retrieveEstatus(to.params.estatusId);
      }
    });
  }

  public retrieveEstatus(estatusId) {
    this.estatusService()
      .find(estatusId)
      .then(res => {
        this.estatus = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
