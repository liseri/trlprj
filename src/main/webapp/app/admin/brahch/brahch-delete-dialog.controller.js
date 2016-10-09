(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('BrahchDeleteController',BrahchDeleteController);

    BrahchDeleteController.$inject = ['$uibModalInstance', 'entity', 'Brahch'];

    function BrahchDeleteController($uibModalInstance, entity, Brahch) {
        var vm = this;

        vm.brahch = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Brahch.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
