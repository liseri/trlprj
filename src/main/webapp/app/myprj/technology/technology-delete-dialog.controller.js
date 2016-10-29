(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('TechnologyDeleteController',TechnologyDeleteController);

    TechnologyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Technology'];

    function TechnologyDeleteController($uibModalInstance, entity, Technology) {
        var vm = this;

        vm.technology = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Technology.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
