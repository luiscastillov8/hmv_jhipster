/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EstatusComponent from '@/entities/estatus/estatus.vue';
import EstatusClass from '@/entities/estatus/estatus.component';
import EstatusService from '@/entities/estatus/estatus.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Estatus Management Component', () => {
    let wrapper: Wrapper<EstatusClass>;
    let comp: EstatusClass;
    let estatusServiceStub: SinonStubbedInstance<EstatusService>;

    beforeEach(() => {
      estatusServiceStub = sinon.createStubInstance<EstatusService>(EstatusService);
      estatusServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EstatusClass>(EstatusComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          estatusService: () => estatusServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      estatusServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEstatuss();
      await comp.$nextTick();

      // THEN
      expect(estatusServiceStub.retrieve.called).toBeTruthy();
      expect(comp.estatuses[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      estatusServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEstatus();
      await comp.$nextTick();

      // THEN
      expect(estatusServiceStub.delete.called).toBeTruthy();
      expect(estatusServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
