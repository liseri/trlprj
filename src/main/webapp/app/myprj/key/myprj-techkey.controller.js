(function () {
    'use strict';

    angular
        .module('trlprjApp')
        .controller('MyprjTechKeyController', MyprjTechKeyController);

    MyprjTechKeyController.$inject = ['$scope', '$state', '$uibModal', '$stateParams', 'TechKey', 'ProjectTech'];

    function MyprjTechKeyController($scope, $state, $uibModal, $stateParams, TechKey, ProjectTech) {
        var vm = this;
        vm.prjId = $stateParams.id;
        vm.techId = $stateParams.techId;
        vm.node = $scope.$parent.vm.currentKeyNode;
        vm.project = $scope.$parent.vm.currentPrj;
        vm.tech = $scope.$parent.vm.currentTech;
        vm.getTclValue = getTclValue;
        vm.saveTclValue = saveTclValue;
        vm.getTrlValue = getTrlValue;
        vm.saveTrlValue = saveTrlValue;
        vm.clickTclReply = clickTclReply;
        vm.clickTrlReply = clickTrlReply;

        vm.myUserFullName = "";
        vm.myUserType = "";

        vm.tclValue1 = '';
        vm.tclValue2 = '';
        vm.tclValue3 = '';
        vm.tclValue4 = '';
        vm.tclValueEnable = true;

        vm.trlValue1a = '';
        vm.trlValue1b = '';
        vm.trlValue1c = '';
        vm.trlValue2a = '';
        vm.trlValue2b = '';
        vm.trlValue2c = '';
        vm.trlValue3a = '';
        vm.trlValue3b = '';
        vm.trlValue3c = '';
        vm.trlValue4a = '';
        vm.trlValue4b = '';
        vm.trlValue4c = '';
        vm.trlValue5a = '';
        vm.trlValue5b = '';
        vm.trlValue5c = '';
        vm.trlValue6a = '';
        vm.trlValue6b = '';
        vm.trlValue6c = '';
        vm.trlValue7a = '';
        vm.trlValue7b = '';
        vm.trlValue7c = '';
        vm.trlValue8a = '';
        vm.trlValue8b = '';
        vm.trlValue8c = '';
        vm.trlValue9a = '';
        vm.trlValue9b = '';
        vm.trlValue9c = '';
        vm.trlValueEnable = true;

        vm.creatorKeyValue;
        vm.creatorTclValue;
        vm.creatorTrlValue;


        vm.replyKeyToId = null;
        vm.replyKeyUserFullName='';
        vm.replyKeyUserType='';
        vm.replyKeyValue='';
        vm.replyKeyNote='';
        vm.replyKeyEnable = false;
        vm.replyKeyEnableValue = false;
        vm.replyTclToId = null;
        vm.replyTclUserFullName='';
        vm.replyTclUserType='';
        vm.replyTclValue='';
        vm.replyTclNote='';
        vm.replyTclEnable = false;
        vm.replyTclEnableValue = false;
        vm.replyTrlToId = null;
        vm.replyTrlUserFullName='';
        vm.replyTrlUserType='';
        vm.replyTrlValue='';
        vm.replyTrlNote='';

        vm.keyValues = null;
        vm.tclValues = null;
        vm.trlValues = null;
        vm.replyKeyUser = {userFullName:'', userType:''};


        function getTclValue() {
            TechKey.get({techId: vm.techId, type: "TCL" }, function (resp) {
                vm.tclValues = resp;
                //创建者数据
                vm.creatorTclValue = resp.creatorValue;
                vm.myUserFullName = resp.myUserFullName;
                vm.myUserType = resp.myUserType;
                vm.replyTclToId = null;
                vm.replyTclUserFullName = '';
                vm.replyTclUserType='';
                vm.replyTclValue = '';
                vm.replyTclNote = '';
                //只有创建者已经提交或未提交但用户是创建者  才可见
                vm.replyTclEnable = (vm.creatorTclValue && vm.creatorTclValue.id) || vm.myUserType == 'ROLE_USER';
                //只有未提交的创建者或评审人  才可见
                vm.replyTclEnableValue = (!(vm.creatorTclValue && vm.creatorTclValue.id) && vm.myUserType == 'ROLE_USER') || vm.myUserType == 'ROLE_EVL';
                //
                if (resp.creatorValue && resp.creatorValue.value2 && resp.creatorValue.value2.length >0) {
                    var tclValueArray= new Array(); //
                    tclValueArray=resp.creatorValue.value2.split(";"); //字符分割
                    for (var i=0;i<tclValueArray.length ;i++ )
                    {
                        if (tclValueArray[i].indexOf('1,') ==0) {
                            vm.tclValue1 = tclValueArray[i];
                        }
                        else if (tclValueArray[i].indexOf('2,') ==0) {
                            vm.tclValue2 = tclValueArray[i];
                        }
                        else if (tclValueArray[i].indexOf('3,') ==0) {
                            vm.tclValue3 = tclValueArray[i];
                        }
                        else if (tclValueArray[i].indexOf('4,') ==0) {
                            vm.tclValue4 = tclValueArray[i];
                        }
                    }
                }
                else {
                    if (vm.myUserType == "ROLE_USER")
                        vm.tclValueEnable = false;
                }

            });

        }
        function getTrlValue() {
            TechKey.get({techId: vm.techId, type: "TRL" }, function (resp) {
                vm.trlValues = resp;
                //创建者数据
                vm.creatorTrlValue = resp.creatorValue;
                vm.myUserFullName = resp.myUserFullName;
                vm.myUserType = resp.myUserType;
                vm.replyTrlToId = null;
                vm.replyTrlUserFullName = '';
                vm.replyTrlUserType='';
                vm.replyTrlValue = '';
                vm.replyTrlNote = '';
                //只有创建者已经提交或未提交但用户是创建者  才可见
                vm.replyTrlEnable = vm.creatorTrlValue.id || vm.myUserType == 'ROLE_USER';
                //只有未提交的创建者或评审人  才可见
                vm.replyTrlEnableValue = (!vm.creatorTrlValue.id && vm.myUserType == 'ROLE_USER') || vm.myUserType == 'ROLE_EVL';
                //
                if (resp.creatorValue.value2 && resp.creatorValue.value2.length >0) {
                    var trlValueArray= new Array(); //
                    trlValueArray=resp.creatorValue.value2.split(")!@#("); //字符分割
                    for (var i=0;i<trlValueArray.length ;i++ )
                    {
                        var index = trlValueArray[i].indexOf('1,a,');
                        if (index ==0) {
                            vm.trlValue1a = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('1,b,');
                        if (index ==0) {
                            vm.trlValue1b = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('1,c,');
                        if (index ==0) {
                            vm.trlValue1c = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('2,a,');
                        if (index ==0) {
                            vm.trlValue2a = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('2,b,');
                        if (index ==0) {
                            vm.trlValue2b = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('2,c,');
                        if (index ==0) {
                            vm.trlValue2c = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('3,a,');
                        if (index ==0) {
                            vm.trlValue3a = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('3,b,');
                        if (index ==0) {
                            vm.trlValue3b = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('3,c,');
                        if (index ==0) {
                            vm.trlValue3c = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('4,a,');
                        if (index ==0) {
                            vm.trlValue4a = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('4,b,');
                        if (index ==0) {
                            vm.trlValue4b = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('4,c,');
                        if (index ==0) {
                            vm.trlValue4c = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('5,a,');
                        if (index ==0) {
                            vm.trlValue5a = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('5,b,');
                        if (index ==0) {
                            vm.trlValue5b = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('5,c,');
                        if (index ==0) {
                            vm.trlValue5c = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('6,a,');
                        if (index ==0) {
                            vm.trlValue6a = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('6,b,');
                        if (index ==0) {
                            vm.trlValue6b = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('6,c,');
                        if (index ==0) {
                            vm.trlValue6c = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('7,a,');
                        if (index ==0) {
                            vm.trlValue7a = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('7,b,');
                        if (index ==0) {
                            vm.trlValue7b = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('7,c,');
                        if (index ==0) {
                            vm.trlValue7c = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('8,a,');
                        if (index ==0) {
                            vm.trlValue8a = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('8,b,');
                        if (index ==0) {
                            vm.trlValue8b = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('8,c,');
                        if (index ==0) {
                            vm.trlValue8c = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('9,a,');
                        if (index ==0) {
                            vm.trlValue9a = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('9,b,');
                        if (index ==0) {
                            vm.trlValue9b = trlValueArray[i].substring(4);
                            continue;
                        }
                        index = trlValueArray[i].indexOf('9,c,');
                        if (index ==0) {
                            vm.trlValue9c = trlValueArray[i].substring(4);
                            continue;
                        }
                    }
                }
                else {
                    if (vm.myUserType == "ROLE_USER")
                        vm.trlValueEnable = false;
                }

            });

        }
        function clickToSelectTclValue() {

        }
        function clickTclReply(toId, toUserFullName, toUserType) {
            vm.replyTclToId = toId;
            vm.replyTclUserFullName=toUserFullName;
            vm.replyTclUserType=toUserType;
        }
        function clickTrlReply(toId, toUserFullName, toUserType) {
            vm.replyTrlToId = toId;
            vm.replyTrlUserFullName=toUserFullName;
            vm.replyTrlUserType=toUserType;
        }
        function saveTclValue() {
            TechKey.save({techId: vm.techId, type: "TCL" }, {
                techId: vm.techId,
                keyValueType: 'TCL',
                toId: vm.replyTclToId,
                value: vm.replyTclValue,
                value2: vm.tclValue1Enable==true?"":vm.tclValue1+';'+vm.tclValue2+';'+vm.tclValue3+';'+vm.tclValue4,
                note: vm.replyTclNote
            }, function (resp) {
                vm.node.tcl = vm.replyTclValue;
                getTclValue();
            });
        }
        function saveTrlValue() {
            if (checkTrlValue() == false)
                return;
            TechKey.save({techId: vm.techId, type: "TRL" }, {
                techId: vm.techId,
                keyValueType: 'TRL',
                toId: vm.replyTrlToId,
                value: vm.replyTrlValue,
                value2: vm.trlValueEnable==true?"":getTrlValueStr(),
                note: vm.replyTrlNote
            }, function (resp) {
                vm.node.trl = vm.replyTrlValue;
                getTrlValue();
            });
        }
        function checkTrlValue() {
            return vm.trlValue1a.length>0 && vm.trlValue1b.length>0 && vm.trlValue1c.length>0 &&
                vm.trlValue2a.length>0 && vm.trlValue2b.length>0 && vm.trlValue2c.length>0 &&
                vm.trlValue3a.length>0 && vm.trlValue3b.length>0 && vm.trlValue3c.length>0 &&
                vm.trlValue4a.length>0 && vm.trlValue4b.length>0 && vm.trlValue4c.length>0 &&
                vm.trlValue5a.length>0 && vm.trlValue5b.length>0 && vm.trlValue5c.length>0 &&
                vm.trlValue6a.length>0 && vm.trlValue6b.length>0 && vm.trlValue6c.length>0 &&
                vm.trlValue7a.length>0 && vm.trlValue7b.length>0 && vm.trlValue7c.length>0 &&
                vm.trlValue8a.length>0 && vm.trlValue8b.length>0 && vm.trlValue8c.length>0 &&
                vm.trlValue9a.length>0 && vm.trlValue9b.length>0 && vm.trlValue9c.length>0;
        }
        function getTrlValueStr() {
            var split = ')!@#(';
            return '1,a,' + vm.trlValue1a + split + '1,b,' + vm.trlValue1b + split + '1,c,' + vm.trlValue1c + split +
                '2,a,' + vm.trlValue2a + split + '2,b,' + vm.trlValue2b + split + '2,c,' + vm.trlValue2c + split +
                '3,a,' + vm.trlValue3a + split + '3,b,' + vm.trlValue3b + split + '3,c,' + vm.trlValue3c + split +
                '4,a,' + vm.trlValue4a + split + '4,b,' + vm.trlValue4b + split + '4,c,' + vm.trlValue4c + split +
                '5,a,' + vm.trlValue5a + split + '5,b,' + vm.trlValue5b + split + '5,c,' + vm.trlValue5c + split +
                '6,a,' + vm.trlValue6a + split + '6,b,' + vm.trlValue6b + split + '6,c,' + vm.trlValue6c + split +
                '7,a,' + vm.trlValue7a + split + '7,b,' + vm.trlValue7b + split + '7,c,' + vm.trlValue7c + split +
                '8,a,' + vm.trlValue8a + split + '8,b,' + vm.trlValue8b + split + '8,c,' + vm.trlValue8c + split +
                '9,a,' + vm.trlValue9a + split + '9,b,' + vm.trlValue9b + split + '9,c,' + vm.trlValue9c;
        }
    }
})();
