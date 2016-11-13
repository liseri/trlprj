(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('MyprjTechTreeController', MyprjTechTreeController);

    MyprjTechTreeController.$inject = ['$scope', '$state', '$uibModal', '$stateParams', 'TechTree', 'ProjectTech'];

    function MyprjTechTreeController($scope, $state, $uibModal, $stateParams, TechTree, ProjectTech) {
        var vm = this;
        vm.prjId = $stateParams.id;
        vm.project = $scope.$parent.vm.currentPrj;
        vm.techs = null;

        vm.expandAll = expandAll;
        vm.collapseAll = collapseAll;
        vm.openTechModal = openTechModal;
        vm.openSubcreatorModal = openSubcreatorModal;
        vm.removeTech = removeTech;
        vm.changeKey = changeKey;
        vm.jumpToKey = jumpToKey;

        TechTree.get({prjId: vm.prjId}, function (resp) {
            vm.techs = [resp];
        });

        function expandAll() {
            $scope.$broadcast('angular-ui-tree:expand-all');
        }

        function collapseAll() {
            $scope.$broadcast('angular-ui-tree:collapse-all');
        }
        $scope.toggle = function (scope) {
            scope.toggle();
        };

        $scope.moveLastToTheBeginning = function () {
            var a = $scope.data.pop();
            $scope.data.splice(0, 0, a);
        };


        function openTechModal(isAdd, tech, scope) {

            var modalInstance = $uibModal.open({
                templateUrl: 'app/prj/project/tech/project-tech-dialog.html',
                controller: 'ProjectTechDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: {
                        isRootTech: false,
                        data1: scope.$modelValue,
                        data2: {
                            id: isAdd ? null: tech.id,
                            name: isAdd ? null: tech.name,
                            descript: isAdd ? null: tech.descript,
                            prjId: tech.prjId,
                            parentTechId: isAdd? tech.id: tech.parentTechId
                        }
                    }
                }
            });
        }

        function openSubcreatorModal(tech, scope) {

            var modalInstance = $uibModal.open({
                templateUrl: 'app/myprj/techtree/subcreator/tech-subcreator-dialog.html',
                controller: 'TechSubcreatorDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: {tech:tech, node:scope.$modelValue}
                }
            });
        }

        function removeTech(tech, scope) {
            ProjectTech.delete({id: tech.prjId, techId: tech.id}, function () {
                scope.remove();
            });
        }

        function changeKey(tech, key, scope) {
            ProjectTech.changeKey({id: tech.prjId, techId: tech.id, isKey: key},{}, function () {
                scope.$modelValue.key = key;
            });
        }

        function jumpToKey(tech, scope) {
            $state.go('techkey', {id:tech.prjId, techId: tech.id});
            $scope.$parent.vm.currentTech = tech;
            $scope.$parent.vm.currentKeyNode = scope.$modelValue;
            $scope.$parent.vm.keyClick();
        }

    }
})();
