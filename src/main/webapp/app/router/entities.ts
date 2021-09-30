import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Proyecto = () => import('@/entities/proyecto/proyecto.vue');
// prettier-ignore
const ProyectoUpdate = () => import('@/entities/proyecto/proyecto-update.vue');
// prettier-ignore
const ProyectoDetails = () => import('@/entities/proyecto/proyecto-details.vue');
// prettier-ignore
const Tarea = () => import('@/entities/tarea/tarea.vue');
// prettier-ignore
const TareaUpdate = () => import('@/entities/tarea/tarea-update.vue');
// prettier-ignore
const TareaDetails = () => import('@/entities/tarea/tarea-details.vue');
// prettier-ignore
const Estatus = () => import('@/entities/estatus/estatus.vue');
// prettier-ignore
const EstatusUpdate = () => import('@/entities/estatus/estatus-update.vue');
// prettier-ignore
const EstatusDetails = () => import('@/entities/estatus/estatus-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/proyecto',
    name: 'Proyecto',
    component: Proyecto,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proyecto/new',
    name: 'ProyectoCreate',
    component: ProyectoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proyecto/:proyectoId/edit',
    name: 'ProyectoEdit',
    component: ProyectoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/proyecto/:proyectoId/view',
    name: 'ProyectoView',
    component: ProyectoDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tarea',
    name: 'Tarea',
    component: Tarea,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tarea/new',
    name: 'TareaCreate',
    component: TareaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tarea/:tareaId/edit',
    name: 'TareaEdit',
    component: TareaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tarea/:tareaId/view',
    name: 'TareaView',
    component: TareaDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/estatus',
    name: 'Estatus',
    component: Estatus,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/estatus/new',
    name: 'EstatusCreate',
    component: EstatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/estatus/:estatusId/edit',
    name: 'EstatusEdit',
    component: EstatusUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/estatus/:estatusId/view',
    name: 'EstatusView',
    component: EstatusDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
