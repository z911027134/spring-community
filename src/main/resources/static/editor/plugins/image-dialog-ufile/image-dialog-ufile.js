/*!
 * Link dialog plugin for Editor.md
 *
 * @file        link-dialog.js
 * @author      pandao
 * @version     1.2.0
 * @updateTime  2015-03-07
 * {@link       https://github.com/pandao/editor.md}
 * @license     MIT
 */
(function() {

	var factory = function (exports) {
		//var $ = jQuery;           // if using module loader(Require.js/Sea.js).
		var pluginName   = "image-dialog-ufile";
		exports.fn.imageDialogUfile = function() {

			var _this       = this;
			var cm          = this.cm;
			var lang        = this.lang;
			var editor      = this.editor;
			var settings    = this.settings;
			var cursor      = cm.getCursor();
			var selection   = cm.getSelection();
			var imageLang   = lang.dialog.image;
			var classPrefix = this.classPrefix;
			var dialogName  = classPrefix + pluginName, dialog;

			// 存储空间名称。
			var bucketName = "blogxxxxblog";
			// 存储空间域名URL地址。
			var bucketUrl = "http://blogxxxxblog.cn-gd.ufileos.com";
			// 计算token的地址。既可以在这里配置，也可以在SDK中全局配置。
			var tokenServerUrl = "/ufileToken";
			// 实例化UCloudUFile
			var ufile =  new UCloudUFile(bucketName, bucketUrl, "", "", tokenServerUrl, "");

			cm.focus();

			var loading = function(show) {
				var _loading = dialog.find("." + classPrefix + "dialog-mask");
				_loading[(show) ? "show" : "hide"]();
			};
			if (editor.find("." + dialogName).length < 1) {
				var guid   = (new Date).getTime();
				var action = settings.imageUploadURL + (settings.imageUploadURL.indexOf("?") >= 0 ? "&" : "?") + "guid=" + guid;

				if (settings.crossDomainUpload)
				{
					action += "&callback=" + settings.uploadCallbackURL + "&dialog_id=editormd-image-dialog-" + guid;
				}

				var dialogContent = ( (settings.imageUpload) ? "<form id=\"qiniuUploadForm\" method=\"post\" enctype=\"multipart/form-data\" class=\"" + classPrefix + "form\" onsubmit=\"return false;\">" : "<div class=\"" + classPrefix + "form\">" ) +
					"<label>" + imageLang.url + "</label>" +
					"<input type=\"text\" data-url />" + (function(){
						return (settings.imageUpload) ? "<div class=\"" + classPrefix + "file-input\">" +
							"<input type=\"file\" name=\"file\" accept=\"image/*\" />"            +
							"<input type=\"submit\" value=\"上传图片\" click=\"alert('dd')\" />" +
							"</div>" : "";
					})() + "<br/>" +
					"<label>" + imageLang.alt + "</label>" +
					"<input type=\"text\" value=\"" + selection + "\" data-alt />" +
					"<br/>" +
					"<label>" + imageLang.link + "</label>" +
					"<input type=\"text\" value=\"https://\" data-link />" +
					"<br/>" +
					( (settings.imageUpload) ? "</form>" : "</div>");

				dialog = this.createDialog({
					title      : imageLang.title,
					width      : (settings.imageUpload) ? 465 : 380,
					height     : 254,
					name       : dialogName,
					content    : dialogContent,
					mask       : settings.dialogShowMask,
					drag       : settings.dialogDraggable,
					lockScreen : settings.dialogLockScreen,
					maskStyle  : {
						opacity         : settings.dialogMaskOpacity,
						backgroundColor : settings.dialogMaskBgColor
					},
					buttons : {
						enter : [lang.buttons.enter, function() {
							var url  = this.find("[data-url]").val();
							var alt  = this.find("[data-alt]").val();
							var link = this.find("[data-link]").val();
							if (url === "") {
								alert(imageLang.imageURLEmpty);
								return false;
							}

							var altAttr = (alt !== "") ? " \"" + alt + "\"" : "";

							if (link === "" || link === "https://")
							{
								cm.replaceSelection("![" + alt + "](" + url + altAttr + ")");
							}
							else
							{
								cm.replaceSelection("[![" + alt + "](" + url + altAttr + ")](" + link + altAttr + ")");
							}

							if (alt === "") {
								cm.setCursor(cursor.line, cursor.ch + 2);
							}

							this.hide().lockScreen(false).hideMask();

							return false;
						}],

						cancel : [lang.buttons.cancel, function() {
							this.hide().lockScreen(false).hideMask();

							return false;
						}]
					}
				});

				dialog.attr("id", classPrefix + "image-dialog-" + guid);

				if (!settings.imageUpload) {
					return ;
				}

				var fileInput  = dialog.find("[name=\"file\"]");

				var submitHandler = function() {
					// 上传文件
					var file = fileInput[0].files[0];
					ufile.getContentMd5(file, function(md5) {
						var suffix = file.name.split(".");
						var fileRename = "";
						//文件名为md5拼接文件的类型
						if (suffix.length > 1) {
							fileRename = md5 + '.' + suffix[suffix.length-1];
						} else{
							fileRename = md5 + '.' + suffix[0];
						}
						var data = {
							file: file,
							fileRename: fileRename
						};
						var errorCallBack = function(res){
							console.log(res);
						};
						var successCallBack = function(res) {
							dialog.find("[data-url]").val(bucketUrl + '/' + fileRename);
							dialog.find("button.editormd-enter-btn").trigger("click");
						};
						var progressCallBack = function(res) {
							console.log("上传进度：" + (res * 100) + "%");
						};
						ufile.uploadFile(data, successCallBack, errorCallBack, progressCallBack);
					});
				};

				dialog.find("[type=\"submit\"]").bind("click", submitHandler);

				fileInput.bind("change", function() {
					var fileName  = fileInput.val();
					var isImage   = new RegExp("(\\.(" + settings.imageFormats.join("|") + "))$"); // /(\.(webp|jpg|jpeg|gif|bmp|png))$/

					if (fileName === "")
					{
						alert(imageLang.uploadFileEmpty);
						return false;
					}

					if (!isImage.test(fileName)){
						alert(imageLang.formatNotAllowed + settings.imageFormats.join(", "));
						return false;
					}

					dialog.find("[type=\"submit\"]").trigger("click");
				});
			}

			dialog = editor.find("." + dialogName);
			dialog.find("[type=\"text\"]").val("");
			dialog.find("[type=\"file\"]").val("");
			dialog.find("[data-link]").val("https://");

			this.dialogShowMask(dialog);
			this.dialogLockScreen();
			dialog.show();
		};
	};

	// CommonJS/Node.js
	if (typeof require === "function" && typeof exports === "object" && typeof module === "object")
	{
		module.exports = factory;
	}
	else if (typeof define === "function")  // AMD/CMD/Sea.js
	{
		if (define.amd) { // for Require.js

			define(["editormd"], function(editormd) {
				factory(editormd);
			});

		} else { // for Sea.js
			define(function(require) {
				var editormd = require("./../../editormd");
				factory(editormd);
			});
		}
	}
	else
	{
		factory(window.editormd);
	}

})();
