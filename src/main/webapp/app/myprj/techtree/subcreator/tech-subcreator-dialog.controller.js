(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('TechSubcreatorDialogController', TechSubcreatorDialogController);

    TechSubcreatorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectTech', 'User'];

    function TechSubcreatorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProjectTech, User) {
        var vm = this;
        vm.technology = entity.tech;
        vm.subcreators = vm.technology.subCreator;
        vm.users = User.queryAllGeneralUsers();
        vm.clear = clear;
        vm.addSubcreator = addSubcreator;
        vm.removeSubcreator = removeSubcreator;
        vm.selectedSubcreator = null;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.close();
        }

        function addSubcreator () {
            ProjectTech.addSubcreator({id:vm.technology.prjId, techId:vm.technology.id, userLogin:vm.selectedSubcreator.login},{},function (result) {
                replaceArr(vm.subcreators, result);
                replaceArr(entity.node, result);
                vm.isSaving = true;
            }, function () {
                vm.isSaving = false;
            });
        }

        function removeSubcreator (user) {
            ProjectTech.removeSubcreator({id:vm.technology.id, techId:vm.technology.id, userFullName:user}, function (result) {
                replaceArr(vm.subcreators, result);
                replaceArr(entity.node, result);
                vm.isSaving = true;
            }, function () {
                vm.isSaving = false;
            });
        }

        function replaceArr(oldArr, newArr) {
            oldArr.length = 0;
            if (newArr.length>0) {
                for(var i=0; i<newArr.length; i++) {
                    oldArr.push(newArr[i]);
                }
            }
        }
        function removeFromArr(arr, data) {
            var index = arr.indexOf(data);
            if (index >=0)
                arr.splice(index);
        }
    }
})();
