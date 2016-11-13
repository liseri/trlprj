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
        vm.isPrjAction = false;
        vm.isTechAction = false;
        vm.isKeyAction = false;
        vm.prjClick = prjClick;
        vm.techClick = techClick;
        vm.keyClick = keyClick;
        openPrj();

        function prjClick(){
            if (vm.isPrjAction == true) {
                vm.isPrjAction = false;
            } else {
                openPrj();
            }
        }

        function techClick() {
            if (vm.isTechAction == true) {
                vm.isTechAction = false;
            } else {
                openTech();
            }
        }

        function keyClick() {
            if (vm.isKeyAction == true) {
                vm.isKeyAction = false;
            } else {
                openKey();
            }
        }

        function openPrj() {
            vm.isPrjAction = true;
            vm.isTechAction = false;
            vm.isKeyAction = false;
            try{
                $state.go('prj');
            }catch (e) {
                console.error(e);
            }

        }

        function openTech() {
            vm.isPrjAction = false;
            vm.isTechAction = true;
            vm.isKeyAction = false;
        }

        function openKey() {
            vm.isPrjAction = false;
            vm.isTechAction = false;
            vm.isKeyAction = true;
        }
    }
})();
