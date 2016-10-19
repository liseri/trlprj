(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('ProjectTrlerDialogController', ProjectTrlerDialogController);

    ProjectTrlerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Project', 'User'];

    function ProjectTrlerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Project, User) {
        var vm = this;
        vm.project = entity;
        vm.trlers = vm.project.trlers;
        vm.users = User.query();
        vm.clear = clear;
        vm.addTrler = addTrler;
        vm.removeTrler = removeTrler;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function addTrler (trlerLogin) {
            vm.isSaving = true;
            Project.addTrler({id:vm.vm.project.id, trlerLogin:trlerLogin},onAddSuccess, onSaveError);
        }

        function removeTrler (trlerLogin) {
            vm.isSaving = true;
            Project.removeTrler({id:vm.vm.project.id, trlerLogin:trlerLogin},onRemoveSuccess, onSaveError);
        }

        function onAddSuccess (result) {
            if (vm.project.trlers)
                vm.project.trlers = [];
            vm.project.trlers.add(result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }
        function onRemoveSuccess (result) {
            if (vm.project.trlers)
                vm.project.trlers = [];
            vm.project.trlers.remove(result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
