(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('MyprjPrjController', MyprjPrjController);

    MyprjPrjController.$inject = ['$scope', '$state', 'MyProject'];

    function MyprjPrjController ($scope, $state, MyProject) {
        var vm = this;
        vm.prjs = MyProject.query();

    }
})();
