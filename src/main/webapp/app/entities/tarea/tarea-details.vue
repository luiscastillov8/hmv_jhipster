<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tarea">
        <h2 class="jh-entity-heading" data-cy="tareaDetailsHeading">
          <span v-text="$t('hmvJhipsterApp.tarea.detail.title')">Tarea</span> {{ tarea.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('hmvJhipsterApp.tarea.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ tarea.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('hmvJhipsterApp.tarea.descripcion')">Descripcion</span>
          </dt>
          <dd>
            <span>{{ tarea.descripcion }}</span>
          </dd>
          <dt>
            <span v-text="$t('hmvJhipsterApp.tarea.estatus')">Estatus</span>
          </dt>
          <dd>
            <div v-if="tarea.estatus">
              <router-link :to="{ name: 'EstatusView', params: { estatusId: tarea.estatus.id } }">{{ tarea.estatus.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('hmvJhipsterApp.tarea.tarea')">Tarea</span>
          </dt>
          <dd>
            <div v-if="tarea.tarea">
              <router-link :to="{ name: 'ProyectoView', params: { proyectoId: tarea.tarea.id } }">{{ tarea.tarea.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="tarea.id" :to="{ name: 'TareaEdit', params: { tareaId: tarea.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./tarea-details.component.ts"></script>
