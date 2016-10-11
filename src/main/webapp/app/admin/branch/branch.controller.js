(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('BranchController', BranchController);

    BranchController.$inject = ['$scope', '$state', 'Branch'];

    function BranchController ($scope, $state, Branch) {
        var vm = this;

        vm.branches = [];

        loadAll();

        function loadAll() {
            Branch.query(function(result) {
                vm.branches = result;
            });
        }
    }
})();
