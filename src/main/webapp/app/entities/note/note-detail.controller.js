(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('NoteDetailController', NoteDetailController);

    NoteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Note'];

    function NoteDetailController($scope, $rootScope, $stateParams, previousState, entity, Note) {
        var vm = this;

        vm.note = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('trlprjApp:noteUpdate', function(event, result) {
            vm.note = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
