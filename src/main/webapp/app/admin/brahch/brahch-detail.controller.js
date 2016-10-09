(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('BrahchDetailController', BrahchDetailController);

    BrahchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Brahch'];

    function BrahchDetailController($scope, $rootScope, $stateParams, previousState, entity, Brahch) {
        var vm = this;

        vm.brahch = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trlprjApp:brahchUpdate', function(event, result) {
            vm.brahch = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
