(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('MyprjTechTclController', MyprjTechTclController);

    MyprjTechTclController.$inject = ['$scope', '$state', '$uibModal', '$stateParams', 'TechTree', 'ProjectTech'];

    function MyprjTechTclController($scope, $state, $uibModal, $stateParams, TechTree, ProjectTech) {
        var vm = this;
        vm.prjId = $stateParams.id;
        vm.techId = $stateParams.techId;
        vm.node = $stateParams.node;
        vm.project = $scope.$parent.vm.currentPrj;
        vm.tech = $scope.$parent.vm.currentTech;

        vm.enable = true
        vm.tclValue = "1,1,1";

    }
})();
