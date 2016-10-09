(function() {
    'use strict';

    angular
        .module('trlprjApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('brahch', {
            parent: 'admin',
            url: '/brahch',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trlprjApp.brahch.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/brahch/brahches.html',
                    controller: 'BrahchController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('brahch');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('brahch-detail', {
            parent: 'admin',
            url: '/brahch/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'trlprjApp.brahch.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/admin/brahch/brahch-detail.html',
                    controller: 'BrahchDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('brahch');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Brahch', function($stateParams, Brahch) {
                    return Brahch.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'brahch',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('brahch-detail.edit', {
            parent: 'brahch-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/brahch/brahch-dialog.html',
                    controller: 'BrahchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Brahch', function(Brahch) {
                            return Brahch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brahch.new', {
            parent: 'brahch',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/brahch/brahch-dialog.html',
                    controller: 'BrahchDialogController',
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
                    $state.go('brahch', null, { reload: 'brahch' });
                }, function() {
                    $state.go('brahch');
                });
            }]
        })
        .state('brahch.edit', {
            parent: 'brahch',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/brahch/brahch-dialog.html',
                    controller: 'BrahchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Brahch', function(Brahch) {
                            return Brahch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brahch', null, { reload: 'brahch' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brahch.delete', {
            parent: 'brahch',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/admin/brahch/brahch-delete-dialog.html',
                    controller: 'BrahchDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Brahch', function(Brahch) {
                            return Brahch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('brahch', null, { reload: 'brahch' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
