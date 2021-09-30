<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="hmvJhipsterApp.estatus.home.createOrEditLabel"
          data-cy="EstatusCreateUpdateHeading"
          v-text="$t('hmvJhipsterApp.estatus.home.createOrEditLabel')"
        >
          Create or edit a Estatus
        </h2>
        <div>
          <div class="form-group" v-if="estatus.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="estatus.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('hmvJhipsterApp.estatus.nombre')" for="estatus-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="estatus-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.estatus.nombre.$invalid, invalid: $v.estatus.nombre.$invalid }"
              v-model="$v.estatus.nombre.$model"
            />
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
            :disabled="$v.estatus.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./estatus-update.component.ts"></script>
