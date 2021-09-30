<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hmvJhipsterApp.proyecto.home.createOrEditLabel"
          data-cy="ProyectoCreateUpdateHeading"
          v-text="$t('hmvJhipsterApp.proyecto.home.createOrEditLabel')"
        >
          Create or edit a Proyecto
        </h2>
        <div>
          <div class="form-group" v-if="proyecto.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="proyecto.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hmvJhipsterApp.proyecto.nombre')" for="proyecto-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="proyecto-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.proyecto.nombre.$invalid, invalid: $v.proyecto.nombre.$invalid }"
              v-model="$v.proyecto.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hmvJhipsterApp.proyecto.estatus')" for="proyecto-estatus">Estatus</label>
            <select class="form-control" id="proyecto-estatus" data-cy="estatus" name="estatus" v-model="proyecto.estatus">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="proyecto.estatus && estatusOption.id === proyecto.estatus.id ? proyecto.estatus : estatusOption"
                v-for="estatusOption in estatuses"
                :key="estatusOption.id"
              >
                {{ estatusOption.id }}
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
            :disabled="$v.proyecto.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./proyecto-update.component.ts"></script>
