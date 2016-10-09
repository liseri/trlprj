(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('ProjectDialogController', ProjectDialogController);

    ProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Project', 'Technology', 'User'];

    function ProjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Project, Technology, User) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.rootteches = Technology.query({filter: 'project-is-null'});
        $q.all([vm.project.$promise, vm.rootteches.$promise]).then(function() {
            if (!vm.project.rootTech || !vm.project.rootTech.id) {
                return $q.reject();
            }
            return Technology.get({id : vm.project.rootTech.id}).$promise;
        }).then(function(rootTech) {
            vm.rootteches.push(rootTech);
        });
        vm.users = User.query();
        vm.technologies = Technology.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveError);
            } else {
                Project.save(vm.project, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trlprjApp:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startTime = false;
        vm.datePickerOpenStatus.completeTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
