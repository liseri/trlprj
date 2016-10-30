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
                    'id': 1,
                    'title': 'node1',
                    'nodes': [
                        {
                            'id': 11,
                            'title': 'node1.1',
                            'nodes': [
                                {
                                    'id': 111,
                                    'title': 'node1.1.1',
                                    'nodes': []
                                }
                            ]
                        },
                        {
                            'id': 12,
                            'title': 'node1.2',
                            'nodes': []
                        }
                    ]
                },
                {
                    'id': 2,
                    'title': 'node2',
                    'nodrop': true, // An arbitrary property to check in custom template for nodrop-enabled
                    'nodes': [
                        {
                            'id': 21,
                            'title': 'node2.1',
                            'nodes': []
                        },
                        {
                            'id': 22,
                            'title': 'node2.2',
                            'nodes': []
                        }
                    ]
                },
                {
                    'id': 3,
                    'title': 'node3',
                    'nodes': [
                        {
                            'id': 31,
                            'title': 'node3.1',
                            'nodes': []
                        }
                    ]
                }
            ];
        vm.expandAll = expandAll;
        vm.collapseAll = collapseAll;

        collapseAll();//todo 为啥不起作用


        function expandAll() {
            $scope.$broadcast('angular-ui-tree:expand-all');
        }

        function collapseAll() {
            $scope.$broadcast('angular-ui-tree:collapse-all');
        }
    }
})();
