import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITarea } from '@/shared/model/tarea.model';
import TareaService from './tarea.service';

@Component
export default class TareaDetails extends Vue {
  @Inject('tareaService') private tareaService: () => TareaService;
  public tarea: ITarea = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tareaId) {
        vm.retrieveTarea(to.params.tareaId);
      }
    });
  }

  public retrieveTarea(tareaId) {
    this.tareaService()
      .find(tareaId)
      .then(res => {
        this.tarea = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
