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
        vm.users = User.queryAllTrlers();
        vm.clear = clear;
        vm.addTrler = addTrler;
        vm.removeTrler = removeTrler;
        vm.selectedTrler = null;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.close();
        }

        function addTrler (trlerLogin) {
            Project.addTrler({id:vm.project.id, trlerLogin:vm.selectedTrler.login},{},onAddSuccess, onSaveError);
        }

        function removeTrler (trlerLogin) {
            Project.removeTrler({id:vm.project.id, trlerLogin:trlerLogin},onRemoveSuccess, onSaveError);
        }

        function onAddSuccess (result) {
            vm.trlers.push(result);
            // $uibModalInstance.close(result);
        }
        function onRemoveSuccess (result) {
            var index = -1;
            for(var i=0;i<vm.trlers.length;i++) {
                if (vm.trlers[i].login == result.login) {
                    index = i;
                    break;
                }
            }
            if (index>=0)
                vm.trlers.splice(index, 1);
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
