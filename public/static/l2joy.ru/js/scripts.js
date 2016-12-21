$(document).ready(function() {
	$('ul.nav li a').append('<span class="hover-left"><span>', '<span class="hover-right"><span>');
});

$(document).ready(function() {
	$('#chaccpass form').addClass('formation-cabinet');
	$('#chaccmail form').addClass('formation-cabinet');
	$('#sexsid, #sexchar, #namesid, #charname, #changersid, #changerchar').addClass('frctable');
	$('.chbutton').addClass('regbutton');
	$('#chbutton').addClass('regbutton');
	$('.button').addClass('regbutton');
	$('.formation-cabinet h4').addClass('frctitle');
	$('#formation h3').addClass('frctitle');
	$('#sexchar input[type="radio"]').addClass('frcradio');
});

$(document).ready(function() {
	//Default Action
	$(".tab_content").hide(); //Hide all content
	$("ul.tabs li:first").addClass("active").show(); //Activate first tab
	$(".tab_content:first").show(); //Show first tab content
	//On Click Event
	$("ul.tabs li").click(function() {
		$("ul.tabs li").removeClass("active"); //Remove any "active" class
		$(this).addClass("active"); //Add "active" class to selected tab
		$(".tab_content").hide(); //Hide all tab content
		var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
		$(activeTab).fadeIn(); //Fade in the active content
		return false;
	});
});

$(document).ready(function() {
	//Default Action
	$(".tab_content1").hide(); //Hide all content
	$("ul.tabs1 li:first").addClass("active").show(); //Activate first tab
	$(".tab_content1:first").show(); //Show first tab content
	//On Click Event
	$("ul.tabs1 li").click(function() {
		$("ul.tabs1 li").removeClass("active"); //Remove any "active" class
		$(this).addClass("active"); //Add "active" class to selected tab
		$(".tab_content1").hide(); //Hide all tab content
		var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
		$(activeTab).fadeIn(); //Fade in the active content
		return false;
	});
});

$(document).ready(function() {
	//Default Action
	$(".tab_content-server").hide(); //Hide all content
	$("ul.tabs-server li:first").addClass("active").show(); //Activate first tab
	$(".tab_content-server:first").show(); //Show first tab content
	//On Click Event
	$("ul.tabs-server li").click(function() {
		$("ul.tabs-server li").removeClass("active"); //Remove any "active" class
		$(this).addClass("active"); //Add "active" class to selected tab
		$(".tab_content-server").hide(); //Hide all tab content
		var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
		$(activeTab).fadeIn(); //Fade in the active content
		return false;
	});
});

(function($){
            $(window).load(function(){
                
                /* all available option parameters with their default values */
                $("section#section-wrapper").mCustomScrollbar({
                    setWidth:false,
                    setHeight:false,
                    setTop:0,
                    setLeft:0,
                    axis:"y",
                    scrollbarPosition:"inside",
                    scrollInertia:500,
                    autoDraggerLength:true,
                    autoHideScrollbar:false,
                    autoExpandScrollbar:false,
                    alwaysShowScrollbar:0,
                    snapAmount:null,
                    snapOffset:0,
                    mouseWheel:{
                        enable:true,
                        scrollAmount:"auto",
                        axis:"y",
                        preventDefault:false,
                        deltaFactor:"auto",
                        normalizeDelta:false,
                        invert:false,
                        disableOver:["select","option","keygen","datalist","textarea"]
                    },
                    scrollButtons:{
                        enable:false,
                        scrollType:"stepless",
                        scrollAmount:"auto"
                    },
                    keyboard:{
                        enable:true,
                        scrollType:"stepless",
                        scrollAmount:"auto"
                    },
                    contentTouchScroll:25,
                    advanced:{
                        autoExpandHorizontalScroll:false,
                        autoScrollOnFocus:"input,textarea,select,button,datalist,keygen,a[tabindex],area,object,[contenteditable='true']",
                        updateOnContentResize:true,
                        updateOnImageLoad:true,
                        updateOnSelectorChange:false,
                        releaseDraggableSelectors:false
                    },
                    theme:"light",
                    callbacks:{
                        onInit:false,
                        onScrollStart:false,
                        onScroll:false,
                        onTotalScroll:false,
                        onTotalScrollBack:false,
                        whileScrolling:false,
                        onTotalScrollOffset:0,
                        onTotalScrollBackOffset:0,
                        alwaysTriggerOffsets:true,
                        onOverflowY:false,
                        onOverflowX:false,
                        onOverflowYNone:false,
                        onOverflowXNone:false
                    },
                    live:false,
                    liveSelector:null
                });
                
            });
})(jQuery);