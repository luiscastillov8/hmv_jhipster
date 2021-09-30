<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hmvJhipsterApp.tarea.home.createOrEditLabel"
          data-cy="TareaCreateUpdateHeading"
          v-text="$t('hmvJhipsterApp.tarea.home.createOrEditLabel')"
        >
          Create or edit a Tarea
        </h2>
        <div>
          <div class="form-group" v-if="tarea.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="tarea.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hmvJhipsterApp.tarea.nombre')" for="tarea-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="tarea-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.tarea.nombre.$invalid, invalid: $v.tarea.nombre.$invalid }"
              v-model="$v.tarea.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hmvJhipsterApp.tarea.descripcion')" for="tarea-descripcion">Descripcion</label>
            <input
              type="text"
              class="form-control"
              name="descripcion"
              id="tarea-descripcion"
              data-cy="descripcion"
              :class="{ valid: !$v.tarea.descripcion.$invalid, invalid: $v.tarea.descripcion.$invalid }"
              v-model="$v.tarea.descripcion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hmvJhipsterApp.tarea.estatus')" for="tarea-estatus">Estatus</label>
            <select class="form-control" id="tarea-estatus" data-cy="estatus" name="estatus" v-model="tarea.estatus">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="tarea.estatus && estatusOption.id === tarea.estatus.id ? tarea.estatus : estatusOption"
                v-for="estatusOption in estatuses"
                :key="estatusOption.id"
              >
                {{ estatusOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hmvJhipsterApp.tarea.tarea')" for="tarea-tarea">Tarea</label>
            <select class="form-control" id="tarea-tarea" data-cy="tarea" name="tarea" v-model="tarea.tarea">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="tarea.tarea && proyectoOption.id === tarea.tarea.id ? tarea.tarea : proyectoOption"
                v-for="proyectoOption in proyectos"
                :key="proyectoOption.id"
              >
                {{ proyectoOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.tarea.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./tarea-update.component.ts"></script>
