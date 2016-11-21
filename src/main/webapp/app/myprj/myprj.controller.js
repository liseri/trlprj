(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('MyprjController', MyprjController);

    MyprjController.$inject = ['$scope', '$state'];

    function MyprjController ($scope, $state) {
        var vm = this;
        vm.currentPrj = null;
        vm.currentTech = null;
        vm.currentKeyNode = null;

        vm.backToPrj = backToPrj;
        vm.backToTechtree = backToTechtree;

        $state.go('prj');


        function backToPrj() {
            vm.currentPrj = null;
            vm.currentTech = null;
            vm.currentKeyNode = null;
            $state.go('prj');
        }

        function backToTechtree() {
            vm.currentTech = null;
            vm.currentKeyNode = null;
            $state.go('techtree', {id:vm.currentPrj.id});
        }
    }
})();
