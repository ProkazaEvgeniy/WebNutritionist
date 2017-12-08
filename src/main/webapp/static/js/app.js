var webnutritionist = {
		
	 init : function() {
		 $('.buy-btn').click(webnutritionist.showAddProductPopup);
		 $('#changeCountVegetables .count').change(webnutritionist.checkNormaVegetables);
			
	},
	convertButtonToLoaderVegetables : function(btn, btnClass) {
		btn.removeClass(btnClass);
		btn.removeClass('btn');
		btn.addClass('load-indicator');
		var text = btn.text();
		btn.text('');
		btn.attr('data-btn-text', text);
		btn.attr('type', 'button');
	},
	convertLoaderToButtonVegetables : function(btn, btnClass) {
		btn.removeClass('load-indicator');
		btn.addClass('btn');
		btn.addClass(btnClass);
		btn.attr('type', 'submit');
		btn.text('Сохранить');
	},
	checkNormaVegetables : function() {
		var norma = parseInt($('#changeCountVegetables .norma').text());
		var cost = parseInt($('#changeCountVegetables #cost').text());
		var btn = $('#loadBtn');
		if(cost > norma){
			$('#changeCountVegetables #cost').attr({class:"alert alert-danger"});
			/*var textToPast = document.getElementById('costLimit');
			pastText = 'Превышен лимит';
			textToPast.innerHTML = pastText;
			$('#changeCountVegetables #costLimit').attr({class:"alert alert-danger"});*/
			webnutritionist.convertButtonToLoaderVegetables(btn, 'btn-primary');
		} else {
			$('#changeCountVegetables #cost').removeClass('alert alert-danger');
			/*var textToPast = $('#changeCountVegetables #costLimit').html();
			textToPast = textToPast.replace('Превышен лимит','');
			$('#changeCountVegetables #costLimit').html(textToPast);
			$('#changeCountVegetables #costLimit').removeClass('alert alert-danger');*/
			webnutritionist.convertLoaderToButtonVegetables(btn, 'btn-primary');
		}
		
	},
	
		showAddProductPopup : function() {
			var idProduct = $(this).attr('data-id-product');
			var product = $('#product' + idProduct);
			$('#addProductPopup').attr('data-id-product', idProduct);
			$('#addProductPopup .product-image').attr('src',
					product.find('.thumbnail img').attr('src'));
			$('#addProductPopup .name').text(product.find('.name').text());
			var price = product.find('.price').text();
			$('#addProductPopup .price').text(price);
			$('#addProductPopup .category').text(product.find('.category').text());
			$('#addProductPopup .producer').text(product.find('.producer').text());
			$('#addProductPopup .count').val(1);
			$('#addProductPopup .cost').text(price);
			$('#addToCart').removeClass('hidden');
			$('#addToCartIndicator').addClass('hidden');
			$('#addProductPopup').modal({
				show : true
			});
		},

	createDatePicker : function() {
		/* http://bootstrap-datepicker.readthedocs.org/en/latest/options.html */
		$('.datepicker').datepicker({
			autoclose : true,
			clearBtn : true
		});
	},
	createPhotoUploader : function() {
		// https://github.com/kartik-v/bootstrap-fileinput
		$("#profilePhoto").fileinput({
			uploadAsync : false,
			showUpload : false,
			allowedFileExtensions : [ 'jpg', 'png' ],
			maxFileCount : 1
		});
		$('#profilePhoto').on('fileclear', function() {
			$('#currentPhoto').css('display', 'block');
		});
		$('#profilePhoto').on('fileloaded', function() {
			$('#currentPhoto').css('display', 'none');
		});
	},

	showErrorDialog : function(message) {
		alert(message);
	},
	post : function(path, params) {
		var form = document.createElement("form");
		form.setAttribute("method", 'post');
		form.setAttribute("action", path);
		for ( var key in params) {
			if (params.hasOwnProperty(key)) {
				var value = params[key];
				if (value != undefined) {
					var hiddenField = document.createElement("input");
					hiddenField.setAttribute("type", "hidden");
					hiddenField.setAttribute("name", key);
					hiddenField.setAttribute("value", params[key]);
					form.appendChild(hiddenField);
				}
			}
		}
		document.body.appendChild(form);
		form.submit();
	},

	logout : function(csrfToken) {
		webnutritionist.post('/logout', {
			_csrf : csrfToken
		});
	},

	moreProfiles : function(searchQuery) {
		var page = parseInt($('#profileContainer').attr('data-profile-number')) + 1;
		var total = parseInt($('#profileContainer').attr('data-profile-total'));
		if (page >= total) {
			return;
		}
		var url = '/fragment/more?page=' + page;
		if (searchQuery != undefined && searchQuery.trim() != '') {
			url += '&query=' + searchQuery;
		}

		$('#loadMoreContainer').css('display', 'none');
		$('#loadMoreIndicator').css('display', 'block');
		$.ajax({
			url : url,
			success : function(data) {
				$('#loadMoreIndicator').css('display', 'none');
				$('#profileContainer').append(data);
				$('#profileContainer').attr('data-profile-number', page);
				if (page >= total - 1) {
					$('#loadMoreIndicator').remove();
					$('#loadMoreContainer').remove();
				} else {
					$('#loadMoreContainer').css('display', 'block');
				}
			},
			error : function(data) {
				webnutritionist.showErrorDialog(messages.errorAjax);
			}
		});
	},

	moreProducts : function() {
		var page = parseInt($('#productContainer').attr('data-product-number')) + 1;
		var total = parseInt($('#productContainer').attr('data-product-total'));
		if (page >= total) {
			return;
		}
		var url = '/fragment/more' + location.pathname + '?page=' + page;

		$('#loadMoreContainer').css('display', 'none');
		$('#loadMoreIndicator').css('display', 'block');
		$.ajax({
			url : url,
			success : function(data) {
				$('#loadMoreIndicator').css('display', 'none');
				$('#productContainer').append(data);
				$('#productContainer').attr('data-product-number', page);
				if (page >= total - 1) {
					$('#loadMoreIndicator').remove();
					$('#loadMoreContainer').remove();
				} else {
					$('#loadMoreContainer').css('display', 'block');
				}
				initBuyBtn();
			},
			error : function(data) {
				webnutritionist.showErrorDialog(messages.errorAjax);
			}
		});
	},

	ui : {
		// http://handlebarsjs.com/
		template : null,

		getTemplate : function() {
			if (webnutritionist.ui.template == null) {
				var source = $("#ui-block-template").html();
				webnutritionist.ui.template = Handlebars.compile(source);
			}
			return webnutritionist.ui.template;
		},

		addBlock : function() {
			var template = webnutritionist.ui.getTemplate();
			var container = $('#ui-block-container');
			var blockIndex = container.find('.ui-item').length;
			var context = {
				blockIndex : blockIndex
			};
			container.append(template(context));

			container.find('input.quantity-slider').slider();
			$('#changeCountVegetables .count').change(function(){
				var arrData = [0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100];
				var arrIndex = $('.quantity-slider')
									.map(function(){
										return $(this).val() | 0;
									}).toArray();
				
				var total = 0;
				for(var i=0; i < arrIndex.length; i++){
					total += arrData[arrIndex[i]];
				}
				
				$('#changeCountVegetables #cost').text(total);
				$('#countVegetables').attr('value', total);
				webnutritionist.checkNormaVegetables();
			});
			
		},

		updateSelect : function(thisObj) {
			if (thisObj.val() == '') {
				var idSelectRef = thisObj.attr('data-ref-select');
				$('#' + idSelectRef).val('');
			}
		}
	},

};

