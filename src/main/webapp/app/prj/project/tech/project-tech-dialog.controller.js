(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('ProjectTechDialogController', ProjectTechDialogController);

    ProjectTechDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectTech', 'User'];

    function ProjectTechDialogController($timeout, $scope, $stateParams, $uibModalInstance, entity, ProjectTech, User) {
        var vm = this;
        vm.isRootTech = entity.isRootTech;
        // vm.project = entity;
        // var parentTechId = null;
        // var rootTech = vm.project.rootTech;
        // if (rootTech && rootTech.parentTech)
        //     parentTechId = rootTech.parentTech.id;
        // rootTech = {
        //     id: rootTech ? rootTech.id : null,
        //     name: rootTech ? rootTech.name : null,
        //     descript: rootTech ? rootTech.descript : null,
        //     prjId: vm.project.id,
        //     parentTechId: parentTechId
        // };

        vm.technologyVM = entity.data2;
        // vm.technologyVM = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            vm.technologyVM = null;
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.technologyVM.id) {
                ProjectTech.update({
                    id: vm.technologyVM.prjId
                }, vm.technologyVM, onSaveSuccess, onSaveError);
            } else {
                ProjectTech.save({id: vm.technologyVM.prjId}, vm.technologyVM, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            //如果是根结点
            if (vm.isRootTech == true)
                entity.data1.rootTech = result;
            else {
                if (vm.technologyVM.id) {
                    var subTechs1 = entity.data1.subTechs;
                    result.subTechs = subTechs1;
                    entity.data1.name = result.name;
                    entity.data1.descript = result.descript;
                }
                else entity.data1.subTechs.push(result);
            }
            $uibModalInstance.close(result);
        }

        function onSaveError() {
            vm.isSaving = false;
        }


    }
})();
