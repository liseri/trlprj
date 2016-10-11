(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('BranchDetailController', BranchDetailController);

    BranchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Branch'];

    function BranchDetailController($scope, $rootScope, $stateParams, previousState, entity, Branch) {
        var vm = this;

        vm.branch = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trlprjApp:branchUpdate', function(event, result) {
            vm.branch = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