$(function() {
	webnutritionist.init();
});

$(function() {
	var init = function() {
		initBuyBtn();
		$('#addToCart').click(addProductToCart);
		$('#addProductPopup .count').change(calculateCost);
		$('#changeCountVegetables .count').change(calculateVegetables);
		$('#changeCountVegetables .count').change(checkNormaVegetables);
		$('#loadMore').click(loadMoreProducts);
		initSearchForm();
		$('#goSearch').click(goSearch);
		$('.remove-product').click(removeProductFromCart);

		$('#loadMore1').click(moreProducts);
		$('.post-request').click(function() {
			postRequest($(this).attr('data-url'));
		});
		$('#loadMoreMyOrders').click(loadMoreMyOrders);
		initSearchForm();
	};
	var loadMoreMyOrders = function() {
		var btn = $('#loadMoreMyOrders');
		convertButtonToLoader(btn, 'btn-success');
		var pageNumber = parseInt($('#myOrders').attr('data-page-number'));
		var url = '/fragment/more/my-orders?page=' + (pageNumber + 1);
		$
				.ajax({
					url : url,
					success : function(html) {
						$('#myOrders tbody').append(html);
						pageNumber++;
						var pageCount = parseInt($('#myOrders').attr(
								'data-page-count'));
						$('#myOrders').attr('data-page-number', pageNumber);
						if (pageNumber < pageCount) {
							convertLoaderToButton(btn, 'btn-success',
									loadMoreMyOrders);
						} else {
							btn.remove();
						}
					},
					error : function(xhr) {
						convertLoaderToButton(btn, 'btn-success',
								loadMoreMyOrders);
						if (xhr.status == 401) {
							window.location.href = '/sign-in';
						} else {
							alert('Error55');
						}
					}
				});
	};
	var postRequest = function(url) {
		var form = '<form id="postRequestForm" action="' + url
				+ '" method="post"></form>';
		$('body').append(form);
		$('#postRequestForm').submit();
	};
	var moreProducts = function() {
		var page = parseInt($('#productContainer').attr('data-product-number')) + 1;
		var total = parseInt($('#productContainer').attr('data-product-total'));
		if (page >= total) {
			return;
		}
		var url = '/fragment/more' + location.pathname + '?page=' + page;

		$('#loadMoreContainer').css('display', 'none');
		$('#loadMoreIndicator').css('display', 'block');
		$.ajax({
			url : url,
			success : function(data) {
				$('#loadMoreIndicator').css('display', 'none');
				$('#productContainer').append(data);
				$('#productContainer').attr('data-product-number', page);
				if (page >= total - 1) {
					$('#loadMoreIndicator').remove();
					$('#loadMoreContainer').remove();
				} else {
					$('#loadMoreContainer').css('display', 'block');
				}
				initBuyBtn();
			},
			error : function(data) {
				webnutritionist.showErrorDialog(messages.errorAjax);
			}
		});
	};
	var showAddProductPopup = function() {
		var idProduct = $(this).attr('data-id-product');
		var product = $('#product' + idProduct);
		$('#addProductPopup').attr('data-id-product', idProduct);
		$('#addProductPopup .product-image').attr('src',
				product.find('.thumbnail img').attr('src'));
		$('#addProductPopup .name').text(product.find('.name').text());
		var price = product.find('.price').text();
		$('#addProductPopup .price').text(price);
		$('#addProductPopup .category').text(product.find('.category').text());
		$('#addProductPopup .producer').text(product.find('.producer').text());
		$('#addProductPopup .count').val(1);
		$('#addProductPopup .cost').text(price);
		$('#addToCart').removeClass('hidden');
		$('#addToCartIndicator').addClass('hidden');
		$('#addProductPopup').modal({
			show : true
		});
	};
	var initBuyBtn = function() {
		$('.buy-btn').click(showAddProductPopup);
	};
	var addProductToCart = function() {
		var idProduct = $('#addProductPopup').attr('data-id-product');
		var count = $('#addProductPopup .count').val();
		var btn = $('#addToCart');
		convertButtonToLoader(btn, 'btn-primary');
		$.ajax({
			url : '/add-product',
			method : 'POST',
			data : {
				id : idProduct,
				count : count
			},
			success : function(resp) {
				var data = JSON.parse(resp);
				$('#currentShoppingCart .total-count').text(data.totalCount);
				$('#currentShoppingCart .total-cost').text(data.totalCost);
				$('#currentShoppingCart').removeClass('hidden');
				convertLoaderToButton(btn, 'btn-primary', addProductToCart);
				$('#addProductPopup').modal('hide');
			},
			error : function(xhr) {
				convertLoaderToButton(btn, 'btn-primary', addProductToCart);
				if (xhr.status == 400) {
					alert(xhr.responseJSON.message);
				} else {
					alert('Error');
				}
			}
		});
	};
	var calculateCost = function() {
		var priceStr = $('#addProductPopup .price').text();
		var price = parseFloat(priceStr.replace('грн', ' '));
		var count = parseInt($('#addProductPopup .count').val());
		var min = parseInt($('#addProductPopup .count').attr('min'));
		var max = parseInt($('#addProductPopup .count').attr('max'));
		if (count >= min && count <= max) {
			var cost = price * count;
			$('#addProductPopup .cost').text(cost + ' грн');
		} else {
			$('#addProductPopup .count').val(1);
			$('#addProductPopup .cost').text(priceStr);
		}
	};
	var calculateVegetables = function() {
		var arrData = [0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100];
		var arrIndex = $('.quantity-slider')
							.map(function(){
								return $(this).val() | 0;
							}).toArray();
		
		var total = 0;
		for(var i=0; i < arrIndex.length; i++){
			total += arrData[arrIndex[i]];
		}
		
		$('#changeCountVegetables #cost').text(total);
		$('#countVegetables').attr('value', total);
	
	};
	var checkNormaVegetables = function() {
		var norma = parseInt($('#changeCountVegetables .norma').text());
		var cost = parseInt($('#changeCountVegetables #cost').text());
		var btn = $('#loadBtn');
		if(cost > norma){
			$('#changeCountVegetables #cost').attr({class:"alert alert-danger"});
			convertButtonToLoaderVegetables(btn, 'btn-primary');
		} else {
			$('#changeCountVegetables #cost').removeClass('alert alert-danger');
			convertLoaderToButtonVegetables(btn, 'btn-primary');
		}
		
	};
	var convertButtonToLoaderVegetables = function(btn, btnClass) {
		btn.removeClass(btnClass);
		btn.removeClass('btn');
		btn.addClass('load-indicator');
		var text = btn.text();
		btn.text('');
		btn.attr('data-btn-text', text);
		btn.attr('type', 'button');
	};
	var convertLoaderToButtonVegetables = function(btn, btnClass) {
		btn.removeClass('load-indicator');
		btn.addClass('btn');
		btn.addClass(btnClass);
		btn.attr('type', 'submit');
		btn.text('Сохранить');
	};
	var convertButtonToLoader = function(btn, btnClass) {
		btn.removeClass(btnClass);
		btn.removeClass('btn');
		btn.addClass('load-indicator');
		var text = btn.text();
		btn.text('');
		btn.attr('data-btn-text', text);
		btn.off('click');
	};
	var convertLoaderToButton = function(btn, btnClass, actionClick) {
		btn.removeClass('load-indicator');
		btn.addClass('btn');
		btn.addClass(btnClass);
		btn.text(btn.attr('data-btn-text'));
		btn.removeAttr('data-btn-text');
		btn.click(actionClick);
	};
	var loadMoreProducts = function() {
		var btn = $('#loadMore');
		convertButtonToLoader(btn, 'btn-success');
		var url = '/fragment/more' + location.pathname + '?'
				+ location.search.substring(1);
		$.ajax({
			url : url,
			success : function(html) {
				$('#productList .text-center').prepend(html);
				initBuyBtn();
				convertLoaderToButton(btn, 'btn-success', loadMoreProducts);
			},
			error : function(data) {
				convertLoaderToButton(btn, 'btn-success', loadMoreProducts);
				alert('Error');
			}
		});
	};

	var initSearchForm = function() {
		$('#allCategories').click(
				function() {
					$('.categories .search-option').prop('checked',
							$(this).is(':checked'));
				});
		$('.categories .search-option').click(function() {
			$('#allCategories').prop('checked', false);
		});
		$('#allProducers').click(
				function() {
					$('.producers .search-option').prop('checked',
							$(this).is(':checked'));
				});
		$('.producers .search-option').click(function() {
			$('#allProducers').prop('checked', false);
		});
	};
	var goSearch = function() {
		var isAllSelected = function(selector) {
			var unchecked = 0;
			$(selector).each(function(index, value) {
				if (!$(value).is(':checked')) {
					unchecked++;
				}
			});
			return unchecked === 0;
		};
		if (isAllSelected('.categories .search-option')) {
			$('.categories .search-option').prop('checked', false);
		}
		if (isAllSelected('.producers .search-option')) {
			$('.producers .search-option').prop('checked', false);
		}
		$('form.search').submit();
	};
	var confirm = function(msg, okFunction) {
		if (window.confirm(msg)) {
			okFunction();
		}
	};
	var removeProductFromCart = function() {
		var btn = $(this);
		confirm('Are you sure?', function() {
			executeRemoveProduct(btn);
		});
	};
	var refreshTotalCost = function() {
		var total = 0;
		$('#shoppingCart .item').each(
				function(index, value) {
					var count = parseInt($(value).find('.count').text());
					var price = parseFloat($(value).find('.price').text()
							.replace('$', ' '));
					var val = price * count;
					total = total + val;
				});
		$('#shoppingCart .total').text('$' + total);
	};
	var executeRemoveProduct = function(btn) {
		var idProduct = btn.attr('data-id-product');
		var count = btn.attr('data-count');
		convertButtonToLoader(btn, 'btn-danger');
		$
				.ajax({
					url : '/remove-product',
					method : 'POST',
					data : {
						id : idProduct,
						count : count
					},
					success : function(data) {
						if (data.totalCount == 0) {
							window.location.href = '/products';
						} else {
							var prevCount = parseInt($(
									'#product' + idProduct + ' .count').text());
							var remCount = parseInt(count);
							if (remCount >= prevCount) {
								$('#product' + idProduct).remove();
							} else {
								convertLoaderToButton(btn, 'btn-danger',
										removeProductFromCart);
								$('#product' + idProduct + ' .count').text(
										prevCount - remCount);
								if (prevCount - remCount == 1) {
									$('#product' + idProduct + ' a.remove-all')
											.remove();
								}
							}
							refreshTotalCost();
						}
					},
					error : function(data) {
						convertLoaderToButton(btn, 'btn-danger',
								removeProductFromCart);
						alert('Error');
					}
				});
	};
	/*var StickyElement = function(node){
		  var doc = $(document), 
		      fixed = false,
		      anchor = node.find('.sticky-anchor'),
		      content = node.find('.sticky-content');
		  
		  var onScroll = function(e){
		    var docTop = doc.scrollTop(),
		        anchorTop = anchor.offset().top;
		    
		    console.log('scroll', docTop, anchorTop);
		    if(docTop > anchorTop){
		      if(!fixed){
		        anchor.height(content.outerHeight());
		        content.addClass('fixed');        
		        fixed = true;
		      }
		    }  else   {
		      if(fixed){
		        anchor.height(0);
		        content.removeClass('fixed'); 
		        fixed = false;
		      }
		    }
		  };
		  
		  $(window).on('scroll', onScroll);
		};

		var demo = new StickyElement($('#sticky'));
	 */
	init();
});