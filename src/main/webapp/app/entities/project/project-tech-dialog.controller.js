(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('ProjectTechDialogController', ProjectTechDialogController);

    ProjectTechDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectTech', 'User'];

    function ProjectTechDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProjectTech, User) {
        var vm = this;

        vm.technologyVM = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.technologyVM.id) {
                ProjectTech.update(vm.technologyVM, onSaveSuccess, onSaveError);
            } else {
                ProjectTech.save(vm.technologyVM, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trlprjApp:technologyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
