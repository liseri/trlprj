(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('TechnologyDialogController', TechnologyDialogController);

    TechnologyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Technology', 'User', 'Project'];

    function TechnologyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Technology, User, Project) {
        var vm = this;

        vm.technology = entity;
        vm.clear = clear;
        vm.save = save;
        vm.technologies = Technology.query();
        vm.users = User.query();
        vm.projects = Project.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.technology.id !== null) {
                Technology.update(vm.technology, onSaveSuccess, onSaveError);
            } else {
                Technology.save(vm.technology, onSaveSuccess, onSaveError);
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
