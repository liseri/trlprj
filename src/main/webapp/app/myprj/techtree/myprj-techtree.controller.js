(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('MyprjTechTreeController', MyprjTechTreeController);

    MyprjTechTreeController.$inject = ['$scope', '$state', 'TechTree'];

    function MyprjTechTreeController($scope, $state, TechTree) {
        var vm = this;
        vm.project = $scope.$parent.vm.currentPrj;
        // vm.techs = TechTree.query();
        vm.data =
            [
                {
                    'id': '1',
                    'title': 'node1',
                    'nodes': [
                        {
                            'id': '1.1',
                            'title': 'node1.1',
                            'nodes': []
                        }
                    ]
                }
            ];
        vm.expandAll = expandAll;
        vm.collapseAll = collapseAll;

        $scope.remove = function (scope) {
            scope.remove();
        };

        $scope.toggle = function (scope) {
            scope.toggle();
        };

        $scope.moveLastToTheBeginning = function () {
            var a = $scope.data.pop();
            $scope.data.splice(0, 0, a);
        };

        $scope.newSubItem = function (scope) {
            var nodeData = scope.$modelValue;
            nodeData.nodes.push({
                id: nodeData.id * 10 + nodeData.nodes.length,
                title: nodeData.title + '.' + (nodeData.nodes.length + 1),
                nodes: []
            });
        };

        function expandAll() {
            $scope.$broadcast('angular-ui-tree:expand-all');
        }

        function collapseAll() {
            $scope.$broadcast('angular-ui-tree:collapse-all');
        }
    }
})();
