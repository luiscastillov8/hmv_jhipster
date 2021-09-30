<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="estatus">
        <h2 class="jh-entity-heading" data-cy="estatusDetailsHeading">
          <span v-text="$t('hmvJhipsterApp.estatus.detail.title')">Estatus</span> {{ estatus.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hmvJhipsterApp.estatus.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ estatus.nombre }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="estatus.id" :to="{ name: 'EstatusEdit', params: { estatusId: estatus.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./estatus-details.component.ts"></script>
