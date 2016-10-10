(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('BrahchDialogController', BrahchDialogController);

    BrahchDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Brahch'];

    function BrahchDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Brahch) {
        var vm = this;

        vm.brahch = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.brahch.id !== null) {
                Brahch.update(vm.brahch, onSaveSuccess, onSaveError);
            } else {
                Brahch.save(vm.brahch, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('trlprjApp:brahchUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
