(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('branch', {
            parent: 'admin',
            url: '/branch',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'trlprjApp.branch.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/branch/branches.html',
                    controller: 'BranchController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('branch');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('branch-detail', {
            parent: 'admin',
            url: '/branch/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'trlprjApp.branch.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/branch/branch-detail.html',
                    controller: 'BranchDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('branch');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Branch', function($stateParams, Branch) {
                    return Branch.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'branch',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('branch-detail.edit', {
            parent: 'branch-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/branch/branch-dialog.html',
                    controller: 'BranchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Branch', function(Branch) {
                            return Branch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('branch.new', {
            parent: 'branch',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/branch/branch-dialog.html',
                    controller: 'BranchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('branch', null, { reload: 'branch' });
                }, function() {
                    $state.go('branch');
                });
            }]
        })
        .state('branch.edit', {
            parent: 'branch',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/branch/branch-dialog.html',
                    controller: 'BranchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Branch', function(Branch) {
                            return Branch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('branch', null, { reload: 'branch' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('branch.delete', {
            parent: 'branch',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/branch/branch-delete-dialog.html',
                    controller: 'BranchDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Branch', function(Branch) {
                            return Branch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('branch', null, { reload: 'branch' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
