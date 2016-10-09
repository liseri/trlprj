(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('NoteDialogController', NoteDialogController);

    NoteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Note'];

    function NoteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Note) {
        var vm = this;

        vm.note = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.note.id !== null) {
                Note.update(vm.note, onSaveSuccess, onSaveError);
            } else {
                Note.save(vm.note, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trlprjApp:noteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
