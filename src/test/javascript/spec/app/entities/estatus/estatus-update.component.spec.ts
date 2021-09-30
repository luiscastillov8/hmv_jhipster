/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import EstatusUpdateComponent from '@/entities/estatus/estatus-update.vue';
import EstatusClass from '@/entities/estatus/estatus-update.component';
import EstatusService from '@/entities/estatus/estatus.service';

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
  describe('Estatus Management Update Component', () => {
    let wrapper: Wrapper<EstatusClass>;
    let comp: EstatusClass;
    let estatusServiceStub: SinonStubbedInstance<EstatusService>;

    beforeEach(() => {
      estatusServiceStub = sinon.createStubInstance<EstatusService>(EstatusService);

      wrapper = shallowMount<EstatusClass>(EstatusUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          estatusService: () => estatusServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.estatus = entity;
        estatusServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(estatusServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.estatus = entity;
        estatusServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(estatusServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEstatus = { id: 123 };
        estatusServiceStub.find.resolves(foundEstatus);
        estatusServiceStub.retrieve.resolves([foundEstatus]);

        // WHEN
        comp.beforeRouteEnter({ params: { estatusId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.estatus).toBe(foundEstatus);
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
