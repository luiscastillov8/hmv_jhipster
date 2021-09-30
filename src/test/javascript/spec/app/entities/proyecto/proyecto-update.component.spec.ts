/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ProyectoUpdateComponent from '@/entities/proyecto/proyecto-update.vue';
import ProyectoClass from '@/entities/proyecto/proyecto-update.component';
import ProyectoService from '@/entities/proyecto/proyecto.service';

import EstatusService from '@/entities/estatus/estatus.service';

import TareaService from '@/entities/tarea/tarea.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Proyecto Management Update Component', () => {
    let wrapper: Wrapper<ProyectoClass>;
    let comp: ProyectoClass;
    let proyectoServiceStub: SinonStubbedInstance<ProyectoService>;

    beforeEach(() => {
      proyectoServiceStub = sinon.createStubInstance<ProyectoService>(ProyectoService);

      wrapper = shallowMount<ProyectoClass>(ProyectoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          proyectoService: () => proyectoServiceStub,

          estatusService: () => new EstatusService(),

          tareaService: () => new TareaService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.proyecto = entity;
        proyectoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proyectoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.proyecto = entity;
        proyectoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proyectoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProyecto = { id: 123 };
        proyectoServiceStub.find.resolves(foundProyecto);
        proyectoServiceStub.retrieve.resolves([foundProyecto]);

        // WHEN
        comp.beforeRouteEnter({ params: { proyectoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.proyecto).toBe(foundProyecto);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
