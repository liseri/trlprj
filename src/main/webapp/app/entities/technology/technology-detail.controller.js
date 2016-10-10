(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('TechnologyDetailController', TechnologyDetailController);

    TechnologyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Technology', 'User', 'Project'];

    function TechnologyDetailController($scope, $rootScope, $stateParams, previousState, entity, Technology, User, Project) {
        var vm = this;

        vm.technology = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trlprjApp:technologyUpdate', function(event, result) {
            vm.technology = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
