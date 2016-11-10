(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('MyprjPrjController', MyprjPrjController);

    MyprjPrjController.$inject = ['$scope', '$state', 'MyProject'];

    function MyprjPrjController ($scope, $state, MyProject) {
        var vm = this;
        vm.prjs = MyProject.query();
        vm.clickTech = clickTech;



        function clickTech(project) {
            $state.go('techtree', {id:project.id});
            $scope.$parent.vm.currentPrj = project;
            $scope.$parent.vm.techClick();
        }
    }
})();
