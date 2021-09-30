/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import TareaUpdateComponent from '@/entities/tarea/tarea-update.vue';
import TareaClass from '@/entities/tarea/tarea-update.component';
import TareaService from '@/entities/tarea/tarea.service';

import EstatusService from '@/entities/estatus/estatus.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

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
  describe('Tarea Management Update Component', () => {
    let wrapper: Wrapper<TareaClass>;
    let comp: TareaClass;
    let tareaServiceStub: SinonStubbedInstance<TareaService>;

    beforeEach(() => {
      tareaServiceStub = sinon.createStubInstance<TareaService>(TareaService);

      wrapper = shallowMount<TareaClass>(TareaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          tareaService: () => tareaServiceStub,

          estatusService: () => new EstatusService(),

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.tarea = entity;
        tareaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tareaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tarea = entity;
        tareaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tareaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTarea = { id: 123 };
        tareaServiceStub.find.resolves(foundTarea);
        tareaServiceStub.retrieve.resolves([foundTarea]);

        // WHEN
        comp.beforeRouteEnter({ params: { tareaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tarea).toBe(foundTarea);
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
