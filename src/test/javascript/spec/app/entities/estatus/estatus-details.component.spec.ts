/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EstatusDetailComponent from '@/entities/estatus/estatus-details.vue';
import EstatusClass from '@/entities/estatus/estatus-details.component';
import EstatusService from '@/entities/estatus/estatus.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Estatus Management Detail Component', () => {
    let wrapper: Wrapper<EstatusClass>;
    let comp: EstatusClass;
    let estatusServiceStub: SinonStubbedInstance<EstatusService>;

    beforeEach(() => {
      estatusServiceStub = sinon.createStubInstance<EstatusService>(EstatusService);

      wrapper = shallowMount<EstatusClass>(EstatusDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { estatusService: () => estatusServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEstatus = { id: 123 };
        estatusServiceStub.find.resolves(foundEstatus);

        // WHEN
        comp.retrieveEstatus(123);
        await comp.$nextTick();

        // THEN
        expect(comp.estatus).toBe(foundEstatus);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEstatus = { id: 123 };
        estatusServiceStub.find.resolves(foundEstatus);

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
