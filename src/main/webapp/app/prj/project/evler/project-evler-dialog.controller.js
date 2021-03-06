(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('ProjectEvlerDialogController', ProjectEvlerDialogController);

    ProjectEvlerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Project', 'User'];

    function ProjectEvlerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Project, User) {
        var vm = this;
        vm.project = entity;
        vm.evlers = vm.project.evlers;
        vm.users = User.queryAllEvlers();
        vm.clear = clear;
        vm.addEvler = addEvler;
        vm.removeEvler = removeEvler;
        vm.selectedEvler = null;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.close();
        }

        function addEvler (evlerLogin) {
            Project.addEvler({id:vm.project.id, evlerLogin:vm.selectedEvler.login},{},onAddSuccess, onSaveError);
        }

        function removeEvler (evlerLogin) {
            Project.removeEvler({id:vm.project.id, evlerLogin:evlerLogin},onRemoveSuccess, onSaveError);
        }

        function onAddSuccess (result) {
            vm.evlers.push(result);
            // $uibModalInstance.close(result);
        }
        function onRemoveSuccess (result) {
            var index = -1;
            for(var i=0;i<vm.evlers.length;i++) {
                if (vm.evlers[i].login == result.login) {
                    index = i;
                    break;
                }
            }
            if (index>=0)
                vm.evlers.splice(index, 1);
            // $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
