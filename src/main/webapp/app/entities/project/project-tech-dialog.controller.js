(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('ProjectTechDialogController', ProjectTechDialogController);

    ProjectTechDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectTech', 'User'];

    function ProjectTechDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProjectTech, User) {
        var vm = this;
        vm.project = entity;
        var parentTechId = null;
        var rootTech = vm.project.rootTech;
        if (rootTech && rootTech.parentTech)
            parentTechId = rootTech.parentTech.id;
        rootTech = {id:rootTech?rootTech.id:null,
                name:rootTech?rootTech.name:null,
                descript:rootTech?rootTech.descript:null,
                prjId:vm.project.id,
                parentTechId:parentTechId};

        vm.technologyVM = rootTech;
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
                ProjectTech.update({id:vm.technologyVM.prjId, techId:vm.technologyVM.id},vm.technologyVM, onSaveSuccess, onSaveError);
            } else {
                ProjectTech.save({id:vm.technologyVM.prjId}, vm.technologyVM, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            vm.project.rootTech = result.rootTech;
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
