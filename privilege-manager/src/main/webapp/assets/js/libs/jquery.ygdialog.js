(function ($) {
    ygDialog = function (opts) {
        var win;
        opts = opts || {};
        var target;
        var winOpts = $.extend({},
            {
                isFrame: false,
                locate: 'document',
                data: undefined,
                width: 'auto',
                height: 'auto',
                cache: false,
                maxed:false,
                autoDestroy: true,
                minimizable: false,
                maximizable: false,
                collapsible: false,
                resizable: true,
                modal: true,
                enableSaveButton: true,
                enableCloseButton: true,
                saveButtonText: '保存',
                saveButtonIconCls: 'icon-save',
                closeButtonText: '取消',
                closeButtonIconCls: 'icon-cancel',
                closed: false,
                loadMsg: $.fn.datagrid.defaults.loadMsg,
                showMask: true,
                iconCls:'icon-standard-application-form',
                onSave: null
            },
            opts);

        function getTop(w, options) {
            var _doc;
            try {
                _doc = w['top'].document;
                _doc.getElementsByTagName;
            } catch (e) {
                return w;
            }

            if (options.locate == 'document' || _doc.getElementsByTagName('frameset').length > 0) {
                return w;
            }

            return w['top'];
        }

        function setWindowSize(w, options) {
            var _top = getTop(w, options);
            var wHeight = $(_top).height(),
                wWidth = $(_top).width();
            if (options.locate == 'top' || options.locate == 'document') {
                if (options.height == 'auto') {
                    options.height = wHeight * 0.8
                }

                if (options.width == 'auto') {
                    options.width = wWidth * 0.8
                }
            } else {
                var locate = /^#/.test(options.locate) ? options.locate : '#' + options.locate;
                if (options.height == 'auto') {
                    options.height = $(locate).height() * 0.8
                }

                if (options.width == 'auto') {
                    options.width = $(locate).width() * 0.8
                }
            }
            if(options.maxed){
                options.height=wHeight*0.9;
                options.width=wWidth*0.9;
            }
        }

        var iframe = null;
        var buttons = [];
        if (winOpts.isFrame && !winOpts.target) {
            iframe = $('<iframe>').attr('height', '100%').attr('width', '100%').attr('marginheight', 0).attr('marginwidth', 0).attr('frameborder', 0);
            iframe.css({
                'visibility': 'hidden'
            });
            iframe.attr('src', winOpts.href);
            delete winOpts.content;
        }

        var selfRefrence = {
            openWin: function () {
                return iframe[0].contentWindow;
            },
            getData: function (name) {
                return winOpts.data ? winOpts.data[name] : null;
            },
            close: function () {
                target.panel('close');
            }
        };

        var _top = getTop(window, winOpts);

        var warpHandler = function (handler) {
            if (typeof handler == 'function') {
                return function () {
                    handler(selfRefrence);
                };
            }

            if (typeof handler == 'function' && winOpts.isFrame) {
                return function () {
                    iframe[0].contentWindow.handler(selfRefrence);
                };
            }

            if (typeof handler == 'string' && winOpts.isFrame) {
                return function () {
                    iframe[0].contentWindow.eval(handler)(selfRefrence);
                   // iframe[0].contentWindow[handler](selfRefrence);
                }
            }

            if (typeof handler == 'string') {
                return function () {
                    eval(handler)(selfRefrence);
                   //eval(_top[handler])(selfRefrence);
                }
            }
        }

        setWindowSize(window, winOpts);

        //包装toolbar中各对象的handler
        if (winOpts.toolbar && $.isArray(winOpts.toolbar)) {
            alert(1);
            $.each(winOpts.toolbar,
                function (i, button) {
                    button.handler = warpHandler(button.handler);
                });
        }

        //包装buttons中各对象的handler
        if (winOpts.buttons && $.isArray(winOpts.buttons)) {
            $.each(winOpts.buttons,
                function (i, button) {
                    button.handler = warpHandler(button.handler);
                });
        }

        var _onClose = winOpts.onClose;
        winOpts.onClose = function () {
            if (winOpts.target) {
                $('.validatebox-invalid', winOpts.target).removeClass('validatebox-invalid');
            }

            if ($.isFunction(_onClose)) {
                _onClose.apply(this, arguments);
            }
            if (winOpts.autoDestroy && !winOpts.target) {
                $(this).dialog("destroy");
            }
        };

        //兼容 检查是否有取消按钮
        var checkButtons = function (t) {
            var r = false;
            if (winOpts.buttons) {
                for (var i = 0; i < winOpts.buttons.length; i++) {
                    if (winOpts.buttons[i].text == t) {
                        r = true;
                        break;
                    }
                }
            }
            return r;
        }


        if (winOpts.enableSaveButton == true && winOpts.onSave) {
            var btnSave = {
                text: winOpts.saveButtonText,
                iconCls: winOpts.saveButtonIconCls,
                handler: function (dia) {
                    if (typeof winOpts.onSave == "string") {
                        return eval(winOpts.onSave)(selfRefrence);
                    }else{
                        return winOpts.onSave(selfRefrence);
                    }
                }
            };
            buttons.push(btnSave);
        }


        if (winOpts.enableCloseButton == true && !checkButtons(winOpts.closeButtonText)) {
            var btnClose = {
                text: winOpts.closeButtonText,
                iconCls: winOpts.closeButtonIconCls,
                handler: function (dia) {
                    dia.dialog("close");
                }
            };
            buttons.push(btnClose);
        }

        if (!$.util.likeArray(winOpts.buttons) || $.util.isString(winOpts.buttons)) {
            winOpts.buttons = [];
        }
        $.array.merge(winOpts.buttons, buttons);

        $.each(winOpts.buttons,
            function () {
                var handler = this.handler;
                if ($.isFunction(handler)) {
                    this.handler = function () {
                        handler.call(target, target);
                    };
                }
            });
        if (!winOpts.buttons.length) {
            winOpts.buttons = null;
        }

        /*
         if ($.isArray(winOpts.buttons)&&winOpts.buttons.length>0) {
         $.each(winOpts.buttons,
         function(i, button) {
         button.handler = warpHandler(button.handler);
         });
         }
         */

        var onLoadCallback = winOpts.onLoad;
        winOpts.onLoad = function () {
            onLoadCallback && onLoadCallback.call(this, selfRefrence, _top);
        }

        if (winOpts.locate == 'top' || winOpts.locate == 'document') {
            if (winOpts.isFrame && iframe && !winOpts.target) {
                winOpts.href = '';
                if (winOpts.showMask) {
                    winOpts.onBeforeOpen = function () {
                        var body = $(this);
                        $.mask({
                            target: body
                        });
                    }
                }
                target = _top.$('<div>').css({
                    'overflow': 'hidden'
                }).append(iframe).dialog(winOpts);
                function iframeLoaded() {
                    onLoadCallback && onLoadCallback.call(iframe, selfRefrence, iframe[0].contentWindow);
                    _top.$('.dialog-button').show();
                    target.panel('body').children("div.datagrid-mask-msg").remove();
                    target.panel('body').children("div.datagrid-mask").remove();
                    iframe.css({
                        'visibility': 'visible'
                    });
                }

                iframe.bind('load',
                    function () {
                        iframeLoaded();
                    });

            } else if (winOpts.target) {
                target = winOpts.target;
                target.dialog(winOpts);

                var _w=target.width()-20;
                target.find('.fieldset').each(function(){
                    try{
                        $(this).panel('resize',{width:_w});
                    }catch(e){};
                });

                //.panel('resize',{width:550});
                _top.$('.dialog-button').show();
                target.panel('body').children("div.datagrid-mask-msg").remove();
                target.panel('body').children("div.datagrid-mask").remove();
            } else {
                target = _top.$('<div>').dialog(winOpts);
                setTimeout(function () {
                        _top.$('.dialog-button').show();
                    },
                    2);
            }
        } else {
            var locate = /^#/.test(winOpts.locate) ? winOpts.locate : '#' + winOpts.locate;
            target = $('<div>').appendTo(locate).dialog($.extend({},
                winOpts, {
                    inline: true
                }));

        }

        return target;

    }
})(jQuery);